package dev.secureflag.enforcer

import android.app.Activity
import android.view.Window
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class SecureFlagModule : IXposedHookLoadPackage {

    private val FLAG_SECURE = 0x00002000

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // LSPosed will only load us in the apps you’ve scoped (TickTick/Anytype/Notion),
        // so no need for package-name checks here. Still, log once for sanity:
        XposedBridge.log("[SecureFlag] Loaded in ${lpparam.packageName}")

        // --- Hook Window.addFlags(int) to OR-in FLAG_SECURE
        XposedBridge.hookAllMethods(Window::class.java, "addFlags", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val orig = param.args[0] as Int
                val newFlags = orig or FLAG_SECURE
                param.args[0] = newFlags
            }
        })

        // --- Hook Window.setFlags(int, int) to force FLAG_SECURE in both 'flags' and 'mask'
        XposedBridge.hookAllMethods(Window::class.java, "setFlags", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val flags = (param.args[0] as Int) or FLAG_SECURE
                val mask  = (param.args[1] as Int) or FLAG_SECURE
                param.args[0] = flags
                param.args[1] = mask
            }
        })

        // --- Hook Window.clearFlags(int) to prevent clearing FLAG_SECURE
        XposedBridge.hookAllMethods(Window::class.java, "clearFlags", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val toClear = param.args[0] as Int
                // Remove FLAG_SECURE bit from the clear-mask so it won't be cleared
                val sanitized = toClear and FLAG_SECURE.inv()
                if (sanitized != toClear) {
                    XposedBridge.log("[SecureFlag] Prevented clearFlags(FLAG_SECURE) in ${lpparam.packageName}")
                }
                param.args[0] = sanitized
            }
        })

        // --- Reassert in Activity.onResume (covers odd flows & late windows)
        try {
            XposedHelpers.findAndHookMethod(
                Activity::class.java,
                "onResume",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val act = param.thisObject as Activity
                        try {
                            act.window?.addFlags(FLAG_SECURE)
                        } catch (_: Throwable) { /* no-op */ }
                    }
                }
            )
        } catch (t: Throwable) {
            XposedBridge.log("[SecureFlag] Failed to hook Activity.onResume: $t")
        }
    }
}
