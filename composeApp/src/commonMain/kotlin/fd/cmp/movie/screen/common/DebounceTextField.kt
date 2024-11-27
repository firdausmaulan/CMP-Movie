package fd.cmp.movie.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fd.cmp.movie.helper.UiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

class Debouncer(
    private val coroutineScope: CoroutineScope,
    private val delayMillis: Long
) {
    private var debounceJob: Job? = null

    fun debounce(action: () -> Unit) {
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch(Dispatchers.Main) {
            delay(delayMillis)
            action()
        }
    }
}

@Composable
fun DebounceTextField(
    placeholderText: String,
    onDebouncedInput: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val debouncer = remember { Debouncer(coroutineScope, 1000L) } // 1000 milliseconds debounce time

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        maxLines = 1,
        value = text,
        onValueChange = { newText ->
            text = newText
            debouncer.debounce {
                onDebouncedInput(text)
            }
        },
        suffix = { Icon(Icons.Default.Search, contentDescription = "search") },
        placeholder = { Text(placeholderText) },
        colors = UiHelper.textFieldCustomColors()
    )
}

@Preview
@Composable
fun PreviewDebouncedTextField() {
    DebounceTextField(
        placeholderText = "Search here...",
        onDebouncedInput = { input ->
            println("Debounced text: $input")
        }
    )
}