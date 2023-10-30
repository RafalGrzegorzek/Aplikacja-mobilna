package pl.app.finder

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



    }
}

