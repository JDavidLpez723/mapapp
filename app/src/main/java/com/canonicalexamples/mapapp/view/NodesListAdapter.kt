package com.canonicalexamples.mapapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canonicalexamples.mapapp.R
import com.canonicalexamples.mapapp.databinding.ItemNodeBinding
import com.canonicalexamples.mapapp.util.observeEvent
import com.canonicalexamples.mapapp.viewmodels.MapViewModel

/**
 * 20210208. Initial version created by jorge
 * for a Canonical Examples training.
 *
 * Copyright 2021 Jorge D. Ortiz Fuentes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class NodesListAdapter(private val viewModel: MapViewModel): RecyclerView.Adapter<NodesListAdapter.TeaItemViewHolder>() {

    class TeaItemViewHolder(private val viewModel: MapViewModel, binding: ItemNodeBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val teaName = binding.itemName

        init {
            binding.root.setOnClickListener(this)
        }

        //When an item of the adapter list has been clicked:
        override fun onClick(v: View?) {
            println("Layout:"+layoutPosition)
            viewModel.onClickItem(layoutPosition)
            //Move to Node Fragment


            //v?.rootView?.findNavController()?.navigate(R.id.action_FirstFragment_to_nodeFragment)
            //v?.findNavController()?.navigate(R.id.action_FirstFragment_to_nodeFragment)

            //print(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaItemViewHolder =
        TeaItemViewHolder(viewModel, ItemNodeBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: TeaItemViewHolder, position: Int) {
        //Get data and fill each item
        val node = viewModel.getItem(position)
        holder.teaName.text = "Position ${node.name}"
//        var z = 14
//        println("Zoom="+z.toString())
//        println(viewModel.getXYTile(40.32907,-3.76592, z))
        //println(viewModel.getItem(1))
    }

    override fun getItemCount(): Int = viewModel.numberOfItems
}
