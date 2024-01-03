//package com.example.myapplication
//
////import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
////import com.google.api.client.googleapis.json.GoogleJsonResponseException
////import com.google.api.client.json.JsonFactory
////import com.google.api.client.json.jackson2.JacksonFactory
////import com.google.api.services.youtube.YouTube
//import java.io.FileOutputStream
//import java.io.IOException
//import java.io.OutputStream
//import java.security.GeneralSecurityException
//
//object ApiExample {
//    // You need to set this value for your code to compile.
//    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
//    private const val DEVELOPER_KEY = "AIzaSyAdbiYukOo76G5gwZNhITEOR7wZQ4Zc9w0"
//    private const val APPLICATION_NAME = "com.example.myapplication"
//    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
//
//    @get:Throws(
//        GeneralSecurityException::class,
//        IOException::class
//    )
//    val service: YouTube
//        /**
//         * Build and return an authorized API client service.
//         *
//         * @return an authorized API client service
//         * @throws GeneralSecurityException, IOException
//         */
//        get() {
//            val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
//            return YouTube.Builder(httpTransport, JSON_FACTORY, null)
//                .setApplicationName(APPLICATION_NAME)
//                .build()
//        }
//
//    /**
//     * Call function to create API service object. Define and
//     * execute API request. Print API response.
//     *
//     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
//     */
//    @Throws(GeneralSecurityException::class, IOException::class, GoogleJsonResponseException::class)
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val youtubeService = service
//        // TODO: Replace "YOUR_FILE" with the location where
//        //       the downloaded content should be written.
//        val output: OutputStream = FileOutputStream("D:\\Arvind_workspace\\projects\\MyApplication2\\captions.txt")
//
//        // Define and execute the API request
//        val request = youtubeService.captions()
//            .download("AUieDabtbewAoYf8lCfnfuXcILjAGpv59b7UPZ_y8u4r").setKey(DEVELOPER_KEY)
//        request.mediaHttpDownloader
//        request.executeMediaAndDownloadTo(output)
//    }
//}