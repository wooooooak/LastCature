package com.wooooooak.lastcapture


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.wooooooak.lastcapture.databinding.FragmentScreenShotAlbumBinding
import com.wooooooak.lastcapture.viewmodels.ShowingLastThreeViewModel

class ShowingLastThreeFragment : Fragment() {

    private lateinit var binding: FragmentScreenShotAlbumBinding
    private lateinit var viewModel: ShowingLastThreeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreenShotAlbumBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(ShowingLastThreeViewModel::class.java)
        binding.setVariable(BR.viewModel, viewModel)

        subscribeUi(viewModel)

        return binding.root
        // Inflate the layout for this fragment
    }

    private fun subscribeUi(viewModel: ShowingLastThreeViewModel) {

    }

}
