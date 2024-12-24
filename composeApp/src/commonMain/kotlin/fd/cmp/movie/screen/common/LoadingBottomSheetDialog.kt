package fd.cmp.movie.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.ic_send
import cmp_movie.composeapp.generated.resources.loading_image_description
import cmp_movie.composeapp.generated.resources.loading_message
import cmp_movie.composeapp.generated.resources.loading_sub_message
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingBottomSheetDialog(
    onDismissRequest: () -> Unit,
    imageRes: DrawableResource = Res.drawable.ic_send,
    message: String = stringResource(Res.string.loading_message),
    subMessage: String = stringResource(Res.string.loading_sub_message),
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image
            Image(
                painter = painterResource(imageRes),
                contentDescription = stringResource(Res.string.loading_image_description),
                modifier = Modifier
                    .height(150.dp)
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Message
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Sub-message
            Text(
                text = subMessage,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}