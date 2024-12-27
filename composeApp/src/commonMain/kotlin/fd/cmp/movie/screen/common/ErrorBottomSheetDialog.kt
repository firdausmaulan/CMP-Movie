package fd.cmp.movie.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.error_image_description
import cmp_movie.composeapp.generated.resources.error_message
import cmp_movie.composeapp.generated.resources.error_sub_message
import cmp_movie.composeapp.generated.resources.ic_question
import cmp_movie.composeapp.generated.resources.ok
import fd.cmp.movie.helper.ColorHelper
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorBottomSheetDialog(
    onDismissRequest: () -> Unit,
    imageRes: DrawableResource = Res.drawable.ic_question,
    message: String = stringResource(Res.string.error_message),
    subMessage: String = stringResource(Res.string.error_sub_message),
    buttonText: String = stringResource(Res.string.ok),
    buttonColor: Color = Color.Red,
    onButtonClick: () -> Unit = onDismissRequest
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = ColorHelper.bottomSheetBackground
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
                contentDescription = stringResource(Res.string.error_image_description),
                modifier = Modifier
                    .height(150.dp)
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Message
            Text(
                text = message,
                fontWeight = FontWeight.Bold,
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
            // Button
            Button(
                onClick = {
                    onButtonClick()
                    onDismissRequest()
                },
                colors = ButtonDefaults.buttonColors(contentColor = buttonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = buttonText, color = Color.White)
            }
        }
    }
}
