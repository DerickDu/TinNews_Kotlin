package link.jingweih.tinnews.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*

abstract class BaseUseCase<in Input, Output>(private val defaultDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(input: Input): Output = withContext(defaultDispatcher) {
        performanceAction(input)
    }

    protected abstract suspend fun performanceAction(input: Input): Output
}