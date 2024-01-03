//package com.example.myapplication
//
//import android.app.Service
//import android.content.Intent
//import android.os.IBinder
//import android.view.Choreographer
//import android.view.LayoutInflater
//import android.view.SurfaceView
//import android.view.ViewGroup
//import com.google.android.filament.utils.*
//import android.widget.FrameLayout
////import com.example.myapplication.MainActivity.Companion.glContainerx
//import com.google.android.filament.Skybox
//import java.nio.ByteBuffer
//
//
//object GlobalContainerHolder {
//    var glContainer: FrameLayout? = null
//}
//
//class LoadGlb : Service() {
//    private var floatView: ViewGroup? = null
//    private lateinit var surfaceView: SurfaceView
//    private lateinit var choreographer: Choreographer
//    private lateinit var modelViewer: ModelViewer
//
//    companion object {
//        init {
//            Utils.init()
//        }
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        println("iiiiiiiiiiiAAAAAAAAAAAAAAAARrrrrrrrrrrrrrrrrrrrrrrrr");
//        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
////        floatView = inflater.inflate(R.layout.overlay_layout, null) as ViewGroup
////        glContainerx = floatView!!.findViewById<FrameLayout>(R.id.gl_container)
////        val glSurfaceView = SurfaceView(this).apply {
////            glContainerx
////        }
////        glContainerx.addView(glSurfaceView)
//
//        choreographer = Choreographer.getInstance()
//        modelViewer = ModelViewer(glSurfaceView)
//        glSurfaceView.setOnTouchListener(modelViewer);
//        choreographer.postFrameCallback(frameCallback)
//
//        var namei =  "Bee"
//        loadGltf(namei.toString())
//        modelViewer.scene.skybox = Skybox.Builder().build(modelViewer.engine)
//        loadEnvironment("venetian_crossroads_2k")
//        choreographer.postFrameCallback(frameCallback)
//        return START_STICKY
//    }
//    override fun onBind(p0: Intent?): IBinder? {
//        TODO("Not yet implemented")
//    }
//    private val frameCallback = object : Choreographer.FrameCallback {
//        private var currentAnimationIndex = 0
//        private val startTime = System.nanoTime()
//        private var seconds = 0.0
//        var count=0.0
//        private var nextAnimationIndex = 1 // Index of the next animation to play
//
//        override fun doFrame(currentTime: Long) {
//            val elapsedSeconds = (currentTime - startTime).toDouble() / 1_000_000_000
//            choreographer.postFrameCallback(this)
//            modelViewer.animator?.apply {
//                if (animationCount > 0) {
////                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
////                    System.out.println(modelViewer.animator?.getAnimationName(currentAnimationIndex));
//                    val currentAnimationDuration = getAnimationDuration(currentAnimationIndex).toFloat()
//                    val nextAnimationDuration = getAnimationDuration(nextAnimationIndex).toFloat()
//                    val currentAnimationTime = (seconds - count) // Time spent in the current animation
//
//                    // Calculate blend weight for current and next animations
//                    val currentBlendWeight = 1.0f - (currentAnimationTime / currentAnimationDuration).toFloat()
//
//                    val nextBlendWeight = 1.0f - ((nextAnimationDuration - currentAnimationTime) / nextAnimationDuration).toFloat()
//
//                    applyAnimation(currentAnimationIndex, currentBlendWeight)
//                    applyAnimation(nextAnimationIndex, nextBlendWeight)
//
//                    // Check if current animation is about to end, and switch to the next animation
//                    if (currentAnimationTime >= currentAnimationDuration - 1.0f) {
//                        count += currentAnimationDuration
//                        currentAnimationIndex = nextAnimationIndex
//                        nextAnimationIndex = (currentAnimationIndex + 1) % animationCount
//                    }
//                    seconds = elapsedSeconds
//                }
//                updateBoneMatrices()
//            }
//            modelViewer.render(0)
//        }
//    }
//
//
//    private fun loadGltf(name: String) {
//        val buffer = readAsset("models/${name}.glb")
//        System.out.println("dddddddddddddddddddddddddddddddddddddddddddddd");
////        System.out.println(buffer);
//        modelViewer.loadModelGltf(buffer) { uri -> readAsset("models/$uri") }
//        modelViewer.transformToUnitCube()
//        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
//    }
//    private fun readAsset(assetName: String): ByteBuffer {
//        val input = assets.open(assetName)
//        val bytes = ByteArray(input.available())
//        input.read(bytes)
//        return ByteBuffer.wrap(bytes)
//    }
//    private fun loadEnvironment(ibl: String) {
//        // Create the indirect light source and add it to the scene.
//        var buffer = readAsset("envs/$ibl/${ibl}_ibl.ktx")
//        KtxLoader.createIndirectLight(modelViewer.engine, buffer).apply {
//            intensity = 50_000f
//            modelViewer.scene.indirectLight = this
//        }
//
//        // Create the sky box and add it to the scene.
//        buffer = readAsset("envs/$ibl/${ibl}_skybox.ktx")
//        KtxLoader.createSkybox(modelViewer.engine, buffer).apply{
//            modelViewer.scene.skybox = this
//        }
//    }
//}
//
