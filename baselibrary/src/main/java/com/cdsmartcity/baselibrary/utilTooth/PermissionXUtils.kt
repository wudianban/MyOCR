package com.cdsmartcity.baselibrary.utilTooth

import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.cdsmartcity.baselibrary.R
import java.util.*


/**
 * 树米科技
 *
 * @author Li
 * 创建日期：2020/7/28 11:02
 * 描述：PermissionXUtils
 */
object PermissionXUtils {
    fun getPermissions(
        activity: FragmentActivity,
        permission: ArrayList<String>,
        iPermissionX: IPermissionX? = null
    ) {
        PermissionX.init(activity)
            .permissions(permission)
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                val message = activity.getString(R.string.explain_request_reason)
                val dialog = CustomDialog(activity, message, deniedList)
                scope.showRequestReasonDialog(dialog)
            }
            .onForwardToSettings { scope, deniedList ->
                val message = activity.getString(R.string.forward_settings)
                val dialog = CustomDialog(activity, message, deniedList)
                scope.showForwardToSettingsDialog(dialog)
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    if (iPermissionX == null) {
                        ToastUtil.showMessage(activity, "所有申请的权限都已通过")
                    }
                    iPermissionX?.agree()
                } else {
                    if (iPermissionX == null) {
                        ToastUtil.showMessage(activity, "您拒绝了如下权限：$deniedList")
                    }
                    iPermissionX?.refuse(deniedList)
                }
            }
    }
}

interface IPermissionX {
    fun agree()
    fun refuse(text: MutableList<String>)
}

