package com.mustufamedium.geocoderexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.mustufamedium.geocoderexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    lateinit var binding: ActivityMainBinding
    var mGoogleMap: GoogleMap? = null
    lateinit var viewModel: MainActivityViewModel
    var latLng: LatLng = LatLng(25.1933895, 66.5949836)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider.NewInstanceFactory().create(MainActivityViewModel::class.java)


        /**
         * initializing MapView
         */
        binding.mapView.onCreate(savedInstanceState)
        MapsInitializer.initialize(this)
        binding.mapView.getMapAsync(this)


        viewModel.getLocationInformation.observe(this, Observer {
            it?.let {
                when (it.status) {
                    ResponseStatus.ERROR -> {
                        binding.addressEditText.setText("Location not found!")
                    }
                    ResponseStatus.LOADING -> {
                        binding.addressEditText.setText("Searching...")
                    }
                    ResponseStatus.SUCCESS -> {
                        it.data?.let { model ->
                            binding.addressEditText.setText(model.locationAddress)

                        }
                    }
                }
            }
        })

    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            mGoogleMap = it
            mGoogleMap?.clear()
            mGoogleMap?.setOnCameraIdleListener(this)
            animateCamera()
        }
    }

    private fun animateCamera() {
        mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()

    }

    override fun onLowMemory() {
        binding.mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onCameraIdle() {
        mGoogleMap?.let {
            it.cameraPosition?.let { position ->
                latLng = mGoogleMap?.cameraPosition!!.target
                viewModel.getLocationInfo(
                    this,
                    latLng.latitude.toString(),
                    latLng.longitude.toString()
                )
            }

        }

    }
}