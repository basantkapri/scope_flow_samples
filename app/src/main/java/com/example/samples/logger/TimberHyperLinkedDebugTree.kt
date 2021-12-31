package com.example.samples

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Debug
import timber.log.Timber
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

public object Debugging {
    public fun logProcessNameAndAwaitDebugger() {
        Timber.w("Service launched in debug mode.\nService is waiting for debugger to attach...\nAttach to Process Name: ${getProcessName()}")
        Debug.waitForDebugger()
    }

    private fun getProcessName(): String {
        return if (Build.VERSION.SDK_INT >= 28) Application.getProcessName() else try {
            @SuppressLint("PrivateApi") val activityThread = Class.forName("android.app.ActivityThread")

            // Before API 18, the method was incorrectly named "currentPackageName", but it still returned the process name
            // See https://github.com/aosp-mirror/platform_frameworks_base/commit/b57a50bd16ce25db441da5c1b63d48721bb90687
            val methodName = if (Build.VERSION.SDK_INT >= 18) "currentProcessName" else "currentPackageName"
            val getProcessName: Method = activityThread.getDeclaredMethod(methodName)
            return getProcessName.invoke(null) as String
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }

    public object TimberHyperLinkedDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return "[(${element.fileName}:${element.lineNumber})\$${element.methodName}] |${Thread.currentThread().name}|"
        }
    }

}