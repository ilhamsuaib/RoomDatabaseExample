package id.ilhamsuaib.roomexample

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import id.ilhamsuaib.roomexample.dao.PersonDao
import id.ilhamsuaib.roomexample.entity.Person

/**
 * Created by ilham on 10/17/17.
 */
@Database(entities = arrayOf(Person::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun personDao(): PersonDao
}