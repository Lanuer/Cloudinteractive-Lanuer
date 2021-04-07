package com.example.cloudinteractivelanuer.model

import com.google.gson.annotations.SerializedName

class DataModel {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
}