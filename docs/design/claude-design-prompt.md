# Ronevis redesign: Claude Design prompts

Use these in this order: paste **Prompt 0** first in each Claude Design project so the shared foundation is set, then the surface prompt for that project (1 = Android app, 2 = admin panel, 3 = web app). Each prompt stands on its own if you only want one surface.

Notes on where the brief comes from are at the end.

---

## Prompt 0: Shared foundation (paste first)

```
You are designing the new version of Ronevis (رونویس), a Persian-first app for writing beautiful text on photos. It launched in 2013, has 500k+ installs from Cafe Bazaar and Myket and about 150k monthly users, and won "best app" picks from both stores. Its edge has always been typography: correct Persian and Arabic shaping, ligatures, kashida stretching and a large library of Persian fonts, including Nastaliq. The product line now has three surfaces that must feel like one family: the Android app, a web app (an editor at ronevis.com/poster-design), and an internal admin panel for running the content store.

Who uses it
- Young Persian speakers making posts and stories for Instagram, Telegram, WhatsApp, Eitaa and Rubika: quotes, poetry, love notes, condolence and religious posts, birthdays.
- Small shops and freelancers making quick promo posters and price cards.
- Occasion spikes drive usage: Nowruz, Yalda, Muharram, Ramadan, Mother's Day (Jalali calendar). Templates and push campaigns follow that calendar.
- Most users are on mid-range Android phones, often with slow or unreliable internet, and many arrive with a photo already in mind.

Positioning against competitors
- Global apps (Canva, Picsart, Phonto, PixelLab, Adobe Express) treat Persian as an afterthought: broken shaping, no kashida, clipped Nastaliq. The direct local rival, Matn Negar (متن نگار, about 250 fonts, layered projects), competes on font count.
- Ronevis wins on typography quality, not quantity. Make these visible in the design: a kashida stretch slider, a dedicated Nastaliq mode (extra line height so tall stacks never clip), correct mixing of Persian with numbers, English and emoji in one line, a Persian/Latin digit toggle, half-space (ZWNJ) insertion on the keyboard bar, text on a curve that reads right to left, and one-tap "mirror for RTL/LTR" on templates.
- Match the category's expected basics: templates and blank canvas as equal starts, a selection-aware bottom toolbar with half-sheets, layers with undo/redo, editable saved projects that can be shared as a file, and export sized for each platform.

Languages and direction
- Persian is the default; Arabic and English ship at launch, and the layout must accept more (Kurdish Sorani, Urdu, Turkish) without redesign.
- Design every screen RTL first, then show the LTR (English) mirror for at least the key screens. Icons that imply direction (back, next, undo/redo, progress) flip; logos, media controls and the canvas itself do not.
- Persian locale uses Persian digits (۰-۹) and the Jalali calendar; Arabic uses Arabic-Indic digits and Hijri dates where relevant; English uses Latin digits and Gregorian.
- UI font: Vazirmatn for Persian/Arabic UI, with a matching Latin face for English. Fonts used inside designs on the canvas are a separate, user-chosen library; never mix the two roles.
- Text strings expand: allow 30-40% growth for English and no truncation of Persian labels on 360dp-wide phones.

Brand
- Existing identity: a feather/fountain-pen mark, warm near-black backgrounds and an amber-gold accent (see ronevis.com). Keep that heritage and make it calmer and more modern rather than replacing it.
- Dark theme is primary (users edit photos and dark keeps attention on the image); a full light theme is required too.
- Suggested starting tokens, refine them: background #121110, surface #1C1A18, raised surface #262320, line #34302B, text #F4EFE6, muted text #A79E91, accent amber #F2B23A, accent pressed #D9982A, success #4CAF7A, danger #E5534B. Check every text/background pair for WCAG AA contrast in both themes and state the ratio.
- Tone of voice: warm, short, conversational Persian, like the current site ("قالب؟ نیازی نیست، خودت بساز!"). No exclamation-mark spam, no corporate phrasing.

Avoid
- Generic AI-app styling: purple/blue gradients, glassmorphism everywhere, glowing orbs, emoji as icons, stock 3D illustrations.
- Hiding the photo under heavy chrome. The canvas is the hero.
- Tiny tap targets: minimum 48dp, with labels under icons in the editor toolbars (icon-only rows confuse this audience).

Deliverables for each surface
- A small design system first: colour tokens (both themes), type scale for Persian and Latin, spacing (4dp grid), radii, elevation, icon style (one outlined set, 1.5-2px stroke), and core components with all states (default, pressed, focused, disabled, loading, error, empty).
- Then the screens listed in the surface prompt, each in RTL dark, plus the LTR and light variants where asked.
- Annotate interaction details that a static frame cannot show: gestures, transitions, what happens offline.
```

