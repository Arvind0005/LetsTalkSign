package com.example.myapplication

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.TextView

class SelectToSpeakService : AccessibilityService() {

    private var readingTextView: TextView? = null
    private val textView: TextView? = null
    override fun onServiceConnected() {
       // val app = application as MyApp
        System.out.println("text service connected");
        super.onServiceConnected()
      //  val info = AccessibilityServiceInfo()
//        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
//        serviceInfo = info

        // Create and register a BroadcastReceiver

        val intentFilter = IntentFilter("READING_TEXT")

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        readingTextView = textView?.findViewById<TextView>(R.id.text20);
        val eventType = event.eventType
        val selectedText = event.text
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val view = event.source
            view?.isFocusable=true
            val text = event.text.toString()
            Log.d("MyAccessibilityService", "Text: $text")
        }
        if (selectedText != null && !selectedText.toString().isEmpty()) {
            System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
            System.out.println(selectedText);
            // Send broadcast with the reading text
            val broadcastIntent = Intent("READING_TEXT")
            readingTextView?.setText("hello");
//
//                app.sharedData= selectedText.toString();
            val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("data", selectedText);
////                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////                startActivity(intent)
//            broadcastIntent.putExtra("READING_TEXT", selectedText.toString());
//            sendBroadcast(broadcastIntent);
        }
    }


    override fun onInterrupt() {
        // Do nothing
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the BroadcastReceiver
//        unregisterReceiver(broadcastReceiver)
    }
}