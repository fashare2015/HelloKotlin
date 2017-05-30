package com.fashare.mydemo

import com.fashare.mydemo.data.HomeInfo
import retrofit2.http.GET
import rx.Observable

/**
 * Created by apple on 17-5-31.
 */
interface Api {
    companion object{
        val BASE_URL: String = "http://news-at.zhihu.com/"
    }

    @GET("api/4/news/latest")
    fun getHomeInfo(): Observable<HomeInfo>
}