---

## Prompt 1: Android app

```
Design the new Ronevis Android app (Material 3 foundations, customised to the Ronevis brand from the foundation brief). Phone first at 360x800dp and 412x915dp, plus one tablet layout of the editor.

What the current app gets wrong (fix these)
- The editor shows 30+ tool buttons in one flat scrolling bar (text size, box size, stroke, gradient, texture, shadow, line spacing, opacity, rotation, move, lock, duplicate, bold, italic...). Users can't find things.
- There is no undo/redo and no layers list, so mistakes mean starting over.
- Fonts, backgrounds and stickers live in a separate "shop" (ویترین) that pulls people out of the design they are making.
- A multi-page tour and up-front permission requests before the user has done anything.
- Store-era visuals (2016 Material, blue app bars) that no longer match the brand.

Information architecture
- Bottom navigation with 4 tabs: Home (خانه), Templates (قالب‌ها), Library (کتابخانه: fonts, backgrounds, stickers, packs), Profile (من). A prominent "Create" action sits on Home, not as a floating button over content.
- The editor is full screen, outside the tab bar.

Screens to design
1. First launch: one welcome screen with language picker (فارسی / العربية / English), then straight to Home. No tour; use contextual coach marks the first time each editor tool is opened. Use the Android 13+ photo picker so no storage permission is asked up front.
2. Home: big "Create" entry with three starts (from a photo, from a colour or gradient, from a template); "Continue editing" row of recent projects with thumbnails; an occasion strip driven by the Jalali calendar (for example "یلدا نزدیکه" with matching templates); new and trending font packs. Show the empty state for a new user.
3. Canvas size picker: Post 1:1, Portrait 4:5, Story 9:16, Wide 16:9, custom; each with the platform icons it suits.
4. Editor, nothing selected: canvas in the middle; top bar with close, undo, redo, layers and Export; bottom "Add" bar: Text, Photo, Sticker, Shape, Background. Show snapping guides and safe-area overlay for stories.
5. Editor, text selected: on-canvas handles (move, scale, rotate, edit by double tap); the bottom bar switches to text tools grouped into at most 7 labelled entries: Edit, Font, Colour, Style, Spacing, Align, More. Each opens a compact bottom sheet that keeps the canvas visible:
   - Font: live preview of the user's own text in every font, tabs for Recent, Favourites, Nastaliq, Naskh, Display, Handwriting, Latin; locked or not-yet-downloaded fonts show an inline download button so the user never leaves the editor.
   - Colour: solid, gradient (2-3 stops, angle), texture/image fill; eyedropper from the photo; recent colours.
   - Style: stroke, shadow, background box, opacity, blur, each with a toggle and one or two sliders.
   - Spacing: size, line height, letter spacing, kashida stretch for Persian/Arabic, text box width, and a Nastaliq mode toggle that adds safe line height.
   - More: duplicate, lock, bring forward/back, delete, curved text (right-to-left along the curve for Persian), Persian/Latin digits.
   Text entry happens in a full-width bottom input with the keyboard and a small accessory row (half-space, digits toggle, direction), mixed RTL/LTR text rendered correctly, with direction auto-detected per paragraph.
6. Editor, photo/sticker selected: crop, filters (a few tasteful presets plus brightness/contrast/saturation), blur background, opacity, flip, erase/remove background (mark as later phase).
7. Layers sheet: reorderable list with thumbnails, visibility and lock toggles, rename.
8. Export sheet: size and format (JPG/PNG), quality, optional Ronevis watermark toggle (on in free tier), save to gallery, and share targets ordered for this audience: Instagram, Telegram, WhatsApp, Eitaa, Rubika, more. Show a success state with "make another" and "share again".
9. Library: segmented fonts / backgrounds / stickers / packs; search; categories; pack detail page with preview images, number of items, size in MB, free/premium badge, download progress, "use now" after download; my downloads with storage used and delete.
10. Templates: categories, occasion collections, template preview, "use template" opening the editor with editable placeholders.
11. Profile: projects (drafts, synced with the web app when signed in, and shareable as a Ronevis project file another user can open and edit), favourites, subscription status, language, theme, notifications, about, support/feedback.
12. Premium paywall: what premium unlocks (premium packs, no watermark, cloud sync, HD export), price in Toman with monthly/yearly options, restore purchase. Payments go through Cafe Bazaar, Myket or Google Play billing depending on the store build, so keep the paywall store-neutral.
13. System states: offline banner with "your downloaded fonts still work", download failed with retry, low storage, forced update screen (for versions the admin panel blocks), and a soft "new version available" prompt.

Also show
- The editor at tablet width (tool sheets become a side panel).
- The text-selected editor and the font sheet in the English LTR mirror and in light theme.
- Motion notes: sheet open/close, selection handles, undo toast, download progress to "ready".
```

