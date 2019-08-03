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
import com.wooooooak.lastcapture.BR
import com.wooooooak.lastcapture.databinding.FragmentShowingLastThreeBinding
import com.wooooooak.lastcapture.ui.screenshots.adater.ScreenShotAdapter
import kotlinx.android.synthetic.main.fragment_showing_last_three.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowingLastThreeFragment : Fragment() {

    private lateinit var binding: FragmentShowingLastThreeBinding
    private val showingLastThreeViewModel: ShowingLastThreeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = ScreenShotAdapter(requireActivity())

        binding = FragmentShowingLastThreeBinding.inflate(inflater, container, false).apply {
            setVariable(BR.viewModel, showingLastThreeViewModel)
            setRecyclerView(this, adapter)
            lifecycleOwner = this@ShowingLastThreeFragment
            executePendingBindings()
        }

        subscribeUi(adapter)

        binding.onClickDefaultFloatingButton = View.OnClickListener {
            showingLastThreeViewModel.changeFloatingButtonVisibility()
        }

        binding.onClickSettingCountButton = View.OnClickListener {
            val view = it as ExtendedFloatingActionButton
            val count = view.text.toString().toInt()
            showingLastThreeViewModel.setShowingCount(count)
            showingLastThreeViewModel.changeFloatingButtonVisibility()
        }

        return binding.root
    }

    private fun subscribeUi(adapter: ScreenShotAdapter) {
        showingLastThreeViewModel.screenShots.observe(viewLifecycleOwner, Observer { screenShots ->
            adapter.submitList(screenShots)
        })
    }

    private fun setRecyclerView(binding: FragmentShowingLastThreeBinding, adapter: ScreenShotAdapter) {
        with(binding) {
            screenshotList.adapter = adapter
            screenshotList.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            screenshotList.screenshot_list.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    showingLastThreeViewModel.setAllFloatingButtonVisibility(dy <= 0)
                }
            })
        }
    }

}
