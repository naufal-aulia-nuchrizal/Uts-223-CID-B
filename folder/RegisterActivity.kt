package com.example.essay_uts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val profileImage = findViewById<ImageView>(R.id.profileImage)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val registerBtn = findViewById<Button>(R.id.registerBtn)

        // Load image from internet
        Glide.with(this)
            .load("https://i.pravatar.cc/300")
            .into(profileImage)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val jsonUsers = sharedPref.getString("users", "{}")
        val type = object : TypeToken<MutableMap<String, String>>() {}.type
        val userMap: MutableMap<String, String> = Gson().fromJson(jsonUsers, type)

        registerBtn.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else if (userMap.containsKey(username)) {
                Toast.makeText(this, "Username sudah digunakan", Toast.LENGTH_SHORT).show()
            } else {
                userMap[username] = password
                sharedPref.edit().putString("users", Gson().toJson(userMap)).apply()
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
