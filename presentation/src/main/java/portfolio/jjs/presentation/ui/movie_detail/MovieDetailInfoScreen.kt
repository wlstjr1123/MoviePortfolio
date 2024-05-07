package portfolio.jjs.presentation.ui.movie_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import portfolio.jjs.domain.model.Movie

@Composable
fun MovieDetailInfoScreen(movie: Movie) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "등장인물",
            modifier = Modifier.padding(12.dp, 12.dp, 0.dp, 12.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(movie.characters.size) {index ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Icon(
                        Icons.Default.AccountCircle, 
                        "description",
                        modifier = Modifier.size(60.dp))
                    Text(text = movie.characters[index])
                    if (movie.characters.size > index) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}