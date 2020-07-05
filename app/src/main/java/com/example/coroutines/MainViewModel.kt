package com.example.coroutines

import android.util.Log
import androidx.lifecycle.*
import com.elgaml.ecommerce.data.source.datamodel.CategoryModel
import com.example.moviesapp.MovieMVVM.data.vo.MovieDetails
import com.example.moviesapp.MovieMVVM.popular_model.popular
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(val repository:DataRepoistory):ViewModel() {
    private val _foo = MutableLiveData<MovieDetails>()
    val foo: LiveData<MovieDetails> get() = _foo


    fun loadData(){
        viewModelScope.launch {
            repository.getMovieDetails()
                .onStart {
                    Log.e("startingg","Starting")
                }
                .catch { exception -> /* _foo.value = error state */
                    Log.e("exception",exception.message)
                }
                .collect{data->

                _foo.value = data
                Log.e("MovieDetails",data.title.toString())
            }

        }
    }
}