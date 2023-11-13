package pl.app.finder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private lateinit var btnCam: Button
    private lateinit var notificationManager: NotificationManager
    private val farewellNotificationId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel()

        btnCam = findViewById(R.id.btnCam)

        btnCam.setOnClickListener {
            // Inicjalizacja skanera ZXing
            val integrator = IntentIntegrator(this)
            integrator.setPrompt("Zeskanuj kod QR") // Komunikat zachęty dla użytkownika
            integrator.setBeepEnabled(true) // Włączenie dźwięku przy skanowaniu
            integrator.setOrientationLocked(false) // Odblokowanie obrotu ekranu podczas skanowania
            integrator.initiateScan() // Uruchomienie aktywności skanowania
        }

        val buttonInfo = findViewById<Button>(R.id.button4)

        buttonInfo.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Autorzy: Szymon Ząbczyk, Rafał Grzegorzek.\nWszelkie prawa zastrzeżone. Nieautoryzowane rozpowszechnianie całości lub fragmentu niniejszej publikacji w jakiejkolwiek postaci jest zabronione. \n@Copyrights")
                .setCancelable(true)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

            val alert = dialogBuilder.create()
            alert.setTitle("Informacje")
            alert.show()
        }

        val buttonMaps = findViewById<Button>(R.id.button2)

        buttonMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=Place") // Tutaj możesz wpisać konkretne współrzędne lub nazwę miejsca
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Jeśli aplikacja Google Maps nie jest zainstalowana, otwórz stronę internetową
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/Żabka+nowy+sącz/@49.6026939,20.708025,13z/data=!3m1!4b1?entry=ttu")
                )
                startActivity(webIntent)
            }
        }
    }

    // Funkcja obsługująca wynik skanowania kodu QR
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Przekazanie danych do skanera ZXing
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        // Sprawdzenie, czy wynik skanowania jest poprawny
        if (result != null) {
            if (result.contents != null) {
                val scannedText = result.contents

                showFarewellNotification()
                showResultAlertDialog(scannedText)
            }
        }
    }


    private fun showFarewellNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val notification = Notification.Builder(this, farewellNotificationChannelId)
            .setContentTitle("Dziękujemy!")
            .setContentText("Dziękujemy za korzystanie z naszej aplikacji.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(farewellNotificationId, notification)
    }

    // Funkcja wyświetlająca alert z wynikiem skanowania kodu QR
    private fun showResultAlertDialog(scannedText: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Odczytano kod QR")
        alertDialogBuilder.setMessage("Treść kodu: $scannedText")
        alertDialogBuilder.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    // Tworzenie kanału powiadomień dla Androida 8.0 i nowszych
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                farewellNotificationChannelId,
                "Farewell Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val farewellNotificationChannelId = "farewell_channel_id"
    }

}

