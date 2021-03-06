package com.kotlin.mall.ui.activity

import android.os.Bundle
import com.kotlin.WanAndroid.ui.activity.HomeActivity
import com.kotlin.base.ext.onClick

import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.utils.StatusBarUtils
import com.kotlin.base.widgets.MCountDownTimer
import com.kotlin.mall.R
import com.kotlin.mall.common.AppConstant
import com.kotlin.provider.common.afterLogin
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

/**
 *  欢迎页面
 */
class SplashActivity : BaseActivity() {


    private var timer: MCountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        StatusBarUtils.darkMode(this)


        countDownTime()

        mToBtn.onClick {
            timer!!.cancel()
            afterLogin {
//                startActivity<MainActivity>()
                startActivity<HomeActivity>()
            }
            finish()
        }


    }

    /**
     *  开启倒计时
     */
    private fun countDownTime() {
        timer = MCountDownTimer(AppConstant.TOTAL_TIME, AppConstant.INTERVAL_TIME)
        timer!!.start()


        timer!!.setFinishInterface(object : MCountDownTimer.FinishInterface {
            override fun onFinish() {

                afterLogin {
//                    startActivity<MainActivity>()
                    startActivity<HomeActivity>()
                }
                finish()
            }

        })

        timer!!.setTickInterface(object : MCountDownTimer.TickInterface {
            override fun onTicks() {

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        timer!!.cancel()
    }


}
