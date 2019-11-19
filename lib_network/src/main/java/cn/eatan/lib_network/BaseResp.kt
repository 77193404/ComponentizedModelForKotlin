package cn.eatan.lib_network

data class BaseResp<out T>(val code:Int
               , val msg:String
               , val data:T)