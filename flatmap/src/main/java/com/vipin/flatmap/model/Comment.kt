package com.vipin.flatmap.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by vipin.c on 11/09/2019
 */
data class Comment(
    @SerializedName("postId")
    var postId: Int,

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String?,

    @SerializedName("email")
    var email: String?,

    @SerializedName("body")
    var body: String?
)
