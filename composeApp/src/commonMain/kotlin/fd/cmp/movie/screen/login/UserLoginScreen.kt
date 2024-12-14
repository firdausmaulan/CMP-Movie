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
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fd.myblog.R
import com.fd.myblog.data.remote.request.UserLoginRequest
import com.fd.myblog.helper.UiHelper
import com.fd.myblog.ui.common.ErrorBottomSheetDialog
import com.fd.myblog.ui.common.LoadingButton

@Composable
fun UserLoginScreen(
    viewModel: UserLoginViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {

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
                        viewModel.emailError = !viewModel.isValidEmail(it)
                    },
                    label = { Text("Email") },
                    isError = viewModel.emailError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = UiHelper.textFieldCustomColors()
                )
                if (viewModel.emailError) {
                    Text(
                        text = "Invalid email format",
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
                        viewModel.passwordError = !viewModel.isValidPassword(it)
                    },
                    label = { Text("Password") },
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
                                if (isPasswordVisible) painterResource(id = R.drawable.ic_visible)
                                else painterResource(id = R.drawable.ic_visible_off),
                                contentDescription = "Password visibility toggle"
                            )
                        }
                    }
                )
                if (viewModel.passwordError) {
                    Text(
                        text = "Password must be at least 8 characters",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    )
                }

                when (val state = viewModel.state) {
                    is UserLoginState.Idle -> {
                        SubmitButton(viewModel, UserLoginRequest(email, password), onRegisterClick)
                    }

                    is UserLoginState.Loading -> {
                        LoadingButton()
                    }

                    is UserLoginState.Success -> {
                        onLoginSuccess()
                    }

                    is UserLoginState.Error -> {
                        ErrorBottomSheetDialog(
                            subMessage = state.message,
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

@Composable
fun SubmitButton(
    viewModel: UserLoginViewModel,
    userLoginRequest: UserLoginRequest,
    onRegisterClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                viewModel.emailError = !viewModel.isValidEmail(userLoginRequest.email)
                viewModel.passwordError = !viewModel.isValidPassword(userLoginRequest.password)
                viewModel.login(userLoginRequest)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("LOGIN")
        }

        Text(
            "OR",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )

        OutlinedButton(
            border = UiHelper.outlinedButtonBorderColors(),
            onClick = { onRegisterClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("REGISTER")
        }
    }
}