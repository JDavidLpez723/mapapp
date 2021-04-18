package com.canonicalexamples.mapapp.view

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.canonicalexamples.mapapp.R
import java.io.IOException
import java.util.*
import java.util.List;
import java.util.Locale;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.*


class MapActivity : AppCompatActivity() {
    //
    private var locationManager : LocationManager? = null
    //private var locationMangaer: LocationManager? = getSystemService(Context.LOCATION_SERVICE) as LocationManager?;
   // private var locationListener: LocationListener? = null
    //private var btnGetLocation: Button? = null
    //private var editLocation: EditText? = null
    //private var pb: ProgressBar? = null;
    //private val TAG = "Debug";
    //private var flag = false
    //private lateinit var binding: CurrentLocBinding;
    //
   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nodes)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Create persistent LocationManager reference
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        //binding.fab.setOnClickListener { view ->
          //  try {
                // Request location updates
            //    locationListener?.let { locationListener -> locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener) };
                //locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
            //} catch(ex: SecurityException) {
              //  Log.d("myTag", "Security Exception, no location available")
            //}
        //}

        //if you want to lock screen for always Portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //pb = findViewById<ProgressBar>(R.id.progressBar1);
        //pb?.let { pb -> pb.setVisibility(View.INVISIBLE) };

        //editLocation = findViewById<EditText>(R.id.editTextLocation)

        //val btnGetLocation = findViewById<Button>(R.id.btnLocation);

    }
    /*val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            binding.thetext.text = ("" + location.longitude + ":" + location.latitude)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }*/

    /*fun onClick(v: View) {
        flag = displayGpsStatus()
        if (flag) {
            Log.v(TAG, "onClick")
            editLocation!!.setText("""Please!! move your device to see the changes in coordinates. Wait..""".trimIndent())
            pb!!.visibility = View.VISIBLE
            locationListener = MyLocationListener()
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            locationMangaer!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, locationListener!!)
        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }
    }*/

    /*----Method to Check GPS is enable or disable ----- *
    private fun displayGpsStatus(): Boolean {
        var gpsStatus: Boolean
        gpsStatus = locationMangaer?.isProviderEnabled(GPS_PROVIDER) == true
        return gpsStatus==true
    }*/

    /*----------Method to create an AlertBox -------------
    protected fun alertbox(title: String?, mymessage: String?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Your Device's GPS is Disable").setCancelable(false).setTitle("** Gps Status **").setPositiveButton("Gps On", DialogInterface.OnClickListener { dialog, id -> // finish the current activity
                            // AlertBoxAdvance.this.finish();
                            val myIntent = Intent(
                                    Settings.ACTION_SECURITY_SETTINGS)
                            startActivity(myIntent)
                            dialog.cancel()
                        }).setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id -> // cancel the dialog box
                            dialog.cancel()
                        })
        val alert: AlertDialog = builder.create()
        alert.show()
    }*/

    /*----------Listener class to get coordinates -------------
    private fun MyLocationListener() : LocationListener {

        fun onLocationChanged(loc: Location) {
            editLocation?.let { editLocation ->  editLocation.setText("") };
            pb?.let { pb -> pb.setVisibility(View.INVISIBLE) };
            Toast.makeText(getApplicationContext(), "Location changed : Lat: " +
                    loc.latitude + " Lng: " + loc.longitude,
                    Toast.LENGTH_SHORT).show()
            val longitude = "Longitude: " + loc.longitude
            Log.v(TAG, longitude)
            val latitude = "Latitude: " + loc.latitude
            Log.v(TAG, latitude)

            /*----------to get City-Name from coordinates -------------
            var cityName: String? = null
            val gcd = Geocoder(getBaseContext(),
                    Locale.getDefault())
            val addresses: List<Address>
            try {
                addresses = gcd.getFromLocation(loc.latitude, loc
                        .longitude, 1)
                if (addresses.size > 0) System.out.println(addresses[0].getLocality())
                cityName = addresses[0].getLocality()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            */


        }

        fun onProviderDisabled(provider: String) {
            // TODO Auto-generated method stub
        }

        fun onProviderEnabled(provider: String) {
            // TODO Auto-generated method stub
        }

        fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            // TODO Auto-generated method stub
        }
        return LocationListener { this }
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            return when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
    }


}

