package com.yobuligo.demoretrofitandroid.model.person

import com.yobuligo.demoretrofitandroid.model.team.TeamDTO

class PersonDTO {
    var id: Long = 0
    var firstname: String = ""
    var lastname: String = ""
    var teamDTOS: List<TeamDTO> = mutableListOf()
}