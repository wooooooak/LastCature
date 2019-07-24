package com.wooooooak.lastcapture.ui.album


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.wooooooak.lastcapture.BR
import com.wooooooak.lastcapture.databinding.FragmentScreenShotAlbumBinding
import com.wooooooak.lastcapture.ui.album.adapter.AlbumListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumSelectorFragment : Fragment() {

    private lateinit var binding: FragmentScreenShotAlbumBinding
    private val viewModel: AlbumSelectorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = AlbumListAdapter()

        binding = FragmentScreenShotAlbumBinding.inflate(inflater, container, false).apply {
            setVariable(BR.viewModel, viewModel)
            albumListView.adapter = adapter
            albumListView.layoutManager = GridLayoutManager(requireContext(), 2)
        }

        subscribeUi(viewModel, adapter)
        return binding.root
    }

    private fun subscribeUi(viewModel: AlbumSelectorViewModel, adapter: AlbumListAdapter) {
        viewModel.albumList.observe(this, Observer {
            adapter.submitList(it)
            Log.d("AlbumSelector", it.toString())
        })

    }

}
