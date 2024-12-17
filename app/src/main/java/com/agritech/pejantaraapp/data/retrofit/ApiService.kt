package com.agritech.pejantaraapp.data.retrofit

import com.agritech.pejantaraapp.data.Article
import com.agritech.pejantaraapp.data.retrofit.response.TrashPredictResponse
import com.agritech.pejantaraapp.data.retrofit.response.VideosResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("classify")
    suspend fun predictTrash(
        @Part file: MultipartBody.Part,
    ): TrashPredictResponse

    @GET("video")
    suspend fun getVideos(): VideosResponse

    @GET("articles")
    suspend fun getArticles(): Response<List<Article>>

//    @POST("email/send-code")
//    suspend fun sendVerificationEmail(@Body email: String): Response<Void>
//
//    @POST("email/verify-code")
//    suspend fun verifyEmailCode(@Body code: String): Response<Void>
//
//    @POST("email/resend-code")
//    suspend fun resendVerificationEmail(): Response<Void>
}