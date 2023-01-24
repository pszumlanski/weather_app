package weatherapp.ui.home

import weatherapp.data.models.local.CityModel
import weatherapp.data.models.local.SimpleWeatherModel

class PreviewData {
    private val cityModels = listOf(
        CityModel(name = "London", lat = 1.2222, lon = 2.3333, country = "GB", state = null),
        CityModel(name = "Paris", lat = 1.2222, lon = 2.3333, country = "FR", state = null),
        CityModel(name = "New York", lat = 1.2222, lon = 2.3333, country = "US", state = null),
        CityModel(name = "Warsaw", lat = 1.2222, lon = 2.3333, country = "PL", state = null),

        )

    private val mockWeatherModel = SimpleWeatherModel(
        name = "London, GB",
        temp = 22.44,
        visualTemp = "21.2\u2103",
        description = "moderate rain"
    )

    val stateData =
        StateData(weatherForSelectedCity = mockWeatherModel, matchingCities = cityModels)
}