package de.fuchsch.email

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import de.fuchsch.email.model.Message

typealias MessageClickListener = (Message) -> Unit

class MessageAdapter(context: Context, private val listener: MessageClickListener = {})
    : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    var messages = emptyList<Message>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(message: Message, listener: MessageClickListener) {
            val subjectView: TextView = itemView.findViewById(R.id.SubjectLineTextView)
            subjectView.text = message.subject
            val messageView: TextView = itemView.findViewById(R.id.PreviewTextView)
            messageView.text = message.message
            val cardView: CardView = itemView.findViewById(R.id.MessageAdapterCardView)
            cardView.setOnClickListener { listener(message) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = inflater.inflate(R.layout.folder_recyclerview_item, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val current = messages[position]
        holder.bind(current, listener)
    }

    override fun getItemCount() = messages.size

}