package portfolio.jjs.presentation.ui.dialog

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CommonDialog(
    title: String,
    text: String,
    onConfirmation: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = { Text(text = text) },
        text = { Text(text = title) },
        onDismissRequest = onDismiss,
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

            }
            Button(onClick = onConfirmation) {
                Text(text = "예")
            }
        }
    )
}