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
import cmp_movie.composeapp.generated.resources.edit_profile_action
import cmp_movie.composeapp.generated.resources.logout_action
import fd.cmp.movie.helper.ColorHelper
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingBottomSheet(
    onDismissRequest: () -> Unit,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = ColorHelper.bottomSheetBackground
    ) {
        ListItem(
            headlineContent = { Text(stringResource(Res.string.edit_profile_action)) },
            modifier = Modifier.clickable { onEditClick() }
        )
        ListItem(
            headlineContent = { Text(stringResource(Res.string.logout_action)) },
            modifier = Modifier.clickable { onLogoutClick() }
        )
    }
}