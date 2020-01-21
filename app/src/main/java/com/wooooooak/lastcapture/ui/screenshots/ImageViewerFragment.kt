package com.wooooooak.lastcapture.ui.screenshots


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.orhanobut.logger.Logger
import com.wooooooak.lastcapture.databinding.FragmentImageViewerBinding
import com.wooooooak.lastcapture.databinding.FragmentImageViewerBindingImpl
import com.wooooooak.lastcapture.ui.screenshots.adater.ScreenShotAdapter
import kotlinx.android.synthetic.main.fragment_image_viewer.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowingLastThreeFragment : Fragment() {

    private lateinit var binding: FragmentImageViewerBinding
    private lateinit var screenShotAdapter: ScreenShotAdapter
    private val imageViewerViewModel: ImageViewerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageViewerBindingImpl.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setRecyclerView()
        subscribeViewModel()
        subscribeUi()
    }

    private fun initBinding() {
        with(binding) {
            viewModel = imageViewerViewModel
            // TODO click 바인딩은 뷰모델에 선언하자.
            onClickDefaultFloatingButton = View.OnClickListener {
                imageViewerViewModel.changeFloatingButtonVisibility()
            }
            onClickSettingCountButton = View.OnClickListener {
                val view = it as ExtendedFloatingActionButton
                val count = view.text.toString().toInt()
                imageViewerViewModel.setShowingCount(count)
                imageViewerViewModel.changeFloatingButtonVisibility()
            }
            lifecycleOwner = this@ShowingLastThreeFragment
            executePendingBindings()
        }
    }

    private fun subscribeViewModel() {
        with(imageViewerViewModel) {
            screenShots.observe(viewLifecycleOwner, Observer { screenShotsUri ->
                screenShotAdapter.submitList(screenShotsUri)
            })
        }
    }

    private fun subscribeUi() {
    }

    private fun setRecyclerView() {
        screenShotAdapter = ScreenShotAdapter(requireActivity(), imageViewerViewModel)
        with(binding) {
            screenshotList.adapter = screenShotAdapter
            screenshotList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            screenshotList.screenshot_list.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    imageViewerViewModel.setAllFloatingButtonVisibility(dy <= 0)
                }
            })
        }
    }

}
