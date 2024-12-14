package fd.cmp.movie.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.error_image
import cmp_movie.composeapp.generated.resources.error_message
import cmp_movie.composeapp.generated.resources.error_sub_message
import cmp_movie.composeapp.generated.resources.ic_question
import cmp_movie.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorScreen(
    imageRes: DrawableResource = Res.drawable.ic_question,
    message: String = stringResource(Res.string.error_message),
    subMessage: String = stringResource(Res.string.error_sub_message),
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(imageRes),
            modifier = Modifier
                .height(150.dp)
                .width(250.dp),
            contentDescription = stringResource(Res.string.error_image)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = message, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        Text(text = subMessage, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(text = stringResource(Res.string.retry))
            }
        }
    }
}
