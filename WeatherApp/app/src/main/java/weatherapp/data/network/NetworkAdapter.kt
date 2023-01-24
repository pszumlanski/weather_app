package weatherapp.data.network

import okhttp3.OkHttpClient.Builder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// Network Adapter used for ApiClient building
object NetworkAdapter {

    fun apiClient(): ApiClient {
        val builder = Builder()

        // Create Retrofit instance to operate API calls
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NetworkConstants.BASE_URL)
                .client(builder.build())
                .build()

        return retrofit.create(ApiClient::class.java)
    }
}