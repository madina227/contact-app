package uz.gita.task.presenter.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.task.data.models.ContactRequestData
import uz.gita.task.presenter.EditViewModel
import uz.gita.task.repository.ContactRepository
import uz.gita.task.repository.impl.ContactRepositoryImpl

class EditViewModelImpl : EditViewModel, ViewModel() {
    private val repository: ContactRepository = ContactRepositoryImpl()
    override val setEditLiveData = MutableLiveData<ContactRequestData>()

    override fun edit(id: Long, contact: ContactRequestData) {
        repository.edit(id, contact)
        setEditLiveData.value = contact
    }
}