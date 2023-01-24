package weatherapp.data.models.local

data class SimpleWeatherModel(
    val name: String,
    val temp: Double,
    val visualTemp: String,
    val description: String,
)