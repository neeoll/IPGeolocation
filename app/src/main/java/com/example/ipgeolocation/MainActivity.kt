package com.example.ipgeolocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ipgeolocation.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val responseData = ResponseData()
        binding.responseData = responseData

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.submitAddress.setOnClickListener { submitIpAddress() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setMapLocationAndShiftCamera((-34.0).toFloat(), 151.0.toFloat())
    }

    private fun setMapLocationAndShiftCamera(lat: Float, lng: Float) {
        markers.forEach { it.remove() }
        markers.clear()

        val markerPos = LatLng(lat.toDouble(), lng.toDouble())
        markers.add(map.addMarker(MarkerOptions().position(markerPos).title("Marker in Sydney")))
        map.moveCamera(CameraUpdateFactory.newLatLng(markerPos))
    }

    private fun submitIpAddress() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GeolocationService::class.java)
        val call = service.getLocationData(Key, "${binding.inputtedAddress.text}")
        Log.d("MAIN", "${binding.inputtedAddress}")

        call.enqueue(object : Callback<GeolocationResponse> {
            override fun onResponse(
                call: Call<GeolocationResponse>?,
                response: Response<GeolocationResponse>?
            ) {
                if (response?.code() == 200) {
                    val response = response.body()

                    val ip = "${response.ip}"
                    val location = "${response.location?.city}, ${response.location?.region} ${response.location?.postalCode}"
                    val timezone = "${response.location?.timezone}"
                    val isp = "${response.isp}"
                    val lat = response.location!!.lat
                    val lng = response.location!!.lng

                    Log.d("MAIN", response.toString())

                    binding.responseData?.receiveAndSetData(ip, location, timezone, isp)
                    setMapLocationAndShiftCamera(lat, lng)
                }
            }

            override fun onFailure(call: Call<GeolocationResponse>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Oops! Something went wrong.", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        var BaseUrl = "https://geo.ipify.org/api/v2/"
        var Key = "at_366R4A0RFUb527rHcVPxenKgAznB2"
    }
}