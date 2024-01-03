//package com.example.myapplication//package com.example.myapplication
////
//
//import android.annotation.SuppressLint
//import android.app.usage.UsageStatsManager
//import android.content.Intent
//import android.content.pm.ApplicationInfo
//import android.content.pm.PackageManager
//import android.graphics.drawable.Drawable
//import android.graphics.drawable.Icon
//import android.net.Uri
//import android.os.Bundle
//import android.provider.Settings
//import android.speech.RecognizerIntent
//import android.util.Log
//import android.view.MotionEvent
//import android.view.View
//import android.view.View.OnTouchListener
//import android.webkit.ConsoleMessage
//import android.webkit.WebChromeClient
//import android.webkit.WebSettings
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import android.widget.AdapterView
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.ListView
//import android.widget.MediaController
//import android.widget.ScrollView
//import android.widget.SearchView
//import android.widget.TextView
//import android.widget.Toast
//import android.widget.VideoView
//import androidx.annotation.Nullable
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.os.LocaleListCompat
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.FirebaseAuth
//import com.google.mlkit.common.model.DownloadConditions
//import com.google.mlkit.nl.languageid.LanguageIdentification
//import com.google.mlkit.nl.languageid.LanguageIdentificationOptions
//import com.google.mlkit.nl.translate.TranslateLanguage
//import com.google.mlkit.nl.translate.Translation
//import com.google.mlkit.nl.translate.TranslatorOptions
//import java.util.Collections
//import java.util.Locale
//import java.util.Objects
//
//
//class HelpPage: AppCompatActivity(){
//
//    lateinit var mGoogleSignInClient: GoogleSignInClient
//
//
//    private val auth by lazy {
//        FirebaseAuth.getInstance()
//    }
//    var app_names = arrayOf("whatsapp,youtube,browser")
//    val NEW_SPINNER_ID = 1
//    private var tv_Speech_to_text: TextView? = null
//
//    val str:String=""
//    private val REQUEST_CODE_SPEECH_INPUT = 1
//    private lateinit var webView: WebView
//
//    val options = TranslatorOptions.Builder()
//        .setSourceLanguage(TranslateLanguage.TAMIL)
//        .setTargetLanguage(TranslateLanguage.ENGLISH)
//        .build()
//    val englishGermanTranslator = Translation.getClient(options)
//
//    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.help_page)
////        val logoutic=findViewById<ImageView>(R.id.logout_ic)
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("238702597146-4f34ai7mlr0n81r85rn3ih0mpi4f79a2.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//
////        logoutic.setOnClickListener {
////            mGoogleSignInClient.signOut().addOnCompleteListener {
////                val intent = Intent(this, MainActivity::class.java)
////                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
////                startActivity(intent)
////                finish()
////            }
////        }
//
//        tv_Speech_to_text = findViewById<TextView>(R.id.webview_text);
//
////        val interpretButton =findViewById<Button>(R.id.interpretButton);
//
//        val dropdownbutton0: ImageView =findViewById(R.id.dropdown_button);
//        val permisionText0: LinearLayout=findViewById(R.id.permission_description);
//
//        val yt_usage_button:LinearLayout =findViewById(R.id.dropdownusage);
//        val yt_description: LinearLayout=findViewById(R.id.Youtube_description);
//
//        val wts_usage_button:LinearLayout=findViewById(R.id.dropdownusagewhatsapp);
//        val wts_description:LinearLayout =findViewById(R.id.whatsapp_description);
//
//        val chrome_usage_button:LinearLayout=findViewById(R.id.dropdownusagechrome);
//        val chrome_description:LinearLayout=findViewById(R.id.chrome_description);
//
//        val helppagebutton:ImageView=findViewById<ImageView>(R.id.help_ic)
//        val helpage:LinearLayout =findViewById(R.id.help_page);
//
//
//        val homeButton:ImageView =findViewById(R.id.helpthome);
//        val homepage:LinearLayout=findViewById(R.id.home_page);
//
//        val homeselection:View =findViewById(R.id.selectionhome);
//        val helpselction:View=findViewById(R.id.selectionhelp);
//        val micselection:View=findViewById(R.id.mic_page);
//        val micbar:View=findViewById(R.id.selectionmic);
//
//        val micButton:ImageView=findViewById(R.id.mic0_ic);
//        val searchSelection:LinearLayout =findViewById(R.id.search_bar)
//        val appbar:LinearLayout =findViewById(R.id.Appbar)
//
//        val settingsIc: ImageView = findViewById(R.id.settings_ic)
//
//        settingsIc.setOnClickListener {
//            val intent = Intent(Settings.ACTION_SETTINGS)
//            startActivity(intent)
//        }
//
//        val text1: TextView =findViewById(R.id.apptext0);
//        val text2: TextView =findViewById(R.id.apptext1);
//        val text3: TextView =findViewById(R.id.apptext2);
//        val text4: TextView =findViewById(R.id.apptext3);
//        val text5: TextView =findViewById(R.id.apptext4);
//        val text6: TextView =findViewById(R.id.apptext5);
//
//
//        val ic_1: ImageView =findViewById(R.id.app_ic0);
//        val ic_2: ImageView =findViewById(R.id.app_ic1);
//        val ic_3: ImageView =findViewById(R.id.app_ic2);
//        val ic_4: ImageView =findViewById(R.id.app_ic3);
//        val ic_5: ImageView =findViewById(R.id.app_ic4);
//        val ic_6: ImageView =findViewById(R.id.app_ic5);
//
//
//        val videoView: VideoView = findViewById(R.id.yt_video)
//        val whatsapp_video: VideoView=findViewById(R.id.whatsapp_video);
//        val chrome_video: VideoView=findViewById(R.id.chrome_video);
//
//        // Set the path of the video file (res/raw/sample.mp4)
//        val path = "android.resource://" + packageName + "/" + R.raw.yt_video
//        val pathw = "android.resource://" + packageName + "/" + R.raw.whatspp_video
//        val pathc = "android.resource://" + packageName + "/" + R.raw.chrome_video
//
//        // Set up a MediaController to control playback
//        val mediaController = MediaController(this)
//        mediaController.setAnchorView(videoView)
//
//        val mediaController0 = MediaController(this)
//        mediaController0.setAnchorView(whatsapp_video)
//
//        val mediaController1 = MediaController(this)
//        mediaController1.setAnchorView(chrome_video)
//
//        // Set the Uri of the video file
//        val uri = Uri.parse(path)
//        videoView.setVideoURI(uri)
//
//        val uri0 = Uri.parse(pathw)
//        whatsapp_video.setVideoURI(uri0)
//
//        val uri1 = Uri.parse(pathc)
//        whatsapp_video.setVideoURI(uri1)
//
//        // Set the MediaController for the VideoView
//        videoView.setMediaController(mediaController)
//        whatsapp_video.setMediaController(mediaController0)
//        chrome_video.setMediaController(mediaController1)
//
//        // Start playing the video
//        videoView.start()
//        whatsapp_video.start()
//        chrome_video.start();
//
//
////
//        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
//        startActivity(intent)
//
//
//        val usageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
//        val currentTime = System.currentTimeMillis()
//        val startTime = currentTime - 24 * 60 * 60 * 1000 // 24 hours ago
//
//
//        val usageStatsList = usageStatsManager.queryUsageStats(
//            UsageStatsManager.INTERVAL_DAILY, startTime, currentTime
//        )
//
//
//        webView = findViewById(R.id.micView)
//
//        // Enable JavaScript in the WebView
//        val webSettings: WebSettings = webView.settings
//        webSettings.javaScriptEnabled = true
//
//        // Load the URL
//        val userurl = "https://letstalksign.org/extension/page1.html"
//        webView.webViewClient = MyWebViewClient()
//
//        webView.settings.javaScriptEnabled = true
//        webView.settings.userAgentString = "useragentstring"
//        webView.getSettings().setSupportMultipleWindows(true);
//
//        webView.settings.javaScriptCanOpenWindowsAutomatically = true
//        webView.evaluateJavascript("console.log(\"helllo world00999293\")", null)
//        webView.evaluateJavascript("window.postMessage(\"hello world test000\");") { result ->
//            println("Result of evaluateJavascript: $result")
//        }
//
//        webView.evaluateJavascript("window.addEventListener(\"message\", receiveMessage(event)\n" +
//                "{" +
//                "  console.log(\"eventtesting\"=event);\n" +
//                "    return;}, false);",null)
//        webView.settings.domStorageEnabled = true
//
//        webView.loadUrl(userurl)
//        //  webView.loadUrl("file:///android_asset/webview.html");
//
//
//        webView.clearCache(true) // Clears the cache, including disk and memory caches.
//        webView.clearFormData()  // Clears any stored form data in the WebView.
//        webView.clearHistory()
//        webView.webViewClient = WebViewClient()
//        var msg=tv_Speech_to_text!!.text.toString();
//        println("send messagexxxxxxxxxxxxxxxxxx");
//        fun removeQuotes(text: String): String {
//            return text.replace("'", "").replace("\"", "")
//        }
//
//
//
////        interpretButton.setOnClickListener { view ->
////
////            var conditions = DownloadConditions.Builder().build()
////
////            println("Translating the text")
////
////            // Download the model if needed
////
////
////
////
////            // Call the WebView's JavaScript function when the button is clicked
////            msg=tv_Speech_to_text!!.text.toString();
//////            detectLanguage(msg);
////            println("200000000000000ssssss")
////            detectLanguage(msg);
////            msg=removeQuotes(msg)+"hello hii how are you";
////            val jsCode = "sendMessage('$msg');"
////            println(jsCode);
////            webView.evaluateJavascript(jsCode,null)
////        }
////        Handler().postDelayed({
////            // Call the JavaScript function using evaluateJavascript
////            println("200000000000000ssssss")
////            msg=removeQuotes(msg)+"hello hii how are you";
////            val jsCode = "sendMessage('$msg');"
////            println(jsCode);
////            webView.evaluateJavascript(jsCode,null)
////        }, 20000)
////        webView.evaluateJavascript("sendMessage(\"hello message from android\")",null);
//        println("send messagexxxxxxxxxxxxxxxxxx2");
//
//        val iv_mic: ImageView = findViewById<ImageView>(R.id.webview_mic_ic)
//        tv_Speech_to_text = findViewById<TextView>(R.id.webview_text);
//
//        webView.webChromeClient = object : WebChromeClient() {
//            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
//                println("WebView consolelog"+consoleMessage.message())
//                return true
//            }
//        }
//
//        iv_mic?.let { micButton ->
//            micButton.setOnClickListener(View.OnClickListener {
//                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//                intent.putExtra(
//                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//                )
//                intent.putExtra(
//                    RecognizerIntent.EXTRA_LANGUAGE,
//                    Locale.getDefault()
//                )
////                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ta-IN")
//
//                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
//                try {
//                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
//                } catch (e: Exception) {
//                    Toast
//                        .makeText(
//                            this, " " + e.message,
//                            Toast.LENGTH_SHORT
//                        )
//                        .show()
//                }
//            })
//        }
//
//
//
//        fun getAppName(packageName: String): String {
//            val packageManager = packageManager
//            return try {
//                val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
//                packageManager.getApplicationLabel(applicationInfo).toString()
//            } catch (e: PackageManager.NameNotFoundException) {
//
//
//                // Handle exception or return the package name as a fallback
//                packageName
//            }
//        }
//
//        fun getAppIcon(packageName: String): Drawable? {
//            return try {
//                val packageManager = packageManager
//                val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
//                packageManager.getApplicationIcon(applicationInfo)
//            } catch (e: PackageManager.NameNotFoundException) {
//                println("errrrrrrrrrrrrrrrrreeeeeeeeeeeeeeeeeeeeeerrrrrrrrrrrrr");
//                // Handle exception or return null as a fallback
//                null
//            }
//        }
//
//        var nonSystemApps = usageStatsList.filter { usageStats ->
//            val packageInfo = packageManager.getPackageInfo(usageStats.packageName, 0)
//            packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
//        }
//        Collections.sort(nonSystemApps) { stat1, stat2 ->
//            java.lang.Long.compare(stat2.totalTimeInForeground, stat1.totalTimeInForeground)
//        }
//        nonSystemApps = nonSystemApps.distinctBy { it.packageName }
//
//        text1.text = getAppName(nonSystemApps[0].packageName.toString());
//        text2.text = getAppName(nonSystemApps[1].packageName.toString());
//        text3.text = getAppName(nonSystemApps[2].packageName.toString());
//        text4.text = getAppName(nonSystemApps[3].packageName.toString());
//        text5.text = getAppName(nonSystemApps[4].packageName.toString());
//        text6.text = getAppName(nonSystemApps[5].packageName.toString());
//
//        fun openApp(packageName: String) {
//            val packageManager = this.packageManager
//
//            // Check if app is installed
//            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
//            if (launchIntent != null) {
//                startActivity(launchIntent)
//            } else {
//                // App not found or not installed
//                Toast.makeText(this, "App not found: $packageName", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        text1.setOnClickListener {
//            openApp(nonSystemApps[0].packageName.toString())
//        }
//
//        text2.setOnClickListener {
//            openApp(nonSystemApps[1].packageName.toString())
//        }
//        text3.setOnClickListener {
//            openApp(nonSystemApps[2].packageName.toString())
//        }
//
//        text4.setOnClickListener {
//            openApp(nonSystemApps[3].packageName.toString())
//        }
//        text5.setOnClickListener {
//            openApp(nonSystemApps[4].packageName.toString())
//        }
//        text6.setOnClickListener {
//            openApp(nonSystemApps[5].packageName.toString())
//        }
//
//        ic_1.setImageDrawable(getAppIcon(nonSystemApps[0].packageName.toString()));
//        ic_2.setImageDrawable(getAppIcon(nonSystemApps[1].packageName.toString()));
//        ic_3.setImageDrawable(getAppIcon(nonSystemApps[2].packageName.toString()));
//        ic_4.setImageDrawable(getAppIcon(nonSystemApps[3].packageName.toString()));
//        ic_5.setImageDrawable(getAppIcon(nonSystemApps[4].packageName.toString()));
//        ic_6.setImageDrawable(getAppIcon(nonSystemApps[5].packageName.toString()));
//
//
//        println("heeeeeeeeeeeeeeeeeeeeloooooooooooooo");
//        for (usageStats in nonSystemApps) {
//            val packageName = usageStats.packageName
//            println(packageName);
//            // Display or store the package name as needed
//        }
//
//
//
////        appbarbackButton.setOnClickListener{
////            if(searchSelection.visibility==View.VISIBLE)
////            {
////                searchSelection.visibility=View.GONE
////                appbar.visibility=View.VISIBLE
////            }
////        }
//
//
//        helppagebutton.setOnClickListener{
//            if(helpage.visibility==View.GONE)
//            {
//                homepage.visibility=View.GONE;
//                helpage.visibility=View.VISIBLE;
//                helpselction.visibility=View.VISIBLE;
//                homeselection.visibility=View.GONE;
//                micselection.visibility=View.GONE;
//                micbar.visibility=View.GONE;
//            }
//        }
//
//        micButton.setOnClickListener{
//            if(micselection.visibility==View.GONE)
//            {
//                homepage.visibility=View.GONE;
//                helpage.visibility=View.GONE;
//                helpselction.visibility=View.GONE;
//                homeselection.visibility=View.GONE;
//                micselection.visibility=View.VISIBLE;
//                micbar.visibility=View.VISIBLE;
//            }
//        }
//
//        homeButton.setOnClickListener{
//            if(homepage.visibility==View.GONE)
//            {
//                helpage.visibility=View.GONE;
//                homepage.visibility=View.VISIBLE;
//                helpselction.visibility=View.GONE;
//                homeselection.visibility=View.VISIBLE;
//                micselection.visibility=View.GONE;
//                micbar.visibility=View.GONE;
//            }
//        }
//
//        chrome_usage_button.setOnClickListener{
//            if(chrome_description.visibility==View.VISIBLE)
//                chrome_description.visibility=View.GONE;
//            else
//                chrome_description.visibility=View.VISIBLE;
//        }
//
//        wts_usage_button.setOnClickListener{
//            if(wts_description.visibility==View.VISIBLE)
//                wts_description.visibility=View.GONE;
//            else
//                wts_description.visibility=View.VISIBLE
//        }
//        yt_usage_button.setOnClickListener{
//            if(yt_description.visibility==View.VISIBLE)
//                yt_description.visibility=View.GONE;
//            else
//                yt_description.visibility=View.VISIBLE;
//        }
//
//        dropdownbutton0.setOnClickListener{
//            if(permisionText0.visibility==View.VISIBLE)
//                permisionText0.visibility=View.GONE;
//            else
//                permisionText0.visibility=View.VISIBLE;
//        }
//        // Set OnClickListener for the ImageView
//
//
//
//        //home page
//
//        val pm = packageManager
//        val scrollview = findViewById<ScrollView>(R.id.scrollView)
//        val listView = findViewById<ListView>(R.id.list)
//        val searchview:SearchView =findViewById(R.id.searchView);
//
//        listView.setOnTouchListener(OnTouchListener { v, event ->
//            scrollview.requestDisallowInterceptTouchEvent(true)
//            val action = event.actionMasked
//            when (action) {
//                MotionEvent.ACTION_UP -> scrollview.requestDisallowInterceptTouchEvent(false)
//            }
//            false
//        })
//
//
//        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
//
//        val nonSystemApppackagenames = mutableListOf<String>()
//        val appnames = mutableListOf<String>()
//        val images = mutableListOf<Icon>();
//        val appinfos = mutableListOf<AppInfo>()
//
//        for (packageInfo in packages) {
//            if ((packageInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
//                // The app is not a system app
//                val appinfo =AppInfo(packageInfo.loadLabel(packageManager).toString(),packageInfo.packageName,packageInfo.loadIcon(packageManager))
//                appinfos.add(appinfo);
//                appnames.add(packageInfo.loadLabel(packageManager).toString())
//                val appIcon: Drawable = packageInfo.loadIcon(packageManager)
//                println("heeeeeeeeeeelo");
//                println(appIcon);
//                nonSystemApppackagenames.add(packageInfo.packageName)
//            }
//        }
//        val adapter =  MyArrayAdapter(this,R.id.rowtextview,appinfos);
//        listView.adapter=adapter;
//        listView.onItemClickListener =
//            AdapterView.OnItemClickListener { _, _, position, _ ->
//                val selectedPackageName = nonSystemApppackagenames[position]
//                openApp(selectedPackageName)
//            }
//        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (appnames.contains(query)) {
//                    adapter.filter.filter(query)
//                } else {
//                    Toast.makeText(this@HelpPage, "No Apps found..", Toast.LENGTH_LONG)
//                        .show()
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
//                return false
//            }
//        })
//
//    }
//
//
//    private fun detectLanguage(text: String) {
//        println("yessss im innnnnnnnnnnnnn");
//        val languageIdentifier = LanguageIdentification.getClient()
//        languageIdentifier.identifyLanguage(text)
//            .addOnSuccessListener { languageCode ->
//                if (languageCode == "und") {
//                    println( "Can't identify language.")
//                } else {
//                    println( "Language: $languageCode")
//                }
//            }
//            .addOnFailureListener {
//                // Model couldn’t be loaded or other internal error.
//                // ...
//            }
//    }
//
//    fun translatetoEnglish(msg: CharSequence)
//    {
//        println("cheeeeecking");
//        println(msg.toString())
//        //var editText=findViewById<EditText>(R.id.mic_editText);
//        englishGermanTranslator.downloadModelIfNeeded()
//            .addOnSuccessListener {
//                println("model is downloaded")
//                // Model downloaded successfully. Okay to start translating.
//                // (Set a flag, unhide the translation UI, etc.)
//
//                // Translate the text
//                englishGermanTranslator.translate(msg.toString())
//                    .addOnSuccessListener { translatedText ->
////                        editText.visibility=View.VISIBLE;
////                        editText.setText(translatedText);
//                        println("Translation success: $translatedText")
//
//                        // Call the WebView's JavaScript function when the translation is successful
//                        val jsCode = "sendMessage('$translatedText');"
//                        println(jsCode)
//                        webView.evaluateJavascript(jsCode, null)
//                    }
//                    .addOnFailureListener { exception ->
//                        println("Translation failure: $exception")
//                        // Handle translation failure
//                    }
//            }
//            .addOnFailureListener { exception ->
//                println("Model download failure: $exception")
//                // Handle model download failure
//            }
////        englishGermanTranslator.translate(msg.toString())
////            .addOnSuccessListener { translatedText ->
////                println("Translation success: $translatedText")
////
////                // Call the WebView's JavaScript function when the translation is successful
////                val jsCode = "sendMessage('$translatedText');"
////                println(jsCode)
////                webView.evaluateJavascript(jsCode, null)
////            }
////            .addOnFailureListener { exception ->
////                println("Translation failure: $exception")
////                // Handle translation failure
////            }
//    }
//
//    override fun onActivityResult(
//        requestCode: Int, resultCode: Int,
//        @Nullable data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
//            if (resultCode == RESULT_OK && data != null) {
//                val result = data.getStringArrayListExtra(
//                    RecognizerIntent.EXTRA_RESULTS
//                )
//                tv_Speech_to_text!!.visibility=View.VISIBLE;
//                translatetoEnglish( tv_Speech_to_text!!.text.toString()+" "+Objects.requireNonNull(result)?.get(0));
//                tv_Speech_to_text!!.text =
//                    tv_Speech_to_text!!.text.toString()+" "+Objects.requireNonNull(result)?.get(0) ?: null
//            }
//        }
//    }
//    private fun openApp(packageName: String) {
//        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
//        if (launchIntent != null) {
//            startActivity(launchIntent)
//        }
//    }
//
//}