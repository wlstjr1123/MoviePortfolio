package portfolio.jjs.movie.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import portfolio.jjs.data.repositoryImpl.AccountRepositoryImpl
import portfolio.jjs.data.repositoryImpl.MainRepositoryImpl
import portfolio.jjs.data.repositoryImpl.MovieDetailRepositoryImpl
import portfolio.jjs.domain.repository.AccountRepository
import portfolio.jjs.domain.repository.MainRepository
import portfolio.jjs.domain.repository.MovieDetailRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    fun bindMovieDetailRepository(movieDetailRepositoryImpl: MovieDetailRepositoryImpl): MovieDetailRepository

    @Binds
    @Singleton
    fun bingAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository
}