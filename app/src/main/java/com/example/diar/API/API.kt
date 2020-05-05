package com.example.diar.API


import com.example.diar.Data.LoginRepo
import retrofit2.Call
import retrofit2.http.*


interface API {

    @POST("/signin")
    @FormUrlEncoded
    fun logIn(@Field("id") id : String, @Field("passwd") pw : String) :  Call<LoginRepo>

    @POST("/signup")
    @FormUrlEncoded
    fun logUp(@Field("name") name : String, @Field("id") id : String, @Field("passwd") pw : String, @Field("phoneNum") ph : String, @Field("nick") nick : String) :  Call<Void>

}