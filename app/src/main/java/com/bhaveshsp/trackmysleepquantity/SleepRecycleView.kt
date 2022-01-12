package com.bhaveshsp.trackmysleepquantity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhaveshsp.trackmysleepquantity.SleepRecycleView.ViewHolder.Companion.from
import com.bhaveshsp.trackmysleepquantity.databinding.ItemViewBinding

class SleepRecycleView(private val clickListener:SleepListener):androidx.recyclerview.widget.ListAdapter<Sleep,SleepRecycleView.ViewHolder>(SleepDiffCallback()) {

    class SleepDiffCallback:DiffUtil.ItemCallback<Sleep>(){
        override fun areItemsTheSame(oldItem: Sleep, newItem: Sleep): Boolean {
            return oldItem.nightKey==newItem.nightKey
        }

        override fun areContentsTheSame(oldItem: Sleep, newItem: Sleep): Boolean {
            return oldItem==newItem
        }


    }
    class ViewHolder: RecyclerView.ViewHolder {
        private var binding:ItemViewBinding
        private constructor(mbinding: ItemViewBinding):super(mbinding.root){
            binding=mbinding
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: Sleep,
            clickListener: SleepListener
        ) {
            binding.sleep=item
            binding.clickListener=clickListener
            binding.executePendingBindings()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item,clickListener)
    }
    class SleepListener(val clickListener:(sleepId:Long)->Unit){
        fun onClick(night:Sleep)=clickListener(night.nightKey)
    }

}