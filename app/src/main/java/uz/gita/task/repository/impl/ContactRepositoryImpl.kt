package uz.gita.task.repository.impl

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import uz.gita.task.data.models.ContactData
import uz.gita.task.data.models.ContactRequestData
import uz.gita.task.data.models.ResponseData
import uz.gita.task.data.source.local.AppDatabase
import uz.gita.task.data.source.remote.ApiClient
import uz.gita.task.data.source.remote.ContactApi
import uz.gita.task.repository.ContactRepository
import uz.gita.task.utils.hasConnection

class ContactRepositoryImpl : ContactRepository {

    private val contactDao = AppDatabase.instance.contactDao()
    private val contactApi = ApiClient.retrofit.create(ContactApi::class.java)


    override fun addWithoutMessage(contact: ContactRequestData, localId: Long) {
        if (hasConnection()) {
            contactApi.insert(contact).enqueue(object : Callback<ResponseData<ContactData>> {
                override fun onResponse(
                    call: Call<ResponseData<ContactData>>,
                    response: Response<ResponseData<ContactData>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val contactData = response.body()!!

                            if (localId == -1L) {
                                contactDao.insert(contactData.data)
                            } else {
                                contactDao.update(
                                    ContactData(
                                        contactData.data.name,
                                        contactData.data.phone,
                                        ContactData.DEFAULT,
                                        localId,
                                        contactData.data.id
                                    )
                                )
                            }
                            contactData.message
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseData<ContactData>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        } else {
            val contactData = ContactData(contact.name, contact.phone, ContactData.ADDED)
            contactDao.insert(contactData)
        }
    }

    override fun edit(id: Long, contact: ContactRequestData) {
        if (hasConnection()) {
            val remoteId = contactDao.getItemByLocalId(id)?.id ?: return
            contactApi.update(remoteId, contact)
                .enqueue(object : Callback<ResponseData<ContactData>> {
                    override fun onResponse(
                        call: Call<ResponseData<ContactData>>,
                        response: Response<ResponseData<ContactData>>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val contactData = response.body()!!
                                contactDao.updateById(
                                    contactData.data.name,
                                    contactData.data.phone,
                                    ContactData.DEFAULT,
                                    id
                                )
                                contactData.message
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseData<ContactData>>, t: Throwable) {

                    }

                })
        } else {
            contactDao.updateById(contact.name, contact.phone, ContactData.UPDATED, id)
        }
    }

    override fun delete(id: Long) {
        if (hasConnection()) {

            val remoteId = contactDao.getItemByLocalId(id)?.id
            if (remoteId != null && remoteId != 0L) {
                contactApi.deleteContactById(remoteId)
                    .enqueue(object : Callback<ResponseData<ContactData>> {
                        override fun onResponse(
                            call: Call<ResponseData<ContactData>>,
                            response: Response<ResponseData<ContactData>>
                        ) {
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    val contactData = response.body()!!
                                    contactDao.deleteById(id)
                                    contactData.message
                                }
                            } else {
                                contactDao.deleteById(id)
                            }
                        }

                        override fun onFailure(
                            call: Call<ResponseData<ContactData>>,
                            t: Throwable
                        ) {
                            TODO("Not yet implemented")
                        }

                    })
            } else {
                contactDao.deleteById(id)
            }
        } else {
            val contactData = contactDao.getItemByLocalId(id) ?: return
            contactDao.updateById(contactData.name, contactData.phone, ContactData.DELETED, id)
        }
    }

    override fun getContacts(): LiveData<List<ContactData>> {
        return contactDao.getAllContacts()
    }

    override fun fetchContacts() {
        if (hasConnection()) {
            contactApi.getAllContacts().enqueue(object : Callback<ResponseData<List<ContactData>>> {
                override fun onResponse(
                    call: Call<ResponseData<List<ContactData>>>,
                    response: Response<ResponseData<List<ContactData>>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseData = response.body()!!
                        syncContacts(responseData.data)
                    }
                }
                override fun onFailure(call: Call<ResponseData<List<ContactData>>>, t: Throwable) {
                    Timber.d(t.message)
                }
            })
        }
    }

    override fun updateState() {
        val contactList = contactDao.getContacts()
        contactList.forEach {
            if (hasConnection()) {
                Timber.tag("contact_app").d("delete local->${it.state}")
                when (it.state) {
                    ContactData.ADDED -> addWithoutMessage(
                        ContactRequestData(it.name, it.phone),
                        it.localId
                    )
                    ContactData.UPDATED -> edit(
                        it.localId,
                        ContactRequestData(it.name, it.phone)
                    )
                    ContactData.DELETED -> delete(it.localId)
                }
            }

            //            contactDao.updateById(it.name, it.phone, 0, it.id)

        }
    }

    override fun syncContacts(contacts: List<ContactData>) {
        val localData = contactDao.getContacts()

        val result = contacts.asSequence().filter { remote ->
            localData.none { local ->
                local.id == remote.id
            }
        }.toList()

        Timber.tag("contact_app").d("syncContacts: $result")

        contactDao.insert(result)
    }
}

//execute
//enqueue