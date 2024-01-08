package pl.app.finder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.content.pm.PackageManager


class StartActivity : AppCompatActivity() {

    private val CHANNEL_ID = "MyChannelId"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        createNotificationChannel()

        val startButton = findViewById<Button>(R.id.buttonstart)

        // Sprawdź, czy imię jest już zapisane w SharedPreferences
        val sharedPrefs = getSharedPreferences("YourPrefsName", Context.MODE_PRIVATE)
        val savedName = sharedPrefs.getString("user_name", null)

        startButton.setOnClickListener {
            // ... (reszta kodu)

            // Dodaj imię do powiadomienia
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Witaj, $savedName!")
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


