package com.kotlin.WanAndroid.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.kotlin.WanAndroid.R
import com.kotlin.WanAndroid.ui.adapter.HomeTabAdapter
import com.kotlin.WanAndroid.ui.fragment.*
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.loge
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.GlideUtils
import com.kotlin.base.utils.StatusBarUtils
import com.kotlin.base.widgets.CircleImageView
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *  WanAndroid  首页 ;
 */
@Route(path = RouterPath.WanAndroid.PATH_HOME)
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class HomeActivity : BaseActivity() {


    private val homeFragment = HomeFragment()
    private val projectFragment = ProjectFragment()
    private val hotFragment = HotFragment()
    private val trendFragment = TrendFragment()
    private val systemFragment = SystemFragment()

    lateinit var homeTabAdapter: HomeTabAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, mToolBar)


        initTabLayout()
        initNavigation()

        mSearch.onClick {
            startActivity<SearchActivity>()
        }
    }


    /**
     * 使用 TabLayout ;
     */
    private fun initTabLayout() {


        var tabList = mutableListOf<String>("主页", "项目", "热门", "动态", "体系")

        var fragmentList =
            mutableListOf<Fragment>(
                homeFragment, projectFragment,
                hotFragment, trendFragment, systemFragment
            )


        homeTabAdapter = HomeTabAdapter(supportFragmentManager, tabList, fragmentList)

        mHomeVp.adapter = homeTabAdapter
        mTab.setupWithViewPager(mHomeVp)

        // 设置 viewPager 缓存页面的个数 ;
        mHomeVp.offscreenPageLimit = 4

        mTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                var position = tab!!.position
//                Log.e("== ", "tab被选中 $position")

                mHomeVp.currentItem = position
            }

        })


    }

    /**
     *  侧滑菜单
     */
    private fun initNavigation() {


        var mUserLogin = mNavigation.getHeaderView(0).findViewById<LinearLayout>(R.id.mUserLogin)
        var mHeaderIv = mNavigation.getHeaderView(0).findViewById<CircleImageView>(R.id.mHeaderIv)
        var mNameTv = mNavigation.getHeaderView(0).findViewById<TextView>(R.id.mNameTv)


        var username = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        var icon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)


        username.let {

            mNameTv.text = it
        }

        icon.let {
            GlideUtils.loadImage(this, icon, mHeaderIv)
        }


        // HeaderView 里面的 空间需要使用 NavigationView 去 find 一下 ;
        mUserLogin.onClick {
            afterLogin {

            }
        }

        //导航栏 Menu  的点击事件 ;
        mNavigation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_collect -> {
                    toast("收藏")
                }
                R.id.nav_todo -> {
                    toast("TODO")
                }
                R.id.nav_night -> {
                    toast("夜间模式")
                }
                R.id.nav_setting -> {

                }
                R.id.nav_about -> {

                }
            }

            true
        }
    }


    /**
     *  按两次退出 APP
     */
    var lastTime: Long = 0

    override fun onBackPressed() {


        var time = System.currentTimeMillis()
        if (time - lastTime > 2000) {
            toast("再按一次退出程序")

            lastTime = time
        } else {
            AppManager.instance.exitApp(this)
            AppManager.instance.finishAllActivity()
        }


    }


}
