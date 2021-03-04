package com.example.map.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.map.R
import com.example.map.app.MapApp
import com.example.map.viewmodel.MapViewModel
import com.example.map.viewmodel.MapViewModelFactory

class HomeFragment: Fragment() {

//    private lateinit var binding: FragmentFirstBinding
    private val viewModel: MapViewModel by viewModels() {
        val app = activity?.application as MapApp
        MapViewModelFactory(app.database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var textToReplace: String? = "Asas"
        textToReplace = viewModel.getEx()

        view.findViewById<TextView>(R.id.textView).setText(textToReplace)
//        viewModel.getUser(0)?.email
    }
}