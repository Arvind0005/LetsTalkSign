package com.example.myapplication

//import android.content.BroadcastReceiver

//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Choreographer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.filament.utils.KtxLoader
import com.google.android.filament.utils.ModelViewer
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.nio.ByteBuffer


class FloatingWindowGFG : Service() {
    private var floatView: ViewGroup? = null
    private var LAYOUT_TYPE = 0
    private var floatWindowLayoutParam: WindowManager.LayoutParams? = null
    private var windowManager: WindowManager? = null
    private var maximizeBtn: Button? = null
    private var closeApplicationBtn: ImageView? = null
    private var readingTextView: TextView? = null
    var captions_message="";
    private var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var myTextView: TextView
    private var handler = Handler(Looper.getMainLooper())
    private var initialX: Int = 0
    private var initialY: Int = 0
    private var initialTouchX: Float = 0.toFloat()
    private var initialTouchY: Float = 0.toFloat()
    private var isMoveAction = false


    //glb
    private lateinit var surfaceView: SurfaceView
    private lateinit var choreographer: Choreographer
    private lateinit var modelViewer: ModelViewer

    private var userurl:String="https://letstalksign.org/extension/page2.html";


    val mysms = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val sms = intent.extras?.getString("stop_message_overlay")
            println("sssssssssssssssstttttttttttttttttttttttooooooooooooooooooooopppppppppppp");
            val ly:LinearLayout=floatView!!.findViewById(R.id.linearlay);
            if(sms=="stop")
            {

                ly.visibility=View.GONE;
                println("sssssssssssssssss222222222222222222222222222222");
//                onDestroy();
            }
            else if(sms=="start")
            {
                ly.visibility=View.VISIBLE;
                println("1111111111111111111111jjjjjjjjjjjjjjjjjjjjjjjjjj");
            }
        }
    }

    fun getAccount(accountManager: AccountManager): Account? {
        val accounts = accountManager.getAccountsByType("com.google")
        val account: Account?
        account = if (accounts.size > 0) {
            accounts[0]
        } else {
            null
        }
        return account
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val metrics = applicationContext.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        registerReceiver(mysms, IntentFilter("stop_action_overlay"));
        var maxi=true;
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatView = inflater.inflate(R.layout.overlay_layout, null) as ViewGroup
        val webView = floatView!!.findViewById<WebView>(R.id.overlay_webview)
        var floatViewheight=floatView!!.height;
        var Scrolltext: TextView =floatView!!.findViewById(R.id.scrollableTextView);
        val showTranscript =floatView!!.findViewById<Button>(R.id.showTranscript);
        val hideTranscript=floatView!!.findViewById<Button>(R.id.hideTranscript);
        val captionsWidget=floatView!!.findViewById<ScrollView>(R.id.captionswidget)
        var floatViewWidth=floatView!!.width;
        println("User URL: $userurl")


        System.out.println("im iNnnnnnnnnnnnnnnnnnnnnnnnnnnn");

        println("wiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        println(width);
        println(height);
        handler = Handler(Looper.getMainLooper())
        println("original floatview ${floatView!!.width},${floatView!!.height}");
        fun  maximizeOverlay(){
            println("maxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxii");
            maxi=true;
            println("${floatView!!.width},${floatView!!.height}");
            floatView!!.layoutParams.height=(height * 0.53f).toInt();
            floatView!!.layoutParams.width=735;
            this.floatWindowLayoutParam=floatWindowLayoutParam;
//            floatView!!.layoutParams=LinearLayout.LayoutParams(webView.width, webView.height)
            println("${floatView!!.width},${floatView!!.height}");
        }
        val manager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        val list: Array<Account> = manager.getAccountsByType("com.google")

        var gmail: String? = null

        for (account in list) {
            gmail = account.name
            println(gmail);
            break
        }

        println("AAAAAAAAAAAAAAAAAAAAAAAAAAACCCCCCCCCCCCC");
        println(gmail);
//        val accountName = account!!.name
//        val fullName = accountName.substring(0, accountName.lastIndexOf("@"))
//        println()

        //        Toast.makeText(this, "Full Name: $fullName", Toast.LENGTH_SHORT).show()
//        emailEditText.setText(accountName)
//        fullNameEditText.setText(fullName)
        fun minimizeOverlay()
        {
            println("minixxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxii");
            maxi=false;
            println("${floatView!!.width},${floatView!!.height}");
            floatView!!.layoutParams.height=150;
            floatView!!.layoutParams.width=150;
            this.floatWindowLayoutParam=floatWindowLayoutParam;
            println("${floatView!!.width},${floatView!!.height}");
        }
//        floatView!!.setOnClickListener {
//            if (maxi)
//                minimizeOverlay();
//            else
//                maximizeOverlay();
//
//        }
//        readingTextView = floatView!!.findViewById(R.id.overlay_text)

//        maximizeBtn = floatView!!.findViewById(R.id.buttonMaximize)
        var mConnection: ServiceConnection? = null
        closeApplicationBtn = floatView!!.findViewById(R.id.window_close)
        closeApplicationBtn?.setOnClickListener(View.OnClickListener {
            showCustomDialog()
//            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//
//            // Inflate the custom layout
//            val view = LayoutInflater.inflate(R.layout.popup_layout, null)
//            builder.setView(view)
//
//            val messageTextView = view.findViewById<TextView>(R.id.messageTextView)
//            val yesButton = view.findViewById<Button>(R.id.yesButton)
//            val noButton = view.findViewById<Button>(R.id.noButton)
//
//            // Set the message text
//            messageTextView.text = "Closing this will disable Accessibility setting. If you wish to continue using Let'sTalkSign interpretation in other apps, switch to another app without closing. Do you wish to close?"
//
//            // This dialog can be closed by tapping anywhere outside the dialog-box
//            builder.setCancelable(true)
//
//            // Handle "Yes" button click
//            builder.setPositiveButton("Yes") { _, _ ->
//                val stopServiceIntent = Intent(this, FloatingWindowGFG::class.java)
//                stopService(stopServiceIntent)
//                stopSelf()
//                System.exit(0)
//            }
//
//            // Handle "No" button click
//            builder.setNegativeButton("No") { _, _ ->
//                // Dismiss the dialog, do nothing
//            }
//
//            val dialog: AlertDialog = builder.create()
//
//            // Show the dialog
//            dialog.show()
        })

        fun adjustheight()
        {
            println("callllllled");
            //    captionsWidget.visibility=View.VISIBLE
            val maxcHeight = (height * 0.20).toInt()
            val layoutParams = captionsWidget.layoutParams
            println("heeeeelllllllllllllloooooooooo");
            var lineCount: Int=1
            println(maxcHeight)
          //  Scrolltext.post(Runnable {
            lineCount = Scrolltext.getLineCount()
                // Use lineCount here
           // })
            println(layoutParams.height)
            if((Scrolltext.height*lineCount)>maxcHeight)
                layoutParams.height = maxcHeight
            else
                layoutParams.height=Scrolltext.height
            captionsWidget.layoutParams = layoutParams
            captionsWidget.requestLayout()
           //  hideTranscript.visibility=View.VISIBLE;
//            val newWidth =  735
//            val newHeight =  (height * 0.80f).toInt()
//            floatWindowLayoutParam!!.width = newWidth
//            floatWindowLayoutParam!!.height = newHeight
//            windowManager!!.updateViewLayout(floatView, floatWindowLayoutParam)
             // showTranscript.visibility=View.GONE
        }
        webView.webViewClient = MyWebViewClient()

        webView.settings.javaScriptEnabled = true
        webView.settings.userAgentString = "useragentstring"
        webView.getSettings().setSupportMultipleWindows(true);

        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        println("helllooo world");
        webView.evaluateJavascript("console.log(\"helllo world00999293\")", null)
//        webView.evaluateJavascript("window.postMessage(\"hello world test000\");") { result ->
//            println("Result of evaluateJavascript: $result")
//        }
//        webView.evaluateJavascript("window.addEventListener(\"message\", receiveMessage(event)\n" +
//                "{" +
//                "  console.log(\"eventtesting\"=event);\n" +
//                "    return;}, false);",null)
        webView.settings.domStorageEnabled = true

        webView.loadUrl(userurl)
 //       webView.loadUrl("file:///android_asset/webview.html");


        webView.clearCache(true) // Clears the cache, including disk and memory caches.
        webView.clearFormData()  // Clears any stored form data in the WebView.
        webView.clearHistory()
        webView.webViewClient = WebViewClient()

        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                println("WebView Console.log "+consoleMessage.sourceId()+" "+consoleMessage.lineNumber()+" "+consoleMessage.message());
                return true
            }
        }
        println("weeebview");
