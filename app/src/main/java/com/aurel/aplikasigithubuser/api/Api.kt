package com.aurel.aplikasigithubuser.api


import com.aurel.aplikasigithubuser.data.model.User
import com.aurel.aplikasigithubuser.data.model.UserDetailResponse
import com.aurel.aplikasigithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}