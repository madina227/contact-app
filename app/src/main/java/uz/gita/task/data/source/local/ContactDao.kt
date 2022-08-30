package uz.gita.task.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.task.data.models.ContactData

@Dao
interface ContactDao {
    @Insert
    fun insert(vararg contact: ContactData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: List<ContactData>)

    @Delete
    fun delete(vararg contact: ContactData)

    @Delete
    fun delete(contact: List<ContactData>)

    @Update
    fun update(vararg contact: ContactData)

    @Update
    fun update(contact: List<ContactData>)

    @Query("SELECT * FROM contacts WHERE state!=3")
    fun getAllContacts(): LiveData<List<ContactData>>

    @Query("SELECT * FROM CONTACTS")
    fun getContacts(): List<ContactData>

    @Query("DELETE FROM contacts")
    fun deleteAll()

    @Transaction
    fun updateAllContact(newList: List<ContactData>) {
        deleteAll()
        insert(newList)
    }

    @Query("SELECT * FROM contacts WHERE localId = :id")
    fun getItem(id: Long): ContactData

    @Query("SELECT * FROM CONTACTS WHERE ID = :id")
    fun getItemByRemoteId(id: Long): ContactData?

    @Query("SELECT * FROM CONTACTS WHERE localId = :id")
    fun getItemByLocalId(id: Long): ContactData?

    @Query("UPDATE contacts set name=:name, phone=:phone,state=:state where localId =:id")
    fun updateById(name: String, phone: String, state: Int, id: Long): Int

    @Query("DELETE from contacts where localId=:id")
    fun deleteById(id: Long)

    @Query("DELETE from contacts where id=:id")
    fun deleteByRemoteId(id: Long)

}