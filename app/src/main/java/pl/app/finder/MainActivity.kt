package pl.app.finder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    private lateinit var btnCam: Button
    private lateinit var buttonInfo: Button
    private lateinit var buttonMaps: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCam = findViewById(R.id.btnCam)
        buttonInfo = findViewById(R.id.button4)
        buttonMaps = findViewById(R.id.button2)

        btnCam.setOnClickListener {
            // Inicjalizacja skanera ZXing
            val integrator = IntentIntegrator(this)
            integrator.setPrompt("Zeskanuj kod QR") // Komunikat zachęty dla użytkownika
            integrator.setBeepEnabled(true) // Włączenie dźwięku przy skanowaniu
            integrator.setOrientationLocked(false) // Odblokowanie obrotu ekranu podczas skanowania
            integrator.initiateScan() // Uruchomienie aktywności skanowania
        }

        buttonInfo.setOnClickListener {
            // Ukryj wszystkie aktualne przyciski
            btnCam.visibility = View.GONE
            buttonMaps.visibility = View.GONE
            buttonInfo.visibility = View.GONE
            findViewById<ImageView>(R.id.imageView3)?.visibility = View.GONE

            // Przekierowanie do fragmentu "AboutFragment"
            val aboutFragment = AboutFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, aboutFragment)
                .addToBackStack(null)
                .commit()
        }

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



