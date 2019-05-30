package com.kotlin.WanAndroid.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout

import com.kotlin.WanAndroid.R
import com.kotlin.WanAndroid.data.module.ProjectListModel
import com.kotlin.WanAndroid.data.module.ProjectTreeModel
import com.kotlin.WanAndroid.injection.component.DaggerWAComponent
import com.kotlin.WanAndroid.injection.module.WAModule
import com.kotlin.WanAndroid.presenter.ProjectPresenter
import com.kotlin.WanAndroid.presenter.view.ProjectView
import com.kotlin.WanAndroid.ui.adapter.HomeTabAdapter
import com.kotlin.base.ext.loge
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_project.*
import javax.inject.Inject


/**
 *  ProjectFragment
 */
class ProjectFragment : BaseMvpFragment<ProjectPresenter>(), ProjectView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
    }


    private fun loadData() {
        mPresenter.getProjectTree()
    }

    override fun injectionComponent() {

        DaggerWAComponent
            .builder()
            .activityComponent(activityComponent)
            .wAModule(WAModule())
            .build()
            .inject(this)

        mPresenter.mView = this

    }


    private fun initTabLayout(nameList: MutableList<String>, fragmentList: MutableList<Fragment>) {

        // 需要动态添加 Fragment ,根据数据返回的多少个
        var tabAdapter = HomeTabAdapter(childFragmentManager, nameList, fragmentList)
        mProVp.adapter = tabAdapter

        mProTab.setupWithViewPager(mProVp)



        mProTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                //  可以做些自定义操作

            }
        })


    }


    /**
     *  拿到 项目分类 请求结果
     */
    override fun projectTreeResult(t: List<ProjectTreeModel>) {

        var nameArr = mutableListOf<String>()
        var fragmentList = mutableListOf<Fragment>()

        for (idx in 0 until t.size) {
            var p = t[idx]
            nameArr.add(p.name)
            var cid = p.id

            // 动态新建 Fragment ;
            fragmentList.add(ProjectsFragment.instance(cid))

        }

        loge("nameArr = ${nameArr.size} ， fragmentList  = ${fragmentList.size}")
        initTabLayout(nameArr, fragmentList)

    }


}
