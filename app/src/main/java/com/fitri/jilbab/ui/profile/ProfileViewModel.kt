package com.fitri.jilbab.ui.profile

import android.webkit.MimeTypeMap
import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.profile.DetailProfileResponse
import com.fitri.jilbab.data.model.profile.edit.EditProfileBody
import com.fitri.jilbab.data.model.profile.edit.EditProfileResponse
import com.fitri.jilbab.data.model.profile.password.BodyPassword
import com.fitri.jilbab.data.model.profile.password.ChangePassResponse
import com.fitri.jilbab.data.model.profile.picture.ChangePictureResponse
import com.fitri.jilbab.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel() {
    val userDetail = MutableLiveData<DetailProfileResponse>()
    val updateProfile = MutableLiveData<EditProfileResponse>()
    val changePass = MutableLiveData<ChangePassResponse>()
    val changeava = MutableLiveData<ChangePictureResponse>()

    suspend fun detailProfile() {
        repository.userDetail(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            }
        ).collect {
            userDetail.postValue(it)
        }
    }

    suspend fun editProfile(
        address: String,
        date_of_birth: String,
        gender: String,
        name: String,
        phone: String
    ) {
        val body = EditProfileBody(address, date_of_birth, gender, name, phone)
        repository.userUpdate(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            body
        ).collect {
            updateProfile.postValue(it)
        }
    }

    suspend fun updatePass(
        confirmpassword: String,
        currentpassword: String,
        newpassword: String
    ) {
        val body = BodyPassword(confirmpassword, currentpassword, newpassword)
        repository.passUpdate(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            body
        ).collect {
            changePass.postValue(it)
        }
    }

    suspend fun isAva(
        profile_picture: File
    ) {

        val ava = profile_picture.asRequestBody(
            getMimeType(profile_picture.path)!!.toMediaType()
        )
        val image = ava.let {
            MultipartBody.Part.createFormData(
                "profile_picture",
                profile_picture.name, it
            )
        }
        repository.changeAva(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            image
        ).collect {
            changeava.postValue(it)
        }
    }


    private fun getMimeType(path: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
}