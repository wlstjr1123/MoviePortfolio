package portfolio.jjs.presentation.ui.my_page

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import portfolio.jjs.domain.model.AccountInfo
import portfolio.jjs.presentation.viewModel.my_page.MyPageViewModel

@Composable
fun MyPageScreen(
    googleSignInClient: GoogleSignInClient,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val accountInfo by viewModel.accountInfo.collectAsState()
    val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    val context = LocalContext.current
    val startForResult =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    handleSignInResult(context, task, viewModel, firebaseAuth)
                }
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (accountInfo != null) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = accountInfo?.profileImageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                ),
                contentDescription = "profileImage",
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Text(
                text = accountInfo?.name.orEmpty(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.signOut()
                    when (accountInfo?.type) {
                        AccountInfo.Type.GOOGLE -> {
                            firebaseAuth.signOut()
                        }

                        else -> {}
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                androidx.compose.material.Text(text = "로그아웃")
            }
            Spacer(modifier = Modifier.height(10.dp))
        } else {
            Button(onClick = {
                startForResult.launch(googleSignInClient.signInIntent)
            }, modifier = Modifier.fillMaxWidth()) {
                androidx.compose.material.Text(text = "구글 로그인")
            }
        }
    }
}

private fun handleSignInResult(
    context: Context,
    accountTask: Task<GoogleSignInAccount>,
    viewModel: MyPageViewModel,
    firebaseAuth: FirebaseAuth
) {
    try {
        val account = accountTask.result ?: return
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.signIn(
                        AccountInfo(
                            account.idToken.orEmpty(),
                            account.displayName.orEmpty(),
                            AccountInfo.Type.GOOGLE,
                            account.photoUrl.toString(),
                            account.id.orEmpty(),
                        )
                    )
                } else {
                    viewModel.signOut()
                    firebaseAuth.signOut()
                }
            }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}