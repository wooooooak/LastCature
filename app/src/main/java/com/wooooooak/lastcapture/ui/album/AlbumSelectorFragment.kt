package com.wooooooak.lastcapture.ui.album


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wooooooak.lastcapture.databinding.FragmentScreenShotAlbumBinding
import com.wooooooak.lastcapture.ui.album.adapter.AlbumListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import wooooooak.com.library.CoroutinesPermissionManager
import wooooooak.com.library.PermissionResult

class AlbumSelectorFragment : Fragment() {

    private lateinit var binding: FragmentScreenShotAlbumBinding

    private val viewModel: AlbumSelectorViewModel by viewModel()

    private val adapter: AlbumListAdapter by lazy {
        AlbumListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            CoroutinesPermissionManager.requestPermission(requireActivity()) {
                permissionList = listOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                Rationale {
                    title = "앱을 사용하시려면 권한이 필요합니다."
                }
            }
        }
        binding = FragmentScreenShotAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        subscribeViewModel()
    }

    private fun initBinding() {
        with(binding) {
            viewModel = viewModel
            albumListView.adapter = adapter
            albumListView.layoutManager = GridLayoutManager(requireContext(), 2)
            lifecycleOwner = this@AlbumSelectorFragment
            executePendingBindings()
        }
    }

    private fun subscribeViewModel() {
        viewModel.albumList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}
