# Simple Weather App

This is a simple weather app that returns weather data for a selected city. This app uses the OpenWeather free API. 
Geocoding API endpoint is used to search for cities by name and return suggestions, and the Weather Data API is used to fetch the weather data based on the selected city.

To run:
1. navigate to "local.properties" file in app's root directory
2. update property "api_key" with your own OpenWeather API key: api_key="{Your API key}"

It is necessary as we don't want to expose our API keys publicly, in order to prevent security breach. In a real environment, a better practice would be to store the API key in either Google Secret Manager or Firebase

How to use:
1. Enter the full name of the city in the search bar
2. Click the search icon
3. Choose your city from the list
4. Weather data will appear, along with a notification if predetermined requirements are met
