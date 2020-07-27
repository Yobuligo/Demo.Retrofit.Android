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

        val mindsetAPI: IMindSetAPI = retrofit.create(IMindSetAPI::class.java)
        val call: Call<List<MindSet>> = mindsetAPI.getMindsets()
        call.enqueue(object : Callback<List<MindSet>> {
            override fun onFailure(call: Call<List<MindSet>>, t: Throwable) {
                textView.setText(t.message)
            }

            override fun onResponse(call: Call<List<MindSet>>, response: Response<List<MindSet>>) {
                if (!response.isSuccessful) {
                    textView.setText("code " + response.code())
                    return
                }

                if (response.body() != null) {
                    val mindsets: List<MindSet> = response.body()!!
                    var text: String = ""

                    for (mindset in mindsets) {
                        text = text + "\n" + mindset.description
                    }

                    textView.setText(text)
                }
            }
        }
        )
    }
}