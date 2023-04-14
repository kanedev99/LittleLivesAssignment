package com.example.littlelivesassignment.data.remote

import android.content.Context
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockDataInterceptor(
    private val context: Context,
    private val filename: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val responseString = readJsonFromAssets(context, filename)
        val parser = JsonParser()
        val jsonObject = parser.parse(responseString).asJsonObject
        return Response.Builder()
            .code(200)
            .message(jsonObject.toString())
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .build()
    }

    private fun readJsonFromAssets(context: Context, fileName: String): String {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

}