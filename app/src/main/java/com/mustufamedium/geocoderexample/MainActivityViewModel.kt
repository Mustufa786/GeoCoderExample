package com.mustufamedium.geocoderexample

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception

/**
 * Created by Mustufa Ansari on 17/01/2021.
 * Email : mustufaayub82@gmail.com
 */
class MainActivityViewModel : ViewModel() {


    private val _getLocationInformation = MutableLiveData<ResponseStatusCallbacks<LocationModel>>()
    val getLocationInformation: LiveData<ResponseStatusCallbacks<LocationModel>>
        get() = _getLocationInformation

    fun getLocationInfo(context: Context, latitude: String, longitude: String) {
        _getLocationInformation.value = ResponseStatusCallbacks.loading(data = null)
        GeoCoderUtil.execute(context, latitude, longitude, object :
            LoadDataCallback<LocationModel> {
            override fun onDataLoaded(response: LocationModel) {
                _getLocationInformation.value = ResponseStatusCallbacks.success(response)
            }
            override fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {
                _getLocationInformation.value =
                    ResponseStatusCallbacks.error(data = null, message = "Something went wrong!")
            }
        })
    }
}