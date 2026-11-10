#!/usr/bin/env python3
"""Draw the generated background packs (gradients, tile patterns, occasion sets) with Pillow.

No third-party artwork: everything here is drawn from code, so the output is ours to ship.

    python3 tools/store/make_backgrounds.py --out <src>/generated
"""
import argparse
import math
import random
from pathlib import Path

from PIL import Image, ImageDraw, ImageFilter

SIZE = 1080
SS = 2  # draw at 2x, then downscale, for smooth edges


def hex_rgb(h):
    return tuple(int(h[i:i + 2], 16) for i in (1, 3, 5))


def gradient(c1, c2, angle=90, radial=False, c3=None):
    """Linear (angle in degrees) or radial gradient, c3 adds a middle stop."""
    w = SIZE * SS
    stops = [hex_rgb(c) for c in ((c1, c3, c2) if c3 else (c1, c2))]
    # A 256-step ramp mapped through a distance mask is far faster than per-pixel Python.
    ramp = []
    for i in range(256):
        t = i / 255 * (len(stops) - 1)
        a, b = stops[min(int(t), len(stops) - 2)], stops[min(int(t), len(stops) - 2) + 1]
        f = t - min(int(t), len(stops) - 2)
        ramp.append(tuple(round(a[k] + (b[k] - a[k]) * f) for k in range(3)))
    if radial:
        mask = Image.radial_gradient("L").resize((w, w))
    else:
        # Rotate a 2x canvas and keep its centre, so the rotated corners never show.
        mask = Image.linear_gradient("L").resize((w * 2, w * 2)).rotate(angle - 90, resample=Image.BILINEAR)
        mask = mask.crop((w // 2, w // 2, w // 2 + w, w // 2 + w))
    lut = [ramp[i][0] for i in range(256)] + [ramp[i][1] for i in range(256)] + [ramp[i][2] for i in range(256)]
    return Image.merge("RGB", [mask] * 3).point(lut)


def star(cx, cy, r_out, r_in, points, rot=0):
    pts = []
    for k in range(points * 2):
        r = r_out if k % 2 == 0 else r_in
        a = math.pi * k / points + rot
        pts.append((cx + r * math.cos(a), cy + r * math.sin(a)))
    return pts


def finish(img):
    img = img.resize((SIZE, SIZE), Image.LANCZOS)
    # Light grain hides gradient banding after JPEG compression.
    noise = Image.effect_noise((SIZE, SIZE), 6).convert("RGB")
    return Image.blend(img, noise, 0.03)


def tile_pattern(bg, fill, line, kind, cell=180):
    img = Image.new("RGB", (SIZE * SS, SIZE * SS), bg)
    d = ImageDraw.Draw(img)
    c = cell * SS
    for gy in range(-1, SIZE * SS // c + 2):
        for gx in range(-1, SIZE * SS // c + 2):
            x, y = gx * c, gy * c
            if kind == "khatam":  # eight-point star, the classic Persian khatam tile
                d.polygon(star(x, y, c * 0.5, c * 0.32, 8, math.pi / 8), fill=fill, outline=line, width=3 * SS)
                d.regular_polygon((x + c / 2, y + c / 2, c * 0.16), 4, rotation=45, fill=line)
            elif kind == "hex":  # six-point stars on a staggered grid
                ox = c / 2 if gy % 2 else 0
                d.polygon(star(x + ox, y * 0.87, c * 0.45, c * 0.26, 6, math.pi / 6), fill=fill, outline=line, width=3 * SS)
            elif kind == "rings":  # overlapping circles
                for dx, dy in ((0, 0), (c / 2, c / 2)):
                    d.ellipse((x + dx - c / 2, y + dy - c / 2, x + dx + c / 2, y + dy + c / 2), outline=line, width=3 * SS)
                d.regular_polygon((x, y, c * 0.08), 4, rotation=45, fill=fill)
            elif kind == "diamond":
                d.regular_polygon((x, y, c * 0.42), 4, rotation=45, fill=fill, outline=line, width=3 * SS)
                d.ellipse((x + c / 2 - c * 0.06, y + c / 2 - c * 0.06, x + c / 2 + c * 0.06, y + c / 2 + c * 0.06), fill=line)
            elif kind == "kite":  # square stars rotated by half a step each row
                d.polygon(star(x, y, c * 0.48, c * 0.2, 4, (gy % 2) * math.pi / 4), fill=fill, outline=line, width=2 * SS)
    return finish(img)


def blossom(d, cx, cy, r, petal, centre):
    for k in range(5):
        a = 2 * math.pi * k / 5
        px, py = cx + r * 0.55 * math.cos(a), cy + r * 0.55 * math.sin(a)
        d.ellipse((px - r * 0.45, py - r * 0.45, px + r * 0.45, py + r * 0.45), fill=petal)
    d.ellipse((cx - r * 0.25, cy - r * 0.25, cx + r * 0.25, cy + r * 0.25), fill=centre)


def scatter(seed, n, margin=0.0):
    rnd = random.Random(seed)
    w = SIZE * SS
    return [(rnd.uniform(margin, 1 - margin) * w, rnd.uniform(margin, 1 - margin) * w, rnd.uniform(0.6, 1.4)) for _ in range(n)]


def nowruz(i, top, bottom, petal, centre):
    img = gradient(top, bottom, 90)
    d = ImageDraw.Draw(img)
    # Blossoms gathered on two corners, leaving the middle free for text.
    for x, y, s in scatter(i, 44):
        corner = (x < img.width * 0.35 and y < img.height * 0.35) or (x > img.width * 0.65 and y > img.height * 0.65)
        if corner or (i % 2 and y > img.height * 0.82):
            blossom(d, x, y, 60 * SS * s, petal, centre)
    if i % 3 == 0:  # thin frame
        m = 40 * SS
        d.rectangle((m, m, img.width - m, img.height - m), outline=centre, width=4 * SS)
    return finish(img)


def night(i, top, bottom, accent, moon=True, lanterns=False, seeds=None):
    img = gradient(top, bottom, 90)
    d = ImageDraw.Draw(img)
    w = img.width
    rnd = random.Random(100 + i)
    for _ in range(90):
        x, y = rnd.uniform(0, w), rnd.uniform(0, w * 0.7)
        r = rnd.uniform(1, 3.5) * SS
        d.ellipse((x - r, y - r, x + r, y + r), fill=(255, 255, 255))
    for x, y, s in scatter(200 + i, 5, 0.1):
        d.polygon(star(x, y * 0.6, 16 * SS * s, 6 * SS * s, 4), fill=accent)
    if moon:
        cx, cy, r = w * (0.25 if i % 2 else 0.75), w * 0.22, 110 * SS
        moon_mask = Image.new("L", img.size)
        md = ImageDraw.Draw(moon_mask)
        md.ellipse((cx - r, cy - r, cx + r, cy + r), fill=255)
        if lanterns or i % 2:  # crescent: cut a second, offset disc out of the full one
            md.ellipse((cx - r * 0.55, cy - r * 1.05, cx + r * 1.35, cy + r * 0.85), fill=0)
        img.paste(accent, mask=moon_mask)
    if lanterns:
        for k, x in enumerate((0.12, 0.3, 0.7, 0.88)):
            x *= w
            drop = (180 + 90 * (k % 2)) * SS
            d.line((x, 0, x, drop), fill=accent, width=3 * SS)
            d.polygon([(x, drop), (x + 34 * SS, drop + 50 * SS), (x, drop + 120 * SS), (x - 34 * SS, drop + 50 * SS)], fill=accent)
            d.ellipse((x - 12 * SS, drop + 38 * SS, x + 12 * SS, drop + 62 * SS), fill=(255, 240, 200))
    if seeds:  # pomegranate seeds along the bottom edge
        for x, y, s in scatter(300 + i, 70):
            y = w - (y / w) ** 2 * w * 0.22
            r = 14 * SS * s
            d.ellipse((x - r, y - r * 1.25, x + r, y + r * 1.25), fill=seeds)
            d.ellipse((x - r * 0.35, y - r * 0.7, x + r * 0.05, y - r * 0.2), fill=(255, 220, 220))
    return finish(img.filter(ImageFilter.SMOOTH))


def muharram(i, bg, line):
    img = Image.new("RGB", (SIZE * SS, SIZE * SS), bg)
    d = ImageDraw.Draw(img)
    w = img.width
    c = 220 * SS
    # A quiet khatam band along one edge only; the rest stays plain for text.
    band = range(0, 1) if i % 2 else range(w // c, w // c + 1)
    for gy in band:
        for gx in range(-1, w // c + 2):
            d.polygon(star(gx * c, gy * c + c / 2, c * 0.42, c * 0.26, 8, math.pi / 8), outline=line, width=4 * SS)
    vignette = Image.radial_gradient("L").resize((w, w)).point(lambda v: min(255, v * 0.9))
    img = Image.composite(Image.new("RGB", img.size, (0, 0, 0)), img, vignette.point(lambda v: v // 4))
    return finish(img)


GRADIENTS = [
    ("#ff9a8b", "#ff6a88", 135), ("#a1c4fd", "#c2e9fb", 90), ("#f6d365", "#fda085", 45),
    ("#84fab0", "#8fd3f4", 120), ("#30cfd0", "#330867", 90), ("#ffecd2", "#fcb69f", 90),
    ("#667eea", "#764ba2", 135), ("#0f2027", "#2c5364", 90), ("#f093fb", "#f5576c", 45),
    ("#fdfbfb", "#ebedee", 90), ("#43e97b", "#38f9d7", 120), ("#141e30", "#243b55", 90),
]
TILES = [  # turquoise, lapis, ochre and brick: the colours of Persian tile work
    ("#0b4f6c", "#1fa3a3", "#f2d492", "khatam"), ("#f4ead5", "#1c5d99", "#c8a24a", "khatam"),
    ("#123b5c", "#e8c170", "#f7f1e1", "hex"), ("#f7f1e1", "#2f8f9d", "#1c4966", "hex"),
    ("#2b2d42", "#8d99ae", "#edf2f4", "rings"), ("#fdf0d5", "#c1121f", "#003049", "rings"),
    ("#1d3557", "#457b9d", "#f1faee", "diamond"), ("#e9d8a6", "#ee9b00", "#9b2226", "diamond"),
    ("#264653", "#2a9d8f", "#e9c46a", "kite"), ("#faf3e0", "#b08968", "#7f5539", "kite"),
]


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--out", required=True, type=Path)
    out = ap.parse_args().out
    sets = {
        "gradients": [lambda c=c: gradient(*c[:2], angle=c[2]) for c in GRADIENTS]
        + [lambda: gradient("#fbc2eb", "#a6c1ee", radial=True), lambda: gradient("#fddb92", "#d1fdff", c3="#ffffff")],
        "tiles": [lambda t=t: tile_pattern(*t) for t in TILES],
        "nowruz": [lambda i=i, p=p: nowruz(i, *p) for i, p in enumerate([
            ("#e8f5e9", "#a5d6a7", "#f8bbd0", "#fdd835"), ("#fffde7", "#c5e1a5", "#ffffff", "#f06292"),
            ("#e3f2fd", "#b2dfdb", "#f48fb1", "#ffee58"), ("#f1f8e9", "#dcedc8", "#ce93d8", "#ffffff"),
            ("#fff3e0", "#ffe0b2", "#ef9a9a", "#66bb6a"), ("#e0f7fa", "#c8e6c9", "#ffffff", "#ff8a65"),
            ("#fce4ec", "#f8bbd0", "#ffffff", "#81c784"), ("#f9fbe7", "#aed581", "#ffccbc", "#e53935"),
        ])],
        "yalda": [lambda i=i, p=p: night(i, *p) for i, p in enumerate([
            ("#1a0505", "#6d0f1a", "#f5d6a0", True, False, "#b3122e"), ("#0d1b2a", "#5c1a33", "#ffd6a5", True, False, "#c9184a"),
            ("#2b0a12", "#8e1b2a", "#fde2c0", True, False, "#e63946"), ("#120c1f", "#4a1942", "#f8e1b4", True, False, "#d00000"),
            ("#200b0b", "#5a0f0f", "#ffe5b4", False, False, "#ba181b"), ("#0b132b", "#3a0ca3", "#ffd166", True, False, "#ef233c"),
        ])],
        "muharram": [lambda i=i, p=p: muharram(i, *p) for i, p in enumerate([
            ("#161616", "#3f8f45"), ("#1c1c1c", "#8d6e63"), ("#141414", "#a32424"),
            ("#1e1e1e", "#b0b0b0"), ("#10241a", "#3c8c40"), ("#221616", "#a33a3a"),
        ])],
        "ramadan": [lambda i=i, p=p: night(i, *p) for i, p in enumerate([
            ("#0b1d3a", "#1e4d6b", "#f9d77e", True, True), ("#03071e", "#1b3a4b", "#ffd166", True, False),
            ("#10002b", "#3c096c", "#ffc857", True, True), ("#001219", "#005f73", "#e9d8a6", True, False),
            ("#0d1b2a", "#415a77", "#f4e285", True, True), ("#1a1a2e", "#16213e", "#e2b714", True, False),
        ])],
    }
    for name, makers in sets.items():
        (out / name).mkdir(parents=True, exist_ok=True)
        for i, make in enumerate(makers, 1):
            make().save(out / name / f"{i:02d}.jpg", quality=86, optimize=True)
        print(name, len(makers))


if __name__ == "__main__":
    main()
