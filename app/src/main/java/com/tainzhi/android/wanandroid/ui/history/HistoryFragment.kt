package com.tainzhi.android.wanandroid.ui.history

import android.content.Context
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HistoryAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.FragmentHistoryBinding
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.util.autoCleardValue
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HistoryFragment : BaseVMFragment<HistoryViewModel>(useBinding = true) {
    
    private var historyAdapter by autoCleardValue<HistoryAdapter>()
    
    override fun getLayoutResId() = R.layout.fragment_history
    
    override fun initVM(): HistoryViewModel = getViewModel()
    
    override fun initView() {
    
        initToolbar()
    
        requireActivity().onBackPressedDispatcher.addCallback { onBack() }
    
        historySwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
    
        (mBinding as FragmentHistoryBinding).viewModel = mViewModel
    
        historyAdapter = HistoryAdapter { browseHistory ->
            mViewModel.insertBrowseHistory(browseHistory.article)
            val action = BrowserFragmentDirections.actionGlobalBrowserFragment(
                    browseHistory.article.link)
            findNavController().navigate(action)
        }
    
        historyRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
            adapter = historyAdapter
        }
    }
    
    private fun initToolbar() {
        toolbar.setTitle(R.string.browse_history)
        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        toolbar.inflateMenu(R.menu.delete_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteAll -> mViewModel.deleteAllBrowseHistory()
                else -> Unit
    
            }
            false
        }
    }
    
    override fun initData() {
    }
    
    override fun startObserve() {
        mViewModel.allBrowseHistory.observe(viewLifecycleOwner,
                                            Observer(historyAdapter::submitList))
    }
    
    private fun onBack() {
        findNavController().navigateUp()
    }
}