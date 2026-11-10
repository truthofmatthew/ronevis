#!/usr/bin/env python3
"""Build the Ronevis store (file.txt, pack zips, preview images) from catalog.json.

Format: docs/store-format.md.

    python3 tools/store/build_store.py --src <folder with the source files> \
        --out <web project>/public --base-url https://www.ronevis.com
"""
import argparse
import io
import json
import re
import zipfile
from pathlib import Path

from PIL import Image, ImageDraw, ImageFont, ImageOps, features

# The prefix of a pack's file name picks the folder the app unzips it into.
NAME_PREFIX = {"font": "font_", "background": "back_", "sticker": "sticker_"}
COUNT_WORD = {"font": "فونت", "background": "پس‌زمینه", "sticker": "استیکر"}
BG, FG, ACCENT = "#1f1f1f", "#f4efe6", "#f2b23a"
SAMPLE = "عکس‌نوشته‌های خاص خودت رو بساز"
MAX_SIDE = {"background": 1080, "sticker": 512}
PACK_LIMIT = 3 * 1024 * 1024


def fa_digits(n):
    return str(n).translate(str.maketrans("0123456789", "۰۱۲۳۴۵۶۷۸۹"))


def render(path, lines, size=(1000, 500)):
    """lines: [(text, font_file, px, colour)], drawn centred top to bottom."""
    img = Image.new("RGB", size, BG)
    draw = ImageDraw.Draw(img)
    scale = 1.0
    while True:  # shrink until it fits: Nastaliq is much taller than its pixel size
        fonts = [ImageFont.truetype(str(f), int(px * scale), layout_engine=ImageFont.Layout.RAQM) for _, f, px, _ in lines]
        boxes = [draw.textbbox((0, 0), t, font=f, anchor="ma", direction="rtl") for (t, *_), f in zip(lines, fonts)]
        total = sum(b[3] - b[1] for b in boxes) + 30 * (len(lines) - 1)
        if total <= size[1] * 0.9 and max(b[2] - b[0] for b in boxes) <= size[0] * 0.95:
            break
        scale *= 0.9
    y = (size[1] - total) / 2
    for (text, _, _, colour), font, box in zip(lines, fonts, boxes):
        draw.text((size[0] / 2, y - box[1]), text, font=font, fill=colour, anchor="ma", direction="rtl")
        y += box[3] - box[1] + 30
    img.save(path, optimize=True)


def pack_image(path, ptype):
    """Shrink to the type's max side and re-encode: backgrounds as JPEG, stickers as PNG with alpha.

    Returns (bytes, extension)."""
    img = ImageOps.exif_transpose(Image.open(path))
    img.thumbnail((MAX_SIDE[ptype], MAX_SIDE[ptype]), Image.LANCZOS)
    buf = io.BytesIO()
    if ptype == "background":
        img.convert("RGB").save(buf, "JPEG", quality=82, optimize=True, progressive=True)
        return buf.getvalue(), ".jpg"
    img.convert("RGBA").save(buf, "PNG", optimize=True)
    return buf.getvalue(), ".png"


def collage(path, images, ptype):
    """Store preview for image packs: a row of backgrounds, or stickers on the dark card."""
    img = Image.new("RGB", (1000, 500), BG)
    if ptype == "background":
        shown = images[:4]
        w = 1000 // len(shown)
        for i, data in enumerate(shown):
            img.paste(ImageOps.fit(Image.open(io.BytesIO(data)).convert("RGB"), (w, 500)), (i * w, 0))
    else:
        for i, data in enumerate(images[:8]):
            st = Image.open(io.BytesIO(data)).convert("RGBA")
            st.thumbnail((210, 210))
            x, y = 30 + (i % 4) * 240, 25 + (i // 4) * 240
            img.paste(st, (x + (210 - st.width) // 2, y + (210 - st.height) // 2), st)
    img.save(path, optimize=True)


def check_subcats(packs):
    """The editor's filter ("<subcat>_*\\d*", matched with find()) accepts any file name containing
    the subcat, so within one install folder no subcat may contain another (bg_sky would also
    pick up bg_skyline's files)."""
    folders = {}
    for ptype, pack in packs:
        folders.setdefault((ptype, pack.get("maincat", "")), []).append(pack["subcat"])
    for subcats in folders.values():
        for a in subcats:
            for b in subcats:
                if a != b and a in b:
                    raise SystemExit(f"subcat {a} is part of {b}; the editor would mix their files")


def build_pack(pack, ptype, src, out, base_url, pack_id):
    subcat = pack["subcat"]
    if not re.fullmatch(r"[A-Za-z0-9]+_[A-Za-z0-9]+", subcat):
        raise SystemExit(f"{subcat}: subcat must look like fa_Name (one underscore, letters and digits)")
    # A list keeps the given order; a string is a glob, taken in name order.
    files = sorted(src.glob(pack["files"])) if isinstance(pack["files"], str) else [src / f for f in pack["files"]]
    if not files:
        raise SystemExit(f"{subcat}: no files match {pack['files']}")
    for f in files:
        if not f.is_file():
            raise SystemExit(f"{subcat}: missing source file {f}")

    name = NAME_PREFIX[ptype] + subcat + ".zip"
    folder = ".img/" if ptype == "background" else ""  # the editor reads backgrounds from .Backs/.img/
    images = [pack_image(f, ptype) for f in files] if ptype in MAX_SIDE else []
    zip_path = out / "store/packs" / name
    with zipfile.ZipFile(zip_path, "w", zipfile.ZIP_DEFLATED) as z:
        if folder:
            z.writestr(folder, "")  # PackageInstall only creates folders that have their own zip entry
        for i, f in enumerate(files, 1):
            if images:
                data, ext = images[i - 1]
                z.writestr(f"{folder}{subcat}_{i}{ext}", data)
            else:
                z.write(f, f"{folder}{subcat}_{i}{f.suffix.lower()}")
        licenses = pack.get("license") or []
        if licenses:
            if isinstance(licenses, str):
                licenses = [licenses]
            text = "\n\n".join((src / lic).read_text(encoding="utf-8", errors="replace").strip() for lic in licenses)
            # Hyphen keeps it from matching the editor's "<subcat>_<n>" file filter.
            z.writestr(f"LICENSE-{subcat.replace('_', '-')}.txt", text + "\n")
    if zip_path.stat().st_size > PACK_LIMIT:
        print(f"warning: {name} is {zip_path.stat().st_size / 1e6:.1f} MB, over the 3 MB pack budget")

    preview = f"store/previews/{subcat}.{'jpg' if ptype == 'background' else 'png'}"
    if ptype == "font":
        sample_font = files[min(len(files) - 1, len(files) // 2)]
        sample = pack.get("sample", SAMPLE)
        title = pack.get("preview_title", pack["name_fa"])  # Latin-only fonts can't draw the Persian name
        render(out / preview, [(title, sample_font, 150, ACCENT), (sample, sample_font, 56, FG)])
    else:
        collage(out / preview, [data for data, _ in images], ptype)

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

    check_subcats([(cat["type"], p) for cat in catalog["categories"] for p in cat["packs"]])
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
    total = sum(f.stat().st_size for f in (args.out / "store").rglob("*") if f.is_file())
    print(f"{pack_id - 1} packs in {len(dlfile)} categories, {total / 1e6:.1f} MB -> {args.out}")


if __name__ == "__main__":
    main()
