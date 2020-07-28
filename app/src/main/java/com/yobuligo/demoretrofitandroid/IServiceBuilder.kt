package com.yobuligo.demoretrofitandroid

interface IServiceBuilder {
    fun <T> build(requestAPI: Class<T>): T
}