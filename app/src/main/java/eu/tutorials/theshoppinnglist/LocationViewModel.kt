package eu.tutorials.theshoppinnglist

import androidx.compose.runtime.mutableStateOf
import android.content.Context
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class LocationViewModel : ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf("No Address")
    val address: State<String> = _address

    fun updateLocation(newLocation: LocationData){
        _location.value = newLocation
    }

    fun fetchAddress(latLng: String,context: Context){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val parts = latLng.split(",")
                val lat = parts[0].toDouble()
                val lng = parts[1].toDouble()

                val locationUtils = LocationUtils(context)
                val result = locationUtils.reverseGeocodeLocation(
                    LocationData(lat,lng)
                )
                _address.value = result
            }
            catch ( e: Exception){
                _address.value = "Address not found"
            }
        }
    }

}