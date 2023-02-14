package com.fitri.jilbab.data.local

import android.app.Application
import com.orhanobut.hawk.Hawk

object SharedPref {
    private const val USER_TOKEN = "userToken"
    private const val USER_ID = "userId"
    private const val USER_EMAIL = "userEmail"
    private const val IS_LOGIN = "isLogin"
    private const val NAME = "nameUser"
    private const val ROLE = "userROLE"
    private const val ID_NAV = "nav"

    fun appInit(application: Application) {
        Hawk.init(application).build()
    }

    var userToken: String? = null
        get() = Hawk.get(USER_TOKEN)
        set(value) {
            Hawk.put(USER_TOKEN, value)
            field = value
        }

    var userId: Int? = null
        get() = Hawk.get(USER_ID)
        set(value) {
            Hawk.put(USER_ID, value)
            field = value
        }

    var userEmail: String? = null
        get() = Hawk.get(USER_EMAIL)
        set(value) {
            Hawk.put(USER_EMAIL, value)
            field = value
        }

    var isLoggedIn: Boolean = false
        get() = Hawk.get(IS_LOGIN, false)
        set(value) {
            Hawk.put(IS_LOGIN, value)
            field = value
        }

    var nameUser: String? = null
        get() = Hawk.get(NAME)
        set(value) {
            Hawk.put(NAME, value)
            field = value
        }

    var userRole: String? = null
        get() = Hawk.get(ROLE)
        set(value) {
            Hawk.put(ROLE, value)
            field = value
        }
    var idNav: Int = 1
        get() = Hawk.get(ID_NAV, 1)
        set(value) {
            Hawk.put(ID_NAV, value)
            field = value
        }

    fun clear() {
        Hawk.deleteAll()
    }
}