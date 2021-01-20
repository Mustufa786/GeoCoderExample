package com.mustufamedium.geocoderexample

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.common.util.CollectionUtils
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * Created by Mustufa Ansari on 18/01/2021.
 * Email : mustufaayub82@gmail.com
 */
object GeoCoderUtil {

    fun execute(
        context: Context,
        latitude: String,
        longitude: String,
        callback: LoadDataCallback<LocationModel>
    ) {
        var address = StringUtils.EMPTY
        var cityName = StringUtils.EMPTY
        var areaName = StringUtils.EMPTY
        val locationModel: LocationModel
        try {
            val addresses: MutableList<Address>
            val geocoder = Geocoder(context, Locale.ENGLISH)
            addresses =
                geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
            if (!CollectionUtils.isEmpty(addresses)) {
                val fetchedAddress = addresses[0]
                if (fetchedAddress.maxAddressLineIndex > -1) {
                    address = fetchedAddress.getAddressLine(0)
                    fetchedAddress.locality?.let {
                        cityName = it
                    }
                    fetchedAddress.subLocality?.let {
                        areaName = it
                    }
                }
                locationModel = LocationModel().apply {
                    locationAddress = address
                    locationCityName = cityName
                    locationAreaName = areaName
                }
                callback.onDataLoaded(locationModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onDataNotAvailable(1, "Something went wrong!")
        }
    }
}

interface LoadDataCallback<T> {
    fun onDataLoaded(response: T) {
    }

    fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {

    }
}