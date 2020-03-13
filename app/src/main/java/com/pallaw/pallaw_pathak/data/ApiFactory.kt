package com.pallaw.pallaw_pathak.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by Pallaw Pathak on 2020-03-13. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
object ApiFactory {
    private val BASE_URL = "https://blog.truecaller.com/"
    val TRUE_CALLER_API: TrueCallerApi = retrofit().create(TrueCallerApi::class.java)

    fun retrofit(): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JsoupConverterFactory)
        .client(createClient())
        .baseUrl(BASE_URL)
        .build()

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoginInterceptor())
            .build()
    }

    private fun createLoginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}