package id.ilhamsuaib.roomexample.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import id.ilhamsuaib.roomexample.entity.Person
import io.reactivex.Flowable

/**
 * Created by ilham on 10/17/17.
 */
@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    fun getAllPerson(): Flowable<List<Person>>

    @Insert
    fun addPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)
}