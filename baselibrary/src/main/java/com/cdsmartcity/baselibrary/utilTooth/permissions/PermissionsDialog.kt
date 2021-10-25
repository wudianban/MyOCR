package com.cdsmartcity.baselibrary.utilTooth.permissions

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.cdsmartcity.baselibrary.R
import com.cdsmartcity.baselibrary.utilTooth.LogUtils
import com.luck.picture.lib.tools.ScreenUtils
import kotlinx.android.synthetic.main.custom_dialog_layout.view.*
import java.util.*


/**
 * 树米科技
 *
 * @author Li
 * 创建日期：2019/12/28 15:20
 * 描述：ShowBottomDialog
 */
class PermissionsDialog(val context: Activity, val iCustomDialog: ICustomDialog) {

    private val permissionMap = mapOf(
        Manifest.permission.READ_CALENDAR to Manifest.permission_group.CALENDAR,
        Manifest.permission.WRITE_CALENDAR to Manifest.permission_group.CALENDAR,
        Manifest.permission.READ_CALL_LOG to Manifest.permission_group.CALL_LOG,
        Manifest.permission.WRITE_CALL_LOG to Manifest.permission_group.CALL_LOG,
        Manifest.permission.PROCESS_OUTGOING_CALLS to Manifest.permission_group.CALL_LOG,
        Manifest.permission.CAMERA to Manifest.permission_group.CAMERA,
        Manifest.permission.READ_CONTACTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.WRITE_CONTACTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.GET_ACCOUNTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.ACCESS_FINE_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.RECORD_AUDIO to Manifest.permission_group.MICROPHONE,
        Manifest.permission.READ_PHONE_STATE to Manifest.permission_group.PHONE,
        Manifest.permission.READ_PHONE_NUMBERS to Manifest.permission_group.PHONE,
        Manifest.permission.CALL_PHONE to Manifest.permission_group.PHONE,
        Manifest.permission.ANSWER_PHONE_CALLS to Manifest.permission_group.PHONE,
        Manifest.permission.ADD_VOICEMAIL to Manifest.permission_group.PHONE,
        Manifest.permission.USE_SIP to Manifest.permission_group.PHONE,
        Manifest.permission.ACCEPT_HANDOVER to Manifest.permission_group.PHONE,
        Manifest.permission.BODY_SENSORS to Manifest.permission_group.SENSORS,
        Manifest.permission.ACTIVITY_RECOGNITION to Manifest.permission_group.ACTIVITY_RECOGNITION,
        Manifest.permission.SEND_SMS to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_SMS to Manifest.permission_group.SMS,
        Manifest.permission.READ_SMS to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_WAP_PUSH to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_MMS to Manifest.permission_group.SMS,
        Manifest.permission.READ_EXTERNAL_STORAGE to Manifest.permission_group.STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE to Manifest.permission_group.STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION to Manifest.permission_group.STORAGE
    )

    private val groupSet = HashSet<String>()
    private val mPermissionsLayout: LinearLayout
    private val mMessageText: TextView

    //1、使用Dialog、设置style
    private var dialog: Dialog = Dialog(context, R.style.CustomDialog)

    //2、设置布局
    private val view = View.inflate(context, R.layout.custom_dialog_layout, null)

    init {
        dialog.setContentView(view)
        dialog.setCancelable(false)

        val window = dialog.window
        //设置弹出位置
        window!!.setGravity(Gravity.CENTER)
//        //设置弹出动画
//        window.setWindowAnimations(R.style.main_menu_animStyle)
        //设置对话框大小
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setLayout(
            (ScreenUtils.getScreenWidth(context) * 0.88).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        view.apply {
            mPermissionsLayout = findViewById(R.id.permissionsLayout)
            mMessageText = findViewById(R.id.messageText)
            negativeBtn.setOnClickListener {
                dialog.dismiss()
                iCustomDialog.negativeButton()
            }
            positiveBtn.setOnClickListener {
                dialog.dismiss()
                iCustomDialog.positiveButton()
            }
        }
    }

    fun show(message: String, permissions: List<String>) {
        LogUtils.e("权限长度：${permissions.size}")
        mMessageText.text = message
        buildPermissionsLayout(permissions)
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    private fun buildPermissionsLayout(permissions: List<String>) {
        mPermissionsLayout.removeAllViews()
        groupSet.clear()
        for (permission in permissions) {
            val permissionGroup = permissionMap[permission]
            if (permissionGroup != null && !groupSet.contains(permissionGroup)) {
                val textView = LayoutInflater.from(context)
                    .inflate(R.layout.permissions_item, mPermissionsLayout, false) as TextView
                textView.text = context.packageManager.getPermissionGroupInfo(permissionGroup, 0)
                    .loadLabel(context.packageManager)
                mPermissionsLayout.addView(textView)
                groupSet.add(permissionGroup)
            }
        }
    }


    interface ICustomDialog {
        fun positiveButton()
        fun negativeButton()
    }

}