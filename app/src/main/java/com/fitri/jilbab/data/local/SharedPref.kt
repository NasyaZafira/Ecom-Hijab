package com.fitri.jilbab.data.local

import android.app.Application
import com.orhanobut.hawk.Hawk

object SharedPref {
    private const val USER_TOKEN    = "userToken"

    fun appInit(application: Application){
        Hawk.init(application).build()
    }
    var userToken: String? = null
        get() = Hawk.get(USER_TOKEN)
        set(value) {
            Hawk.put(USER_TOKEN, value)
            field = value
        }
    fun clear() {
        Hawk.deleteAll()
    }
}