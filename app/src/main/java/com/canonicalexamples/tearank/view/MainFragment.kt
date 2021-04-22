package com.canonicalexamples.tearank.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.MapApp
import com.canonicalexamples.tearank.databinding.FragmentMainBinding
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.model.Node
import com.canonicalexamples.tearank.util.observeEvent
import com.canonicalexamples.tearank.viewmodels.MainViewModel
import com.canonicalexamples.tearank.viewmodels.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MainFragment : Fragment() {
    val database by lazy {
        val app = activity?.application as MapApp
        app.database
    }
    private val requestPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                println("Permission Granted")
            } else {
                println("Permission Rejected")
            }
        }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        val app = activity?.application as MapApp
        MainViewModelFactory(app.database)
    }
    var node_id=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context by lazy {
            activity?.application as MapApp
        }
        val app = activity?.application as MapApp
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        binding = FragmentMainBinding.inflate(inflater, container, false)



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton3.setOnClickListener {
            viewModel.button1Clicked()
        }

        binding.button2.setOnClickListener {
            viewModel.button2Clicked()
        }

        binding.button3.setOnClickListener {
            viewModel.button3Clicked()
        }

        viewModel.set_parking.observeEvent(viewLifecycleOwner) {

            val baseContext by lazy {
                val app = activity?.application as MapApp
                app.baseContext
            }
            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                node_id = if (database.nodeDao.getMaximumId() != null) database.nodeDao.getMaximumId()!! else 0
                node_id++
            }

            if (it) {

                    if (ActivityCompat.checkSelfPermission(
                            baseContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            baseContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        println("It didn't work")
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        println("permissions asked")
                    }
                    fusedLocationClient.lastLocation.addOnSuccessListener { location->
                        if (location != null) {
                            println("It worked")
                            println(location.latitude)
                            println(location.longitude)
                            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                                database.nodeDao.apply {
                                    this.create(node = Node(id = node_id, x = location.latitude, y = location.longitude, tag = "Parking "+node_id))
                                }
                            }
                        }
                        else{
                            println("It didn't work")
                        }
                    }
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
}
