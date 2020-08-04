package com.yobuligo.demoretrofitandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.yobuligo.demoretrofitandroid.model.InternalCall
import com.yobuligo.demoretrofitandroid.model.person.PersonDTO
import com.yobuligo.demoretrofitandroid.model.team.TeamDTO
import com.yobuligo.demoretrofitandroid.model.person.IPersonDTOService
import com.yobuligo.demoretrofitandroid.model.team.ITeamDTOService
import com.yobuligo.demoretrofitandroid.services.IServiceExecutorBuilder
import com.yobuligo.demoretrofitandroid.services.ServiceBuilder
import com.yobuligo.demoretrofitandroid.services.ServiceExecutorBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val btnAddPerson: Button = findViewById(R.id.btn_persons_add)
        btnAddPerson.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.Main) { addPerson() }
            GlobalScope.launch(Dispatchers.Main) { findAllTeams() }
        })

        val btnAddTeam: Button = findViewById(R.id.btn_teams_add)
        btnAddTeam.setOnClickListener(View.OnClickListener {
            GlobalScope.launch(Dispatchers.Main) { addTeam() }
        })
    }

    private suspend fun addTeam() = runBlocking {
        val editText: EditText = findViewById(R.id.txt_team_name)
        val teamDTO = TeamDTO()
        teamDTO.name = editText.text.toString()

        val teamDTOService = ServiceBuilder().build(ITeamDTOService::class.java)
        val call = teamDTOService.addTeam(teamDTO)
        call.enqueue(object : Callback<TeamDTO> {
            override fun onFailure(call: Call<TeamDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<TeamDTO>, response: Response<TeamDTO>) {
                Log.i("addTeam", "onResponse: Adding team worked")
            }
        }
        )

    }

    suspend fun findAllPersons() = runBlocking {
        val personService = ServiceBuilder()
            .build(IPersonDTOService::class.java)
        val callPersonDTO: Call<List<PersonDTO>> = personService.findAll()
        callPersonDTO.enqueue(object : Callback<List<PersonDTO>> {
            override fun onFailure(call: Call<List<PersonDTO>>, t: Throwable) {
                textView.text = t.message
            }

            override fun onResponse(
                call: Call<List<PersonDTO>>,
                response: Response<List<PersonDTO>>
            ) {
                if (!response.isSuccessful) {
                    textView.text = "code " + response.code()
                    return
                }

                if (response.body() != null) {
                    val personDTOS: List<PersonDTO> = response.body()!!
                    var text: String = ""

                    for (person in personDTOS) {
                        text =
                            text + "\n " + person.id + " " + person.firstname + " " + person.lastname
                    }

                    textView.text = text
                }
            }
        }
        )
    }

    suspend fun findById() = runBlocking {
        val id: Long = findViewById<TextView>(R.id.txt_id).text.toString().toLong()
        val personService = ServiceBuilder()
            .build(IPersonDTOService::class.java)
        val callPersonDTO: Call<PersonDTO> = personService.findById(id, "firstname,lastname")



        callPersonDTO.enqueue(object : Callback<PersonDTO> {
            override fun onFailure(call: Call<PersonDTO>, t: Throwable) {
                textView.text = t.message
            }

            override fun onResponse(call: Call<PersonDTO>, response: Response<PersonDTO>) {
                if (!response.isSuccessful) {
                    textView.text = "code " + response.code()
                    return
                }

                if (response.body() != null) {
                    val personDTO: PersonDTO = response.body()!!

                    textView.text =
                        "Person with id ${personDTO.id} is ${personDTO.firstname} ${personDTO.lastname}"
                }
            }
        }
        )
    }

    suspend fun addPerson() = runBlocking {
        var person = PersonDTO()
        person.firstname = findViewById<TextView>(R.id.txt_firstname).text.toString()
        person.lastname = findViewById<TextView>(R.id.txt_lastname).text.toString()

        val personService = ServiceBuilder()
            .build(IPersonDTOService::class.java)
        val call: Call<PersonDTO> = personService.addPerson(person)
        call.enqueue(object : Callback<PersonDTO> {
            override fun onFailure(call: Call<PersonDTO>, t: Throwable) {
                textView.text = t.message
            }

            override fun onResponse(call: Call<PersonDTO>, response: Response<PersonDTO>) {
                if (!response.isSuccessful) {
                    textView.text = "code " + response.code()
                    return
                }

                if (response.body() != null) {
                    val personDTO: PersonDTO = response.body()!!
                    textView.text =
                        "Person ${personDTO.id} ${personDTO.firstname} ${personDTO.lastname} was added"
                }
            }
        })
    }

    suspend fun findAllTeams() = runBlocking {

        //Service
        //ExpectedResult
/*        List<TeamDTO> teamDTO = ServiceBuilder(ITeamDTOService::class.java)
            .call().findAll()
            .setResult(List<TeamDTO>)
            .execute()*/


        var teamService = ServiceBuilder().build(ITeamDTOService::class.java)
        val call: Call<List<TeamDTO>> = teamService.findAll()
        call.enqueue(object : Callback<List<TeamDTO>> {
            override fun onFailure(call: Call<List<TeamDTO>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<TeamDTO>>, response: Response<List<TeamDTO>>) {
                if (!response.isSuccessful) {
                    return
                }

                if (response.body() != null) {
                    val teams = response.body()
                    val team = teams?.get(0)
                    Log.i("Test", "onResponse: ${team?.description} ${team?.name}")

                }
            }
        }
        )
    }
}