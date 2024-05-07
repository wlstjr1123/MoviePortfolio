package portfolio.jjs.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import portfolio.jjs.data.datasource.MovieDataSource
import portfolio.jjs.data.db.dao.ReplyDao
import portfolio.jjs.data.db.entity.toDomainModel
import portfolio.jjs.data.db.entity.toReplyEntiy
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.model.Reply
import portfolio.jjs.domain.repository.MovieDetailRepository
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
    private val replyDao: ReplyDao
): MovieDetailRepository {
    override fun getMovieDetail(movieId: String): Flow<Movie> {
        return dataSource.getMovieData().map { movie ->
            movie.filterIsInstance<Movie>().first { it.movieId == movieId }
        }
    }

    override suspend fun getMovieReply(movieId: String): Flow<List<Reply>> {
        return replyDao.getMovieReplies(movieId).map { list ->
            list.map { it.toDomainModel() }
        }
    }

    override suspend fun insertReply(reply: Reply) {
        replyDao.insert(reply.toReplyEntiy())
    }

    override suspend fun deleteReply(replyId: Int) {
        replyDao.delete(replyId)
    }
}