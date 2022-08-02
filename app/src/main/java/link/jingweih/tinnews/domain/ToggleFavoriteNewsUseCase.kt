package link.jingweih.tinnews.domain

import kotlinx.coroutines.CoroutineDispatcher
import link.jingweih.tinnews.model.Article
import link.jingweih.tinnews.repository.NewsRepository
import javax.inject.Inject

class ToggleFavoriteNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    defaultDispatcher: CoroutineDispatcher
): BaseUseCase<Article, Unit>(defaultDispatcher) {

    override suspend fun execute(input: Article) {
        if (input.isFavorite) {
            newsRepository.favoriteArticle(input)
        } else {
            newsRepository.unFavoriteArticle(input)
        }
    }
}