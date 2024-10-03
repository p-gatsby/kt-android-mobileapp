package com.dev.nextchapter.data

import com.dev.nextchapter.BuildConfig

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit Instance
private val BASE_URL = "https://www.googleapis.com/books/v1/"

private val API_KEY = BuildConfig.GOOGLE_API_KEY

private val retrofit: Retrofit =
    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .build()

// Create the API instance
val googleService: GoogleBooksApiService = retrofit.create(GoogleBooksApiService::class.java)

// API Interface
interface GoogleBooksApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String = API_KEY
    ): BookListResponse

    @GET("volumes/{volumeId}")
    suspend fun getBookDetails(
        @Path("volumeId") volumeId: String,
        @Query("key") apiKey: String = API_KEY
    ) : Book
}

