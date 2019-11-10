package de.fuchsch.email

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import de.fuchsch.email.model.Folder

typealias FolderClickListener = (Folder) -> Unit

class FolderAdapter(context: Context, private val listener: FolderClickListener)
    : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    var folders = emptyList<Folder>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class FolderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(folder: Folder, listener: FolderClickListener) {
            val nameView: TextView = itemView.findViewById(R.id.FolderNameTextView)
            nameView.text = folder.name
            val messageView: TextView = itemView.findViewById(R.id.MessageCountTextView)
            messageView.text = folder.messageCount.toString()
            if (folder.hasNewMessages) {
                messageView.setTypeface(messageView.typeface, Typeface.BOLD)
            } else {
                messageView.setTypeface(messageView.typeface, Typeface.NORMAL)
            }
            val cardView: CardView = itemView.findViewById(R.id.FolderAdapterCardView)
            cardView.setOnClickListener { listener(folder) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val itemView = inflater.inflate(R.layout.account_recyclerview_item, parent, false)
        return FolderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val current = folders[position]
        holder.bind(current, listener)
    }

    override fun getItemCount() = folders.size

}