package com.example.practica02_alvarez_martinezg.playersapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import com.example.practica02_alvarez_martinezg.R
import com.example.practica02_alvarez_martinezg.model.PlayersModel
import com.google.firebase.firestore.FirebaseFirestore


class RegisterPlayersFragment : Fragment() {


private lateinit var spCountry: Spinner
private lateinit var spPlayerType: Spinner
    private lateinit var etPlayerNickName: EditText
    private lateinit var etPlayerName: EditText
    private lateinit var etPlayerDorsal: EditText
    private lateinit var etPlayerTeam: EditText
    private lateinit var etPlayerImage: EditText
    private lateinit var btPlayerSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_register_players, container, false)
        spCountry = view.findViewById(R.id.spCountry)
        spPlayerType = view.findViewById(R.id.spPlayerType)
        etPlayerNickName = view.findViewById(R.id.etPlayerNickName)
        etPlayerName = view.findViewById(R.id.etPlayerName)
        etPlayerDorsal = view.findViewById(R.id.etPlayerDorsal)
        etPlayerTeam = view.findViewById(R.id.etPlayerTeam)
        etPlayerImage = view.findViewById(R.id.etPlayerImage)
        btPlayerSave = view.findViewById(R.id.btPlayerSave)

        loadSpinnerData()

        btPlayerSave.setOnClickListener {
            savePlayerData(etPlayerNickName, etPlayerName, etPlayerDorsal, etPlayerTeam, etPlayerImage)
        }


        return view

    }

    private fun loadSpinnerData() {
        val db = FirebaseFirestore.getInstance()

        // Load countries into spCountry spinner
        db.collection("countries").get()
            .addOnSuccessListener { documents ->
                val countries = ArrayList<String>()
                for (document in documents) {
                    document.getString("cname")?.let { countries.add(it) }
                }
                val countryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countries)
                countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spCountry.adapter = countryAdapter
            }
            .addOnFailureListener { exception ->
                // Handle any errors
            }

        // Load player types into spPlayerType spinner
        db.collection("playerPositions").get()
            .addOnSuccessListener { documents ->
                val playerTypes = ArrayList<String>()
                for (document in documents) {
                    document.getString("pposition")?.let { playerTypes.add(it) }
                }
                val playerTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, playerTypes)
                playerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spPlayerType.adapter = playerTypeAdapter
            }
            .addOnFailureListener { exception ->
                // Handle any errors
                exception.printStackTrace()
            }


    }
    private fun savePlayerData(
        etPlayerNickName: EditText,
        etPlayerName: EditText,
        etPlayerDorsal: EditText,
        etPlayerTeam: EditText,
        etPlayerImage: EditText
    ) {
        val db = FirebaseFirestore.getInstance()

        // Validar que el dorsal sea un número entero
        val dorsalStr = etPlayerDorsal.text.toString()
        val dorsal: Int = try {
            dorsalStr.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(requireContext(), "Dorsal debe ser un número entero", Toast.LENGTH_SHORT).show()
            return
        }

        val player = PlayersModel(
            pname = etPlayerName.text.toString(),
            pcountry = spCountry.selectedItem.toString(),
            ptype = spPlayerType.selectedItem.toString(),
            pdorsal = dorsal,
            pteam = etPlayerTeam.text.toString(),
            papodo = etPlayerNickName.text.toString(),
            pimagen = etPlayerImage.text.toString()
        )

        db.collection("players")
            .add(player)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Player data saved successfully", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error saving player data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun clearFields() {
        etPlayerNickName.text.clear()
        etPlayerName.text.clear()
        etPlayerDorsal.text.clear()
        etPlayerTeam.text.clear()
        etPlayerImage.text.clear()
        spCountry.setSelection(0)
        spPlayerType.setSelection(0)
    }
}

