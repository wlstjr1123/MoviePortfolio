package portfolio.jjs.data.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import portfolio.jjs.domain.model.Banner
import portfolio.jjs.domain.model.Movie
import java.io.InputStreamReader
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getBannerData(): Flow<List<Banner>> = flow {
        val inputStream = context.assets.open("banner_list.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonString = inputStreamReader.readText()
        val type = object: TypeToken<List<Banner>>() { }.type

        emit(Gson().fromJson<List<Banner>?>(jsonString, type).toList())
    }

    fun getMovieData(): Flow<List<Movie>> = flow {
        val inputStream = context.assets.open("movie_list.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonString = inputStreamReader.readText()
        val type = object: TypeToken<List<Movie>>() { }.type

        emit(Gson().fromJson<List<Movie>?>(jsonString, type).toList())
    }
}