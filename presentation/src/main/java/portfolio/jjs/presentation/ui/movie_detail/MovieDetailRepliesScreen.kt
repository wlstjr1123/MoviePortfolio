package portfolio.jjs.presentation.ui.movie_detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gowtham.ratingbar.RatingBar
import portfolio.jjs.domain.model.AccountInfo
import portfolio.jjs.domain.model.Movie
import portfolio.jjs.domain.model.Reply
import portfolio.jjs.presentation.ui.dialog.CommonDialog
import portfolio.jjs.presentation.viewModel.movie_detail.MovieDetailViewModel

@Composable
fun MovieDetailRepliesScreen(
    movie: Movie,
    viewModel: MovieDetailViewModel,
    navHostController: NavHostController
) {
    val accountInfo by viewModel.accountInfo.collectAsState()
    val replyList: List<Reply>? by viewModel.reply.collectAsState()
    var isDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                if (accountInfo != null) {
                    viewModel.openAddReply(
                        navHostController = navHostController,
                        movie = movie,
                        accountInfo = accountInfo,
                    )
                } else {
                    isDialog = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Filled.Create, "replyBtn")

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "댓글 달기")
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .border(4.dp, Color.Gray)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            replyList?.size?.let {
                items(it) { index ->
                    val reply = replyList?.get(index)

                    RepliesCard(reply = reply, accountInfo = accountInfo, viewModel = viewModel)

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .border(1.dp, Color.LightGray)
                    )
                }
            }
        }

        if (isDialog) {
            CommonDialog(
                title = "로그인을 해야합니다",
                text = "로그인 페이지로 이동하시겠습니까?",
                onConfirmation = {
                    viewModel.openMyPage(navHostController = navHostController)
                    isDialog = false
                },
                onDismiss = { isDialog = false }
            )
        }
    }
}

@Composable
fun RepliesCard(reply: Reply?, accountInfo: AccountInfo?, viewModel: MovieDetailViewModel) {

    if (reply != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.weight(0.2f),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Filled.AccountCircle,
                        "description",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Column(modifier = Modifier.weight(0.8f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RatingBar(
                            value = reply.rating,
                            onValueChange = {
                            },
                            onRatingChanged = {

                            },
                            modifier = Modifier.weight(1f)
                        )
                        if(accountInfo != null && accountInfo.userId == reply.userId) {
                            IconButton(onClick = { reply?.replyId?.let { viewModel.deleteReply(replyId = it) } },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(Icons.Filled.Delete, "delete",
                                    modifier = Modifier.size(30.dp))
                            }
                        }

                    }

                    Text(
                        text = reply.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = reply.content,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}