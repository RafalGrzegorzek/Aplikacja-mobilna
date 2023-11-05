package pl.app.finder

import android.net.Uri
import android.widget.Toast
import android.content.Intent
import android.provider.MediaStore
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.app.AlertDialog
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnCam: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCam = findViewById(R.id.btnCam)
        imageView = findViewById(R.id.imageView)

        btnCam.setOnClickListener {
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val buttonInfo = findViewById<Button>(R.id.button4)

        buttonInfo.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Aplikacja jest w trakcie projektowania. Wkrótce pojawią się nowe treści.")
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
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/Żabka+nowy+sącz/@49.6026939,20.708025,13z/data=!3m1!4b1?entry=ttu"))
                startActivity(webIntent)
            }
        }

    }
}

