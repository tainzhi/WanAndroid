package com.tainzhi.android.wanandroid.ui

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.tainzhi.android.wanandroid.R
import com.tainzhi.android.wanandroid.adapter.HomeArticleAdapter
import com.tainzhi.android.wanandroid.base.ui.BaseVMFragment
import com.tainzhi.android.wanandroid.databinding.ItemProjectBinding
import com.tainzhi.android.wanandroid.util.Preference
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

    private val isLogin by Preference(Preference.KEY_IS_LOGIN, false)
    private val cid by lazy { arguments?.getInt(CID) }
    private val isLated by lazy { arguments?.getBoolean(LASTED) }
    private val projectAdapter by lazy { HomeArticleAdapter<ItemProjectBinding>(R.layout.item_project, BR
            .article) }

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
        projectRefreshLayout.run {
            setColorSchemeColors(ContextCompat.getColor(activity as Context, R.color.color_secondary))
            setOnRefreshListener { refresh() }
        }
        projectAdapter.run {
            setOnItemClickListener { _, _, position ->
                mViewModel.insertBrowseHistory(projectAdapter.data[position])

                val action = BrowserFragmentDirections.actionGlobalBrowserFragment(projectAdapter
                        .data[position]
                        .link)
                findNavController().navigate(action)
            }
            loadMoreModule.run {
                loadMoreView = CustomLoadMoreView()
                setOnLoadMoreListener{ loadMore() }

            }
            setOnItemChildClickListener(this@ProjectTypeFragment.onItemChildClickListener)
        }
        projectRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context.resources.getDimension(R.dimen.margin_small)))
            adapter = projectAdapter
        }

    }

    private fun refresh() {
        projectAdapter.loadMoreModule.isEnableLoadMore =  false
        loadData(true)
    }

    private fun loadMore() {
        loadData(false)
    }

    private fun loadData(isRefresh: Boolean) {
        isLated?.run {
            if (this) {
                mViewModel.getLatestProjectList(isRefresh)
            } else {
                cid?.let {
                    mViewModel.getProjectTypeDetailList(isRefresh, it)
                }
            }
        }
    }

    override fun startObserve() {
        mViewModel.uiState.observe(viewLifecycleOwner, Observer {
            projectRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                projectAdapter.run {
                    if (it.isRefresh) setList(list.datas)
                    else addData(list.datas)
                    loadMoreModule.run {
                        isEnableLoadMore = true
                        loadMoreComplete()
                    }
                }
            }
    
            if (it.showEnd) projectAdapter.loadMoreModule.loadMoreEnd()
        })
    }
    
    override fun onPause() {
        super.onPause()
        projectRefreshLayout.isEnabled = false
    }
    
    override fun onResume() {
        super.onResume()
        projectRefreshLayout.isEnabled = true
    }
    
    private val onItemChildClickListener = OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.collectIv -> {
                if (isLogin) {
                    projectAdapter.run {
                        data[position].run {
                            collect = !collect
                            mViewModel.collectArticle(id, collect)
                        }
                        notifyItemChanged(position)
                    }
                } else {
                    Navigation.findNavController(projectRecyclerView)
                            .navigate(R.id.action_tabHostFragment_to_login)
                }
            }
        }

    }

}
 
 