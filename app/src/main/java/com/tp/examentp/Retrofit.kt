package com.tp.examentp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun createApiService(): MovieApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MovieApiService::class.java)
        }
    }