---

## Prompt 2: Admin panel (web, internal)

```
Design the Ronevis admin panel: a desktop web app (1440px, plus a usable 1024px layout) the Ronevis team uses to run the content store, track usage and talk to users. Same brand family as the app but optimised for dense, calm data work; light theme primary for long sessions, dark theme available. The panel UI itself is bilingual (Persian RTL and English LTR, switchable per user), and every user-facing content field is edited in all supported languages side by side.

Navigation (left sidebar in LTR, right sidebar in RTL)
Dashboard, Content (Packs, Fonts, Backgrounds, Stickers, Templates), Collections & Home layout, Push & Messages, Users, Purchases, App Releases, Remote Config, Analytics, Support, Team & Roles, Settings.

Screens to design
1. Dashboard: today vs last 7/30 days for installs (split by store: Cafe Bazaar, Myket, Google Play, web), DAU/MAU, designs exported, top downloaded packs and fonts, premium conversions and revenue in Toman, crash-free sessions, and an upcoming occasions calendar (Jalali) with linked campaigns. Each tile clicks through to its analytics.
2. Content list (packs): table with cover thumbnail, title (in current panel language), type, items, size, free/premium, status (draft, scheduled, live, archived), downloads, last updated; filters, bulk actions, search.
3. Pack editor: upload files by drag and drop (fonts as TTF/OTF/WOFF2, images as PNG/WebP/SVG); automatic preview generation (for fonts: a Persian sample sentence, an Arabic one and a Latin one, with a warning when a font lacks Persian glyphs such as پ چ ژ گ or kashida support); per-language title, description and tags in side-by-side columns with a completeness indicator; category, free/premium, price tier; minimum app version; target stores and regions; font licence and source fields; schedule publish; version history with rollback.
4. Template editor entry: list of templates with occasion tags; editing opens the same canvas editor as the web app with placeholder fields marked.
5. Home layout and collections: drag-to-order the sections users see on the app's Home tab (banner carousel, occasion strip, featured packs), with a live phone preview in Persian and English and a schedule per section.
6. Push notifications composer: title and body per language with phone-lockscreen previews (RTL and LTR); image; deep link picker (a pack, a template, a screen); audience builder with segments (language, store, app version, last active, has downloaded X, premium or not, country/province); estimated reach; send now or schedule in the user's local time; A/B variant; save as draft.
7. Push campaign results: sent, delivered, opened, opened-to-export conversion, by variant and segment, with timeline.
8. In-app messages: banners and modal announcements, same targeting as push, with frequency caps.
9. Users: search by ID, email or phone; user detail with devices, app versions, store, language, downloads, exports, purchases and support history; actions such as grant premium, refund note, reset.
10. App releases: one row per platform and store with version name/code, rollout percentage, changelog per language, and "minimum supported version" that triggers the in-app forced update; a soft-update toggle.
11. Remote config and feature flags: key/value list with type, default, per-segment overrides and an audit trail.
12. Analytics: funnels (open → create → export → share), retention cohorts, per-pack and per-font usage, search terms with no results (to inform which fonts to add), exports by canvas size.
13. Support inbox: in-app feedback and bug reports with device info and screenshot, assign, reply, tag.
14. Team and roles: invite, roles (owner, admin, content editor, marketing, support, viewer) with a permission matrix, and an audit log of who changed what.
15. Settings: supported languages (add a language and see translation completeness across content), stores and payment providers, storage/CDN, API keys (masked).

States to show: empty states for a fresh install of the panel, loading skeletons, validation errors in the pack editor (missing translation, font without Persian glyphs, file too large), a confirmation step before sending a push to a large audience, and a toast after publishing.
```

