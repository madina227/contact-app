package uz.gita.task.presenter

import androidx.lifecycle.LiveData
import uz.gita.task.data.models.ContactRequestData

interface EditViewModel {
    val setEditLiveData: LiveData<ContactRequestData>

    fun edit(id: Long, contact: ContactRequestData)
}