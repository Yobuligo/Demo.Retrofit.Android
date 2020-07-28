package com.yobuligo.demoretrofitandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)

        GlobalScope.launch(Dispatchers.Main) { doWebserviceCall() }
    }

    suspend fun doWebserviceCall() = runBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val personDAO: IPersonDAO = retrofit.create(IPersonDAO::class.java)
        val callPerson: Call<List<Person>> = personDAO.getPersons()
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
}