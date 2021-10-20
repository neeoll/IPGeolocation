package com.example.ipgeolocation

import android.util.Log
import androidx.databinding.BaseObservable

class ResponseData: BaseObservable() {
    private var ip: String? = null
    private var location: String? = null
    private var timezone: String? = null
    private var isp: String? = null

    fun receiveAndSetData(ip: String, location: String, timezone: String, isp: String) {
        Log.d("RESPONSE", ip)
        setIp(ip)
        Log.d("RESPONSE", location)
        setLocation(location)
        Log.d("RESPONSE", timezone)
        setTimezone(timezone)
        Log.d("RESPONSE", isp)
        setIsp(isp)
        notifyPropertyChanged(BR._all)
    }

    private fun setIp(ip: String?) {
        this.ip = ip
    }
    fun getIp(): String? {
        return ip
    }


    private fun setLocation(location: String?) {
        this.location = location
    }
    fun getLocation(): String? {
        return location
    }


    private fun setTimezone(timezone: String?) {
        this.timezone = timezone
    }
    fun getTimezone(): String? {
        return timezone
    }


    private fun setIsp(isp: String?) {
        this.isp = isp
    }
    fun getIsp(): String? {
        return isp
    }
}