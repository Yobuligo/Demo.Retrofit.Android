package com.yobuligo.demoretrofitandroid.model.paging

import retrofit2.http.QueryMap

class PageRequestDTO<T> {
    var totalPages: Int = 0
    var totalElements: Int = 0
    var numberOfElements: Int = 0
    var content: List<T> = mutableListOf<T>()
    var pageable = PageInfoDTO()
}