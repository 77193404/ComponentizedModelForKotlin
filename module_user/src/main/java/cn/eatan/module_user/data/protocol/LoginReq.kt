package cn.eatan.module_user.data.protocol

data class LoginReq(val username: String, val password: String, val code: String) {
}