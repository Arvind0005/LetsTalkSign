//package com.example.myapplication
//
//import android.Manifest
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.telecom.PhoneAccountHandle
//import android.telecom.TelecomManager
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.telecom.CallAttributesCompat.Companion.CALL_TYPE_VIDEO_CALL
//import androidx.core.telecom.CallAttributesCompat.Companion.DIRECTION_INCOMING
//import androidx.core.telecom.CallsManager
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
//
//class Phone_Call : AppCompatActivity() {
//
//    companion object {
//        private const val REQUEST_CALL_PHONE_PERMISSION = 1
//    }
//
//    private val telecomManager by lazy {
//        getSystemService(Context.TELECOM_SERVICE) as TelecomManager
//    }
//
//    private fun getYourPhoneAccountHandle(): PhoneAccountHandle {
//        // You need to define and obtain your PhoneAccountHandle
//        // For simplicity, this example returns an empty handle. Replace it with your implementation.
//        return PhoneAccountHandle(ComponentName(this, PhoneCall_Service::class.java), "001")
//    }
//
//    private fun initiateOutgoingCall(phoneNumberUri: Uri) {
//
//
//        val phoneAccountHandle = getYourPhoneAccountHandle() // Replace with your PhoneAccountHandle
//
//        val extras = Bundle()
//        extras.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle)
//
//        val intent = Intent(Intent.ACTION_CALL, phoneNumberUri)
//        intent.putExtra(TelecomManager.EXTRA_OUTGOING_CALL_EXTRAS, extras)
//
//        startActivity(intent)
//    }
//
//    // define objects for edit text and button
//    private lateinit var edittext: EditText
//    private lateinit var button: Button
//
//    //new
//
//    companion object {
//        const val APP_SCHEME = "MyCustomScheme"
//        const val ALL_CALL_CAPABILITIES = (CallAttributes.SUPPORTS_SET_INACTIVE
//                or CallAttributes.SUPPORTS_STREAM or CallAttributes.SUPPORTS_TRANSFER)
//
//        const val INCOMING_NAME = "Luke"
//        val INCOMING_URI: Uri = Uri.fromParts(APP_SCHEME, "", "")
//        // Define all possible properties for CallAttributes
//        val INCOMING_CALL_ATTRIBUTES =
//            CallAttributes(
//                INCOMING_NAME,
//                INCOMING_URI,
//                DIRECTION_INCOMING,
//                CALL_TYPE_VIDEO_CALL,
//                ALL_CALL_CAPABILITIES)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private val callsManager = CallsManager(this)
//
//    var capabilities: @CallsManager.Companion.Capability Int =
//        CallsManager.CAPABILITY_BASELINE or
//                CallsManager.CAPABILITY_SUPPORTS_CALL_STREAMING or
//                CallsManager.CAPABILITY_SUPPORTS_VIDEO_CALLING
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.phone_call)
//        callsManager.registerAppWithTelecom(capabilities);
//
//
//        callsManager.addCall(
//            INCOMING_CALL_ATTRIBUTES,
//            onIsCallAnswered, // Watch needs to know if it can answer the call
//            onIsCallDisconnected,
//            onIsCallActive,
//            onIsCallInactive
//        ) {
//            callControlScope = this
//        }
//        // Getting instance of edittext and button
//        button = findViewById(R.id.call_button)
//        edittext = findViewById(R.id.call_editText)
//
//        // Attach set on click listener to the button for initiating intent
//        button.setOnClickListener(View.OnClickListener {
//            println("yessssssssssssssssim in");
//            val uri = Uri.fromParts("tel", "+919087442642", null)
//            val extras = Bundle()
//            extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, true)
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.CALL_PHONE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//
//                telecomManager.placeCall(uri, extras)
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//            }
////            val phoneNumber = "tel:+919087442642"
////            initiateOutgoingCall(Uri.parse(phoneNumber))
////            if (ContextCompat.checkSelfPermission(
////                    this,
////                    Manifest.permission.CALL_PHONE
////                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
////                    this,
////                    Manifest.permission.RECORD_AUDIO
////                ) == PackageManager.PERMISSION_GRANTED
////            ) {
////                // Permissions are granted, make the phone call
////                makePhoneCall()
////            } else {
////                // Request permissions
////                ActivityCompat.requestPermissions(
////                    this,
////                    arrayOf(
////                        Manifest.permission.CALL_PHONE,
////                        Manifest.permission.RECORD_AUDIO
////                    ),
////                    REQUEST_CALL_PHONE_PERMISSION
////                )
////            }
//        })
//
//    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // CALL_PHONE permission granted, you can proceed with the call
//                makePhoneCall()
//            }
//        }
//    }
//    private fun makePhoneCall() {
//        val phone_number = edittext.text.toString()
//        val phoneIntent = Intent(Intent.ACTION_CALL)
//        phoneIntent.data = Uri.parse("tel:$phone_number")
//
//        // Check if CALL_PHONE permission is granted (just to be sure)
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CALL_PHONE
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            startActivity(phoneIntent)
//        }
//    }
//}
