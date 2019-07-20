package com.wooooooak.lastcapture.ui.screenshots


import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wooooooak.lastcapture.BR
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.databinding.FragmentShowingLastThreeBinding
import com.wooooooak.lastcapture.ui.screenshots.adater.ScreenShotAdapter
import kotlinx.android.synthetic.main.item_thumbnail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowingLastThreeFragment : Fragment() {

    private lateinit var binding: FragmentShowingLastThreeBinding
    private val viewModel: ShowingLastThreeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val adapter = ScreenShotAdapter(requireActivity())

        binding = FragmentShowingLastThreeBinding.inflate(inflater, container, false).apply {
            setVariable(BR.viewModel, viewModel)
            screenshotList.adapter = adapter
            screenshotList.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        }

        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: ScreenShotAdapter) {
        viewModel.screenShots.observe(this, Observer { screenShots ->
            adapter.submitList(screenShots)
        })
    }

}
