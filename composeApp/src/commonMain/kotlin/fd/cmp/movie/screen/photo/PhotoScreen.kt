package fd.cmp.movie.screen.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.ic_close
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.ui.camera.CameraMode
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import fd.cmp.movie.screen.common.ErrorBottomSheetDialog
import fd.cmp.movie.screen.common.ErrorScreen
import fd.cmp.movie.screen.common.ImageOptionsBottomSheet
import fd.cmp.movie.screen.common.LoadingBottomSheetDialog
import fd.cmp.movie.screen.common.SuccessBottomSheetDialog
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    onClose: () -> Unit,
    onSuccessUpload: (imageUrl: String) -> Unit
) {

    val viewModel = koinViewModel<PhotoViewModel>()
    var showImageOptionBottomSheet by remember { mutableStateOf(true) }
    var type by remember { mutableStateOf(PhotoEnum.NONE) }

    val cameraState = rememberPeekabooCameraState(
        initialCameraMode = CameraMode.Front,
        onCapture = {
            type = PhotoEnum.NONE
            viewModel.uploadPhoto(it)
        }
    )

    val galleryScope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = galleryScope,
        onResult = { byteArrays ->
            type = PhotoEnum.NONE
            byteArrays.firstOrNull()?.let {
                viewModel.uploadPhoto(it)
            }
        }
    )

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
                navigationIconContentColor = Color.Black,
                actionIconContentColor = Color.Black,
            ),
            title = {
                Text(
                    "Edit Photo",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            },
            actions = {
                IconButton(onClick = { onClose() }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        tint = Color.Black,
                        contentDescription = "Close"
                    )
                }
            }
        )
    }) { paddingValues ->

        if (showImageOptionBottomSheet) {
            ImageOptionsBottomSheet(
                onDismissRequest = {
                    showImageOptionBottomSheet = false
                },
                onCameraClick = {
                    showImageOptionBottomSheet = false
                    type = PhotoEnum.CAMERA
                },
                onGalleryClick = {
                    showImageOptionBottomSheet = false
                    type = PhotoEnum.GALLERY
                }
            )
        }

        when (type) {
            PhotoEnum.CAMERA -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    PeekabooCamera(
                        state = cameraState,
                        modifier = Modifier.fillMaxSize(),
                        permissionDeniedContent = {
                            ErrorScreen(
                                message = "Camera permission denied",
                                subMessage = "Please grant the camera permission to use the camera"
                            )
                        },
                    )

                    // Capture button positioned at bottom center
                    Box(
                        modifier = Modifier.fillMaxSize().padding(bottom = 32.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        CaptureButton(
                            onClick = { cameraState.capture() }
                        )
                    }
                }
            }

            PhotoEnum.GALLERY -> {
                singleImagePicker.launch()
            }

            else -> {
                // Do Nothing
            }
        }

        when (val state = viewModel.state) {
            is PhotoState.Idle -> {}
            is PhotoState.Success -> {
                SuccessBottomSheetDialog(
                    subMessage = "Image uploaded successfully",
                    onDismissRequest = { viewModel.state = PhotoState.Idle },
                    onButtonClick = {
                        viewModel.state = PhotoState.Idle
                        onSuccessUpload(state.imagePath)
                    },
                )
            }

            is PhotoState.Error -> {
                ErrorBottomSheetDialog(
                    subMessage = state.message.toString(),
                    onDismissRequest = { viewModel.state = PhotoState.Idle },
                )
            }

            PhotoState.Loading -> {
                LoadingBottomSheetDialog(
                    onDismissRequest = {}
                )
            }
        }
    }
}

@Composable
fun CaptureButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Outer circle
    Box(
        modifier = modifier
            .size(80.dp)
            .border(
                width = 4.dp,
                color = Color.White,
                shape = CircleShape
            )
            .padding(4.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Inner circle
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        )
    }
}