---

## Prompt 3: Web app (ronevis.com)

```
Redesign the Ronevis web app at ronevis.com so it matches the new Android app and the brand foundation. Responsive from 360px to 1440px. Two parts:

A. Marketing site
- Keep the current content and voice (hero "عکس‌نوشته‌های خاص خودت رو بساز", four feature points, stats: since 1392, 500k+ downloads, 150k monthly users, store awards, open-source Android code) but give it a stronger first screen: a live, playable mini-editor in the hero where visitors type their own Persian text and switch between 4-5 fonts instantly.
- Sections: how it works in three steps, font showcase (scrolling specimens rendered in real fonts, including Nastaliq), templates gallery by occasion, download buttons for Cafe Bazaar, Myket and Google Play with QR on desktop, premium plans, press and awards, FAQ, footer with about, contact, press, social links.
- Language switcher (fa, ar, en) with fully mirrored layouts.

B. Web editor (ronevis.com/poster-design)
- The current web editor has a layers panel, templates, canvas size presets (Instagram, Story, Facebook, A4), grid, rulers, snapping and an "AI" button. Redesign it to share the Android editor's model and wording so a project opened on either surface feels the same:
  - Top bar: logo, project name (editable), undo/redo, zoom, Export, account.
  - Left (RTL: right) rail: Add (text, photo, sticker, shape, background), Templates, Fonts library, Uploads, Layers.
  - Contextual properties panel on the other side, grouped the same way as the app (Font, Colour, Style, Spacing, Align, More).
  - Canvas in the centre with rulers, guides and size presets.
- Show: empty editor with template suggestions, editing a text layer, the font library panel with live preview, export modal, sign-in prompt to sync projects with the phone, and the mobile-width version of the editor (simplified to the Android layout).
- Show the English LTR mirror of the editing state.
```

---

## Where the brief comes from

- **Current app** (this repo): about 30 editor tools in one flat bar (`res/values/strings.xml`, `btn*` strings), no undo/redo or layers, a separate shop screen (`menushop` / ویترین), a multi-page tour (`tour_*.xml`), and Persian-only strings.
- **Web demo** (ronevis.com, Vercel project `octabiz/ronevis`): dark warm background with an amber accent and pen logo; stats and awards copy; the `/poster-design` editor with layers, templates, canvas presets, grid, rulers and an AI button.
- **Competitors** (scanned November 2026):
  - **Matn Negar** (متن نگار): the main local rival, on Bazaar and Myket since 2015, about 4.5★ from around 17k votes on each. It has about 250 fonts and layered project files you can share. [Bazaar](https://cafebazaar.ir/app/com.ma.textgraphy)
  - **Neveshtnama** (نوشت نما): a small, curated set of good fonts. **Adoos** (آدوس): the best Shekasteh Nastaliq calligraphy found. **Kitaba**: Arabic-first, with seasonal Ramadan and Eid templates.
  - **Phonto**: gets you to a first edit in two taps, but Persian handling is left to the OS.
  - **PixelLab**: one overcrowded tool panel, and reviews complain about Arabic support.
  - **Canva**: templates first, and has a "mirror page" setting for RTL. **Adobe Express**: Adobe's own community calls its RTL support "limited". **Hypic**: aggressive paywall, 3.0★.
  - **Over / GoDaddy Studio**: folded into the GoDaddy app in February 2026, so no longer a competitor.
  - **Gaps Ronevis can own:** kashida justification, a proper Nastaliq mode, mixed-direction text, curved RTL text, one-tap template mirroring, and licensed font packs instead of a race on font count.
  - **Not checked:** Textgram, CapCut's image editor, and the RTL quality of Phonto and Picsart.
