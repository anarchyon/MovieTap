package project.paveltoy.movietap.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.paveltoy.movietap.databinding.ItemContactBinding

class ContactAdapter(private val contactsList: ArrayList<ArrayList<String?>>) :
    RecyclerView.Adapter<ContactAdapter.BaseViewHolder>() {
    lateinit var onContactClick: (phoneNumber: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemContactBinding =
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(itemContactBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int = contactsList.size

    inner class BaseViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(contact: java.util.ArrayList<String?>) {
                binding.contactName.text = contact[0]
                binding.contactPhoneNumber.text = contact[1]
                itemView.setOnClickListener {
                    contact[1]?.let {
                        onContactClick(it)
                    }
                }
            }
    }
}