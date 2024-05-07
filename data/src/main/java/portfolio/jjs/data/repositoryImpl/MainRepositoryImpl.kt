package portfolio.jjs.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import portfolio.jjs.data.datasource.MovieDataSource
import portfolio.jjs.domain.model.Banner
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource
): MainRepository {
    override fun getBannerList(): Flow<List<Banner>> {
        return dataSource.getBannerData()
    }

    override fun getMovieList(): Flow<List<Movie>> {
        return dataSource.getMovieData()
    }
}