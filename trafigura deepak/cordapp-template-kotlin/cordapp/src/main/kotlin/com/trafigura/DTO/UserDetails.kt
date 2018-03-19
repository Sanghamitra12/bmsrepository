package com.trafigura.DTO

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class UserDetails(

        var user_id: String,
        var user_type: String,
        var  password: String

)