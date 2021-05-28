package com.ngopidev.project.starterkit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ngopidev.project.starterkit.databinding.FragmentSampleBinding


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 *   digunakan untuk penggunaan fragment,
 *   refactor fragment ini jika ingin digunakan
 *   atau delete fragment ini jika tidak ingin digunakan
 **/
class SampleFragment : Fragment() {

    //ini merupakan cara binding layout fragment_sample
    // kedalam SampleFragment menggunakan konsep viewbinding
    private lateinit var sampleBinding: FragmentSampleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sampleBinding = FragmentSampleBinding.inflate(inflater, container, false)
        return sampleBinding.root
    }
}