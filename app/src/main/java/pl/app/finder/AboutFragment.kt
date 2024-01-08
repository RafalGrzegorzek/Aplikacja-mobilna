package pl.app.finder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment



class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val button11 = view.findViewById<Button>(R.id.button11)
        button11.setOnClickListener {
            // Wyświetl informację o firmie
            showSampleDialog("Autorzy: Szymon Ząbczyk, Rafał Grzegorzek.\nWszelkie prawa zastrzeżone. Nieautoryzowane rozpowszechnianie całości lub fragmentu niniejszej publikacji w jakiejkolwiek postaci jest zabronione. \n@Copyrights")
            // Ukryj przyciski
            hideButtons()
        }

        val button12 = view.findViewById<Button>(R.id.button12)
        button12.setOnClickListener {
            // Wyświetl informację o aktualnej wersji aplikacji
            showSampleDialog("Aplikacja jest w fazie testów")

            // Dodaj powiadomienie na pasku
            showNotification("Aktualna wersja", "Korzystasz z aktualnej wersji aplikacji")

            // Ukryj przyciski
            hideButtons()
        }

        val button13 = view.findViewById<Button>(R.id.button13)
        button13.setOnClickListener {
            // Przekieruj do NameInputFragment
            val nameInputFragment = NameInputFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, nameInputFragment)
                .addToBackStack(null)
                .commit()

            // Ukryj przyciski
            hideButtons()
        }

        val backButton = view.findViewById<Button>(R.id.button14)
        backButton.setOnClickListener {
            // Powrót do MainActivity
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    // Funkcja do wyświetlania informacji w okienku
    private fun showSampleDialog(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Informacja")
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    // Funkcja do ukrywania przycisków w Activity
    private fun hideButtons() {
        val mainActivity = activity as MainActivity?
        // Ukryj przyciski w Activity
        mainActivity?.findViewById<Button>(R.id.btnCam)?.visibility = View.GONE
        mainActivity?.findViewById<Button>(R.id.button2)?.visibility = View.GONE
        mainActivity?.findViewById<Button>(R.id.button4)?.visibility = View.GONE
        mainActivity?.findViewById<ImageView>(R.id.imageView3)?.visibility = View.GONE
    }

    // Funkcja do wyświetlania powiadomienia na pasku
    private fun showNotification(title: String, message: String) {
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "my_channel_id"
            val channel = NotificationChannel(
                channelId,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(requireContext(), "my_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, notificationBuilder.build())
    }
}



