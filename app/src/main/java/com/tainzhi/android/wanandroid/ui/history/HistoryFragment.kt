package com.tainzhi.android.wanandroid.ui.history

import android.graphics.Color
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kennyc.view.MultiStateView
import com.tainzhi.android.common.base.ui.BaseVMFragment
import com.tainzhi.android.common.util.autoClearedValue
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HistoryAdapter
import com.tainzhi.android.wanandroid.adapter.RecyclerItemTouchHelper
import com.tainzhi.android.wanandroid.bean.BrowseHistory
import com.tainzhi.android.wanandroid.databinding.FragmentHistoryBinding
import com.tainzhi.android.wanandroid.ui.BrowserFragmentDirections
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HistoryFragment : BaseVMFragment<HistoryViewModel>(useBinding = true) {

    private var historyAdapter by autoClearedValue<HistoryAdapter>()

    override fun getLayoutResId() = R.layout.fragment_history

    override fun initVM(): HistoryViewModel = getViewModel()

    override fun initView() {

        initToolbar()

        requireActivity().onBackPressedDispatcher.addCallback { onBack() }

        // 使用Paging暂时不使用SwipeRefreshLayout
        // historySwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))

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
        ItemTouchHelper(recyclerItemTouchHelper).attachToRecyclerView(historyRecyclerView)
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
        // 使用该注释的code也能做到更新, 但是无法判断是有显示EmptyView
        // mViewModel.allBrowseHistory.observe(viewLifecycleOwner,
        //                                     Observer(historyAdapter::submitList))
        mViewModel.allBrowseHistory.observe(viewLifecycleOwner,
                Observer<PagedList<BrowseHistory>> {

                    showEmptyView(it?.size == 0)
                    historyAdapter.submitList(it)
                })
    }

    private fun showEmptyView(show: Boolean) {
        if (show) {
            historyMultiStateView.viewState = MultiStateView.ViewState.EMPTY
        } else {
            historyMultiStateView.viewState = MultiStateView.ViewState.CONTENT
        }
    }

    private fun onBack() {
        findNavController().navigateUp()
    }

    private val recyclerItemTouchHelper = RecyclerItemTouchHelper(
            0,
            ItemTouchHelper.LEFT,
            object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
                    if (viewHolder is HistoryAdapter.HistoryViewHolder) {
                        mViewModel.deleteBrowseHistory(viewHolder.browseHistory!!)


                        val snackbar: Snackbar = Snackbar.make(mBinding.root, R.string.remove_item_msg,
                                Snackbar.LENGTH_LONG)
                        snackbar.setAction(R.string.undo) { _ ->
                            mViewModel.insertBrowseHistory(viewHolder.browseHistory!!)
                        }
                        snackbar.setActionTextColor(Color.YELLOW)
                        snackbar.show()
                    }
                }
            }
    )
}