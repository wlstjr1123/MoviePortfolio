package portfolio.jjs.domain.usecase

import kotlinx.coroutines.flow.Flow
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.model.Reply
import portfolio.jjs.domain.repository.MovieDetailRepository
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    private val repository: MovieDetailRepository
) {
    fun getMovieDetail(movieId: String): Flow<Movie> {
        return repository.getMovieDetail(movieId = movieId)
    }

    suspend fun getMovieReply(movieId: String): Flow<List<Reply>> {
        return repository.getMovieReply(movieId)
    }

    suspend fun insertReply(reply: Reply) {
        repository.insertReply(reply)
    }

    suspend fun deleteReply(replyId: Int) {
        repository.deleteReply(replyId)
    }
}