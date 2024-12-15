package fd.cmp.movie.screen.common

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.edit_profile_action
import cmp_movie.composeapp.generated.resources.logout_action
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