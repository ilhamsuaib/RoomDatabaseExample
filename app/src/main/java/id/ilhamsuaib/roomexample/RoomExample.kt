package id.ilhamsuaib.roomexample

import android.app.Application
import android.arch.persistence.room.Room

/**
 * Created by ilham on 10/17/17.
 */
class RoomExample: Application() {

    companion object {
        var db: Database? = null
    }

    override fun onCreate() {
        super.onCreate()
        RoomExample.db = Room.databaseBuilder(this, Database::class.java, "room-example-db").build()
    }
}