package portfolio.jjs.domain.repository

import kotlinx.coroutines.flow.Flow
import portfolio.jjs.domain.model.Banner
import portfolio.jjs.domain.model.Movie

interface MainRepository {
    fun getBannerList(): Flow<List<Banner>>

    fun getMovieList(): Flow<List<Movie>>
}