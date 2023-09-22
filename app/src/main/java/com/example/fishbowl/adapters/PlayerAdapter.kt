package com.example.fishbowl.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.fishbowl.R
import com.example.fishbowl.databinding.PlayerItemBinding
import com.example.fishbowl.modals.Player
import com.example.fishbowl.viewmodal.GameViewModel

class PlayerAdapter (private val players: List<Player>, val deletePlayer: (player: Player) -> Unit) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = PlayerItemBinding.bind(itemView);

        fun databind(player: Player, position: Int) {
            binding.tvTitle.text = player.username;


            // Specialized users
            val name = player.username.lowercase();
            if (!name.equals("tris") && !name.equals("trisna") && !name.equals("trixy") && !name.equals("trisq")) {
                if (position % 3 == 0) {
                    binding.ivFish.setImageResource(R.drawable.small_fish1);
                } else if (position % 3 == 1) {
                    binding.ivFish.setImageResource(R.drawable.small_fish2)
                } else {
                    binding.ivFish.setImageResource(R.drawable.gold_fish)
                }
            }

            binding.ivTrash.setOnClickListener{
                deletePlayer(player)
            }

            binding.cardView.setOnClickListener{
                clickListener(player, binding)
            }
        }
    }

    private fun clickListener(player: Player, binding: PlayerItemBinding) {

        Log.d("TEST", "Clicked on player " + player.username);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.databind(players[position], position);
    }

    override fun getItemCount(): Int {
        return players.size;
    }


}