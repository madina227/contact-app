package uz.gita.task.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contacts")
data class ContactData(
    val name: String,
    val phone: String,
    val state: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val localId: Long = 0,
    val id: Long = 0
) : Serializable {

    companion object {
        const val DEFAULT = 0
        const val ADDED = 1
        const val UPDATED = 2
        const val DELETED = 3
    }
}
