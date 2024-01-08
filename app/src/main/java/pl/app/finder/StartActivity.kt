// StartActivity.kt

package pl.app.finder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.pm.PackageManager


class StartActivity : AppCompatActivity() {

    private val CHANNEL_ID = "MyChannelId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        createNotificationChannel()

        val startButton = findViewById<Button>(R.id.buttonstart)

        startButton.setOnClickListener {
            // Sprawdzanie uprawnienia do powiadomień
            if (checkSelfPermission(android.Manifest.permission.VIBRATE) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Tworzenie powiadomienia
                val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Dziękujemy za korzystanie z naszej aplikacji")
                    .setContentText("Teraz możesz rozpocząć korzystanie z aplikacji.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                // Wysyłanie powiadomienia
                with(NotificationManagerCompat.from(this)) {
                    notify(1, notification)
                }

                // Przejście do MainActivity po wciśnięciu przycisku
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Poproś użytkownika o uprawnienia
                requestPermissions(arrayOf(android.Manifest.permission.VIBRATE), 1)
            }
        }
    }

    // Tworzenie kanału powiadomień (wymagane w Android 8.0 i nowszych)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val name = "MyChannel"
        val descriptionText = "My Notification Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        // Rejestrowanie kanału w systemie
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}

