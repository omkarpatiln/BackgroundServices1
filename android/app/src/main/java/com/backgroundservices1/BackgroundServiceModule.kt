package com.backgroundservices1

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.util.Timer
import java.util.TimerTask


class BackgroundServiceModule(reactContext: ReactApplicationContext?) :
    ReactContextBaseJavaModule(reactContext) {
    private var timer: Timer? = null
    private var isServiceRunning = false

    private var pendingIntervalMillis: Int? = null
    private var pendingServiceContent: ReadableMap? = null
    override fun getName(): String {
        return "BackgroundServiceCheck"
    }






    @ReactMethod
    fun startBackgroundService(intervalMillis: Int,serviceContent:ReadableMap) {
        Log.d("intervalMillis", "min $intervalMillis")
        isServiceRunning = true;

        val title = serviceContent.getString("Title") ?: "Foreground Services Running"
        val text = serviceContent.getString("Text") ?: "You can Change This"

        Log.d("Title","$title");



        val context: Context = reactApplicationContext
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                val serviceIntent = Intent(
                    context,
                    BackgroundService::class.java
                )


                serviceIntent.putExtra("title", title)
                serviceIntent.putExtra("text", text)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }

                reactApplicationContext.getJSModule(
                    DeviceEventManagerModule.RCTDeviceEventEmitter::class.java
                )
                    .emit("backgroundServiceTriggered", null)
            }
        }, intervalMillis.toLong(), intervalMillis.toLong())
    }


    @ReactMethod
    fun isBackgroundServiceRunning(promise: Promise) {
        promise.resolve(isServiceRunning)
    }

    @ReactMethod
    fun stopBackgroundService() {
        val context: Context = reactApplicationContext


        timer?.cancel()
        timer = null

        isServiceRunning = false;

        val serviceIntent = Intent(context, BackgroundService::class.java)
        context.stopService(serviceIntent)

        Log.d("BackgroundServiceModule", "Background service stopped")
    }





    companion object {
        private const val INTERVAL: Long = 10000 // Interval in milliseconds
    }


}
