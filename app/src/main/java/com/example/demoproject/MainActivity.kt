package com.example.demoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : BasicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[BasicViewModel::class.java]

        customLaunchEffect(viewModel.customString){
            Log.d("test1", viewModel.customString.value.toString())
        }
        viewModel.changeValue()

    }

    private fun customLaunchEffect(key: MutableLiveData<String>, callback: ()->Unit){
        var oldKey = ""
        key.observe(this){
            if (oldKey!=it){
                oldKey = it
                callback()
            }
        }
    }
}

class BasicViewModel : ViewModel(){

    val customString : MutableLiveData<String> = MutableLiveData("abc")

    fun changeValue(){
        CoroutineScope(Dispatchers.IO).launch{
            for (i in 0 until 5){
                delay(5000)
                customString.postValue("abc$i")
            }
        }
    }
}