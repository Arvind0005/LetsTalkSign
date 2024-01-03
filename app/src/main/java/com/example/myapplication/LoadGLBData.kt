package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.Choreographer
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.filament.utils.ModelViewer

class LoadGLBData : Service() {

    private lateinit var surfaceView: SurfaceView
    private lateinit var choreographer: Choreographer
    private lateinit var modelViewer: ModelViewer

    var floatView: ViewGroup? = null
    var glContainer: FrameLayout? =null
    var glSurfaceView: SurfaceView? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatView = inflater.inflate(R.layout.overlay_layout, null) as ViewGroup
        println("MALA NALLA IRUKAIYA MALA");


        glSurfaceView = SurfaceView(this).apply {
            glContainer
        }
        glContainer?.addView(glSurfaceView)

        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}