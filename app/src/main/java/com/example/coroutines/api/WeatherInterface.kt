package com.example.coroutines.api

import com.elgaml.ecommerce.data.source.datamodel.CategoryModel
import com.elgaml.ecommerce.data.source.datamodel.topDealModel
import com.example.coroutines.model.WeatherResponse
import com.example.moviesapp.MovieMVVM.data.vo.MovieDetails
import com.example.moviesapp.MovieMVVM.popular_model.popular
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.security.auth.callback.Callback

interface WeatherInterface{


    @GET("categories")
   suspend fun getCategory():List<CategoryModel>


    @GET("home")
    suspend fun getHome():List<topDealModel>

    @GET("movie/popular")
   suspend fun getPopularMovie(@Query("page") page:Int):List<popular>

    @GET("movie/{movie_id}")
   suspend fun loadData(@Path("movie_id") id:Int):MovieDetails


}