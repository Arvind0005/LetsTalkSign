package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Logs : AppCompatActivity() {
//    var message :String="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logs)
        var logsintent = intent
        var message = logsintent.getStringExtra("message")
        println("hehhhhhhhhheeeelo");
        println(message);

        println("hello world");
        var textView =findViewById<TextView>(R.id.clogs);
        textView.setText(message.toString());
    }
}