package com.example.planmymeetings.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.planmymeetings.R
import com.example.planmymeetings.screens.add_appointment.AddAppointmentActivity


class NotificationUtil(private val mContext: Context) {
    private val CHANNEL_ID = "appointment_notification_channel"
    private val NOTIFICATION_ID = 123

    private val mNotificationManager: NotificationManager =
        mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createChannel()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Appointment Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.enableLights(true)
        channel.lightColor = Color.MAGENTA
        channel.enableVibration(true)
        channel.description = "Notifications from Shop application"
        mNotificationManager.createNotificationChannel(channel)
    }

    fun send(message: String?) {
        val intent = Intent(mContext, AddAppointmentActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            mContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
            .setContentTitle("Play My Meetings")
            .setContentText(message)
            .setSmallIcon(R.drawable.notification_logo)
            .setContentIntent(pendingIntent)
        mNotificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun cancel() {
        mNotificationManager.cancel(NOTIFICATION_ID)
    }
}