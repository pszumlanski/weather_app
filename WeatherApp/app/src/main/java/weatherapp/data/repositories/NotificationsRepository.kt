package weatherapp.data.repositories

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import weatherapp.injection.WeatherApp
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import weatherapp.R

class NotificationsRepository @Inject constructor(@ApplicationContext val context: Context) {

    fun fireNotification(body: String) {
        createNotificationChannelIfNotExisting(context)

        val channelId = WeatherApp.getLocalResources().getString(R.string.notifications_channel_id)
        val title = WeatherApp.getLocalResources().getString(R.string.notifications_title)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.color.transparent)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(NotificationID.newNotificationId, builder.build())
        }
    }

    private fun createNotificationChannelIfNotExisting(context: Context) {
        // We can call createNotificationChannel anytime we try to send notification, as per documentation:
        // "Creating an existing notification channel with its original values performs no operation".

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId =
                WeatherApp.getLocalResources().getString(R.string.notifications_channel_id)
            val name = WeatherApp.getLocalResources().getString(R.string.notifications_channel_name)
            val descriptionText =
                WeatherApp.getLocalResources().getString(R.string.notifications_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    object NotificationID {
        private val counter: AtomicInteger = AtomicInteger(0)
        val newNotificationId = counter.incrementAndGet()
    }
}