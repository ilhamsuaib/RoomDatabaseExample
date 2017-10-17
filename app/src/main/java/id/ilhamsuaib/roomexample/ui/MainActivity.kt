package id.ilhamsuaib.roomexample.ui

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import id.ilhamsuaib.roomexample.R
import id.ilhamsuaib.roomexample.RoomExample
import id.ilhamsuaib.roomexample.entity.Person
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_person.view.*

class MainActivity : AppCompatActivity() {

    private val personList = ArrayList<Person>()
    private lateinit var personAdapter: ArrayAdapter<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personAdapter = ArrayAdapter(this, R.layout.adapter_person, personList)
        lvPerson.adapter = personAdapter
        lvPerson.setOnItemClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                    .setMessage("Delete ${personList[position].firstName}?")
                    .setPositiveButton("yes", { _, _ ->
                        deletePerson(personList[position])
                    })
                    .setNegativeButton("no", { dialog, _ -> dialog.dismiss() })
                    .show()
        }
        getAllPerson()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_add)
            showAddDialog()
        return super.onOptionsItemSelected(item)
    }

    fun showMessage(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun getAllPerson(){
        RoomExample.db?.personDao()?.getAllPerson()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    personList.clear()
                    personList.addAll(it)
                    personAdapter.notifyDataSetChanged()
                }, {
                    it.printStackTrace()
                    showMessage("error while loading : ${it.message}")
                })

    }

    private fun showAddDialog() {
        val v: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_person, LinearLayout(this), false)
        AlertDialog.Builder(this)
                .setTitle("Add New Person")
                .setView(v)
                .setPositiveButton("save", {dialog, which ->
                    savePerson(v.etFirstName.text.toString(), v.etLastName.text.toString())
                })
                .setNegativeButton("cancel", { dialog, _ -> dialog.cancel() })
                .show()
    }

    private fun savePerson(firstName: String, lastName: String) {
        if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty()){
            showMessage("cant save, fistname and lastname cannot be blank")
            return
        }
        val person = Person(0, firstName, lastName)
        Single.fromCallable { RoomExample.db?.personDao()?.addPerson(person) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    /*do something here when success*/
                },{
                    it.printStackTrace()
                    showMessage("error while saving : ${it.message}")
                })
    }

    fun deletePerson(person: Person){
        Single.fromCallable { RoomExample.db?.personDao()?.deletePerson(person) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}
