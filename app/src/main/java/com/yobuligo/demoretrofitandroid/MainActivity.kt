package com.yobuligo.demoretrofitandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)

        val btnFindAll: Button = findViewById(R.id.btn_persons_find_all)
        btnFindAll.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.Main) { findAllPersons() }
        })

        val btnFindById: Button = findViewById(R.id.btn_persons_find_by_id)
        btnFindById.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.Main) { findById() }
        })

        val btnAdd: Button = findViewById(R.id.btn_persons_add)
        btnAdd.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.Main) { addPerson() }
        })
    }

    suspend fun findAllPersons() = runBlocking {
        val personDAO = createRestCall(IPersonDAO::class.java)
        val callPerson: Call<List<Person>> = personDAO.findAll()
        callPerson.enqueue(object : Callback<List<Person>> {
            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                textView.text = t.message
            }

            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                if (!response.isSuccessful) {
                    textView.text = "code " + response.code()
                    return
                }

                if (response.body() != null) {
                    val persons: List<Person> = response.body()!!
                    var text: String = ""

                    for (person in persons) {
                        text = text + "\n " + person.firstname + person.lastname
                    }

                    textView.text = text
                }
            }
        }
        )
    }

    suspend fun findById() = runBlocking {
        val personDAO = createRestCall(IPersonDAO::class.java)
        val callPerson: Call<Person> = personDAO.findById(1)
        callPerson.enqueue(object : Callback<Person> {
            override fun onFailure(call: Call<Person>, t: Throwable) {
                textView.text = t.message
            }

            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (!response.isSuccessful) {
                    textView.text = "code " + response.code()
                    return
                }

                if (response.body() != null) {
                    val person: Person = response.body()!!

                    textView.text =
                        "Person with id $person.id is ${person.firstname} ${person.lastname}"
                }
            }
        }
        )
    }

    suspend fun addPerson() = runBlocking {
        var person = Person()
        person.firstname = "New User Firstname"
        person.lastname = "New User Lastname"

        val personDAO = createRestCall(IPersonDAO::class.java)
        val call: Call<Person> = personDAO.addPerson(person)
        call.enqueue(object : Callback<Person> {
            override fun onFailure(call: Call<Person>, t: Throwable) {
                textView.text = t.message
            }

            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (!response.isSuccessful) {
                    textView.text = "code " + response.code()
                    return
                }

                if (response.body() != null) {
                    val person: Person = response.body()!!
                    textView.text = "Person ${person.firstname} ${person.lastname} was added"
                }
            }
        })
    }

    private fun <T> createRestCall(requestAPI: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(requestAPI)
    }
}