package com.mustufamedium.geocoderexample

import org.apache.commons.lang3.StringUtils
import java.io.Serializable

class LocationModel : Serializable {
    var locationAddress: String = StringUtils.EMPTY
    var locationCityName: String = StringUtils.EMPTY
    var locationAreaName: String = StringUtils.EMPTY

}