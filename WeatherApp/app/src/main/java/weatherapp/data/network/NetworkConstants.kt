package weatherapp.data.network

class NetworkConstants {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        const val API_KEY_PARAMETER = "appid"

        const val GEOCODING_CITY_LOOKUP_ENDPOINT = "/geo/1.0/direct"
        const val GEOCODING_QUERY_PARAMETER = "q"
        const val GEOCODING_LIMIT_PARAMETER = "limit"

        const val OPEN_WEATHER_ENDPOINT = "/data/2.5/weather"
        const val OPEN_WEATHER_LAT_PARAMETER = "lat"
        const val OPEN_WEATHER_LON_PARAMETER = "lon"
        const val OPEN_WEATHER_UNITS_PARAMETER = "units"
        const val METRIC_UNITS = "metric"
    }
}