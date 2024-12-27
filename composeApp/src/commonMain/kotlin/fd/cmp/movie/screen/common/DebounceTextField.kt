package fd.cmp.movie.screen.common

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.search_placeholder
import fd.cmp.movie.helper.UiHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

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
    placeholderText: String = stringResource(Res.string.search_placeholder),
    onDebouncedInput: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val debouncer = remember { Debouncer(coroutineScope, 1000L) } // 1000 milliseconds debounce time

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        value = text,
        onValueChange = { newText ->
            text = newText
            debouncer.debounce {
                onDebouncedInput(text)
            }
        },
        suffix = { Icon(Icons.Default.Search, contentDescription = placeholderText) },
        placeholder = { Text(placeholderText) },
        colors = UiHelper.textFieldCustomColors()
    )
}