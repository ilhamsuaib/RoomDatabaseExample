package id.ilhamsuaib.roomexample

import android.app.Application
import android.arch.persistence.room.Room

/**
 * Created by ilham on 10/17/17.
 */

class RoomExample: Application() {

    companion object {
        lateinit var db: Database
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, Database::class.java, "room-example-db")
                .fallbackToDestructiveMigration()
                .build()
    }
}