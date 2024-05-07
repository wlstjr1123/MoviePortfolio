package portfolio.jjs.domain.repository

import kotlinx.coroutines.flow.Flow
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.model.Reply

interface MovieDetailRepository {
    fun getMovieDetail(movieId: String): Flow<Movie>

    suspend fun getMovieReply(movieId: String): Flow<List<Reply>>

    suspend fun insertReply(reply: Reply)

    suspend fun deleteReply(replyId: Int)
}