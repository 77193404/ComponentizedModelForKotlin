package cn.eatan.module_user.data.api

import cn.eatan.lib_network.BaseResp
import cn.eatan.module_user.data.protocol.LoginReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UserApi {

    //登录
    @POST("/user/login")
    fun login(@Body req: LoginReq) : Observable<BaseResp<String>>
}