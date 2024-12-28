package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.activities.MainActivity.Companion.sharedPref
import com.example.noteapp.ui.adapters.OnBoardViewPagerAdapter
import com.example.noteapp.utils.PreferenceHelper

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref.unit(requireContext())
        checkSP()
        initialize()
        setUpListener()
    }

    private fun checkSP() {
        if (!sharedPref.isFirstTimeOnBoard){
            findNavController().navigate(R.id.action_onBoardFragment_to_signInFragment)
        }
    }

    private fun initialize() {
        binding.viewPager.adapter = OnBoardViewPagerAdapter(this)
        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    private fun setUpListener() = with(binding.viewPager) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.tvSkip.visibility = View.INVISIBLE
                    binding.btnStart.visibility = View.VISIBLE
                }else{
                    binding.tvSkip.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.GONE
                    binding.tvSkip.setOnClickListener{
                        setCurrentItem(currentItem + 2, true)
                    }
                }
            }
        })

        binding.btnStart.setOnClickListener{
            findNavController().navigate(R.id.action_onBoardFragment_to_signInFragment)
            if (!sharedPref.isFirstTimeOnBoard){
                sharedPref.isFirstTimeOnBoard = true
            }
        }
    }
}