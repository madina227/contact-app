package uz.gita.task.presenter

import androidx.lifecycle.LiveData
import uz.gita.task.data.models.ContactRequestData

interface AddContactViewModel {

    val messageLiveData: LiveData<String>

    fun addContact(contactRequestData: ContactRequestData)

}