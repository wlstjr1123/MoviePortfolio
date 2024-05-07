package portfolio.jjs.presentation.ui.main

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import portfolio.jjs.domain.model.Banner
import portfolio.jjs.presentation.MainViewModel
import portfolio.jjs.presentation.ui.component.MovieCard

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavHostController) {
    val bannerList by viewModel.bannerList.collectAsState(initial = listOf())
    val movieList by viewModel.movieList.collectAsState(initial = listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()) {
        MainBanner(bannerList = bannerList)

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(
                movieList.size,
                span = {
                    GridItemSpan(1)
                }
            ) {
                MovieCard(movieData = movieList[it], viewModel = viewModel, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainBanner(bannerList: List<Banner>) {
    val pagerState = rememberPagerState()
    LaunchedEffect(key1 = pagerState) {
        autoScrollInfinity(pagerState)
    }

    HorizontalPager(count = bannerList.size, state = pagerState) { index ->
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .shadow(10.dp)
        ) {
            Box(Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = bannerList[index].imageurl),
                    contentDescription = "배너이미지$index",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                )

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "${index+1}/${bannerList.size}",
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0x33000000)))
                }

                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 0.dp, 10.dp)
                ) {
                    Text(text = bannerList[index].movieName,
                        color = Color.White,
                        modifier = Modifier.background(Color(0x33000000)))
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
private suspend fun autoScrollInfinity(pagerState: PagerState) {
    while (true) {
        delay(3000)
        val currentPage = pagerState.currentPage
        var nextPage = currentPage + 1
        if (nextPage >= pagerState.pageCount) {
            nextPage = 0
        }
        pagerState.animateScrollToPage(nextPage)
    }
}
