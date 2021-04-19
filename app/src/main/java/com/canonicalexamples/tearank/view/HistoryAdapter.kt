package com.canonicalexamples.tearank.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canonicalexamples.tearank.databinding.ItemPointBinding
import com.canonicalexamples.tearank.viewmodels.HistoryViewModel

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
class HistoryAdapter(private val viewModel: HistoryViewModel): RecyclerView.Adapter<HistoryAdapter.TeaItemViewHolder>() {

    class TeaItemViewHolder(private val viewModel: HistoryViewModel, binding: ItemPointBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val teaName = binding.itemName
        init {
            binding.root.setOnClickListener(this)
        }

        //When an item of the adapter list has been clicked:
        override fun onClick(v: View?) {
            viewModel.onClickItem(layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaItemViewHolder =
        TeaItemViewHolder(viewModel, ItemPointBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: TeaItemViewHolder, position: Int) {
        //Get data and fill each item
        val node = viewModel.getItem(position)
        holder.teaName.text = "Position ${node.name}"
    }

    override fun getItemCount(): Int = viewModel.numberOfItems
}
