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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bhaveshsp.trackmysleepquantity.databinding.FragmentTrackingBinding
import com.google.android.material.snackbar.Snackbar

class TrackingFragment : Fragment() {

    private lateinit var binding:FragmentTrackingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // using dataBinding
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_tracking,container,false)
        (activity as AppCompatActivity).supportActionBar?.title="Track My Sleep"
        // Attach view Model to the fragment
        val application= requireNotNull(this.activity).application
        val sleepDao=SleepDatabase.getInstance(application)?.sleepDao
        val viewModelFactory=SleepViewModelFactory(application = application,sleepDao = sleepDao)
        val sleepTrackerViewModel= ViewModelProviders.of(this,viewModelFactory).get(SleepViewModel::class.java)
        // Adapter
        val adapter=SleepRecycleView(SleepRecycleView.SleepListener {
            nightId->
            Toast.makeText(context, "${nightId}", Toast.LENGTH_SHORT).show()
        })
        sleepTrackerViewModel.night.observe(this, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        sleepTrackerViewModel.navigateToSleepQuality.observe(this, Observer {
            night->
            run {
                night?.let {
                    findNavController().navigate(
                        TrackingFragmentDirections.actionTrackingFragmentToQualityFragment(
                            night.nightKey
                        )
                    )
                    sleepTrackerViewModel.doneNavigation()
                }
            }

        })
        sleepTrackerViewModel.showSnackBar.observe(this, Observer {
            if (it==true){
                Snackbar.make(activity!!.findViewById(android.R.id.content),"Database Cleared",Snackbar.LENGTH_SHORT).show()
            }
            sleepTrackerViewModel.doneShowingSnackBar()

        })

        binding.recyclerView.adapter=adapter
        //set the layout manager to grid layout
        val manager=GridLayoutManager(activity,1,GridLayoutManager.VERTICAL,false)
        binding.recyclerView.layoutManager=manager
        binding.lifecycleOwner = this
        binding.sleepTrackerViewModel=sleepTrackerViewModel
        return binding.root
    }



}