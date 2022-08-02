package link.jingweih.tinnews.ui.save

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import link.jingweih.tinnews.domain.GetAllSavedNewsUseCase
import link.jingweih.tinnews.domain.ToggleFavoriteNewsUseCase
import link.jingweih.tinnews.model.Article
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    private val getAllSavedNewsUseCase: GetAllSavedNewsUseCase,
    private val toggleFavoriteNewsUseCase: ToggleFavoriteNewsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<SaveUiState> =
        MutableStateFlow(SaveUiState.Success(emptyList()))

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<SaveUiState> = _uiState

    init {
        getAllSavedNews()
    }

    private fun getAllSavedNews() {
        getAllSavedNewsUseCase(Unit)
            .onStart {
                _uiState.value = SaveUiState.Loading
            }
            .onEach {
                _uiState.value = SaveUiState.Success(it)
            }
            .catch { e ->
                _uiState.value = SaveUiState.Error(e.message)
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite(article: Article) {
        viewModelScope.launch {
            try {
                val newArticle = article.copy(isFavorite = !article.isFavorite)
                toggleFavoriteNewsUseCase.invoke(newArticle)
            } catch (e: Exception) {
                _uiState.value = SaveUiState.Error(e.message)
            }

        }
    }
}

sealed class SaveUiState {

    data class Success(val articles: List<Article>): SaveUiState()

    data class Error(val error: String?): SaveUiState()

    object Loading: SaveUiState()
}
