package com.yobuligo.demoretrofitandroid.services

class ServiceExecutorBuilder<T, P> : IServiceExecutorBuilder<P> {
    private lateinit var service: Class<T>
    private lateinit var result: Class<P>

    constructor(service: Class<T>) {
        this.service = service
    }

    constructor(service: Class<T>, result: Class<P>) {
        this.service = service
        this.result = result
    }

    override fun execute() {
        val service = ServiceBuilder().build(service)
    }
}