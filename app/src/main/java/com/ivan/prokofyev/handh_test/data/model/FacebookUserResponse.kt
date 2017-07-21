package com.ivan.prokofyev.handh_test.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Shiraha on 7/21/2017.
 */

data class FacebookUserResponse(
        @SerializedName("email") val email: String,
        @SerializedName("first_name") val name: String)