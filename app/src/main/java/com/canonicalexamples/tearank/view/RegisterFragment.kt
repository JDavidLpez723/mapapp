package com.canonicalexamples.tearank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.MapApp
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.databinding.FragmentRegisterBinding
import com.canonicalexamples.tearank.viewmodels.RegisterViewModel
import com.canonicalexamples.tearank.viewmodels.RegisterViewModelFactory
import com.canonicalexamples.tearank.util.observeEvent

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        val app = activity?.application as MapApp
        RegisterViewModelFactory(app.database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.RegisterBRegisterFragment.setOnClickListener {
            viewModel.getRegister(binding.mailRegisterFragment.text.toString(),binding.passRegisterFragment.text.toString())
            viewModel.navigate1()
        }

        viewModel.go_to_main_fragment.observeEvent(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_registerFragment_to_MainFragment)
            }
        }
    }

}