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
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.zxing.integration.android.IntentIntegrator
import pl.app.finder.AboutFragment
import android.view.View



class MainActivity : AppCompatActivity() {
    private lateinit var btnCam: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val buttonInfo = findViewById<Button>(R.id.button4)

            buttonInfo.setOnClickListener {
                // Ukryj wszystkie aktualne przyciski
                findViewById<Button>(R.id.btnCam).visibility = View.GONE
                findViewById<Button>(R.id.button2).visibility = View.GONE
                findViewById<Button>(R.id.button4).visibility = View.GONE

                // Przekierowanie do fragmentu "AboutFragment"
                val aboutFragment = AboutFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, aboutFragment)
                    .addToBackStack(null)
                    .commit()
            }



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

                showResultAlertDialog(scannedText)
            }
        }
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

}

