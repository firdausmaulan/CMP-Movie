package fd.cmp.movie.screen.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}