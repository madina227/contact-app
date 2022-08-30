package uz.gita.task.repository

import androidx.lifecycle.LiveData
import uz.gita.task.data.models.ContactData
import uz.gita.task.data.models.ContactRequestData

interface ContactRepository {

    fun addWithoutMessage(contact: ContactRequestData, localId: Long = -1)

    fun edit(id: Long, contact: ContactRequestData)

    fun delete(id: Long)

    fun getContacts(): LiveData<List<ContactData>>

    fun fetchContacts()

    fun updateState()

    fun syncContacts(contacts: List<ContactData>)
}