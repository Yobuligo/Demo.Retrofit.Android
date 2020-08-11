package com.yobuligo.demoretrofitandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.yobuligo.demoretrofitandroid.model.person.PersonDTO
import com.yobuligo.demoretrofitandroid.model.team.TeamDTO
import com.yobuligo.demoretrofitandroid.model.person.IPersonDTOService
import com.yobuligo.demoretrofitandroid.model.team.ITeamDTOService
import com.yobuligo.demoretrofitandroid.services.ServiceExecutor
import com.yobuligo.demoretrofitandroid.services.ServiceFactory
import kotlinx.coroutines.*
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

        ServiceExecutor().execute<TeamDTO>(
            ServiceFactory().createService(ITeamDTOService::class.java).addTeam(teamDTO)
        )
    }

    suspend fun findAllPersons() = runBlocking {
        val pageRequestDTO = ServiceExecutor().execute(
            ServiceFactory().createService(IPersonDTOService::class.java)
                .findAll("1", "3", "firstname")
        )

        var text = ""
        for (personDTO in pageRequestDTO.content) {
            text =
                text + "\n " + personDTO.id + " " + personDTO.firstname + " " + personDTO.lastname
        }

        textView.text = text
    }

    suspend fun findById() = runBlocking {
        val id: Long = findViewById<TextView>(R.id.txt_id).text.toString().toLong()

        val personDTO = ServiceExecutor().execute(
            ServiceFactory().createService(IPersonDTOService::class.java).findById(id)
        )

        textView.text =
            "Person with id ${personDTO.id} is ${personDTO.firstname} ${personDTO.lastname}"
    }

    suspend fun addPerson() = runBlocking {
        var personDTO = PersonDTO()
        personDTO.firstname = findViewById<TextView>(R.id.txt_firstname).text.toString()
        personDTO.lastname = findViewById<TextView>(R.id.txt_lastname).text.toString()

        val addedPersonDTO = ServiceExecutor().execute(
            ServiceFactory().createService(IPersonDTOService::class.java).addPerson(personDTO)
        )

        textView.text =
            "Person ${addedPersonDTO.id} ${addedPersonDTO.firstname} ${addedPersonDTO.lastname} was added"
    }

    suspend fun findAllTeams() = runBlocking {
        val teamsDTOs = ServiceExecutor().execute(
            ServiceFactory().createService(ITeamDTOService::class.java).findAll()
        )
    }
}