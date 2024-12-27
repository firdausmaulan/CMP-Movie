package fd.cmp.movie.screen.common

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.camera_label
import cmp_movie.composeapp.generated.resources.gallery_label
import fd.cmp.movie.helper.ColorHelper
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageOptionsBottomSheet(
    onDismissRequest: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = ColorHelper.bottomSheetBackground
    ) {
        ListItem(
            headlineContent = { Text(stringResource(Res.string.camera_label)) },
            modifier = Modifier.clickable { onCameraClick() }
        )
        ListItem(
            headlineContent = { Text(stringResource(Res.string.gallery_label)) },
            modifier = Modifier.clickable { onGalleryClick() }
        )
    }
}