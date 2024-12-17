package fd.cmp.movie.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.email_error
import cmp_movie.composeapp.generated.resources.email_label
import cmp_movie.composeapp.generated.resources.ic_visible
import cmp_movie.composeapp.generated.resources.ic_visible_off
import cmp_movie.composeapp.generated.resources.login_action
import cmp_movie.composeapp.generated.resources.password_error
import cmp_movie.composeapp.generated.resources.password_label
import fd.cmp.movie.data.remote.request.UserLoginRequest
import fd.cmp.movie.helper.TextHelper
import fd.cmp.movie.helper.UiHelper
import fd.cmp.movie.screen.common.ErrorBottomSheetDialog
import fd.cmp.movie.screen.common.LoadingButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserLoginScreen(
    navigateToMovieList: () -> Unit
) {

    val viewModel = koinViewModel<UserLoginViewModel>()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Email Input with Validation
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        viewModel.emailError = !TextHelper.isValidEmailFormat(it)
                    },
                    label = { Text(stringResource(Res.string.email_label)) },
                    isError = viewModel.emailError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = UiHelper.textFieldCustomColors()
                )
                if (viewModel.emailError) {
                    Text(
                        text = stringResource(Res.string.email_error),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    )
                }

                // Password Input with Validation
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        viewModel.passwordError = !TextHelper.isValidPassword(it)
                    },
                    label = { Text(stringResource(Res.string.password_label)) },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = viewModel.passwordError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = UiHelper.textFieldCustomColors(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible })
                        {
                            Icon(
                                if (isPasswordVisible) painterResource(Res.drawable.ic_visible)
                                else painterResource(Res.drawable.ic_visible_off),
                                contentDescription = stringResource(Res.string.password_label)
                            )
                        }
                    }
                )
                if (viewModel.passwordError) {
                    Text(
                        text = stringResource(Res.string.password_error),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    )
                }

                when (val state = viewModel.state) {
                    is UserLoginState.Idle -> {
                        val userLoginRequest = UserLoginRequest(email, password)
                        Button(
                            onClick = {
                                viewModel.emailError =
                                    !TextHelper.isValidEmailFormat(userLoginRequest.email)
                                viewModel.passwordError =
                                    !TextHelper.isValidPassword(userLoginRequest.password)
                                viewModel.login(userLoginRequest)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(stringResource(Res.string.login_action))
                        }
                    }

                    is UserLoginState.Loading -> {
                        LoadingButton()
                    }

                    is UserLoginState.Success -> {
                        navigateToMovieList()
                    }

                    is UserLoginState.Error -> {
                        ErrorBottomSheetDialog(
                            subMessage = state.message.toString(),
                            onDismissRequest = {
                                viewModel.state = UserLoginState.Idle
                            }
                        )
                    }
                }

            }
        }
    }
}