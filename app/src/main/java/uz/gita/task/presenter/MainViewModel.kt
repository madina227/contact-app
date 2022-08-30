package uz.gita.task.presenter

import androidx.lifecycle.LiveData
import uz.gita.task.data.models.ContactData

interface MainViewModel {

    val listLiveData: LiveData<List<ContactData>>

    val progressLiveData:LiveData<Boolean>

    val addLiveData:LiveData<Unit>

    val editLiveData:LiveData<ContactData>

    val deleteLiveData:LiveData<ContactData>

    fun getContacts()

    fun edit(contactData: ContactData)

    fun delete(contactData: ContactData)

    fun add()

    fun updateSate()

}