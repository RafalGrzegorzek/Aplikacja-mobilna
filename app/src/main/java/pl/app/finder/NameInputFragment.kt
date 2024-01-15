package pl.app.finder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class NameInputFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var btnSave: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name_input, container, false)

        etName = view.findViewById(R.id.etName)
        btnSave = view.findViewById(R.id.btnSave)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        // Obsługa przycisku "Zapisz"
        btnSave.setOnClickListener {
            val enteredName = etName.text.toString()

            // Zapisz imię w SharedPreferences
            val sharedPrefs = requireActivity().getSharedPreferences("YourPrefsName", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("user_name", enteredName)
            editor.apply()
        }

        val backButton2 = view.findViewById<Button>(R.id.buttonback)
        backButton2.setOnClickListener {
            // Powrót do MainActivity
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun saveUserName(userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_name", userName)
        editor.apply()
    }

}

