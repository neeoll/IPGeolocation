package com.example.ipgeolocation

import com.google.gson.annotations.SerializedName

class GeolocationResponse {
    @SerializedName("ip")
    var ip: String? = null
    @SerializedName("location")
    var location: Location? = null
    @SerializedName("domains")
    var domains = ArrayList<String>()
    @SerializedName("as")
    var aS: AS? = null
    @SerializedName("isp")
    var isp: String? = null
}

class Location {
    @SerializedName("country")
    var country: String? = null
    @SerializedName("region")
    var region: String? = null
    @SerializedName("city")
    var city: String? = null
    @SerializedName("lat")
    var lat: Float = 0.toFloat()
    @SerializedName("lng")
    var lng: Float = 0.toFloat()
    @SerializedName("postalCode")
    var postalCode: String? = null
    @SerializedName("timezone")
    var timezone: String? = null
    @SerializedName("geonameId")
    var geonameId: Long = 0
}

class AS {
    @SerializedName("asn")
    var asn: Long? = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("route")
    var route: String? = null
    @SerializedName("domain")
    var domain: String? = null
    @SerializedName("type")
    var type: String? = null
}