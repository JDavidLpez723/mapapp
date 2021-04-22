package com.canonicalexamples.tearank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canonicalexamples.tearank.BuildConfig
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.MapApp
import com.canonicalexamples.tearank.databinding.FragmentNodeBinding
import com.canonicalexamples.tearank.model.Node
import com.canonicalexamples.tearank.util.observeEvent
import com.canonicalexamples.tearank.viewmodels.NodeViewModel
import com.canonicalexamples.tearank.viewmodels.NodeViewModelFactory
import com.squareup.picasso.Picasso

class NodeFragment: Fragment(){

    private lateinit var binding: FragmentNodeBinding

    //Recieve arguments (itemSelected)
    val args: NodeFragmentArgs by navArgs()

    private var myNode: Node? = null

    private val viewModel: NodeViewModel by viewModels {
        val app = activity?.application as MapApp
        NodeViewModelFactory(app.database, args.itemSelected+1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener { viewModel.buttonBackClicked()}

        viewModel.go_to_main_fragment.observeEvent(viewLifecycleOwner){
            if (it) findNavController().navigate(R.id.action_nodeFragment_to_MainFragment)
        }

        viewModel.node.observe(viewLifecycleOwner) {
            binding.titleTextView.setText(it.tag)
            binding.xTextView.setText(it.x.toString())
            binding.yTextView.setText(it.y.toString())

            if(it.tag != "") {
                viewModel.setZoom(10)
            }
        }

        binding.buttonAddZoom.setOnClickListener{
            viewModel.addZoom()
        }
        binding.buttonSubsZoom.setOnClickListener{
            viewModel.subsZoom()
        }

        viewModel.zoom.observe(viewLifecycleOwner){
            val z = viewModel.zoom.value ?: -1

//            println("Zoom changed to "+z.toString())

            if(z > 0 ){
                val builder = this.context?.let { Picasso.Builder(it) }
                if (builder != null) {
                     val uri = viewModel.getTileUri()
                    builder.build().load(uri)
                        .placeholder(R.drawable.just_grey_light_grey)
                        .error(R.drawable.error)
                        .into(binding.iv)
                }

            }
        }



    }


}