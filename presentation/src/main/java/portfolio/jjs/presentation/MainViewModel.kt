package portfolio.jjs.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.usecase.MainUseCase
import portfolio.jjs.presentation.utils.NavigationUtils
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
): ViewModel() {

    val bannerList = mainUseCase.getBannerList()
    val movieList = mainUseCase.getMovieList()

    fun openMyPage(navHostController: NavHostController) {
        NavigationUtils.navigate(navHostController, MyPageNav.route)
    }

    fun openMovieDetail(navHostController: NavHostController, movie: Movie) {
        NavigationUtils.navigate(navHostController, MovieDetailNav.navigateWithArg(movie.movieId))
    }
}