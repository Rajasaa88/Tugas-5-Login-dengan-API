package com.example.tugas5

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val success: Boolean, val message: String, val data: LoginData?)
data class LoginData(val token: String, val user: User)
data class User(val name: String, val email: String)

data class PasienResponse(val success: Boolean, val message: String, val data: List<Pasien>?)
data class Pasien(
    val id: Int,
    val nama: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String,
    val alamat: String,
    val no_telepon: String
)

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("pasien")
    suspend fun getPasien(@Header("Authorization") token: String): Response<PasienResponse>
}

// 3. RETROFIT CLIENT
object ApiClient {
    private const val BASE_URL = "https://api.pahrul.my.id/api/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}