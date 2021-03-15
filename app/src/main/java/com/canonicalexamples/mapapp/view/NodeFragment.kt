package com.canonicalexamples.mapapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canonicalexamples.mapapp.R
import com.canonicalexamples.mapapp.app.MapApp
import com.canonicalexamples.mapapp.databinding.FragmentNodeBinding
import com.canonicalexamples.mapapp.model.Node
import com.canonicalexamples.mapapp.viewmodels.NodeViewModel
import com.canonicalexamples.mapapp.viewmodels.NodeViewModelFactory
import com.squareup.picasso.Picasso


class NodeFragment: Fragment(){

    private lateinit var binding: FragmentNodeBinding

    //Recieve arguments (itemSelected)
    val args: NodeFragmentArgs by navArgs()

    private var myNode: Node? = null

    private val viewModel: NodeViewModel by viewModels {
        val app = activity?.application as MapApp
        NodeViewModelFactory(app.database, app.webservice, args.itemSelected+1)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNodeBinding.inflate(inflater, container, false)
        println("[NODE FRAGMENT]: CREATED")
        //val ivBasicImage: ImageView = view.findViewById<ImageView>(R.id.iv)
        //Picasso.get().load("https://https://a.tile.openstreetmap.org/10/62/12.png").into(binding.iv)
        //Picasso.get().load("https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg").into(binding.iv)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //println("[NODE FRAGMENT]: #items:" +viewModel.numberOfItems)
        viewModel.node.observe(viewLifecycleOwner) {
            binding.titleTextView.setText(it.tag)
            binding.xTextView.setText(it.x.toString())
            binding.yTextView.setText(it.y.toString())

            if(it.tag != "") {
                viewModel.setZoom(10)
            }
        }

        binding.btaddzoom.setOnClickListener{
            viewModel.addZoom()
        }
        binding.btsubszoom.setOnClickListener{
            viewModel.subsZoom()
        }
        //binding.iv.setImageResource(R.drawable.i42)

        viewModel.body.observe(viewLifecycleOwner) {
            //println("Body: ${it.string()}")
        }

        viewModel.zoom.observe(viewLifecycleOwner){
            val z = viewModel.zoom.value ?: -1

            println("Zoom changed to "+z.toString())

            if(z > 0 ){
                val builder = this.context?.let { Picasso.Builder(it) }
                if (builder != null) {
                    //val uri = "https://api.maptiler.com/maps/basic/256/7/63/42.png?key=w3yoRskFIgZceY3WMSjy"
                    val uri = viewModel.getTileUri()
                    builder.build().load(uri)
                         //   .placeholder(R.drawable.just_grey_light_grey)
                         //   .error(R.drawable.error)
                            .into(binding.iv)
                }

            }
        }

        //Picasso.get().load("https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg").into(binding.iv)
        /*

        this works -> https://img.freepik.com/vector-gratis/circulo-brillante-iluminacion-purpura-aislado-sobre-fondo-oscuro_1441-2396.jpg?size=626&ext=jpg
                       https://a.tile.opentopomap.org/7/63/42.png


         */


        binding.buttonBack.setOnClickListener{
            findNavController().navigate(R.id.action_nodeFragment_to_NodesListFragment)
        }

    }

    override fun onResume() {
        //println("[NODE FRAGMENT]: RESUME")
        super.onResume()
    }
}