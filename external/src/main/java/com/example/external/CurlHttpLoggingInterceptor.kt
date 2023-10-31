package com.example.external

import android.util.Log
import java.io.IOException
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer

class CurlHttpLoggingInterceptor @JvmOverloads constructor() :
    Interceptor {
    //https://spotify-redesign-mock.firebaseapp.com/__/auth/handler
    interface Logger {
        fun log(message: String)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.toString()
        val method = request.method
        var bodyString = ""
        var contentType = ""

        if (request.body != null) {
            val body = parseRequestBody(request.body)
            if (body != null && body != "") {
                bodyString = "-d '$body'"
            }
            val type = request.body!!.contentType()
            if (type != null) {
                contentType = "-H 'Content-Type: $type'"
            }
        }
        val headers = request.headers
        val headersBuilder = StringBuilder()
        var i = 0
        val size = headers.size
        while (i < size) {
            headersBuilder.append(" -H '" + headers.name(i)).append(": ")
                .append(headers.value(i) + "' \\\n")
            i++
        }

        val headersString = headersBuilder.toString()
        val content = if (contentType != "") "$contentType \\\n" else ""
        val body = if (bodyString != "") "$bodyString \\\n" else ""
        Log.d("PhucNK1","curl  -X $method \\\n $content$headersString$body'$url'")
        return chain.proceed(request)
    }

    private fun parseRequestBody(body: RequestBody?): String? {
        if (body == null) {
            return null
        }
        return try {
            val buffer = Buffer()
            body.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            null
        }
    }
}
