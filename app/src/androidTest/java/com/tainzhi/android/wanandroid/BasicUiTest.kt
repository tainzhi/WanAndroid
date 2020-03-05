package com.tainzhi.android.wanandroid

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tainzhi.android.wanandroid.ui.MainActivity
import com.tainzhi.android.wanandroid.utils.DataBindingIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/3/5 上午6:26
 * @description:
 **/


@RunWith(AndroidJUnit4::class)
class BasicUiTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var dataBindingIdlingResource: IdlingResource

    @Before
    fun registerIdlingResources() {
        dataBindingIdlingResource = DataBindingIdlingResource(activityScenarioRule)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun activityLaunches() {
//        onView(withId(R.id.time_transformed)).check(matches(isDisplayed()))
    }
}
