package portfolio.jjs.domain.usecase

import kotlinx.coroutines.flow.Flow
import portfolio.jjs.domain.model.Banner
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.repository.MainRepository
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    fun getBannerList(): Flow<List<Banner>> {
        return mainRepository.getBannerList()
    }

    fun getMovieList(): Flow<List<Movie>> {
        return mainRepository.getMovieList()
    }
}