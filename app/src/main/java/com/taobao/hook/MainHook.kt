package com.taobao.hook

import android.text.TextUtils
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * description:
 * author:kyXiao
 * date:2019/6/20
 */
class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        val curPkgName = lpparam?.packageName
        if (TextUtils.isEmpty(curPkgName) || curPkgName != "com.taobao.taobao") {
            return
        }

        XposedHelpers.findAndHookMethod("mtopsdk.mtop.domain.MtopResponse", lpparam.classLoader,
            "setBytedata", ByteArray::class.java, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam?) {
                    val bytes = param?.args?.get(0) as ByteArray
                    val strData = String(bytes)
                    XposedBridge.log("setBytedata: param = $strData")
                }
            })
    }
}