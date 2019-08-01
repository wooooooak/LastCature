package com.wooooooak.lastcapture.ui.screenshots


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.wooooooak.lastcapture.BR
import com.wooooooak.lastcapture.databinding.FragmentShowingLastThreeBinding
import com.wooooooak.lastcapture.ui.screenshots.adater.ScreenShotAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowingLastThreeFragment : Fragment() {

    private lateinit var binding: FragmentShowingLastThreeBinding
    private val viewModel2: ShowingLastThreeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ShowingLastThree", "onCreate")
        val adapter = ScreenShotAdapter(requireActivity())

        binding = FragmentShowingLastThreeBinding.inflate(inflater, container, false).apply {
            setVariable(BR.viewModel, viewModel2)
            screenshotList.adapter = adapter
            screenshotList.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            lifecycleOwner = this@ShowingLastThreeFragment
            executePendingBindings()
        }

        subscribeUi(adapter)

        binding.onClickDefaultFloatingButton = View.OnClickListener {
            Log.d("ShowingLastThree", "click")
            viewModel2.setFloatingButtonVisibility()
        }

        binding.onClickSettingCountButton = View.OnClickListener {
            val view = it as ExtendedFloatingActionButton
            val count = view.text.toString().toInt()
            Snackbar.make(it, count.toString(), Snackbar.LENGTH_SHORT).show()
            viewModel2.setShowingCount(count)
        }

        return binding.root
    }

    private fun subscribeUi(adapter: ScreenShotAdapter) {
        viewModel2.screenShots.observe(viewLifecycleOwner, Observer { screenShots ->
            adapter.submitList(screenShots)
        })
    }

}
