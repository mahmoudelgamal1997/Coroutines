package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.coroutines.api.WeatherClient
import com.example.coroutines.api.WeatherInterface
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val api = WeatherClient.getClient()
        val repoistory=DataRepoistory(api)
        val viewmodel = MainViewModel(repoistory)

        but.setOnClickListener({
        viewmodel.loadData()
        viewmodel.foo.observe(this, Observer {
        Log.e("Dataaa",it.title)
        })

        })






    }
    suspend fun SetNewText(input:String){
        val newTxt=input.toString()
        withContext(Main){
            text.setText(newTxt)
        }
    }

     fun makeHeavyTask(){
        val parentJob= CoroutineScope(Dispatchers.IO).launch {
            val job1 = launch {
                Log.e("getResultFromAPI1", getResultFromApi())
            }

            job1.invokeOnCompletion {
                Log.e("onComplete","job1 is complete")
            }
            val job2 = launch {
                Log.e("getResultFromAPI2", getResult2FromApi())
            }
        }
    }

     fun parallelJob()
    {

        CoroutineScope(IO).launch{
        val time= measureTimeMillis {

            val job1: Deferred<String> = async {

                println("job1 Thread is ${Thread.currentThread().name}")
                getResultFromApi()

            }

            val job2 :Deferred<String> = async {
                println("job2 Thread is ${Thread.currentThread().name}")
                getResult2FromApi()
            }

            SetNewText(job1.await())
            SetNewText(job2.await())

        }
            println("time Execution ${time}")


        }


    }


    suspend fun fakeApiRequest(){

        val startTime= System.currentTimeMillis()
        val parentJob= withContext(IO) {

                val job1 = launch {
                    val result1 = getResultFromApi()
                    SetNewText(result1)
                    println("job 1 : ${Thread.currentThread().name}")
                    println("job1 : ${result1}")
                }

            val job2 =launch {
                val result2 =  getResult2FromApi()
                SetNewText(result2)
                println("job 2 : ${Thread.currentThread().name}")
                println("job2 : ${result2}")

            }

                }

       }



     suspend fun getResultFromApi():String{
         logThread("getResultFromAPI")
         kotlinx.coroutines.delay(5000)
         return "Result 1"
     }

    suspend fun getResult2FromApi():String{
        logThread("getResultFromAPI")
        kotlinx.coroutines.delay(2000)
        return "Result 2"
    }

    fun  logThread(methodname:String){
        println("debug"+"${methodname} : ${Thread.currentThread().name}")
    }
}
