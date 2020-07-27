package com.yobuligo.demoretrofitandroid

import com.google.gson.annotations.SerializedName

class MindSet {
    var id: Long = 0L
    var description: String = ""

    @SerializedName("unreadable")
    var information: String = ""
}