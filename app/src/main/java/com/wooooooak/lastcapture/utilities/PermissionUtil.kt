package com.wooooooak.lastcapture.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.ArrayList

class PermissionUtil(val context: Context) {
    fun checkExtStoragePermission(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val permissionListener: PermissionListener = object : PermissionListener {
            @SuppressLint("MissingPermission")
            override fun onPermissionGranted() {
                onSuccess()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                onFailure()
            }
        }

        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("[설정] > [권한] 에서 권한을 허용하시면 앱을 사용 하실 수 있습니다.")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).check()
    }
}