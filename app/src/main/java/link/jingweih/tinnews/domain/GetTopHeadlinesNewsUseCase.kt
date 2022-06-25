package link.jingweih.tinnews.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import link.jingweih.tinnews.model.Article
import link.jingweih.tinnews.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    defaultDispatcher: CoroutineDispatcher
): BaseUseCase<Int, Flow<List<Article>>>(defaultDispatcher) {

    override suspend fun performanceAction(input: Int): Flow<List<Article>> {
        return newsRepository.fetchTopHeadlinesNews(input)
    }
}