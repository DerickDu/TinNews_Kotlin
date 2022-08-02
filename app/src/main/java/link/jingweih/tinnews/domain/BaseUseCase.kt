package link.jingweih.tinnews.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Input, Output>(private val defaultDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(input: Input): Output = withContext(defaultDispatcher) {
        execute(input)
    }

    protected abstract suspend fun execute(input: Input): Output
}