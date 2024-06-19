package com.example.practica02_alvarez_martinezg.playersapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica02_alvarez_martinezg.Adapter.PlayersAdapter
import com.example.practica02_alvarez_martinezg.Model.PlayersModel
import com.example.practica02_alvarez_martinezg.R
import com.google.firebase.firestore.FirebaseFirestore

class ListarPlayersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_listar_players, container, false)

        val db = FirebaseFirestore.getInstance()
        var lstPlayers: MutableList<PlayersModel> = mutableListOf()
        val rvPlayers: RecyclerView = view.findViewById(R.id.rvPlayers)
        val playersAdapter = PlayersAdapter(lstPlayers)
        rvPlayers.adapter = playersAdapter
        rvPlayers.layoutManager = LinearLayoutManager(requireContext())

        db.collection("players")
            .addSnapshotListener{snap, error ->
                if (error != null){
                    Log.e("ERROR-FIREBASE", "Ocurrio error: ${error.message}")
                    return@addSnapshotListener
                }

                lstPlayers.clear()
                lstPlayers.addAll(snap!!.documents.map{document ->
                    PlayersModel(
                        document["papodo"].toString(),
                        document["pcountry"].toString(),
                        document["pdorsal"].toString(),
                        document["pimagen"].toString(),
                        document["pname"].toString(),
                        document["pteam"].toString(),
                        document["ptype"].toString()
                    )
                })

                playersAdapter.notifyDataSetChanged()
            }

        return view
    }
}