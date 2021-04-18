package com.canonicalexamples.mapapp.view

import android.content.Context.LOCATION_SERVICE
import android.icu.math.BigDecimal.valueOf
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.mapapp.R
import com.canonicalexamples.mapapp.app.MapApp
import com.canonicalexamples.mapapp.databinding.FragmentSecondBinding
import com.canonicalexamples.mapapp.model.MapDatabase
import com.canonicalexamples.mapapp.model.Node
import com.canonicalexamples.mapapp.util.observeEvent
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModel
import com.canonicalexamples.mapapp.viewmodels.NodesListViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    val app =MapApp()
    val database=app.database
    var counter=5
    lateinit var pointName:String
    var pointLatitude:Double=0.0
    var pointLongitude:Double=0.0
    var gps_enabled = false
    var network_enabled = false
    private var locationManager : LocationManager? = null
    private lateinit var binding: FragmentSecondBinding;
    private val viewModel: NodesListViewModel by viewModels {
        val app = MapApp()
        NodesListViewModelFactory(app.database, app.webservice)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            binding.thetext.text = ("" + location.longitude + ":" + location.latitude)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {

            gps_enabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == false
        } catch (ex: Exception) {
        }
        try {

            network_enabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == false
        } catch (ex: Exception) {
        }

        try {
            gps_enabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)== false
        } catch (ex: Exception) {
        }
        super.onViewCreated(view, savedInstanceState)
        binding.addPointToList.setOnClickListener {
            viewModel.addButtonClicked(2)
        }
        locationManager = getActivity()?.getSystemService(LOCATION_SERVICE) as LocationManager?
        binding.fab.setOnClickListener { view ->
            try {
                // Request location updates
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                } catch (ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
            }
        }

        viewModel.navigate.observeEvent(viewLifecycleOwner) { navigate ->
            if (navigate) {
                viewModel.navigate_index.observeEvent(viewLifecycleOwner) { navigate_index ->
                    if (navigate_index == 2) {
                        pointName= binding.editTextTextPersonName.text.toString()
                        pointLatitude= binding.editTextTextPersonName2.text.toString().toDouble()
                        pointLongitude= binding.editTextTextPersonName3.text.toString().toDouble()

                        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                            database.nodeDao.apply {
                                //this.create(node = Node(id=0, x=7.0, y=99.0, tag="Mi Casa"))
                                this.create(node = Node(id=counter, x=pointLatitude, y=pointLongitude, tag=pointName))
                               counter++;
//                this.create(tea = Tea(id = 0, name = "Oolong", rating = 1))
//                this.create(tea = Tea(id = 1, name = "Pu erh", rating = 1))
//                this.create(tea = Tea(id = 2, name = "Green tea", rating = 1))
                            }
                        }

                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    }
                }
            }
        }
        if (network_enabled==false){
            binding.network.text = ("NETWORK PROVIDER DIDN'T RECEIVIED ANY DATA")
        }
        if (gps_enabled==false){
            binding.gps.text = ("GPS PROVIDER DIDN'T RECEIVE ANY DATA")
        }

        println("[SecondFragment]: " + viewModel.numberOfItems)
       // view.findViewById<Button>(R.id.button_second).setOnClickListener {
       //     findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
       // }
    }
}
