package weatherapp.data.repositories

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import weatherapp.R
import weatherapp.data.models.api.OpenWeatherApiResponse
import weatherapp.data.models.local.CityModel
import weatherapp.data.models.local.SimpleWeatherModel
import weatherapp.data.network.ApiClient
import weatherapp.data.network.ApiFetchingCallback
import weatherapp.injection.WeatherApp
import weatherapp.utils.titlecaseFirstCharIfItIsLowercase
import java.math.RoundingMode
import javax.inject.Inject

// Data Repository - the main gate of the model (data) part of the application
class WeatherRepository @Inject constructor(private val apiClient: ApiClient) {

    fun fetchWeatherForLocation(
        cityModel: CityModel, callback: ApiFetchingCallback
    ) {
        val endpoint = apiClient.getWeatherForLocation(lat = cityModel.lat, lon = cityModel.lon)

        endpoint.enqueue(object : Callback<OpenWeatherApiResponse> {

            override fun onResponse(
                call: Call<OpenWeatherApiResponse>, response: Response<OpenWeatherApiResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val transformedModel =
                        transformReceivedWeatherModel(response.body()!!, cityModel)
                    callback.weatherFetchedSuccessfully(transformedModel)
                } else {
                    logErrorDetails(prepareLogFriendlyErrorMessage(null))
                    callback.dataFetchingError()
                }
            }

            override fun onFailure(call: Call<OpenWeatherApiResponse>, t: Throwable) {
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

    private fun transformReceivedWeatherModel(
        apiModel: OpenWeatherApiResponse,
        cityModel: CityModel
    ): SimpleWeatherModel {
        val tempRoundedDown =
            apiModel.main.temp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        val tempWithDegreeSign = "$tempRoundedDown\u2103"
        val name = "${cityModel.name}, ${cityModel.country.uppercase()}"

        return SimpleWeatherModel(
            name = name,
            temp = apiModel.main.temp,
            visualTemp = tempWithDegreeSign,
            description = apiModel.weather.first().description.titlecaseFirstCharIfItIsLowercase()
        )
    }
}