package com.example.myapplication
import android.app.ActivityManager
import android.content.Context
import com.google.android.filament.utils.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.nio.ByteBuffer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.google.android.filament.Skybox


class Display : AppCompatActivity()  {
    private lateinit var surfaceView: SurfaceView
    private lateinit var choreographer: Choreographer
    private lateinit var modelViewer: ModelViewer

    companion object {
        init { Utils.init() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        var namei =  "facial_expressions"

        System.out.println(namei);
        super.onCreate(savedInstanceState)
        surfaceView = SurfaceView(this).apply{
            setContentView(this)
        }
        // setContentView(R.layout.activity_main)
        choreographer = Choreographer.getInstance()
        modelViewer = ModelViewer(surfaceView)
        surfaceView.setOnTouchListener(modelViewer)

        //display
        loadGltf(namei.toString())
        modelViewer.scene.skybox = Skybox.Builder().build(modelViewer.engine)
        loadEnvironment("venetian_crossroads_2k")
    }
    fun getMemoryInfo(context: Context): ActivityManager.MemoryInfo
    {
        val activityManager=context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryinfo=ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryinfo)
        return memoryinfo;
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when(level)
        {
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
                ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
                ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL->
            {
          //      bitmap.recycle()
                System.gc()
            }

        }
    }

    fun getAllGLBFileNames(context: Context): List<String> {
        val assetManager = context.assets
        val glbFileNames = mutableListOf<String>()

        try {
            val fileList = assetManager.list("models") // Assuming "models" is the directory name

            if (fileList != null) {
                for (file in fileList) {
                    if (file.endsWith(".glb")) {
                        glbFileNames.add(file)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return glbFileNames
    }

    private val frameCallback = object : Choreographer.FrameCallback {
        private var currentAnimationIndex = 0
        private val startTime = System.nanoTime()
        private var seconds = 0.0
        var count=0.0
        private var nextAnimationIndex = 1 // Index of the next animation to play

        override fun doFrame(currentTime: Long) {
            val elapsedSeconds = (currentTime - startTime).toDouble() / 1_000_000_000
            choreographer.postFrameCallback(this)
            modelViewer.animator?.apply {
                if (animationCount > 0) {
//                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                    System.out.println(modelViewer.animator?.getAnimationName(currentAnimationIndex));
                    val currentAnimationDuration = getAnimationDuration(currentAnimationIndex).toFloat()
                    val nextAnimationDuration = getAnimationDuration(nextAnimationIndex).toFloat()
                    val currentAnimationTime = (seconds - count) // Time spent in the current animation

                    // Calculate blend weight for current and next animations
                    val currentBlendWeight = 1.0f - (currentAnimationTime / currentAnimationDuration).toFloat()

                    val nextBlendWeight = 1.0f - ((nextAnimationDuration - currentAnimationTime) / nextAnimationDuration).toFloat()

                    applyAnimation(currentAnimationIndex, currentBlendWeight)
                    applyAnimation(nextAnimationIndex, nextBlendWeight)

                    // Check if current animation is about to end, and switch to the next animation
                    if (currentAnimationTime >= currentAnimationDuration - 1.0f) {
                        count += currentAnimationDuration
                        currentAnimationIndex = nextAnimationIndex
                        nextAnimationIndex = (currentAnimationIndex + 1) % animationCount
                    }
                    seconds = elapsedSeconds
                }
                updateBoneMatrices()
            }
            modelViewer.render(0)
        }
    }
    override fun onResume() {
        super.onResume()
        choreographer.postFrameCallback(frameCallback)
        //modelViewer.startAnimation()
    }
    override fun onPause() {
        super.onPause()
        choreographer.removeFrameCallback(frameCallback)
        // modelViewer.stopAnimation()
    }
    override fun onDestroy() {
        super.onDestroy()
        choreographer.removeFrameCallback(frameCallback)
        //modelViewer.release()
    }
    private fun loadGltf(name: String) {
        val buffer = readAsset("models/${name}.gltf")

        modelViewer.loadModelGltf(buffer) { uri -> readAsset("models/$uri") }
        modelViewer.transformToUnitCube()
    }
    private fun readAsset(assetName: String): ByteBuffer {
        val input = assets.open(assetName)
        val bytes = ByteArray(input.available())
        input.read(bytes)
        return ByteBuffer.wrap(bytes)
    }
    private fun loadEnvironment(ibl: String) {
        // Create the indirect light source and add it to the scene.
        var buffer = readAsset("envs/$ibl/${ibl}_ibl.ktx")
        KtxLoader.createIndirectLight(modelViewer.engine, buffer).apply {
            intensity = 50_000f
            modelViewer.scene.indirectLight = this
        }

        // Create the sky box and add it to the scene.
        buffer = readAsset("envs/$ibl/${ibl}_skybox.ktx")
        KtxLoader.createSkybox(modelViewer.engine, buffer).apply{
            modelViewer.scene.skybox = this
        }
    }
}