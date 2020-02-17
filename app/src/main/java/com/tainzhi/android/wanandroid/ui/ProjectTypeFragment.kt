package com.tainzhi.android.wanandroid.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.util.Preference
import com.tainzhi.android.wanandroid.util.dp2px
import com.tainzhi.android.wanandroid.view.CustomLoadMoreView
import com.tainzhi.android.wanandroid.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_project_type.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/28 上午6:00
 * @description:  最新项目/项目分类
 **/

class ProjectTypeFragment : BaseVMFragment<ArticleViewModel>() {

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val cid by lazy { arguments?.getInt(CID) }
    private val isLated by lazy { arguments?.getBoolean(LASTED) }
    private val projectAdapter by lazy { HomeArticleAdapter(R.layout.item_project) }

    companion object {
        private const val CID = "cid"
        private const val LASTED = "lasted"

        fun newInstance(cid: Int, isLasted: Boolean): ProjectTypeFragment {
            val fragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            bundle.putBoolean(LASTED, isLasted)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResId() = R.layout.fragment_project_type

    override fun initVM(): ArticleViewModel = getViewModel()

    override fun initView() {
        initRecyclerView()
    }

    override fun initData() {
        refresh()
    }

    private fun initRecyclerView() {
        projectRefreshLayout.setOnRefreshListener { refresh() }
        projectAdapter.run {
            setOnItemClickListener { _, _, position ->
                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(projectAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, projectRecyclerView)
            onItemChildClickListener = this@ProjectTypeFragment.onItemChildClickListener
        }
        projectRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(projectRecyclerView.dp2px(10)))
            adapter = projectAdapter
        }

    }

    private fun refresh() {
        projectAdapter.setEnableLoadMore(false)
        loadData(true)
    }

    private fun loadMore() {
        loadData(false)
    }

    private fun loadData(isRefresh: Boolean) {
        isLated?.run {
            if (this) {
                viewModel.getLatestProjectList(isRefresh)
            } else {
                cid?.let {
                    viewModel.getProjectTypeDetailList(isRefresh, it)
                }
            }
        }
    }

    override fun startObserve() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer {
            projectRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                projectAdapter.run {
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) projectAdapter.loadMoreEnd()
        })
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {

                    projectAdapter.run {
                        data[position].run {
                            collect = !collect
                            viewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(projectRecyclerView)
                            .navigate(R.id.action_main_to_login)
                }
            }
        }

    }

}
 
 