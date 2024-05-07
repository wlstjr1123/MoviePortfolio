package portfolio.jjs.presentation.ui.movie_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import portfolio.jjs.presentation.viewModel.movie_detail.MovieDetailViewModel

@Composable
fun MovieDetailScreen(movieId: String, navController: NavHostController, viewModel: MovieDetailViewModel = hiltViewModel()) {
    val movie by viewModel.movie.collectAsState()

    val tabs = listOf("상제 정보", "댓글")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = movieId) {
        viewModel.updateMovie(movieId = movieId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            verticalAlignment = Alignment.Bottom) {
            Image(
                painter = rememberAsyncImagePainter(model = movie?.imageUrl),
                contentDescription = "moviePoster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.3f)
                )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(0.7f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "${movie?.movieName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "${movie?.time}")
                Text(text = "${movie?.filmRatings}")
            }
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .border(1.dp, Color.Black)
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Text(text = "${movie?.summary}")
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .border(4.dp, Color.Gray)
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            backgroundColor = Color.White,
            contentColor = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> movie?.let { MovieDetailInfoScreen(movie = it) }
            1 -> movie?.let {
                coroutineScope.launch {
                    viewModel.getMovieReply(it.movieId)
                }
                MovieDetailRepliesScreen(movie = it, viewModel = viewModel, navHostController = navController)
            }
        }
    }
}