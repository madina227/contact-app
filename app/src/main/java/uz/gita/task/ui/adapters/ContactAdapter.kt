package uz.gita.task.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.task.data.models.ContactData
import uz.gita.task.databinding.ItemContactBinding

class ContactAdapter : ListAdapter<ContactData, ContactAdapter.Holder>(ContactComparator) {

    private var editListener: ((ContactData) -> Unit)? = null

    fun setEditListener(block: (ContactData) -> Unit) {
        editListener = block
    }

    private var deleteListener: ((ContactData) -> Unit)? = null

    fun setDeleteListener(block: (ContactData) -> Unit) {
        deleteListener = block
    }

    inner class Holder(val viewBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind() {
            viewBinding.tvContact.text = getItem(absoluteAdapterPosition).name
            viewBinding.tvNumber.text = getItem(absoluteAdapterPosition).phone
        }

        init {
            viewBinding.imgEdit.setOnClickListener {
                editListener?.invoke(getItem(absoluteAdapterPosition))
            }
            viewBinding.imgDelete.setOnClickListener {
                deleteListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }
    }

    object ContactComparator : DiffUtil.ItemCallback<ContactData>() {
        override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
            return oldItem.name == newItem.name && oldItem.id == newItem.id && oldItem.phone == newItem.phone
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind()
}