# Privacy Guard (LSPosed Module)

**Protect your apps from unwanted capture.**

---

## 🔒 What It Does

When enabled for an app, Privacy Guard will:

1. **Block screenshots** — taking a screenshot shows a black/empty image or an error.
2. **Block screen recording** — recordings show a black area instead of app content.
3. **Hide app content in Recents** — the preview in the recent apps screen is blurred or blank.

Combine this module with any app locker of your choice if you want both **screen capture protection + app locking** for maximum privacy.

---

## Backstory

This project actually started with something pretty personal.

I was planning a surprise for my wife. I was jotting down my ideas in Anytype and Google Keep. The catch? My wife knows the pattern to unlock my phone. If she stumbled onto those apps, the surprise would be ruined.

Naturally, I went looking for ways to lock or hide my notes. Here’s what I tried:

- Built-in App Locker (ROM feature)
  - ✅ Reliable and built-in 
  - ❌ Used the same lock credentials as my screen lock — not private enough
- Android 15 Private Space
  - ✅ Could set a completely different fingerprint, PIN, or password
  - ❌ Too many steps every time I wanted to access the apps
- Third-Party App Lockers 
  - ✅ Allowed a different lock than the phone screen 
  - ❌ Still showed the last used screen in the recents page (meaning my surprise notes would still be visible!)

That’s when I realized Xiaomi’s MIUI and HyperOS had a feature I really wanted: blurred recents for selected apps. But outside their ecosystem, I couldn’t find anything like it.

So, I decided to build my own solution. This module is the result — a simple way to hide sensitive app content from the recents page, and something you can pair with an app locker for stronger privacy.

---

## ✅ Minimum Requirements

- Rooted Android Phone
- **Magisk** with **Zygisk enabled**
- Framework: **LSPosed (Zygisk variant)**
- Android version: **8.1 (Oreo MR1) or newer** recommended

---

## 📦 How to Install

1. Enable **Zygisk**  
   Open Magisk → Settings → turn on **Zygisk**, then reboot.

2. Install **Zygisk-LSPosed**  
   Flash LSPosed (Zygisk version) through Magisk → reboot.

3. Download the latest apk from **[Releases](https://github.com/tan-dew/Privacy-Guard-LSPosed-Module/releases/)** and install it on your phone.

4. Enable in **LSPosed Manager**
    - Open **LSPosed Manager → Modules → Privacy Guard**
    - Tap **Scope** and select the apps you want to protect.

5. Reboot phone.

**It should block screenshots, block screen recording and block showcasing app content on recent apps page for the selected apps now.**
