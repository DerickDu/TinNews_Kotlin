package link.jingweih.tinnews.network

import link.jingweih.tinnews.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String = "us", @Query("page") page: Int,
    @Query("pageSize") pageSize: Int = 10): NewsResponse
}