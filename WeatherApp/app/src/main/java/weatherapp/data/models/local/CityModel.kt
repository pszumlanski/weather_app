package weatherapp.data.models.local

data class CityModel (
        val name: String,
        val lat: Double,
        val lon: Double,
        val country: String,
        val state: String?
)