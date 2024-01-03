//package com.example.myapplication
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.content.pm.ApplicationInfo
//import android.content.pm.PackageManager
//import android.graphics.drawable.Drawable
//import android.graphics.drawable.Icon
//import android.os.Bundle
//import android.widget.AdapterView
//import android.widget.ImageView
//import android.widget.ListView
//import androidx.appcompat.app.AppCompatActivity
//
//
//class HomePage: AppCompatActivity() {
//    @SuppressLint("ResourceType")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.home)
//        val pm = packageManager
//        val listView = findViewById<ListView>(R.id.list)
//
//        val imageView: ImageView = findViewById(R.id.help_ic0);
//
//        // Set OnClickListener for the ImageView
//        imageView.setOnClickListener {
//            // Launch the TargetActivity when ImageView is clicked
//            val intent = Intent(this,   HelpPage::class.java)
//            startActivity(intent)
//        }
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
//
//    }
//    private fun openApp(packageName: String) {
//        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
//        if (launchIntent != null) {
//            startActivity(launchIntent)
//        }
//    }
//}
