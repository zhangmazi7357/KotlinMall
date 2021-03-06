package com.kotlin.base.injection.component

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.kotlin.base.injection.scope.ActivityScope
import com.kotlin.base.injection.module.ActivityModule
import dagger.Component

/**
 * @author : ${Zhang}
 * @Date   : 2019/4/22 14:08:43
 * @Des    :   这里还需要考虑作用域的问题;  (Scope)
 *
 * [ ** ]  等价于 arrayOf()
 */
@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun activity(): FragmentActivity      // 可以在所有 Activity 中 提供 Activity 对象 ;
    fun context(): Context       // 在 所有 Activity 中可以提供一个 Context 对象 ;

}