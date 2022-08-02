package link.jingweih.tinnews.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import link.jingweih.tinnews.model.Article
import link.jingweih.tinnews.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    defaultDispatcher: CoroutineDispatcher
) : BaseFlowUseCase<Int, List<Article>>(defaultDispatcher) {

    override fun execute(input: Int): Flow<List<Article>> {
        return flow {
            emit(newsRepository.fetchTopHeadlinesNews(input))
        }.combine(newsRepository.fetchAllSavedArticles()) { rawArticles, favoriteArticles ->
            val newArticle = rawArticles.map { article ->
                if (favoriteArticles.find { it.url == article.url } != null) {
                    article.copy(isFavorite = true)
                } else {
                    article.copy(isFavorite = false)
                }
            }
            newArticle
        }
    }
}