//
        fun removeQuotes(sentence: String): String {
//            val withoutBraces = sentence.replace("{", "").replace("}", "")
//
//            // Remove apostrophes
//            val withoutApostrophes = withoutBraces.replace("'", "")
//            val withoutApostrophes0 = withoutApostrophes.replace("\""", "")
//
//            return withoutApostrophes0
            val words = sentence.split("\\s+".toRegex())

            // Remove special characters from each word
            val cleanWords = words.map { word ->
                word.replace(Regex("[^a-zA-Z]"), "")
            }

            // Join the cleaned words back into a sentence
            return cleanWords.joinToString(" ")
            val pattern = "\\b(.+?)\\b(?:\\s+\\1\\b)+".toRegex(RegexOption.IGNORE_CASE)
            return cleanWords.toString().replace(pattern, "$1").trim().toString();
        }
     //   webView.evaluateJavascript("receiveMessageFromAndroid(\"hello how are you this is the 1st message from android\")", null);

        fun sendMessageToWebView(source:String, message:String) {
            println("messsageeeeeeeeeeeeesss");
//            println(message);
            webView.clearCache(true) // Clears the cache, including disk and memory caches.
            webView.clearFormData()  // Clears any stored form data in the WebView.
            webView.clearHistory()
            webView.webViewClient = WebViewClient()
            println("before execution");
            var msg=message
          //  webView.evaluateJavascript("receiveMessageFromAndroid('$msg')", null);
            msg=removeQuotes(msg);
            var initial=true;
            println(msg);
            var jsCode0 = "sendMessage('$msg');"
            if(source=="youtube")
            {
                webView.postDelayed({
                    webView.webViewClient = object : WebViewClient() {
                        @SuppressLint("SetTextI18n")
                        override fun onPageFinished(view: WebView?, url: String?) {
                            if (initial) {
                                println("200000000000000ssssss")
                                msg = removeQuotes(msg)
                                Scrolltext.text = msg.toString()
                                if(Scrolltext.text!="Your captions are loading...!")
                                {
                                    showTranscript.setBackgroundResource(R.drawable.rounded_button_background);
                                    showTranscript.setText("show tanscript");
                                    showTranscript.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                                }
                                val jsCode = "sendMessage('$msg');"
                                println(jsCode)
                                webView.evaluateJavascript("javascript:sendMessage(\"$msg\")", null)
                                initial = false
                                adjustheight();
                                adjustheight();
                            }
                        }
                    }
                }, 10000)
            }
            msg = removeQuotes(msg);
            msg = removeQuotes(msg);
            Scrolltext.setText(msg);
            adjustheight()
            adjustheight();
            jsCode0 = "sendMessage('$msg');"
            println(jsCode0);
            println("helllllllllllloo");
            webView.evaluateJavascript("sendMessage(\"${msg}\")", null)
            println("executed");

        }


        fun fetchCaptionsFromAPI(youtubeUrl: String): String {

            println("heeeeeeeeeeeeeeelo"+youtubeUrl);
            // Define your Flask API endpoint

            val apiEndpoint = "http://152.69.200.148:7070/get_captions?url=$youtubeUrl"


            // Create an instance of OkHttpClient
            val client = OkHttpClient()

            // Create a request for the API endpoint
            val request = Request.Builder()
                .url(apiEndpoint)
                .build()
            println("hiiii worldlnckjnskjncjnnkjscnjksd");
            var responseB="";

            // Make the GET request asynchronously
            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    println("errrrrrrrrrrrrrrrroeeeeeeeeeeeeeeeerrrrr");
                    // Handle the failure, e.g., show an error message

                    // Update your UI here with the error message if needed
                    // For example, you can show a Toast message
                    println(e.message)
                    responseB= "Unexpected error occurred please try again.."

                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call, response: Response) {
                    // Handle the successful response
                    val responseBody = response.body?.string()

                    handler.post {
                        println("helllo number")
                        println(responseBody);
                        if(responseBody!!.startsWith("Tunnel",true) || responseBody.toString()=="Tunnel 2644-103-130-204-201.ngrok-free.app not found")
                        {
                            println("he111111111111111111111111111111111111111111");
                            Scrolltext.setText("Unexpected error occurred please try again..");

                            sendMessageToWebView("youtube","Unexpected error occurred please try again..");
                            showTranscript.setText("Error! please try again..")
                            showTranscript.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            return@post;
//                            println("response from youtube!!!"+responseBody.toString());
//                            sendMessageToWebView("youtube",responseBody)
                        }
                        else if (responseBody != null) {
                            println("he222222222222222222222222222222222222222222222222");
                            Scrolltext.setText(responseBody.toString());
                            println("response from youtube!!!"+responseBody.toString());
                            sendMessageToWebView("youtube",responseBody)
                        };
                        else {
                            println("heeeeeeeeeeeeeeeeeeeeee33333333333333333333333333333333333333");
                            sendMessageToWebView(
                                "youtube",
                                "Unexpected error occurred please try again.."
                            );
                        }

//                    readingTextView?.text = responseBody
//                    myTextView.text = responseBody
                        if(Scrolltext.text!="Your captions are loading...!")
                        {
                            showTranscript.setBackgroundResource(R.drawable.rounded_button_background);
                            showTranscript.setText("Show Transcript")
                            showTranscript.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
//            showTranscript.background=R.drawable.disable_button_bg as GradientDrawable
                        }
                        Scrolltext.setText(responseBody.toString());
                        adjustheight();
                        adjustheight();
                    }
//                    readingTextView?.text = responseBody
                    println("uithereeeeeeeeeeeeeeeeed");
//                    myTextView.text = responseBody;
                    println(responseBody);
                    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    if (responseBody != null) {
                       // Scrolltext.setText(responseBody.toString());
                    }
                    if (responseBody != null) {
                        responseB=responseBody
                    };
                }
            })
            return responseB;
        }


