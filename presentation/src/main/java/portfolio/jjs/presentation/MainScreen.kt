package portfolio.jjs.presentation

import android.annotation.SuppressLint
import android.view.Menu
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import portfolio.jjs.presentation.ui.main.HomeScreen
import portfolio.jjs.presentation.ui.movie_detail.MovieDetailAddReplyScreen
import portfolio.jjs.presentation.ui.movie_detail.MovieDetailScreen
import portfolio.jjs.presentation.ui.my_page.MyPageScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(googleSignInClient: GoogleSignInClient) {
    val viewModel = hiltViewModel<MainViewModel>()
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentRoute == MainNav.route) {
                MainHeader(viewModel = viewModel, navController = navController)
            }
        })
    {
        MainNavigationScreen(
            mainViewModel = viewModel,
            navController = navController,
            googleSignInClient = googleSignInClient)
    }
}

@Composable
fun MainHeader(viewModel: MainViewModel, navController: NavHostController) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(onClick = {
                viewModel.openMyPage(navController)
            }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigationScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    googleSignInClient: GoogleSignInClient
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavHost(
        navController = navController,
        startDestination = MainNav.route) {
        composable(
            route = MainNav.route,
            deepLinks = MainNav.deepLinks) {
            HomeScreen(viewModel = mainViewModel, navController = navController)
        }
        composable(
            route = MyPageNav.route,
            deepLinks = MyPageNav.deepLinks,
        ) {
            MyPageScreen(googleSignInClient = googleSignInClient)
        }
        composable(
            route = MovieDetailNav.routeWithArgName(),
            arguments = MovieDetailNav.arguments,
            deepLinks = MovieDetailNav.deepLinks,
        ) {
            val movieString = MovieDetailNav.findArgument(it)
            if (movieString != null) {
                MovieDetailScreen(movieId = movieString, navController = navController)
            }
        }
        composable(
            route = MovieDetailAddReplyNav.routeWithArgName(),
            arguments = MovieDetailAddReplyNav.arguments,
            deepLinks = MovieDetailAddReplyNav.deepLinks,
        ) {
            val movieString = MovieDetailAddReplyNav.findArgument(it)
            if (movieString != null) {
                MovieDetailAddReplyScreen(navController, movieString)
            }
        }
    }
}
