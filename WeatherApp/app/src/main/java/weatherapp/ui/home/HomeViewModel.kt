package weatherapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import weatherapp.R
import weatherapp.data.models.local.CityModel
import weatherapp.data.models.local.SimpleWeatherModel
import weatherapp.data.network.ApiFetchingCallback
import weatherapp.data.repositories.CityLookupRepository
import weatherapp.data.repositories.NotificationsRepository
import weatherapp.data.repositories.WeatherRepository
import weatherapp.injection.WeatherApp
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityLookupRepository: CityLookupRepository,
    private val weatherRepository: WeatherRepository,
    private val notificationsRepository: NotificationsRepository
) :
    ViewModel(),
    ApiFetchingCallback {

    var stateData by mutableStateOf(StateData())

    fun fetchMatchingCities(input: String) {
        stateData = stateData.copy(isLoading = true, errorOccurred = false)
        cityLookupRepository.fetchMatchingCities(searchInput = input, callback = this)
    }

    fun fetchWeatherForSelectedCity(selectedCity: CityModel) {
        stateData = stateData.copy(isLoading = true, errorOccurred = false)
        weatherRepository.fetchWeatherForLocation(cityModel = selectedCity, callback = this)
    }

    override fun citiesFetchedSuccessfully(list: List<CityModel>) {
        stateData =
            stateData.copy(
                matchingCities = list,
                weatherForSelectedCity = null,
                isLoading = false
            )
    }

    override fun weatherFetchedSuccessfully(weatherModel: SimpleWeatherModel) {
        stateData =
            stateData.copy(
                weatherForSelectedCity = weatherModel,
                matchingCities = emptyList(),
                isLoading = false
            )

        val tempInInt = weatherModel.temp.roundToInt()
        if (weatherModel.temp > 20) {
            val hotWeatherDescription = WeatherApp.getLocalResources()
                .getString(R.string.notifications_description_hot, tempInInt)
            notificationsRepository.fireNotification(hotWeatherDescription)
        } else if (weatherModel.temp < 0) {
            val coldWeatherDescription = WeatherApp.getLocalResources()
                .getString(R.string.notifications_description_cold, tempInInt)
            notificationsRepository.fireNotification(coldWeatherDescription)
        }
    }

    override fun dataFetchingError() {
        stateData = stateData.copy(errorOccurred = true)
    }
}

data class StateData(
    val matchingCities: List<CityModel> = emptyList(),
    val weatherForSelectedCity: SimpleWeatherModel? = null,
    val isLoading: Boolean = false,
    val errorOccurred: Boolean = false,
)