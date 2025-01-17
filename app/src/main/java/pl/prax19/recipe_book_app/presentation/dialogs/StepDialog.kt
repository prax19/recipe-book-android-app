package pl.prax19.recipe_book_app.presentation.dialogs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun StepDialog(
    rawSteps: String,
    onValueChange: (String) -> Unit,
    onClose: () -> Unit,
    isShown: Boolean
) {

    val splitInput = rawSteps.split("\n")

    // TODO: optimise
    val styledText = buildAnnotatedString {
        splitInput.forEachIndexed { index, paragraph ->
            withStyle(ParagraphStyle(lineHeight = 24.sp)) {
                append(paragraph)
            }
            if (index < splitInput.size - 1) {
                append("\n")
            }
        }
    }

    when (isShown) {
        true -> {
            Dialog(
                properties = DialogProperties(
                    decorFitsSystemWindows = false,
                    usePlatformDefaultWidth = false
                ),
                onDismissRequest = { onClose() },
                content = {
                    Surface (
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxSize().padding(16.dp).imePadding(),
                            value = styledText.text,
                            onValueChange = { onValueChange(it) },
                            label = { Text("Steps") },
                            placeholder = { Text("Add your recipe steps separated by enter") }
                        )
                    }
                }
            )
        }
        false -> { }
    }

}