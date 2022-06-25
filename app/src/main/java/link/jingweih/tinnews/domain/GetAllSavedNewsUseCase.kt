package link.jingweih.tinnews.domain

import kotlinx.coroutines.flow.Flow
import link.jingweih.tinnews.model.Article
import link.jingweih.tinnews.repository.NewsRepository
import javax.inject.Inject

class GetAllSavedNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.fetchAllSavedArticles()
    }
}