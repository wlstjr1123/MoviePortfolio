package portfolio.jjs.presentation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import portfolio.jjs.presentation.arg_model.MovieDetailAddReplyArg
import portfolio.jjs.presentation.utils.GsonUtils
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object MainNav: Destination {
    override val route: String = NavigationRouteName.MAIN
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${NavigationRouteName.DEEP_LINK_SCHEME}$route" }
    )
}

object MyPageNav: Destination {
    override val route: String = NavigationRouteName.MY_PAGE
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${NavigationRouteName.DEEP_LINK_SCHEME}$route" }
    )
}

object MovieDetailNav: DestinationArg<String> {
    override val argName: String = "movieId"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )

    override fun findArgument(navBackStackEntry: NavBackStackEntry): String? {
        val movieString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<String>(movieString)
    }

    override fun navigateWithArg(item: String): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }

    override val route: String = NavigationRouteName.MOVIE_DETAIL
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${NavigationRouteName.DEEP_LINK_SCHEME}$route/{$argName}" }
    )
}

object MovieDetailAddReplyNav: DestinationArg<MovieDetailAddReplyArg> {
    override val argName: String = "movie_reply"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )

    override fun findArgument(navBackStackEntry: NavBackStackEntry): MovieDetailAddReplyArg? {
        val movieString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<MovieDetailAddReplyArg>(movieString)
    }

    override fun navigateWithArg(item:MovieDetailAddReplyArg): String {
        val arg = GsonUtils.toJson(item)
        var encode = URLEncoder.encode(arg,StandardCharsets.UTF_8.toString())
        return "$route/$encode"
    }

    override val route: String = NavigationRouteName.MOVIE_DETAIL_ADD_REPLY
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${NavigationRouteName.DEEP_LINK_SCHEME}$route/{$argName}" }
    )
}

interface Destination {
    val route: String
    val deepLinks: List<NavDeepLink>
}

interface DestinationArg<T> : Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}

object NavigationRouteName {
    const val DEEP_LINK_SCHEME = "jjsmovie://"
    const val MAIN = "main"
    const val MY_PAGE = "my_page"
    const val MOVIE_DETAIL = "movie_detail"
    const val MOVIE_DETAIL_ADD_REPLY = "movie_detail_add_reply"
}