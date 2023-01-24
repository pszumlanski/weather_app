package weatherapp.data.models.api

data class OpenWeatherApiResponse (
    val weather: List<Weather>,
    val main: Main,
)

data class Main (
    val temp: Double,
)

data class Weather (
    val description: String,
)
