#!/usr/bin/env python3
"""Build the Ronevis store (file.txt, pack zips, preview images) from catalog.json.

Format: docs/store-format.md.

    python3 tools/store/build_store.py --src <folder with the source files> \
        --out <web project>/public --base-url https://www.ronevis.com
"""
import argparse
import json
import re
import zipfile
from pathlib import Path

from PIL import Image, ImageDraw, ImageFont, features

# The prefix of a pack's file name picks the folder the app unzips it into.
NAME_PREFIX = {"font": "font_", "background": "back_", "sticker": "sticker_"}
COUNT_WORD = {"font": "فونت", "background": "پس‌زمینه", "sticker": "استیکر"}
BG, FG, ACCENT = "#1f1f1f", "#f4efe6", "#f2b23a"
SAMPLE = "عکس‌نوشته‌های خاص خودت رو بساز"


def fa_digits(n):
    return str(n).translate(str.maketrans("0123456789", "۰۱۲۳۴۵۶۷۸۹"))


def render(path, lines, size=(1000, 500)):
    """lines: [(text, font_file, px, colour)], drawn centred top to bottom."""
    img = Image.new("RGB", size, BG)
    draw = ImageDraw.Draw(img)
    fonts = [ImageFont.truetype(str(f), px, layout_engine=ImageFont.Layout.RAQM) for _, f, px, _ in lines]
    heights = [draw.textbbox((0, 0), t, font=f, direction="rtl")[3] for (t, *_), f in zip(lines, fonts)]
    y = (size[1] - sum(heights) - 30 * (len(lines) - 1)) / 2
    for (text, _, _, colour), font, h in zip(lines, fonts, heights):
        draw.text((size[0] / 2, y), text, font=font, fill=colour, anchor="ma", direction="rtl")
        y += h + 30
    img.save(path, optimize=True)


def build_pack(pack, ptype, src, out, base_url, pack_id):
    subcat = pack["subcat"]
    if not re.fullmatch(r"[A-Za-z0-9]+_[A-Za-z0-9]+", subcat):
        raise SystemExit(f"{subcat}: subcat must look like fa_Name (one underscore, letters and digits)")
    files = [src / f for f in pack["files"]]
    for f in files:
        if not f.is_file():
            raise SystemExit(f"{subcat}: missing source file {f}")

    name = NAME_PREFIX[ptype] + subcat + ".zip"
    folder = ".img/" if ptype == "background" else ""  # the editor reads backgrounds from .Backs/.img/
    with zipfile.ZipFile(out / "store/packs" / name, "w", zipfile.ZIP_DEFLATED) as z:
        if folder:
            z.writestr(folder, "")  # PackageInstall only creates folders that have their own zip entry
        for i, f in enumerate(files, 1):
            z.write(f, f"{folder}{subcat}_{i}{f.suffix.lower()}")
        if pack.get("license"):
            # Hyphen keeps it from matching the editor's "<subcat>_<n>" file filter.
            z.write(src / pack["license"], f"LICENSE-{subcat.replace('_', '-')}.txt")

    preview = f"store/previews/{subcat}.png"
    if ptype == "font":
        sample_font = files[min(len(files) - 1, len(files) // 2)]
        render(out / preview, [(pack["name_fa"], sample_font, 150, ACCENT), (SAMPLE, sample_font, 56, FG)])
    else:
        Image.open(files[0]).convert("RGB").resize((1000, 500)).save(out / preview)

    return {
        "id": str(pack_id),
        "type": ptype,
        "maincat": pack.get("maincat", ""),
        "subcat": subcat,
        "title": f"{fa_digits(len(files))} {COUNT_WORD[ptype]}",
        "name": name,
        "name_fa": pack["name_fa"],
        "image": f"{base_url}/{preview}",
        "url": f"{base_url}/store/packs/{name}",
    }


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--catalog", default=Path(__file__).with_name("catalog.json"), type=Path)
    ap.add_argument("--src", required=True, type=Path)
    ap.add_argument("--out", required=True, type=Path)
    ap.add_argument("--base-url", required=True)
    args = ap.parse_args()
    if not features.check("raqm"):
        raise SystemExit("Pillow needs libraqm for Persian text shaping (brew install libraqm)")

    base_url = args.base_url.rstrip("/")
    catalog = json.loads(args.catalog.read_text(encoding="utf-8"))
    for d in ("store/packs", "store/previews", "store/covers"):
        (args.out / d).mkdir(parents=True, exist_ok=True)

    seen, dlfile, pack_id = set(), [], 1
    for cat in catalog["categories"]:
        ptype = cat["type"]
        if ptype not in NAME_PREFIX:
            raise SystemExit(f"unknown type {ptype}; use font, background or sticker")
        packs = []
        for pack in cat["packs"]:
            if pack["subcat"] in seen:
                raise SystemExit(f"duplicate subcat {pack['subcat']}")
            seen.add(pack["subcat"])
            packs.append(build_pack(pack, ptype, args.src, args.out, base_url, pack_id))
            pack_id += 1
        cover = f"store/covers/{ptype}.png"
        render(args.out / cover, [(cat["title"], args.src / cat["cover_font"], 170, ACCENT)])
        dlfile.append({"title": cat["title"], "type": ptype, "cover": f"{base_url}/{cover}", "packs": packs})

    (args.out / "file.txt").write_text(json.dumps({"dlfile": dlfile}, ensure_ascii=False, indent=2), encoding="utf-8")
    print(f"{pack_id - 1} packs in {len(dlfile)} categories -> {args.out}")


if __name__ == "__main__":
    main()
