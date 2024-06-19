package com.example.practica02_alvarez_martinezg.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica02_alvarez_martinezg.R
import com.example.practica02_alvarez_martinezg.model.PlayersModel
import com.squareup.picasso.Picasso

class PlayersAdapter (private var lstPlayers: List<PlayersModel>):
    RecyclerView.Adapter<PlayersAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName: TextView = itemView.findViewById(R.id.tvPname)
        val tvCountry: TextView = itemView.findViewById(R.id.tvPcountry)
        val tvDorsal: TextView = itemView.findViewById(R.id.tvPdorsal)
        val tvTeam: TextView = itemView.findViewById(R.id.tvPteam)
        val tvType: TextView = itemView.findViewById(R.id.tvPtype)
        val tvApodo: TextView = itemView.findViewById(R.id.tvPapodo)
        val ivPlayer: ImageView = itemView.findViewById(R.id.ivPimagen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_players, parent, false))
    }

    override fun getItemCount(): Int {
        return lstPlayers.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPlayer = lstPlayers[position]
        holder.tvName.text = lstPlayers[position].pname
        holder.tvCountry.text = lstPlayers[position].pcountry
        holder.tvDorsal.text = lstPlayers[position].pdorsal.toString()
        //Importar a Picasso
        Picasso.get()
            .load(itemPlayer.pimagen)
            .resize(320, 350)
            .into(holder.ivPlayer)
        holder.tvTeam.text = lstPlayers[position].pteam
        holder.tvType.text = lstPlayers[position].ptype
        holder.tvApodo.text = lstPlayers[position].papodo

    }
}