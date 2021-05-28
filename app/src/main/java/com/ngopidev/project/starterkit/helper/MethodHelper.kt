package com.ngopidev.project.starterkit.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.NumberFormat
import java.util.*


/**
 *   created by Irfan Assidiq
 *   email : assidiq.irfan@gmail.com
 *   digunakan untuk menyimpan beberapa method yang membantu untuk mempermudah beberapa proses
 *   yang ada didalam android, seperti mempersingkat pembuatan toast dan beberapa hal
 *   yang kemungkinan akan digunakan didalam aplikasi android yang dibuat.
 **/
object MethodHelper {
    /**
     * @param ctx
     * @param msg
     * using for show short toast
     */
    fun showShortToast(ctx : Context, msg : String ) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * @param ctx
     * @param msg
     * using for show short toast
     */
    fun showLoangToast(ctx : Context, msg : String ) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * @param ctx
     * @param goto
     * using for do intent for one to another activity
     */
    fun <T : Any> goTo(ctx : Context, goto : Class<T>){
        ctx.startActivity(Intent(ctx, goto))
    }

    /**
     * @param ctx
     * @param goto
     * @param key
     * @param stringExtras
     * using for do intent for one to another activity plus string extras
     */
    fun <T : Any> goTo(ctx : Context, goto : Class<T>, key : String, stringExtras : String){
        val intent =  Intent(ctx, goto)
        intent.putExtra(key, stringExtras)
        ctx.startActivity(intent)
    }

    /**
     * @param nilai
     * @return String
     * using for change currency to idr /Rp
     */
    fun changeCurrencytoIDR(nilai : Double) : String{
        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
        return formatRupiah.format(nilai)
    }

    /**
     * @param saved
     * @param color
     * using for change view of all navigation background (android sdk > lollipop)
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setNavBackground(saved: AppCompatActivity, color : Int){
        val windows = saved.window
        windows.navigationBarColor = saved.resources.getColor(color)
    }

    /**
     * @param saved
     * @param color
     * using for change background of notification bar (android sdk > lollipop)
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setWindowsBar(saved : AppCompatActivity, color: Int){
        val windows = saved.window
        windows.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        windows.setStatusBarColor(ContextCompat.getColor(saved, color))
    }


    /**
     * @param ctx
     * @return Boolean
     * cek permission return true if all granted, return false if some Denied
     * note : add all permission you need here
     */
    fun checkPermissionNeed(ctx : Context) : Boolean{
        return PermissionChecker.checkSelfPermission(
            ctx, Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(
            ctx, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(
            ctx, Manifest.permission.READ_PHONE_STATE) == PermissionChecker.PERMISSION_GRANTED
    }

    /**
     * @param ctx as AppCompatActivity
     * note : add all permission you need here
     */
    fun reqAllPermission(ctx: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permisi = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )
            if (PermissionChecker.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission( ctx, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission( ctx, Manifest.permission.READ_PHONE_STATE
                ) != PermissionChecker.PERMISSION_GRANTED) {
                ctx.requestPermissions(permisi, Const.REQCODE)
            }
        }
    }

    /**
     * @param ctx as Fragment
     * use this method to request permission inside fragment
     * note : add all permission you need here
     */
    fun reqStorageAndCameraPermission(ctx: Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permisi = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (PermissionChecker.checkSelfPermission(ctx.requireContext(), Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission( ctx.requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PermissionChecker.PERMISSION_GRANTED && PermissionChecker.checkSelfPermission( ctx.requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PermissionChecker.PERMISSION_GRANTED
            ) {
                ctx.requestPermissions(permisi, Const.REQCODE)
            }
        }
    }

    /**
     * @param ctx as AppcompatActivity
     * @param idParent
     * @param msg
     * use this for create simple snackbar without action
     */
    fun createSnackbarShort(ctx: AppCompatActivity, idParent : Int, msg: String){
        Snackbar.make(ctx.findViewById(idParent), msg, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * @param ctx as AppcompatActivity
     * @param idParent
     * @param msg
     * @param actionTitle
     * @param clickMethod
     * use this for create snackbar with action
     */
    fun createSnackbarWithAction(ctx: AppCompatActivity, idParent: Int, msg: String, actionTitle : String, clickMethod : View.OnClickListener){
        Snackbar.make(ctx.findViewById(idParent), msg, Snackbar.LENGTH_LONG).setAction(actionTitle, clickMethod).show()
    }

    /**
     * jpeg extension compressor
     * @param bitmap
     * only for android sdk < 10
     */
    fun jpegCompressor(bitmap : Bitmap) : File {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapData = bos.toByteArray()
        val locpath = Environment.getExternalStorageDirectory()
        val locDir = File(locpath.path+"/BengkelNet/")
        val filex = File(locDir,"BN_${System.currentTimeMillis()}_img.jpg")

        try{
            filex.createNewFile()
            val fos = FileOutputStream(filex)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        }catch (err : IOException){
            err.printStackTrace()
        }
        return filex
    }

    /**
     * Jpeg new Compressor
     * using for compress image to JPG for android sdk 11
     */

    fun jpegNewCompressor(context : Context, bitmap: Bitmap) : File {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapData = bos.toByteArray()
        val locpath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val filex = File(locpath!!,"BN_${System.currentTimeMillis()}_img.jpg")

        try{
            filex.createNewFile()
            val fos = FileOutputStream(filex)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        }catch (err : IOException){
            err.printStackTrace()
        }
        return filex
    }

    /**
     * get app version bycode
     * @param ctx
     * @return String
     */
    fun getAppsVersion(ctx : Context) : String{
        val info = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
        return  info.versionName
    }

    /**
     * base64 encoder
     * @param bitmap
     * @return String
     */
    fun base64Encoder(bitmap : Bitmap) : String{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)

        val b = baos.toByteArray()
        val encodingFile = Base64.encodeToString(b, Base64.DEFAULT)
        return  encodingFile
    }
    /**
     * base64 decoder
     * @param base64src
     * @return Bitmap
     */
    fun base64Decoder(base64src : String) : Bitmap {
        Log.e("TADERR", base64src)
        val baos = ByteArrayOutputStream()
        Log.e("TADERRx", baos.toString())
        val b = baos.toByteArray()
        val base64Decoding  = Base64.decode(base64src, Base64.DEFAULT)
        val imageBitmap = BitmapFactory.decodeByteArray(b, 0, 255)
        return imageBitmap
    }

    /**
     * using for execute retrofit2 services
     */
    fun doRetrofitExecute() : APIServices{

        val client = OkHttpClient.Builder().build()
        val gson = GsonBuilder()
            .setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Const.getUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        val apiAccess = retrofit.create(APIServices::class.java)
        return apiAccess
    }

    /**
     * using for execute retrofit activities
     * and data which send is json type
     */
    fun doRetrofitExecuteJSON() : APIServices{
        val httpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requester = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                return chain.proceed(requester)
            }
        }).build()

        val gson = GsonBuilder()
            .setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Const.getUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()

        val apiAccess = retrofit.create(APIServices::class.java)
        return apiAccess
    }

    /**
     * using for get device id
     */
    @SuppressLint("HardwareIds")
    fun doGetDeviceID(ctx : Context) : String{
        val ID = Settings.Secure.getString(ctx.contentResolver,
            Settings.Secure.ANDROID_ID)
        return ID
    }

    /**
     * usinf for getdevicename
     */
    fun getDeviceName() : String{
        return Build.DEVICE +" "+ Build.BRAND +" "+ Build.MANUFACTURER
    }
}