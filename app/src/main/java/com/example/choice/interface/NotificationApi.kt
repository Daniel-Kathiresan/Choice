package com.example.choice.`interface`

import com.example.choice.constants.Constants.Companion.SERVER_KEY
import com.example.choice.model.PushNotification
import com.google.firebase.analytics.FirebaseAnalytics.Param.CONTENT
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface NotificationApi {
    @Headers("Authorization: key=$SERVER_KEY", "Content-type:$CONTENT-TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification:PushNotification
    ): Response<ResponseBody>
}