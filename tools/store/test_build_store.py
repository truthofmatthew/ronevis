"""python3 -m unittest tools/store/test_build_store.py"""
import io
import re
import sys
import tempfile
import unittest
import zipfile
from pathlib import Path

from PIL import Image

sys.path.insert(0, str(Path(__file__).parent))
import build_store  # noqa: E402


def editor_lists(subcat, name):
    # Same filter as Tools_sticker / Tools_bg: RegexFileFilter(subcat + "_*\\d*") with find().
    return re.search(subcat + r"_*\d*", name) is not None


class ImagePacks(unittest.TestCase):
    def setUp(self):
        tmp = tempfile.TemporaryDirectory()
        self.addCleanup(tmp.cleanup)
        self.src, self.out = Path(tmp.name) / "src", Path(tmp.name) / "out"
        (self.src / "st").mkdir(parents=True)
        (self.out / "store/packs").mkdir(parents=True)
        (self.out / "store/previews").mkdir(parents=True)
        for i in range(3):
            Image.new("RGBA", (800, 800), (255, 0, 0, 0)).save(self.src / f"st/{i}.png")
        Image.new("RGB", (2000, 1500), "navy").save(self.src / "wide.jpg")
        Image.new("RGB", (900, 1600), "teal").save(self.src / "tall.png")
        (self.src / "a.txt").write_text("licence A")
        (self.src / "b.txt").write_text("licence B")

    def build(self, pack, ptype):
        entry = build_store.build_pack(pack, ptype, self.src, self.out, "http://x", 1)
        return entry, zipfile.ZipFile(self.out / "store/packs" / entry["name"])

    def test_sticker_pack_from_glob(self):
        entry, z = self.build({"subcat": "stk_test", "name_fa": "آزمون", "files": "st/*.png", "license": "a.txt"}, "sticker")
        self.assertEqual(entry["name"], "sticker_stk_test.zip")
        names = z.namelist()
        self.assertEqual(sorted(n for n in names if editor_lists("stk_test", n)), ["stk_test_1.png", "stk_test_2.png", "stk_test_3.png"])
        img = Image.open(io.BytesIO(z.read("stk_test_1.png")))
        self.assertEqual((img.size, img.mode), ((512, 512), "RGBA"))
        self.assertIn("LICENSE-stk-test.txt", names)

    def test_background_pack(self):
        entry, z = self.build({"subcat": "bg_test", "name_fa": "آزمون", "files": ["wide.jpg", "tall.png"], "license": ["a.txt", "b.txt"]}, "background")
        self.assertEqual(entry["name"], "back_bg_test.zip")
        self.assertIn(".img/", z.namelist())
        self.assertIn(".tmb/", z.namelist())
        self.assertEqual(Image.open(io.BytesIO(z.read(".tmb/bg_test_1.jpg"))).size, (256, 192))
        self.assertEqual(Image.open(io.BytesIO(z.read(".img/bg_test_1.jpg"))).size, (1080, 810))
        self.assertEqual(Image.open(io.BytesIO(z.read(".img/bg_test_2.jpg"))).size, (607, 1080))
        self.assertEqual(z.read("LICENSE-bg-test.txt").decode(), "licence A\n\nlicence B\n")
        self.assertTrue(entry["image"].endswith(".jpg"))

    def test_subcat_inside_another_is_rejected(self):
        with self.assertRaises(SystemExit):
            build_store.check_subcats([("background", {"subcat": "bg_sky"}), ("background", {"subcat": "bg_skyline"})])
        # Different install folders can't see each other's files.
        build_store.check_subcats([("background", {"subcat": "bg_sky"}), ("sticker", {"subcat": "xbg_sky"})])
        build_store.check_subcats([("font", {"subcat": "fa_X", "maincat": "fa"}), ("font", {"subcat": "fa_XY", "maincat": "en"})])


if __name__ == "__main__":
    unittest.main()
