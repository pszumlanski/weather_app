package weatherapp.data.models.api

data class GeocodingApiResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)