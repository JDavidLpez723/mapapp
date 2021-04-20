package com.canonicalexamples.tearank.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.MapApp
import com.canonicalexamples.tearank.databinding.FragmentMainBinding
import com.canonicalexamples.tearank.util.observeEvent
import com.canonicalexamples.tearank.viewmodels.MainViewModel
import com.canonicalexamples.tearank.viewmodels.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        val app = activity?.application as MapApp
        MainViewModelFactory(app.database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding = FragmentMainBinding.inflate(inflater, container, false)



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button1.setOnClickListener {
            viewModel.button1Clicked()
        }

        binding.button2.setOnClickListener {
            viewModel.button2Clicked()
        }

        binding.button3.setOnClickListener {
            viewModel.button3Clicked()
        }

        viewModel.set_parking.observeEvent(viewLifecycleOwner) {
            if (it) {
                getLastKnownLocation()
            }
        }

        viewModel.go_to_history_fragment.observeEvent(viewLifecycleOwner) {
            if(it) {
                findNavController().navigate(R.id.action_MainFragment_to_HistoryFragment)
            }
        }

        viewModel.go_to_node_fragment.observeEvent(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_MainFragment_to_nodeFragment)
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
            println("Permissions are not granted")
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
                println("It worked")
                println(location.latitude)
                println(location.longitude)

            }
            else{
                println("It didn't work")
            }

        }

    }
}