//        fun FloatingWindowGFG.fetchAndSendMessage(url:String): String {
//             var res="";
//            // Start a coroutine
//            CoroutineScope(Dispatchers.Main).launch {
//                try {
//                    // Perform the API call asynchronously
//                    val captionsMessage = withContext(Dispatchers.IO) {
//                        fetchCaptionsFromAPI(url.toString())
//                    }
//                    res=captionsMessage;
//                    println("heeeeeeeeeelomachaan");
//                    println(captionsMessage);
//                    // Once the API call is complete, send the message to WebView
//                    val handler = Handler()
//                    handler.postDelayed({ sendMessageToWebView("youtube",captions_message) }, 2000)
//
//                } catch (e: Exception) {
//                    // Handle exceptions if needed
//                    res=e.toString();
//                    e.printStackTrace()
//                }
//            }
//            return res.toString();
//        }
//



        if (intent != null) {
            if(intent.getStringExtra("url")!=null) {
                val extras = intent.getStringExtra("url");
                println("extrasssssssssssssssssssssssssssssss");
                println(extras);
                captions_message = fetchCaptionsFromAPI(extras.toString());
                println(captions_message)
            }
            else if(intent.getStringExtra("message")!=null)
            {
                println("heeeeeeeeeeeeeeeeeeeeeeeeelooooooooooooooo meeeeeeeeeeeeeeeesss");
                val extras = intent.getStringExtra("message");
                println(extras.toString())
                sendMessageToWebView("accessibility",extras.toString());
            }

//            val handler = Handler()
//            handler.postDelayed({ sendMessageToWebView(captions_message) }, 2000)
//            userurl=extras.toString();
//            userurl="https://letstalksign.org/extension/page1.html";
        } else {
            // Handle the case where the intent is null
//            userurl = "https://deepvisiontech.ai/tnstartup/home.html"
//            userurl="https://letstalksign.org/extension/page1.html";
        }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                webView.evaluateJavascript("var FunctionOne = function () { window.postMessage(\"hellllo world how are you\",*);};"
//                       , null);
            } else {
//                webView.loadUrl("javascript:"
//                        + "var FunctionOne = function () {"
//                        + "  window.postMessage(\"hellllo world how are you\",*);}catch(e){}"
//                        + "};");
            }
            println("Uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuurrrrrrrrrrrrrrrrrrrrrrrrrrlllllll"+userurl+" hello");
            println(userurl);



        println("meeeeeeeeeeeeehvcgdfgcfcfdcfcfd");
        println("fcfdcdcddcdcdcdcdcdcdc"+captions_message);



        LAYOUT_TYPE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_TOAST
        }

        floatWindowLayoutParam = WindowManager.LayoutParams(
            735, (height * 0.65f).toInt(),
            LAYOUT_TYPE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSPARENT
        )
        val screenWidth = resources.displayMetrics.widthPixels
        floatWindowLayoutParam!!.gravity = Gravity.TOP or Gravity.END
        floatWindowLayoutParam!!.x=0
        floatWindowLayoutParam!!.y =0
        windowManager!!.addView(floatView, floatWindowLayoutParam)

