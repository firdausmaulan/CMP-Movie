package fd.cmp.movie.screen.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.back
import cmp_movie.composeapp.generated.resources.date_of_birth_error
import cmp_movie.composeapp.generated.resources.date_of_birth_label
import cmp_movie.composeapp.generated.resources.email_label
import cmp_movie.composeapp.generated.resources.ic_back
import cmp_movie.composeapp.generated.resources.ic_user_setting
import cmp_movie.composeapp.generated.resources.name_error
import cmp_movie.composeapp.generated.resources.name_label
import cmp_movie.composeapp.generated.resources.phone_number_error
import cmp_movie.composeapp.generated.resources.phone_number_label
import cmp_movie.composeapp.generated.resources.profile_label
import cmp_movie.composeapp.generated.resources.settings_label
import cmp_movie.composeapp.generated.resources.submit_action
import fd.cmp.movie.data.model.User
import fd.cmp.movie.helper.TextHelper
import fd.cmp.movie.helper.UiHelper
import fd.cmp.movie.screen.common.ConfirmationBottomSheet
import fd.cmp.movie.screen.common.ErrorBottomSheetDialog
import fd.cmp.movie.screen.common.ErrorScreen
import fd.cmp.movie.screen.common.LoadingButton
import fd.cmp.movie.screen.common.LoadingScreen
import fd.cmp.movie.screen.common.NetworkImage
import fd.cmp.movie.screen.common.SuccessBottomSheetDialog
import fd.cmp.movie.screen.common.UserSettingBottomSheet
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navigateBack: () -> Unit,
    onLogout: () -> Unit
) {

    val viewModel = koinViewModel<UserViewModel>()

    var showBottomSheetSetting by remember { mutableStateOf(false) }
    var showBottomSheetConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = UiHelper.topAppBarColors(),
                title = {
                    Text(
                        stringResource(Res.string.profile_label),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showBottomSheetSetting = true }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_user_setting),
                            contentDescription = stringResource(Res.string.settings_label)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.state) {
                is UserDetailState.Loading -> {
                    LoadingScreen()
                }

                is UserDetailState.Error -> {
                    ErrorScreen(message = state.message)
                }

                is UserDetailState.Success -> {
                    UserDetailContent(
                        user = state.user,
                        viewModel = viewModel,
                    )
                }
            }
        }

        // Show bottom sheet when settings icon is clicked
        if (showBottomSheetSetting) {
            UserSettingBottomSheet(
                onDismissRequest = {
                    showBottomSheetSetting = false
                },
                onEditClick = {
                    viewModel.isEdit = true
                    showBottomSheetSetting = false
                },
                onLogoutClick = {
                    showBottomSheetConfirmation = true
                    showBottomSheetSetting = false
                }
            )
        }

        if (showBottomSheetConfirmation) {
            ConfirmationBottomSheet(
                onDismissClick = {
                    showBottomSheetConfirmation = false
                },
                onConfirmClick = {
                    viewModel.logout()
                    onLogout()
                }
            )
        }
    }
}

@Composable
fun UserDetailContent(
    user: User,
    viewModel: UserViewModel,
) {

    var name by remember { mutableStateOf(user.name) }
    var phone by remember { mutableStateOf(user.phone) }
    var dateOfBirth by remember { mutableStateOf(user.dateOfBirth) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            imageUrl = user.imageUrl,
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .padding(8.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )

        OutlinedTextField(
            colors = UiHelper.textFieldCustomColors(),
            value = if (user.email.isNullOrEmpty()) "" else user.email.toString(),
            onValueChange = { },
            readOnly = true,
            label = { Text(stringResource(Res.string.email_label)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            colors = UiHelper.textFieldCustomColors(),
            value = name.toString(),
            onValueChange = {
                name = it
                viewModel.nameError = it.isEmpty()
            },
            readOnly = !viewModel.isEdit,
            label = { Text(stringResource(Res.string.name_label)) },
            isError = viewModel.nameError,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (viewModel.nameError) {
            Text(
                text = stringResource(Res.string.name_error),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        OutlinedTextField(
            colors = UiHelper.textFieldCustomColors(),
            value = phone.toString(),
            onValueChange = {
                phone = it
                viewModel.phoneError = !TextHelper.isValidPhoneNumber(it)
            },
            readOnly = !viewModel.isEdit,
            label = { Text(stringResource(Res.string.phone_number_label)) },
            isError = viewModel.phoneError,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (viewModel.phoneError) {
            Text(
                text = stringResource(Res.string.phone_number_error),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        OutlinedTextField(
            colors = UiHelper.textFieldCustomColors(),
            value = dateOfBirth.toString(),
            onValueChange = {
                dateOfBirth = it
                viewModel.dateOfBirthError = !TextHelper.isValidDate(it)
            },
            readOnly = !viewModel.isEdit,
            label = { Text(stringResource(Res.string.date_of_birth_label)) },
            isError = viewModel.dateOfBirthError,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (viewModel.dateOfBirthError) {
            Text(
                text = stringResource(Res.string.date_of_birth_error),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }

    when (val state = viewModel.stateEdit) {
        is UserEditState.Idle -> {
            if (viewModel.isEdit) {
                user.name = name
                user.phone = phone
                user.dateOfBirth = dateOfBirth
                SubmitButton(viewModel, user)
            }
        }

        is UserEditState.Loading -> LoadingButton()
        is UserEditState.Error -> {
            ErrorBottomSheetDialog(
                onDismissRequest = {
                    viewModel.stateEdit = UserEditState.Idle
                },
                subMessage = state.message
            )
        }

        is UserEditState.Success -> {
            SuccessBottomSheetDialog(onDismissRequest = {
                viewModel.isEdit = false
                viewModel.stateEdit = UserEditState.Idle
            })
        }
    }
}

@Composable
fun SubmitButton(viewModel: UserViewModel, user: User) {
    Button(
        onClick = {
            viewModel.editUser(user)
        },
        elevation = UiHelper.buttonElevation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(stringResource(Res.string.submit_action).uppercase())
    }
}