package fd.cmp.movie.screen.location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.close_action
import cmp_movie.composeapp.generated.resources.confirm_action
import cmp_movie.composeapp.generated.resources.edit_location_label
import cmp_movie.composeapp.generated.resources.ic_close
import cmp_movie.composeapp.generated.resources.search_location_label
import fd.cmp.movie.data.model.LocationData
import fd.cmp.movie.helper.ColorHelper
import fd.cmp.movie.helper.UiHelper
import fd.cmp.movie.screen.common.DebounceTextField
import fd.cmp.movie.screen.common.ErrorBottomSheetDialog
import fd.cmp.movie.screen.common.MapScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    locationData: String?,
    onClose: () -> Unit,
    onConfirm: (locationData: String?) -> Unit
) {

    val viewModel = koinViewModel<LocationViewModel>()

    if (viewModel.requestPermission) {
        viewModel.kLocationService.EnableLocation()
        viewModel.requestPermission = false
    }

    LaunchedEffect(viewModel) {
        // buggy when activate location service
        // map always show current location
        /*if (viewModel.isGPSEnabled) {
            viewModel.getCurrentLocation()
        } else {
            viewModel.enableGPSAndLocation()
        }*/
        if (locationData != null) {
            viewModel.initLocation(LocationData.fromJson(locationData))
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            colors = UiHelper.topAppBarColors(),
            title = {
                Text(
                    stringResource(Res.string.edit_location_label),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            },
            actions = {
                IconButton(onClick = { onClose() }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        tint = Color.Black,
                        contentDescription = stringResource(Res.string.close_action)
                    )
                }
            }
        )
    }) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            MapScreen(
                viewModel.locationData,
                mapModifier = Modifier.fillMaxSize(),
            )

            Column(modifier = Modifier.fillMaxSize()) {
                Box(Modifier.padding(8.dp)) {
                    DebounceTextField(placeholderText = stringResource(Res.string.search_location_label)) {
                        viewModel.searchLocation(it)
                    }
                }

                when (val state = viewModel.state) {
                    is LocationState.Idle -> {}

                    is LocationState.Update -> {}

                    is LocationState.Success -> {
                        LocationList(
                            state.results,
                            onSelect = {
                                viewModel.state = LocationState.Idle
                                viewModel.setLocation(it)
                            }
                        )
                    }

                    is LocationState.Error -> {
                        ErrorBottomSheetDialog(
                            subMessage = state.message,
                            onDismissRequest = { viewModel.state = LocationState.Idle },
                        )
                    }
                }
            }

            if (viewModel.address.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 150.dp)
                        .align(Alignment.BottomCenter)
                        .background(color = MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = viewModel.address,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Button(
                        onClick = {
                            viewModel.selectedLocation?.let {
                                onConfirm(LocationData.toJson(it))
                            }
                        },
                        elevation = UiHelper.buttonElevation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = stringResource(Res.string.confirm_action).uppercase())
                    }
                }
            }
        }
    }
}

@Composable
fun LocationList(locations: List<LocationData>, onSelect: (locationData: LocationData) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        locations.forEach { location ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .clickable {
                        onSelect(location)
                    }) {
                Text(
                    text = location.displayName.toString(),
                    color = Color.Black,
                    minLines = 2,
                    modifier = Modifier.padding(8.dp),
                )
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}