//        captionsWidget.visibility=View.VISIBLE
//        val newcHeight = (height * 0.20).toInt()
//        val layoutParams = captionsWidget.layoutParams
//        layoutParams.height = newcHeight
//        captionsWidget.layoutParams = layoutParams
//        captionsWidget.requestLayout()
//        hideTranscript.visibility=View.VISIBLE;
//        val newWidth =  (width * 0.67f).toInt()
//        val newHeight =  (height * 0.80f).toInt()
//        floatWindowLayoutParam!!.width = newWidth
//        floatWindowLayoutParam!!.height = newHeight
//        windowManager!!.updateViewLayout(floatView, floatWindowLayoutParam)
//        showTranscript.visibility=View.GONE
        if(Scrolltext.text=="Your captions are loading...!")
        {
            showTranscript.setText("Loading captions...!")
            showTranscript.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            showTranscript.setBackgroundResource(R.drawable.disable_button_bg);
            showTranscript.setOnClickListener(null)

//            showTranscript.background=R.drawable.disable_button_bg as GradientDrawable
        }

        showTranscript.setOnClickListener {
            captionsWidget.visibility=View.VISIBLE
            val maxcHeight = (height * 0.20).toInt()
            val layoutParams = captionsWidget.layoutParams
            println("heeeeelllllllllllllloooooooooo");
            var lineCount: Int=1
            println(maxcHeight)
//            Scrolltext.post(Runnable {
            lineCount = Scrolltext.getLineCount()
                // Use lineCount here
        //    })
            println(layoutParams.height)
            if((Scrolltext.height*lineCount)>maxcHeight)
                layoutParams.height = maxcHeight
            else
                layoutParams.height=Scrolltext.height
            captionsWidget.layoutParams = layoutParams
            captionsWidget.requestLayout()
            hideTranscript.visibility=View.VISIBLE;
            val newWidth =  735
            val newHeight =  (height * 0.80f).toInt()
            floatWindowLayoutParam!!.width = newWidth
            floatWindowLayoutParam!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
            windowManager!!.updateViewLayout(floatView, floatWindowLayoutParam)
            showTranscript.visibility=View.GONE
        }
        hideTranscript.setOnClickListener {
            showTranscript.visibility=View.VISIBLE;
            captionsWidget.visibility=View.GONE;
            val newWidth =  735
            val newHeight =  (height * 0.58f).toInt()
            floatWindowLayoutParam!!.width = newWidth
            floatWindowLayoutParam!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
            windowManager!!.updateViewLayout(floatView, floatWindowLayoutParam)
            hideTranscript.visibility=View.GONE
        }






        maximizeBtn?.setOnClickListener(View.OnClickListener {
                stopSelf()
                windowManager!!.removeView(floatView)
                val backToHome = Intent(this@FloatingWindowGFG, MainActivity::class.java)
                backToHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(backToHome)
            })



            //moves the floating window
            floatView!!.setOnTouchListener(object : OnTouchListener {
                val floatWindowLayoutUpdateParam: WindowManager.LayoutParams =
                    floatWindowLayoutParam as WindowManager.LayoutParams
                var x = Gravity.END.toDouble()
                var y = Gravity.TOP.toDouble()
                var px = 0.0
                var py = 0.0



                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    System.out.println("xcvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            x = floatWindowLayoutUpdateParam.x.toDouble()
                            y = floatWindowLayoutUpdateParam.y.toDouble()
                            px = event.rawX.toDouble()
                            py = event.rawY.toDouble()
                            initialTouchX = event.rawX
                            initialTouchY = event.rawY
                            isMoveAction = false
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val deltaX = (event.rawX + initialTouchX).toInt()
                            val deltaY = (event.rawY + initialTouchY).toInt()
                            floatWindowLayoutUpdateParam.x = (x - event.rawX + px).toInt()
                            floatWindowLayoutUpdateParam.y = (y + event.rawY - py).toInt()
                            windowManager!!.updateViewLayout(floatView, floatWindowLayoutUpdateParam)
                            println("xxxxxxxxxxxxxxxxxxxxxyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                            println(Math.abs(deltaX))
                            println( Math.abs(deltaY))
//                            if (Math.abs(deltaX) < 1000 || Math.abs(deltaY) < 1000) {
//                                isMoveAction = true
//                            }
                        }
//                        MotionEvent.ACTION_UP -> {
//                            if (!isMoveAction) {
//                             //    If it's a tap (not a move), call the appropriate function
//                                if (maxi) {
//                                    minimizeOverlay()
//                                } else {
//                                    maximizeOverlay()
//                                }
//                            }
//                        }
                    }
//                    if (!isMoveAction) {
//                        //    If it's a tap (not a move), call the appropriate function
//                        if (maxi) {
//                            minimizeOverlay()
//                        } else {
//                            maximizeOverlay()
//                        }
//                    }

                    return false
                }
            })

            // Create and register a BroadcastReceiver
        broadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                println("reeeeeeeeeeeeeeeeeeeeeciveeeeeeeeeeeeeeeeeed");
                val readingText = intent.getStringExtra("SendMessage")
                sendMessageToWebView("accessibility",readingText.toString());
            }
        }
        val intentFilter = IntentFilter("SendMessage")
        registerReceiver(broadcastReceiver, intentFilter,)

