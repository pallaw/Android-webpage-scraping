package com.pallaw.pallaw_pathak.data

import io.reactivex.Single
import org.jsoup.nodes.Document
import retrofit2.http.GET

/**
 * Created by Pallaw Pathak on 2020-03-13. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
interface TrueCallerApi {

    @GET("2018/01/22/life-as-an-android-engineer")
    fun getBlogData(): Single<Document>
}