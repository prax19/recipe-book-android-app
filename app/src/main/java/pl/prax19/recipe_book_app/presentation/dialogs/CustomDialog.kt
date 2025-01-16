package pl.prax19.recipe_book_app.presentation.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomDialog(
    dismissText: String = "Dismiss",
    acceptText: String = "Accept",
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    actionColor: Color = MaterialTheme.colorScheme.primary
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = dialogTitle)
        },
        iconContentColor = actionColor,
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAccept()
                }
            ) {
                Text(
                    acceptText,
                    color = actionColor
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(dismissText)
            }
        }
    )
}