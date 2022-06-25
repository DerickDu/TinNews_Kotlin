package link.jingweih.tinnews.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import link.jingweih.tinnews.model.Article

@Dao
interface ArticleDao {
    @Insert
    suspend fun favoriteArticle(article: Article)

    @Delete
    suspend fun unFavoriteArticle(article: Article)

    @Query("select * from article")
    fun fetchAllSavedArticles(): Flow<List<Article>>
}
