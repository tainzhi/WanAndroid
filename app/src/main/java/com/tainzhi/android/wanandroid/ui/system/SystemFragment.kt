package com.tainzhi.android.wanandroid.ui.system

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tainzhi.android.wanandroid.BR
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.SystemAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.ItemSystemBinding
import com.tainzhi.android.wanandroid.ui.main.TabHostFragmentDirections
import com.tainzhi.android.wanandroid.util.toast
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_system.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


/**
 * 体系
 */
class SystemFragment : BaseVMFragment<SystemViewModel>() {
    private val systemAdapter by lazy {
        SystemAdapter<ItemSystemBinding>(R.layout.item_system, BR
                .systemParent)
    }

    override fun getLayoutResId() = R.layout.fragment_system

    override fun initVM(): SystemViewModel = getViewModel()

    override fun initView() {
        initRecyclerView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecyclerView() {

        systemRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
            adapter = systemAdapter
        }

        systemAdapter.setOnItemChildClickListener {  _, _, position ->
            val action = TabHostFragmentDirections.actionSystemFragmentToSystemTypeNormalFragment(systemAdapter
                    .data[position])
            findNavController().navigate(action)
        }

        systemRefreshLayout.run {
            setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
            setOnRefreshListener { refresh() }
        }
    }

    private fun refresh() {
        mViewModel.getSystemTypes()
    }

    override fun startObserve() {
        mViewModel.run {
            uiState.observe(viewLifecycleOwner, Observer {
                systemRefreshLayout.isRefreshing = it.showLoading

                it.showSuccess?.let { list -> systemAdapter.setList(list) }

                it.showError?.let { message -> activity?.toast(message) }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        systemRefreshLayout.isEnabled = true
    }

    override fun onStop() {
        super.onStop()
        systemRefreshLayout.isEnabled = false
    }
}