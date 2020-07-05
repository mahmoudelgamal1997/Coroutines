package com.example.coroutines

import android.util.Log
import com.elgaml.ecommerce.data.source.datamodel.CategoryModel
import com.elgaml.ecommerce.data.source.datamodel.Data
import com.example.coroutines.api.WeatherInterface
import com.example.moviesapp.MovieMVVM.data.vo.MovieDetails
import com.example.moviesapp.MovieMVVM.popular_model.popular
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class DataRepoistory(val api:WeatherInterface) {


    fun getFoo(): kotlinx.coroutines.flow.Flow<List<popular>> {
        return flow {
            // exectute API call and map to UI object
            val fooList = api.getPopularMovie(2)

            // Emit the list to the stream
            emit(fooList)

        }.flowOn(Dispatchers.IO).onCompletion {
            Log.e("Sucess","success ")
        } // Use the IO thread for this Flow
}


fun getMovieDetails(): Flow<MovieDetails> {
 return flow {
  // exectute API call and map to UI object
 var fooList = api.loadData(2)
  // Emit the list to the stream
 emit(fooList)
}.flowOn(Dispatchers.IO)
     .map {
    it.title+="  medoo"
    it
     }
     .onCompletion {
         Log.e("Sucess","success ")
     }

}


}