package fd.cmp.movie.screen.common

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageOptionsBottomSheet(
    onDismissRequest: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {
        ListItem(
            headlineContent = { Text("Camera") },
            modifier = Modifier.clickable { onCameraClick() }
        )
        ListItem(
            headlineContent = { Text("Gallery") },
            modifier = Modifier.clickable { onGalleryClick() }
        )
    }
}