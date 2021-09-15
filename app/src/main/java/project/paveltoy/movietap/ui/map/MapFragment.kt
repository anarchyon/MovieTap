package project.paveltoy.movietap.ui.map

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.paveltoy.movietap.R
import project.paveltoy.movietap.databinding.FragmentMapBinding

class MapFragment : Fragment(R.layout.fragment_map) {
    private val binding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defineLocation()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun defineLocation() {
        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f) {
//                val lat = it.latitude
//                val lon = it.longitude
//                binding.latitudeValue.text = lat.toString()
//                binding.longitudeValue.text = lon.toString()
//                val geocoder = Geocoder(requireContext())
//                CoroutineScope(Dispatchers.Default).launch {
//                    val adresses = geocoder.getFromLocation(lat, lon, 1)
//                    view?.post {
//                        binding.address.text = adresses[0].getAddressLine(0)
//                    }
//                }
//            }
        }
    }
}