package com.yuxi.myocr

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.cdsmartcity.baselibrary.baseView.GlideEngine
import com.cdsmartcity.baselibrary.utilTooth.LogUtils
import com.cdsmartcity.baselibrary.utilTooth.PermissionsUtils
import com.cdsmartcity.baselibrary.utilTooth.SharePreferenceUtils.FILE_NAME
import com.cdsmartcity.baselibrary.utilTooth.ToastUtil
import com.googlecode.tesseract.android.TessBaseAPI
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    //设置权限
    private fun setPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private fun getPermissions() {
        if (setPermissions().isNotEmpty()) {
            //这里的this不是上下文，是Activity对象
            PermissionsUtils.showSystemSetting = true//是否支持显示系统设置权限设置窗口跳转
            PermissionsUtils.getInstance().chekPermissions(
                WeakReference<Activity>(this).get(),
                setPermissions(),
                permissionsResult
            )
        }
    }

    //创建监听权限的接口对象
    private var permissionsResult: PermissionsUtils.IPermissionsResult =
        object : PermissionsUtils.IPermissionsResult {
            override fun passPermissons() {
                LogUtils.e("权限通过")
                languageIsExists()
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ) {
                        languageIsExists()
                    }
                }
            }

            override fun forbitPermissons(permissions: MutableList<String>?) {
                ToastUtil.showMessage(applicationContext, "权限不通过")
                LogUtils.e("权限不通过")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT < 23) {
            languageIsExists()
        }
        getPermissions()
        but_img.setOnClickListener {
            setPictureSelector()
        }

    }

    //所选相册图片的路径(原图/压缩后/剪裁后)
    private var albumPath = ""

    /**
     * picSelector的相册相机界面
     *
     *
     * // 例如 LocalMedia 里面返回三种 path
     * // 1.media.getPath(); 为原图 path
     * // 2.media.getCutPath();为裁剪后 path，需判断 media.isCut();是否为 true
     * // 3.media.getCompressPath();为压缩后 path，需判断 media.isCompressed();是否为 true
     * // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
     */
    private fun setPictureSelector() {
        //拍照
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
//            .theme(R.style.picture_default_style1)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
            .loadImageEngine(GlideEngine.createGlideEngine())
            .compress(true)// 是否压缩
            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
            .isCamera(true)// 是否显示拍照按钮
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
//                .forResult(PictureConfig.CHOOSE_REQUEST)
            .forResult(object : OnResultCallbackListener {
                override fun onResult(localMedias: MutableList<LocalMedia>?) {
                    val size = localMedias?.size
                    LogUtils.e("返回选择图片的数量==$size")
                    localMedias?.apply {
                        val media = localMedias[0]
                        if (localMedias[0].isCompressed) {
                            albumPath = localMedias[0].compressPath
                            LogUtils.e("压缩过")
                        } else {
                            albumPath = localMedias[0].path
                        }
                        val bitmap = BitmapFactory.decodeFile(albumPath)
                        val fileName = albumPath.substring(albumPath.lastIndexOf("/") + 1)
                        LogUtils.e("获取图片的位置==$albumPath，文件名字=$fileName,${localMedias[0].fileName}")
                        LogUtils.e("androidQToPath==${localMedias[0].androidQToPath}，realPath=${localMedias[0].realPath}")
                        iv_show.setImageBitmap(bitmap)
                        load(bitmap)
                    }
                }

                override fun onCancel() {
                    LogUtils.i("PictureSelector Cancel");
                }

            })
    }

    private fun load(bitmap: Bitmap) {
        val outFile = File(getExternalFilesDir(FILE_NAME), LANGUAGE_NAME)
        if (!outFile.exists()) {
            ToastUtil.showMessage(this, "找不到tessdata")
            return
        }
        val baseApi = TessBaseAPI()
        baseApi.setDebug(BuildConfig.DEBUG)
        val path = getExternalFilesDir("")?.absolutePath ?: ""
        if (TextUtils.isEmpty(path)) {
            ToastUtil.showMessage(this, "tessdata路径出现错误")
            return
        }
        baseApi.init(path, LANGUAGE_FILE_NAME)
        ToastUtil.showMessage(this, "文字识别中...")
        Thread(Runnable {
            baseApi.setImage(bitmap)
            val text = baseApi.utF8Text
            runOnUiThread {
                et_show?.setText(text)
            }
        }).start()

    }

    private fun languageIsExists() {
        val outFile = File(getExternalFilesDir(FILE_NAME), LANGUAGE_NAME)
        if (!outFile.exists()) {
            copyToSdCard(this, LANGUAGE_NAME, outFile)
        }
    }

    private fun copyToSdCard(context: Context, name: String, outFile: File) {
        try {
            val inputStream = context.assets.open(name)
            val fos = FileOutputStream(outFile)
            val buffer = ByteArray(1024)
            var byteCount = inputStream.read(buffer)
            while (byteCount != -1) {
                fos.write(buffer, 0, byteCount)
                byteCount = inputStream.read(buffer)
            }
            fos.flush()
            inputStream.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
            ToastUtil.showMessage(this, "保存语言出现错误")
        }
    }

    companion object {
        private const val FILE_NAME = "tessdata"
        private const val LANGUAGE_NAME = "chi_sim.traineddata"
        private const val LANGUAGE_FILE_NAME = "chi_sim"
        private const val PERMISSION_REQUEST_CODE = 0
    }

}