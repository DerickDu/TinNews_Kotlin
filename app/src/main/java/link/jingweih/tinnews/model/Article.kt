package link.jingweih.tinnews.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Article(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    @PrimaryKey
    val url: String,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    var isFavorite: Boolean = false
) : Parcelable {
    override fun toString(): String {
        return "News(author=$author, content=$content, description=$description, publishedAt=$publishedAt, title=$title, url='$url', imageUrl=$imageUrl)"
    }

    companion object {
        val DIFF = object: DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }
}
