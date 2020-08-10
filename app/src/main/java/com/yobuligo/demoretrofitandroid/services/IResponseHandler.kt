package com.yobuligo.demoretrofitandroid.services

interface IResponseHandler<T> {
    fun onHandleResponse(response: T)
}