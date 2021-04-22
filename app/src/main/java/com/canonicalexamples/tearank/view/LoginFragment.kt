package com.canonicalexamples.tearank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.MapApp
import com.canonicalexamples.tearank.databinding.FragmentLoginBinding
import com.canonicalexamples.tearank.util.observeEvent
import com.canonicalexamples.tearank.viewmodels.LoginViewModel
import com.canonicalexamples.tearank.viewmodels.LoginViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        val app = activity?.application as MapApp
        LoginViewModelFactory(app.database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LoginBLoginFragment.setOnClickListener {
            viewModel.getLogin(binding.mailLoginFragment.text.toString(),binding.passLoginFragment.text.toString())
            //viewModel.navigate1()
        }
        binding.RegisterBLoginFragment.setOnClickListener {
            viewModel.navigate2()
        }

        viewModel.go_to_main_fragment.observeEvent(viewLifecycleOwner) {
            if(it) {
                //Toast.makeText(this,"Prueba",Toast.LENGTH_SHORT)
                findNavController().navigate(R.id.action_loginFragment_to_MainFragment)
                Toast.makeText(activity, "Welcome again!", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.incorrect_credentials.observeEvent(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(activity, "Sorry, wrong credentials. Try again.", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.go_to_reg_fragment.observeEvent(viewLifecycleOwner) {
            if(it) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}