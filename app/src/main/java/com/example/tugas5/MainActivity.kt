package com.example.tugas5

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugas5.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken()

        if (token == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val userName = sessionManager.fetchUserName()
        binding.tvGreeting.text = "Halo, $userName!"

        binding.btnLogout.setOnClickListener {
            sessionManager.clearSession()

            Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.rvPasien.layoutManager = LinearLayoutManager(this)

        fetchDataPasien(token)
    }

    private fun fetchDataPasien(token: String) {
        showLoading(true)
        val bearerToken = "Bearer $token"

        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.getPasien(bearerToken)
                showLoading(false)

                if (response.isSuccessful && response.body()?.success == true) {
                    val dataPasien = response.body()?.data ?: emptyList()
                    val adapter = PasienAdapter(dataPasien)
                    binding.rvPasien.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Gagal mengambil data pasien", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                showLoading(false)
                Toast.makeText(this@MainActivity, "Error koneksi jaringan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressMain.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvPasien.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}