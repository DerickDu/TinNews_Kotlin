package link.jingweih.tinnews.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
) {
    override fun toString(): String {
        return "NewsResponse(status=$status, totalResults=$totalResults, articles=$articles, code=$code, message=$message)"
    }
}