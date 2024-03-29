package link.jingweih.tinnews.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import link.jingweih.tinnews.domain.GetTopHeadlinesNewsUseCase
import link.jingweih.tinnews.domain.ToggleFavoriteNewsUseCase
import link.jingweih.tinnews.model.Article
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase,
    private val toggleFavoriteNewsUseCase: ToggleFavoriteNewsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<TopHeadlinesUiState> = MutableStateFlow(
        TopHeadlinesUiState.Success(emptyList())
    )

    val uiState: StateFlow<TopHeadlinesUiState> = _uiState

    private var initPage = 0
    private var job: Job? = null

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines() {
        if (_uiState.value is TopHeadlinesUiState.Loading) {
            return
        }
        if (job != null) {
            job?.cancel()
        }
        job = getTopHeadlinesNewsUseCase(++initPage)
            .onStart {
                _uiState.value = TopHeadlinesUiState.Loading
            }
            .onEach {
                _uiState.value = TopHeadlinesUiState.Success(it)
            }
            .catch { e ->
                _uiState.value = TopHeadlinesUiState.Error(e.message)
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavoriteNews(article: Article) {
        viewModelScope.launch {
            try {
                val newArticle = article.copy(isFavorite = !article.isFavorite)
                toggleFavoriteNewsUseCase.invoke(newArticle)
            } catch (e: Exception) {
                _uiState.value = TopHeadlinesUiState.Error(e.message)
            }
        }
    }
}


sealed class TopHeadlinesUiState {
    data class Success(val articles: List<Article>) : TopHeadlinesUiState()

    data class Error(val error: String?) : TopHeadlinesUiState()

    object Loading : TopHeadlinesUiState()
}