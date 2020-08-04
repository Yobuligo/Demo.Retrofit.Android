package com.yobuligo.demoretrofitandroid.services

interface IServiceBuilder {
    fun <T> build(serviceName: Class<T>): T
}