package de.fuchsch.email

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import de.fuchsch.email.database.entity.Account
import kotlinx.android.synthetic.main.accounts_recyclerview_item.view.*

typealias AccountClickListener = (Account) -> Unit

class AccountAdapter(context: Context, val listener: AccountClickListener)
    : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>()
{

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var accounts = emptyList<Account>()

    inner class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(account: Account, listener: AccountClickListener) {
            val card: CardView = itemView.findViewById(R.id.AccountCardView)
            card.AccountNameTextView.text = account.name
            card.setOnClickListener { listener(account) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val itemView = inflater.inflate(R.layout.accounts_recyclerview_item, parent, false)
        return AccountViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val current = accounts[position]
        holder.bind(current, listener)
    }

    internal fun setAccounts(accounts: List<Account>) {
        this.accounts = accounts
        notifyDataSetChanged()
    }

    override fun getItemCount() = accounts.size

}