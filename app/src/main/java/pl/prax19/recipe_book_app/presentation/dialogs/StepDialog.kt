package pl.prax19.recipe_book_app.presentation.dialogs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pl.prax19.recipe_book_app.data.model.RecipeStep

@Composable
fun StepDialog(
    steps: List<RecipeStep>,
    onClose: (List<String>) -> Unit,
    isShown: Boolean
) {

    val rawInput = remember {
        mutableStateOf(
            steps.joinToString("\n") { it.description }
        )
    }

    val splitInput = rawInput.value.split("\n")

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
                onDismissRequest = { onClose(splitInput.filterNot { it.isBlank() }) },
                content = {
                    Surface (
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxSize().padding(16.dp).imePadding(),
                            value = styledText.text,
                            onValueChange = { rawInput.value = it },
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