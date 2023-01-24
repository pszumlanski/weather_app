package weatherapp.data.repositories

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import weatherapp.R
import weatherapp.data.models.api.GeocodingApiResponse
import weatherapp.data.models.local.CityModel
import weatherapp.data.network.ApiClient
import weatherapp.data.network.ApiFetchingCallback
import weatherapp.injection.WeatherApp
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class CityLookupRepository @Inject constructor(private val apiClient: ApiClient) {

    fun fetchMatchingCities(
        searchInput: String, callback: ApiFetchingCallback
    ) {
        val endpoint = apiClient.getLocationsFromCityName(query = searchInput)

        endpoint.enqueue(object : Callback<List<GeocodingApiResponse>> {

            override fun onResponse(
                call: Call<List<GeocodingApiResponse>>,
                response: Response<List<GeocodingApiResponse>>
            ) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    val receivedList = response.body()
                    val transformedList = transformReceivedCityList(receivedList!!)
                    callback.citiesFetchedSuccessfully(transformedList)
                } else {
                    logErrorDetails(prepareLogFriendlyErrorMessage(null))
                    callback.dataFetchingError()
                }
            }

            override fun onFailure(call: Call<List<GeocodingApiResponse>>, t: Throwable) {
                logErrorDetails(prepareLogFriendlyErrorMessage(t))
                callback.dataFetchingError()
            }
        })
    }

    private fun prepareLogFriendlyErrorMessage(throwable: Throwable?): String {
        val genericErrorMessage =
            WeatherApp.getLocalResources().getString(R.string.error_api_call_failure)
        val errorTextFromApi = throwable?.message
        return errorTextFromApi ?: genericErrorMessage
    }

    private fun logErrorDetails(errorMessage: String) {
        val errorTag = WeatherApp.getLocalResources().getString(R.string.error)
        Log.e(errorTag, errorMessage)
    }

    private fun transformReceivedCityList(list: List<GeocodingApiResponse>): List<CityModel> {
        return list.map { apiCityModel ->
            apiCityModel.let {
                CityModel(
                    it.name, it.lat, it.lon, it.country, it.state
                )
            }
        }
    }
}