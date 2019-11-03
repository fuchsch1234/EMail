package de.fuchsch.email

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.fuchsch.email.database.entity.Account

class AccountAdapter(context: Context): RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var accounts = emptyList<Account>()

    inner class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val accountItemView: TextView = itemView.findViewById(R.id.TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val itemView = inflater.inflate(R.layout.accounts_recyclerview_item, parent, false)
        return AccountViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val current = accounts[position]
        holder.accountItemView.text = current.name
    }

    internal fun setAccounts(accounts: List<Account>) {
        this.accounts = accounts
        notifyDataSetChanged()
    }

    override fun getItemCount() = accounts.size

}