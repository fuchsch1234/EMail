package de.fuchsch.email

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ListAdapter<T>(
    context: Context,
    diffCallback: DiffUtil.ItemCallback<T>,
    private val viewType: Int,
    private val bind: (View, T, (T) -> Unit) -> Unit,
    private val listener: (T) -> Unit = {}
) : androidx.recyclerview.widget.ListAdapter<T, ListAdapter<T>.ViewHolder>(diffCallback) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(viewType, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        bind(holder.itemView, current, listener)
    }

}
