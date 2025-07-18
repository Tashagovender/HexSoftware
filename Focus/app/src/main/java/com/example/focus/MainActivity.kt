package com.example.focus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            //allows us to go to the next screen
            val intent = Intent(this@MainActivity, Screentwo::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}//main ends