//        glb files renderning

//        val glContainer = floatView!!.findViewById<FrameLayout>(R.id.gl_container)
//        println(glContainerx)
            println("dshchgsdvchvscghvsdghvsdvcdggcsvdfdf");
//        println(glContainer);

//        glContainer.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.MATCH_PARENT
//        );


//        val loadedglbContainer = floatView!!.findViewById<FrameLayout>(R.id.gl_container)
//        println(floatView!!.findViewById<FrameLayout>(R.id.gl_container))
//        println("globalContainer")
//        println(loadedglbContainer);
//        println("jhsdcbhdvchgdvcvhsvcvsgdcgcgvdg $loadedglbContainer")
//        val glSurfaceView = SurfaceView(this).apply {
//            loadedglbContainer
//        }
//        loadedglbContainer?.addView(glSurfaceView)

// Request a layout invalidation
            //    loadedglbContainer?.requestLayout()

            //ewj
//        val loadedglbContainer = GlobalContainerHolder.glContainer
//        println("jhsdcbhdvchgdvcvhsvcvsgdcgcgvdg $loadedglbContainer")
//        val glSurfaceView = SurfaceView(this)
//        loadedglbContainer.apply { loadedglbContainer}
//


//        val glSurfaceView = SurfaceView(this)
//        glContainer.addView(glSurfaceView)
//        choreographer = Choreographer.getInstance()
//        modelViewer = ModelViewer(glSurfaceView)
//        glSurfaceView.setOnTouchListener(modelViewer);
//        choreographer.postFrameCallback(frameCallback)
//
//        var namei =  "facial_expressions"
//        loadGltf(namei.toString())
//        modelViewer.scene.skybox = Skybox.Builder().build(modelViewer.engine)
//        loadEnvironment("venetian_crossroads_2k")
//        choreographer.postFrameCallback(frameCallback)


        return START_STICKY
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCustomDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Are you sure ?")
            .setMessage("Closing this will disable Accessibility setting. \n" +
                    "If you wish to continue using Let'sTalkSign interpretation in other apps, switch to other app without closing\n Do you wish to close?")
            .setPositiveButton("Yes") { dialog, which ->
                val intent = Intent("stop_action")
                intent.putExtra("stop_message", "stop_and_exit")
                sendBroadcast(intent)
//                val stopServiceIntent = Intent(this, FloatingWindowGFG::class.java)
//                stopService(stopServiceIntent)
 //               stopSelf()
//                System.exit(0)

                // Code to execute when "Yes" is clicked
                // You can dismiss the dialog or perform any other action
                dialog.dismiss()
                // Add your code here for "Yes" button click
            }
            .setNegativeButton("No") { dialog, which ->
                // Code to execute when "No" is clicked
                // You can dismiss the dialog or perform any other action
                dialog.dismiss()
                // Add your code here for "No" button click
            }
            .create()

