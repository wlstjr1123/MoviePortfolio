package portfolio.jjs.presentation.viewModel.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import portfolio.jjs.domain.model.AccountInfo
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.model.Reply
import portfolio.jjs.domain.usecase.AccountUseCase
import portfolio.jjs.domain.usecase.MovieDetailUseCase
import portfolio.jjs.presentation.MovieDetailAddReplyNav
import portfolio.jjs.presentation.MovieDetailNav
import portfolio.jjs.presentation.MyPageNav
import portfolio.jjs.presentation.arg_model.MovieDetailAddReplyArg
import portfolio.jjs.presentation.utils.GsonUtils
import portfolio.jjs.presentation.utils.NavigationUtils
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val useCase: MovieDetailUseCase,
    private val accountUseCase: AccountUseCase,
): ViewModel() {
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    private val _reply = MutableStateFlow<List<Reply>?>(null)
    val reply: StateFlow<List<Reply>?> = _reply

    val accountInfo = accountUseCase.getAccountInfo()

    suspend fun updateMovie(movieId: String) {
        useCase.getMovieDetail(movieId = movieId).collectLatest {
            _movie.emit(it)
        }
    }

    suspend fun getMovieReply (movieId: String) {
        useCase.getMovieReply(movieId = movieId).collectLatest {
            _reply.emit(it)
        }
    }

    fun openAddReply(navHostController: NavHostController, movie: Movie, accountInfo: AccountInfo?) {
        if (accountInfo != null) {
            val movieDetailAddReplyArg = MovieDetailAddReplyArg(movieId = movie.movieId, accountInfo = accountInfo)

            NavigationUtils.navigate(navHostController, MovieDetailAddReplyNav.navigateWithArg(movieDetailAddReplyArg))
        }
    }

    fun openMyPage(navHostController: NavHostController) {
        NavigationUtils.navigate(navHostController, MyPageNav.route)

    }

    fun insertReply(navHostController: NavHostController, movieDetailAddReplyArg: MovieDetailAddReplyArg, rating: Float, title: String, content: String) {
        viewModelScope.launch {

            useCase.insertReply(
                Reply(
                    replyId = null,
                    movieId = movieDetailAddReplyArg.movieId,
                    rating = rating,
                    title = title,
                    content = content,
                    userId = movieDetailAddReplyArg.accountInfo.userId,
                    userName = movieDetailAddReplyArg.accountInfo.name,
                    date = ZonedDateTime.now()
                )
            )
            navHostController.popBackStack()
        }
    }

    fun deleteReply(replyId: Int) {
        viewModelScope.launch {
            useCase.deleteReply(replyId)
            _reply.collectLatest {list ->
                val filterList = list?.filter { reply ->
                    reply.replyId.toString() != replyId.toString()
                }

                _reply.emit(filterList)
            }
        }
    }
}