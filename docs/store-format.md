# Ronevis store: how the app loads packs

This is how the Android app (as of this branch) fetches, installs and shows downloadable packs. Rebuild the server against this and the existing app versions work without code changes, apart from the HTTPS switch described at the end.

## 1. Catalogue

The store screen (ویترین) fetches one JSON file:

```
GET https://www.ronevis.com/file.txt
```

Source: `fragments/download/DataSource.java`, `fragments/download/model/*.java`. The `.txt` name is historical: the body must be JSON, served with any content type.

```json
{
  "dlfile": [
    {
      "title": "فونت فارسی",
      "type": "font",
      "cover": "https://www.ronevis.com/store/covers/font.jpg",
      "packs": [
        {
          "id": "12",
          "type": "font",
          "maincat": "fa",
          "subcat": "fa_Vazirmatn",
          "title": "۹ فونت",
          "name": "font_fa_Vazirmatn.zip",
          "name_fa": "وزیرمتن",
          "image": "https://www.ronevis.com/store/previews/fa_Vazirmatn.jpg",
          "url": "https://www.ronevis.com/store/packs/font_fa_Vazirmatn.zip"
        }
      ]
    }
  ]
}
```

Each `dlfile` entry is one tab of the store. Each `packs` entry is one row the user can download.

| Field | Used for |
|---|---|
| `title` (category) | Tab title in the store |
| `type` (category) | Matches the packs' `type`; also the store tab's tag |
| `cover` | Category image, loaded from the URL |
| `id` | Any unique string |
| `type` | `font`, `background` or `sticker`. Decides which editor screen lists the pack |
| `maincat` | Fonts only: `fa` or `en`. Picks the font folder the editor reads |
| `subcat` | **The pack's key.** Unique across all packs. Every file in the zip must start with it |
| `title` | Small caption under the name, e.g. «۹ فونت» |
| `name` | Download file name. **Its prefix decides the install folder** (see below) |
| `name_fa` | Pack name shown to users |
| `image` | Pack preview image, loaded from the URL |
| `url` | The zip to download |

## 2. Download and install

`DownloadService` saves `url` to the app cache as `name`, then `PackageInstall` unzips it into a folder chosen **by the prefix of `name`**:

| `name` starts with | Unzips into (app private storage) |
|---|---|
| `back` | `ronevis/.Backs/` |
| `sticker` | `ronevis/.Stickers/` |
| `font_fa` | `ronevis/.Fonts/.fa/` |
| `font_en` | `ronevis/.Fonts/.en/` |
| `Pattern` | `ronevis/.Texture/` |

Zip entries are written with their paths kept. A nested path only works if the zip also contains an entry for that directory; `zip -r` adds one automatically.

After unzipping, the pack is saved in the app's local database (`FilesDBManager`) with its `type`, `maincat` and `subcat`. That record is what makes it appear in the editor; the store doesn't need to be online afterwards.

## 3. How the editor finds the files

Each installed pack becomes a tab in the matching editor panel. The tab lists files in its folder whose name contains `<subcat>_` followed by digits (`RegexFileFilter`, a `find()` on `<subcat>_*\d*`).

| Type | Editor reads | Zip contents |
|---|---|---|
| `font` (`maincat` `fa`) | `ronevis/.Fonts/.fa/` | `fa_Vazirmatn_1.ttf`, `fa_Vazirmatn_2.ttf`, … at the zip root |
| `font` (`maincat` `en`) | `ronevis/.Fonts/.en/` | `en_Roboto_1.ttf`, … at the zip root |
| `sticker` | `ronevis/.Stickers/` | `stk_hearts_1.png`, `stk_hearts_2.png`, … at the zip root |
| `background` | `ronevis/.Backs/.img/` | `.img/bg_nature_1.jpg`, … inside a `.img/` folder, plus same-named thumbnails in `.tmb/` |
| texture (`Pattern…` name) | `ronevis/.Texture/` | any file with `_<number>` in its name; the texture panel lists every one, not per pack |

Background zips are the odd one out. The zip unpacks into `.Backs/` but the editor reads `.Backs/.img/`, so the images must sit in a `.img/` folder inside the zip. The editor's background grid loads each image's thumbnail from `.Backs/.tmb/` under the same name (`.tmb/bg_nature_1.jpg`); without it the grid stays empty. `build_store.py` writes 256px thumbnails.

Image formats are anything Android decodes: PNG, JPG and WebP. Stickers need transparency, so use PNG or WebP. Fonts are TTF or OTF.

Built-in example: the app ships `assets/sans/fa_IRANSans_1.ttf` to `_5.ttf` and registers them as `type: font`, `maincat: fa`, `subcat: fa_IRANSans`. A downloaded font pack should look exactly like that.

## 4. Update check

`Util` checks `https://www.ronevis.com/update/update.txt` (Siren). It's JSON keyed by package name:

```json
{
  "matthew.truth.ronevis": {
    "minVersionCode": 43,
    "minVersionName": "2.65"
  }
}
```

Users below `minVersionCode` see the update prompt, which opens the build's own store (Cafe Bazaar, Myket and so on). Debug builds use `matthew.truth.ronevis.debug`, so they only see a prompt if that key is present too.

## 5. Hosting

- **HTTPS only.** Android 9+ blocks plain HTTP. Store versions up to 2.65 request `http://www.ronevis.com/file.txt`. If the host redirects HTTP to HTTPS, Android 8 and older follow the redirect, but on Android 9+ those versions can't reach the store at all; only this branch's build, which requests HTTPS, works there.
- **ronevis.com is the Vercel site** (`truthofmatthew/ronevis-core`). Files in its `public/` folder are served before the `vercel.json` catch-all rewrite. So `public/file.txt`, `public/update/update.txt` and `public/store/...` would work as-is. Today both URLs return the site's HTML page instead.
- **Licences.** Only ship fonts and images you're allowed to redistribute. IRANSans in the app's assets is a commercial font (see `assets/self/FontLicense.txt`). Open-licence Persian fonts include Vazirmatn, Sahel, Samim, Shabnam, Lalezar and Amiri.
