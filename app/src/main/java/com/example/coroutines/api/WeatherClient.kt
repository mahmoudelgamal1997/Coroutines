package com.example.coroutines.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



const val KeyAPI="66575115dec5f4cb026c527ef6750e9d"
const val BaseURL="https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20


// https://image.tmdb.org/t/p/w342/or06FN3Dka5tukK1e9sl16pB3iy.jpg

object WeatherClient
{

    fun  getClient():WeatherInterface{


        val requestInceptor= Interceptor{ chain ->

            val url =chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", KeyAPI)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)

        }

        val okHttp= OkHttpClient.Builder()
            .addInterceptor(requestInceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttp)
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
            .create(WeatherInterface::class.java)

    }


}