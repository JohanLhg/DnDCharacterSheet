package com.jlahougue.dndcharactersheet.ui.characterSelection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jlahougue.dndcharactersheet.R
import com.jlahougue.dndcharactersheet.dal.entities.Character
import com.jlahougue.dndcharactersheet.databinding.RecyclerCharacterBinding

class CharacterAdapter(
    private val context: Context,
    private val listener: CharacterListener
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolderCharacter>() {

    var characters: List<Character> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolderCharacter(val bind: RecyclerCharacterBinding) :
        RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCharacter {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderCharacter(RecyclerCharacterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderCharacter, position: Int) {
        holder.bind.apply {
            if (position == characters.size) {
                imageCharacter.setImageResource(R.drawable.plus_framed)
                textCharacterName.visibility = View.GONE
                buttonFavorite.visibility = View.GONE
                root.setOnClickListener {
                    listener.onCharacterCreate()
                }
                return
            }

            val character = characters[position]
            Glide.with(context)
                .load(R.drawable.loading)
                .centerInside()
                .into(imageCharacter)
            listener.loadCharacterImage(imageCharacter, character.id)
            textCharacterName.visibility = View.VISIBLE
            textCharacterName.text = character.name

            root.setOnClickListener {
                listener.onCharacterClick(characters[position].id)
            }
            root.setOnLongClickListener {
                listener.onCharacterDelete(characters[position])
                true
            }

            buttonFavorite.visibility = View.VISIBLE
            buttonFavorite.setImageResource(
                if (characters[position].isFavorite) R.drawable.favorite_filled
                else R.drawable.favorite
            )
            buttonFavorite.setOnClickListener {
                toggleFavorite(position)
            }
        }
    }

    private fun toggleFavorite(position: Int) {
        val character = characters[position]
        listener.onCharacterMadeFavorite(character.id, !character.isFavorite)
    }

    override fun getItemCount(): Int {
        return characters.size + 1
    }

    interface CharacterListener {
        fun onCharacterClick(characterID: Long)
        fun onCharacterCreate()
        fun onCharacterDelete(character: Character)
        fun onCharacterMadeFavorite(characterID: Long, isFavorite: Boolean)
        fun loadCharacterImage(view: ImageView, characterID: Long)
    }
}