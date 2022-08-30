package uz.gita.task.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.task.data.models.ContactData

@Database(entities = [ContactData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private var _instance: AppDatabase? = null

        fun init(context: Context) {
            _instance = Room.databaseBuilder(context, AppDatabase::class.java, "Appdatabase")
                .allowMainThreadQueries()
                .build()
        }

        val instance
            get() = _instance!!
    }

}