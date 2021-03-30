package com.canonicalexamples.mapapp.view

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.mapapp.R
import com.canonicalexamples.mapapp.app.MapApp
import com.canonicalexamples.mapapp.databinding.FragmentMainBinding
import com.canonicalexamples.mapapp.databinding.FragmentSecondBinding
import com.canonicalexamples.mapapp.util.observeEvent
import com.canonicalexamples.mapapp.viewmodels.MainViewModel
import com.canonicalexamples.mapapp.viewmodels.MainViewModelFactory
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModel
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices

class MainFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        val app = activity?.application as MapApp
        MainViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button4.setOnClickListener {
            viewModel.buttonClicked(1)
        }
        binding.button5.setOnClickListener {
            viewModel.buttonClicked(2)
        }
        binding.button6.setOnClickListener {
            viewModel.buttonClicked(3)
        }

        viewModel.click.observeEvent(viewLifecycleOwner) { click ->
            if (click) {
                viewModel.click_index.observeEvent(viewLifecycleOwner) { click_index ->
                    if (click_index == 1) {

                        getLastKnownLocation()
                    }
                    else if (click_index == 2) {
                        findNavController().navigate(R.id.action_mainFragment_to_SecondFragment)
                    }
                    else if (click_index == 3) {
                        findNavController().navigate(R.id.action_mainFragment_to_SecondFragment)
                    }
                }
            }
        }

    }
    fun getLastKnownLocation() {
        println("hola")
        println(fusedLocationClient.lastLocation)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            println("me cago en todos los permisos")
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        println(fusedLocationClient.lastLocation)
        fusedLocationClient.lastLocation.addOnSuccessListener { location->
                if (location != null) {
                    println("ole ole y ole")
                    println(location.latitude)
                    println(location.longitude)

                }
                else{
                    println("mierda")
                }

            }

    }

}