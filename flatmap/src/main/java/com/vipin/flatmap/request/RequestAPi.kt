package com.vipin.flatmap.request

import com.vipin.flatmap.model.Comment
import com.vipin.flatmap.model.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by vipin.c on 11/09/2019
 */
interface RequestAPi {

    @GET("posts")
    fun getPosts() : Observable<MutableList<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int) : Observable<MutableList<Comment>>
}