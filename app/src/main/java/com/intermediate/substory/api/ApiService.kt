package com.intermediate.substory.api

import com.intermediate.substory.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getStoryById(
        @Path("id") id: String,
        @Header("Authorization") authorization: String
    ): Detail

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?,
        @Header("Authorization") token: String
    ): UploadStory

}