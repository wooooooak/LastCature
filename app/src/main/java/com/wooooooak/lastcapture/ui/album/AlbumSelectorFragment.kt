package com.wooooooak.lastcapture.ui.album


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wooooooak.lastcapture.BR
import com.wooooooak.lastcapture.databinding.FragmentScreenShotAlbumBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumSelectorFragment : Fragment() {

    private lateinit var binding: FragmentScreenShotAlbumBinding
    private val viewModel: AlbumSelectorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenShotAlbumBinding.inflate(inflater, container, false).apply {
            setVariable(BR.viewModel, viewModel)
        }

        viewModel

        return binding.root
    }

}
