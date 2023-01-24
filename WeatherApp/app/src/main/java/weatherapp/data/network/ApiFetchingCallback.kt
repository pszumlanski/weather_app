package weatherapp.data.network

import weatherapp.data.models.local.CityModel
import weatherapp.data.models.local.SimpleWeatherModel

interface ApiFetchingCallback {
    fun citiesFetchedSuccessfully(list: List<CityModel>)
    fun weatherFetchedSuccessfully(weatherModel: SimpleWeatherModel)
    fun dataFetchingError()
}