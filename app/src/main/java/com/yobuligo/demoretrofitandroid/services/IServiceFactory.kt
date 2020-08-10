package com.yobuligo.demoretrofitandroid.services

interface IServiceFactory {
    fun <T> createService(serviceName: Class<T>): T
}