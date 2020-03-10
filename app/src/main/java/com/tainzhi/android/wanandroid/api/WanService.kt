package com.tainzhi.android.wanandroid.api

import com.tainzhi.android.wanandroid.base.Response
import com.tainzhi.android.wanandroid.bean.*
import retrofit2.http.*

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/19 下午9:49
 * @description:
 **/

interface WanService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): Response<ArticleList>

    @GET("/banner/json")
    suspend fun getBanner(): Response<List<Banner>>

    @GET("/tree/json")
    suspend fun getSystemType(): Response<List<SystemParent>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): Response<ArticleList>

    @GET("/navi/json")
    suspend fun getNavigation(): Response<List<Navigation>>

    @GET("/project/tree/json")
    suspend fun getProjectType(): Response<List<SystemParent>>

    @GET("/wxarticle/chapters/json")
    suspend fun getBlogType(): Response<List<SystemParent>>

    @GET("/wxarticle/list/{id}/{page}/json")
    fun getBlogArticle(@Path("id") id: Int, @Path("page") page: Int): Response<ArticleList>

    @GET("/project/list/{page}/json")
    suspend fun getProjectTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int): Response<ArticleList>

    @GET("/article/listproject/{page}/json")
    suspend fun getLastedProject(@Path("page") page: Int): Response<ArticleList>

    @GET("/friend/json")
    suspend fun getWebsites(): Response<List<Hot>>

    @GET("/hotkey/json")
    suspend fun getHot(): Response<List<Hot>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun searchHot(@Path("page") page: Int, @Field("k") key: String): Response<ArticleList>

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): Response<User>

    @GET("/user/logout/json")
    suspend fun logOut(): Response<Any>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): Response<User>

    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticles(@Path("page") page: Int): Response<ArticleList>

    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): Response<ArticleList>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): Response<ArticleList>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): Response<ArticleList>

    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    suspend fun shareArticle(@Field("title") title: String, @Field("link") url: String): Response<String>

    @GET("https://gitee.com/qinmen/GithubServer/raw/master/WanAndroid/update.json")
    suspend fun getUpdateInfo(): Response<UpdateInfo>

}