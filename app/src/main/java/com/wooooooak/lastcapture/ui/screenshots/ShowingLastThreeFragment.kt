package com.wooooooak.lastcapture.ui.screenshots


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wooooooak.lastcapture.BR
import com.wooooooak.lastcapture.databinding.FragmentShowingLastThreeBinding
import com.wooooooak.lastcapture.ui.screenshots.adater.ScreenShotAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowingLastThreeFragment : Fragment() {

    private lateinit var binding: FragmentShowingLastThreeBinding
    private val viewModel: ShowingLastThreeViewModel by viewModel()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("ShowingLastThree", "onAttach")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ShowingLastThree", "onCreate")
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
        viewModel.screenShots.observe(viewLifecycleOwner, Observer { screenShots ->
            adapter.submitList(screenShots)
        })
    }

}
