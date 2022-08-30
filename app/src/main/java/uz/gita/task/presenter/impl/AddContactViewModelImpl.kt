package uz.gita.task.presenter.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.task.data.models.ContactRequestData
import uz.gita.task.presenter.AddContactViewModel
import uz.gita.task.repository.ContactRepository
import uz.gita.task.repository.impl.ContactRepositoryImpl

class AddContactViewModelImpl : AddContactViewModel, ViewModel() {
    private val repository: ContactRepository = ContactRepositoryImpl()

    override val messageLiveData = MutableLiveData<String>()

    override fun addContact(contactRequestData: ContactRequestData) {
        repository.addWithoutMessage(contactRequestData)
    }
}