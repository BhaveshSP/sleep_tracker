package com.bhaveshsp.trackmysleepquantity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bhaveshsp.trackmysleepquantity.databinding.FragmentQualityBinding


class QualityFragment : Fragment(){
    private lateinit var binding:FragmentQualityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title="Select the Quality of Sleep"
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_quality,container,false)
        val arguments=QualityFragmentArgs.fromBundle(arguments!!)
        val application= requireNotNull(this.activity).application
        val database=SleepDatabase.getInstance(application)!!.sleepDao
        val viewModelFactory=SleepQualityViewModelFactory(arguments.nightId,database)
        val sleepQualityViewModel=ViewModelProviders.of(this,viewModelFactory).get(SleepQualityViewModel::class.java)
        sleepQualityViewModel.navigateToSleepTracker.observe(this, Observer {
            if (it==true){
                findNavController().navigate(QualityFragmentDirections.actionQualityFragmentToTrackingFragment())
                sleepQualityViewModel.doneNavigation()
            }
        })
        binding.lifecycleOwner=this
        binding.sleepQualityViewModel=sleepQualityViewModel
        return binding.root

    }



}