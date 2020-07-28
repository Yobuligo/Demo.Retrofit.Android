package com.yobuligo.demoretrofitandroid

interface IRestCallRequest {
    fun <T> create(requestAPI: Class<T>): T
}