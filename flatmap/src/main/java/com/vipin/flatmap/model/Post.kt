package com.vipin.flatmap.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by vipin.c on 11/09/2019
 */
data class Post(

    @SerializedName("userId")
    var userId: Int,

    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String?,

    @SerializedName("body")
    var body: String?,

    @SerializedName("comments")
    var comments: List<Comment>?
) {

    override fun toString(): String {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\''.toString() +
                ", body='" + body + '\''.toString() +
                '}'.toString()
    }
}
