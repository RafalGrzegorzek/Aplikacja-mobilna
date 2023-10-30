package pl.app.finder

import android.content.Intent
import android.provider.MediaStore
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

        val infoButton = findViewById<Button>(R.id.button4)
        infoButton.setOnClickListener {
            val textView = findViewById<TextView>(R.id.textView)
            textView.text = "Aplikacja jest w trakcie projektowania. Wkrótce pojawią się nowe treści."
        }
    }
}

