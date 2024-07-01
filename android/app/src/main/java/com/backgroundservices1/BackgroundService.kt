    // BackgroundService.java
    package com.backgroundservices1

    import android.annotation.SuppressLint
    import android.app.ActivityManager
    import android.app.AppOpsManager
    import android.app.Notification
    import android.app.NotificationChannel
    import android.app.NotificationManager
    import android.app.PendingIntent
    import android.app.Service
    import android.app.usage.UsageEvents
    import android.app.usage.UsageStatsManager
    import android.content.ContentValues
    import android.content.Context
    import android.content.Intent
    import android.content.SharedPreferences
    import android.content.pm.PackageManager
    import android.database.Cursor
    import android.database.sqlite.SQLiteDatabase
    import android.graphics.PixelFormat
    import android.os.Build
    import android.os.IBinder
    import android.provider.Settings
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.WindowManager
    import android.widget.Button
    import android.widget.ImageButton
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.annotation.RequiresApi
    import androidx.core.app.NotificationCompat
    import org.json.JSONArray
    import org.json.JSONException
    import org.json.JSONObject
    import java.sql.SQLException
    import java.text.ParseException
    import java.text.SimpleDateFormat
    import java.util.Calendar
    import java.util.Date
    import java.util.Locale
    import java.util.concurrent.TimeUnit


    class BackgroundService : Service() {

        var totalDuration = 0L


        override fun onCreate() {
            super.onCreate()

            Log.d("MyForegroundService", "Foreground service onCreate")
        }



        @RequiresApi(api = Build.VERSION_CODES.O)
        override fun onDestroy() {
            super.onDestroy()
            Log.d("MyForegroundService", "Foreground service onDestroy")
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
            val notification: Notification = if (intent != null && intent.hasExtra("title") && intent.hasExtra("text")) {


                val title = intent.getStringExtra("title")
                val text = intent.getStringExtra("text")
                Log.d("Data","title $title Text $text");
                createNotification(title ?: "Foreground Services Running", text ?: "You can Change This")
            } else {
                createNotification("Foreground Services Running", "You can Change This")
            }

            startForeground(NOTIFICATION_ID, notification)
            return START_STICKY
        }






































































        override fun onBind(intent: Intent?): IBinder? {
            return null
        }

        private fun createNotification(Title:String,Text:String): Notification {
            // Create a notification channel for Android Oreo and higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
                )
                val manager = getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
            }


            // Create the notification
            val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(Title)
                .setContentText(Text)
                .setSmallIcon(R.mipmap.ic_launcher) // Replace with a valid drawable resource
                .setContentIntent(pendingIntent)
                .setCategory(Notification.CATEGORY_SERVICE) // Specify the category as CATEGORY_SERVICE

            return builder.build()
        }












        companion object {
            private const val CHANNEL_ID = "ForegroundServiceChannel"
            private const val NOTIFICATION_ID = 12345
            private const val OVERLAY_PERMISSION_REQUEST_CODE = 123

        }


    }