// Set the window type for overlay
        alertDialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)

// Show the dialog
        alertDialog.show()
//        handler?.post {
//            this?.let { ctx ->
//                val builder: AlertDialog.Builder = AlertDialog.Builder(ctx)
//
//                // Inflate the custom layout
//                val view = LayoutInflater.from(ctx).inflate(R.layout.close_layout, null)
//                builder.setView(view)
//
//                val messageTextView = view.findViewById<TextView>(R.id.messageTextView)
//                val yesButton = view.findViewById<Button>(R.id.yesButton)
//                val noButton = view.findViewById<Button>(R.id.noButton)
//
//                // Set the message text
//                messageTextView.text = "Closing this will disable Accessibility setting. If you wish to continue using Let'sTalkSign interpretation in other apps, switch to another app without closing. Do you wish to close?"
//
//                // This dialog can be closed by tapping anywhere outside the dialog-box
//                builder.setCancelable(true)
//
//                // Handle "Yes" button click
//                builder.setPositiveButton("Yes") { _, _ ->
//                    val stopServiceIntent = Intent(ctx, FloatingWindowGFG::class.java)
//                    ctx.stopService(stopServiceIntent)
//                    stopSelf()
//                    System.exit(0)
//                }
//
//                // Handle "No" button click
//                builder.setNegativeButton("No") { _, _ ->
//                    // Dismiss the dialog, do nothing
//                }
//
//                val dialog: AlertDialog = builder.create()
//
//                // Show the dialog
//                dialog.show()
//            }
//        }
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


    private fun loadGltf(name: String) {
        val buffer = readAsset("models/${name}.gltf")
        System.out.println("dddddddddddddddddddddddddddddddddddddddddddddd");
//        System.out.println(buffer);
        modelViewer.loadModelGltf(buffer) { uri -> readAsset("models/$uri") }
        modelViewer.transformToUnitCube()
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
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


    override fun onBind(intent: Intent): IBinder? {
//        choreographer.postFrameCallback(frameCallback)
        return null
    }

    private fun finish() {
        System.exit(0)
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                //      bitmap.recycle()
                System.gc()
            }

        }
    }

    override fun onDestroy() {
        println("deeeeeeeeeeeeeesrrrrrrrrrrrrrrrrrrooooooooooooyyyyyyyyyyyy");
//        unregisterReceiver(mysms)
        stopSelf();
        super.onDestroy()
    }



}


