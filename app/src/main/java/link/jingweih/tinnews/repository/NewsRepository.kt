package link.jingweih.tinnews.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import link.jingweih.tinnews.database.ArticleDao
import link.jingweih.tinnews.model.Article
import link.jingweih.tinnews.network.NewsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) {

    private val mutex = Mutex()
    private val topHeadlines = mutableMapOf<Int, List<Article>>()

    suspend fun fetchTopHeadlinesNews(page: Int): Flow<List<Article>> {
        if (!topHeadlines.containsKey(page)) {
            mutex.withLock {
                topHeadlines[page] = newsApi.getTopHeadlines(
                    page = page
                ).articles
            }
        }
        return flow {
            emit(topHeadlines.values.flatten())
        }.combine(articleDao.fetchAllSavedArticles()) { rawArticles, favoriteArticles ->
            val newArticle = rawArticles.map {  article ->
                if (favoriteArticles.find { it.url == article.url } != null) {
                    article.copy(isFavorite = true)
                } else {
                    article.copy(isFavorite = false)
                }
            }
            newArticle
        }.cancellable()
    }

    fun fetchAllSavedArticles(): Flow<List<Article>> {
        return articleDao.fetchAllSavedArticles()
    }

    suspend fun favoriteArticle(article: Article) {
        articleDao.favoriteArticle(article)
    }

    suspend fun unFavoriteArticle(article: Article) {
        articleDao.unFavoriteArticle(article)
    }
}