package com.tainzhi.android.wanandroid.repository

import com.tainzhi.android.wanandroid.base.Result
import com.tainzhi.android.wanandroid.utils.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.get
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2020/3/3 上午11:26
 * @description:
 */

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class HomeRepositoryTest : KoinTest {

    @get:Rule
    val mainCoroutineScope = MainCoroutineScopeRule()

    @Test
    fun getBanners() = mainCoroutineScope.runBlockingTest {
        val repository = get<HomeRepository>()
        val result = repository.getBanners()
        assertTrue(result is Result.Success)
    }
}