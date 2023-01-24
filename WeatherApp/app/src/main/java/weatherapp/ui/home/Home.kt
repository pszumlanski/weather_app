package weatherapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import weatherapp.R
import weatherapp.data.models.local.CityModel
import weatherapp.data.models.local.SimpleWeatherModel
import weatherapp.ui.theme.AppTheme
import weatherapp.ui.theme.BlueJeans

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = viewModel.stateData

    Surface(Modifier.fillMaxSize()) {
        HomeContent(
            state = viewState,
            fetchMatchingCities = viewModel::fetchMatchingCities,
            optionSelected = viewModel::fetchWeatherForSelectedCity,
        )
    }
}

@Composable
private fun HomeContent(
    state: StateData,
    fetchMatchingCities: (input: String) -> Unit,
    optionSelected: (input: CityModel) -> Unit

) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.errorOccurred) {
            Toast.makeText(
                context,
                stringResource(R.string.generic_error_message),
                Toast.LENGTH_SHORT
            ).show()
        }
        AppBar()
        SearchBar(onSearchTapped = fetchMatchingCities)
        if (state.isLoading) {
            LoadingSpinner(
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 24.dp)
            )
        } else {
            if (state.weatherForSelectedCity == null) {
                MatchingCitiesList(cities = state.matchingCities, onOptionSelected = optionSelected)
            } else {
                WeatherDisplay(weatherData = state.weatherForSelectedCity)
            }
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        elevation = 4.dp,
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primarySurface,
    )
}

@Composable
fun SearchBar(
    onSearchTapped: (String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val hint = stringResource(R.string.search_bar_hint)
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Row(
        modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            BasicTextField(value = text,
                onValueChange = {
                    text = it
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .shadow(5.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isHintDisplayed = !it.isFocused
                    })
            if (isHintDisplayed) {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        Button(shape = CircleShape, onClick = {
            onSearchTapped(text)
            focusManager.clearFocus()
            text = ""
        }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_button_description),
                tint = Color.Black,
            )
        }
    }
}

@Composable
private fun WeatherDisplay(weatherData: SimpleWeatherModel) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .height(200.dp)
                .background(BlueJeans)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                Text(weatherData.name)
                Text(weatherData.visualTemp, fontSize = 64.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(weatherData.description)
            }
        }
    }
}

@Composable
private fun MatchingCitiesList(
    cities: List<CityModel>, onOptionSelected: (input: CityModel) -> Unit
) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
    ) {
        items(cities.size) { index ->
            ClickableText(text = AnnotatedString("${cities[index].name}, ${cities[index].country.uppercase()}"),
                style = TextStyle(fontSize = 20.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onClick = {
                    onOptionSelected(cities[index])
                })
        }
    }
}

@Composable
private fun LoadingSpinner(modifier: Modifier = Modifier) {
    CircularProgressIndicator(strokeWidth = 3.dp, modifier = modifier)
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewHomeContent() {
    AppTheme {
        HomeContent(state = PreviewData().stateData, fetchMatchingCities = {}, optionSelected = {})
    }
}
