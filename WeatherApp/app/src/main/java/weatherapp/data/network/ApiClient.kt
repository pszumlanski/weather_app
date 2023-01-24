package weatherapp.data.network

import weatherapp.data.models.api.GeocodingApiResponse
import weatherapp.data.network.NetworkConstants.Companion.GEOCODING_CITY_LOOKUP_ENDPOINT
import weatherapp.data.network.NetworkConstants.Companion.GEOCODING_QUERY_PARAMETER
import weatherapp.data.network.NetworkConstants.Companion.API_KEY_PARAMETER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import weatherapp.BuildConfig
import weatherapp.data.models.api.OpenWeatherApiResponse
import weatherapp.data.network.NetworkConstants.Companion.GEOCODING_LIMIT_PARAMETER
import weatherapp.data.network.NetworkConstants.Companion.METRIC_UNITS
import weatherapp.data.network.NetworkConstants.Companion.OPEN_WEATHER_ENDPOINT
import weatherapp.data.network.NetworkConstants.Companion.OPEN_WEATHER_LAT_PARAMETER
import weatherapp.data.network.NetworkConstants.Companion.OPEN_WEATHER_LON_PARAMETER
import weatherapp.data.network.NetworkConstants.Companion.OPEN_WEATHER_UNITS_PARAMETER

// External gate for communication with API endpoints (operated by Retrofit)
interface ApiClient {

    @GET(GEOCODING_CITY_LOOKUP_ENDPOINT)
    fun getLocationsFromCityName(
        @Query(GEOCODING_QUERY_PARAMETER) query: String,
        @Query(GEOCODING_LIMIT_PARAMETER) limit: Int = 5,
        @Query(API_KEY_PARAMETER) key: String = BuildConfig.API_KEY
    ): Call<List<GeocodingApiResponse>>

    @GET(OPEN_WEATHER_ENDPOINT)
    fun getWeatherForLocation(
        @Query(OPEN_WEATHER_LAT_PARAMETER) lat: Double,
        @Query(OPEN_WEATHER_LON_PARAMETER) lon: Double,
        @Query(OPEN_WEATHER_UNITS_PARAMETER) units: String = METRIC_UNITS,
        @Query(API_KEY_PARAMETER) key: String = BuildConfig.API_KEY
    ): Call<OpenWeatherApiResponse>
}