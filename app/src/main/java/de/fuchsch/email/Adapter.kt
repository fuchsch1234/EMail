package de.fuchsch.email

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter<T>(
    context: Context,
    private val viewType: Int,
    private val bind: (View, T, (T) -> Unit) -> Unit,
    private val listener: (T) -> Unit = {}
) : RecyclerView.Adapter<Adapter<T>.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    var items = emptyList<T>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(viewType, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = items[position]
        bind(holder.itemView, current, listener)
    }

    override fun getItemCount() = items.size

}
