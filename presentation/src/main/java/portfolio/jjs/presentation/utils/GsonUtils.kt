package portfolio.jjs.presentation.utils

import com.google.gson.Gson
import portfolio.jjs.presentation.arg_model.MovieDetailAddReplyArg

object GsonUtils {
    fun toJson(value: Any?): String {
        return Gson().toJson(value)
    }

    inline fun <reified T> fromJson(value: String?): T? {
        return kotlin.runCatching {
            Gson().fromJson(value, T::class.java)
        }.getOrNull()
    }
}