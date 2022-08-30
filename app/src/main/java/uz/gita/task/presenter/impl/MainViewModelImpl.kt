package uz.gita.task.presenter.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.task.data.models.ContactData
import uz.gita.task.presenter.MainViewModel
import uz.gita.task.repository.ContactRepository
import uz.gita.task.repository.impl.ContactRepositoryImpl

class MainViewModelImpl : MainViewModel, ViewModel() {

    private val repository: ContactRepository = ContactRepositoryImpl()

    override val listLiveData: MutableLiveData<List<ContactData>> = MutableLiveData<List<ContactData>>()

    override val progressLiveData: MediatorLiveData<Boolean> = MediatorLiveData()

    override val addLiveData: MutableLiveData<Unit> = MutableLiveData()

    override val editLiveData: MutableLiveData<ContactData> = MutableLiveData()

    override val deleteLiveData: MutableLiveData<ContactData> = MutableLiveData()


    override fun getContacts() {
        progressLiveData.addSource(repository.getContacts()) {
            listLiveData.value = it
        }

        //TODO BUG bo'sa shuni o'chirish  kere
       // repository.fetchContacts()
    }

    override fun edit(contactData: ContactData) {
        editLiveData.value = contactData
    }

    override fun delete(contactData: ContactData) {
        repository.delete(contactData.localId)
    }

    override fun add() {
        addLiveData.value = Unit
    }

    override fun updateSate() {
        repository.updateState()
    }

    init {
        getContacts()
        repository.fetchContacts()
    }
}