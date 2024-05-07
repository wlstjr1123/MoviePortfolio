package portfolio.jjs.presentation.ui.movie_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gowtham.ratingbar.RatingBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import portfolio.jjs.presentation.arg_model.MovieDetailAddReplyArg
import portfolio.jjs.presentation.viewModel.movie_detail.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailAddReplyScreen(navHostController: NavHostController, movieDetailAddReplyArg: MovieDetailAddReplyArg, viewModel: MovieDetailViewModel = hiltViewModel()) {
    var rating by remember { mutableStateOf(5f) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
        RatingBar(
            value = rating,
            onValueChange = {
                rating = it
            },
            onRatingChanged = {

            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = title,
            onValueChange = {
                title = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                .focusRequester(focusRequester)
                .background(color = Color.White),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = content,
            onValueChange = {
                content = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                .background(color = Color.White),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (title == "" || content == "") {
                        popupSnackBar(scope, snackbarHostState, "모두 입력해주세요.")
                    } else {
                        viewModel.insertReply(
                            navHostController = navHostController,
                            movieDetailAddReplyArg = movieDetailAddReplyArg,
                            content = content,
                            title = title,
                            rating = rating
                        )
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                      if (title == "" || content == "") {
                          popupSnackBar(scope, snackbarHostState, "모두 입력해주세요.")
                      } else {
                          viewModel.insertReply(
                              navHostController = navHostController,
                              movieDetailAddReplyArg = movieDetailAddReplyArg,
                              content = content,
                              title = title,
                              rating = rating
                          )
                      }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "댓글달기")
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(hostState = snackbarHostState) { data ->
            Snackbar(
                snackbarData = data, modifier = Modifier.padding(50.dp),
                shape = RoundedCornerShape(10.dp),
            )
        }
    }
}

fun popupSnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
    onDismissCallback: () -> Unit = {}
) {

    scope.launch {
        snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
        onDismissCallback.invoke()
    }
}