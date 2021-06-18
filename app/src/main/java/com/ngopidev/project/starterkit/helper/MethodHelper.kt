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
     * @param msg
     */
        /**
         * simple short toast
         */
        infix fun Context.shortToast(message : String){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * simple long toast
         */
        infix fun Context.longToast(message : String){
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

    /**
     * simple intent method
     */
        /**
         * move to another page
         * it cannot handle intent extras inside
         */
        infix fun <T : Any> Context.goTo(pindahKelas : Class<T>){
            startActivity(Intent(this, pindahKelas))
        }

        /**
         * move to another page
         * second method with intent parameter
         * it can handle intent with extras inside
         */
        infix fun Context.goTo(intentData : Intent){
            startActivity(intentData)
        }


    /**
     * change currencty to IDR
     */
    infix fun Context.currencyRp(nilai : Double) : String{
        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
        return formatRupiah.format(nilai)
    }

    /**
     * @param saved
     * @param color
     * using for change view of all navigation background
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    infix fun AppCompatActivity.setNavBckg(color : Int){
        val windows = window
        windows.navigationBarColor = resources.getColor(color)
    }

    /**
     * @param saved
     * @param color
     * using for change background of notification bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    infix fun AppCompatActivity.setNotificationBar( color: Int){
        val windows = window
        windows.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        windows.setStatusBarColor(ContextCompat.getColor(this, color))
    }


    /**
     * cek all permission needed
     */
    infix fun Context.cekPermission(permissions : Array<String>) : Boolean {
        return permissions.all {
            PermissionChecker.checkSelfPermission(this, it) == PermissionChecker.PERMISSION_GRANTED
        }
    }

    /**
     * request all permission
     */
    @RequiresApi(Build.VERSION_CODES.M)
    infix fun AppCompatActivity.reqPermission(permissions : Array<String>){
        this.requestPermissions(permissions, Const.REQCODE)
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
     * work in android-11
     */
    infix fun Context.jpegCompressor(bitmap: Bitmap) : File {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapData = bos.toByteArray()
        val locpath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val filex = File(locpath!!,"NGOPIDEV_${System.currentTimeMillis()}_img.jpg")

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
     * get app version
     */
    fun Context.hasVersion() : String{
        val info = packageManager.getPackageInfo(packageName, 0)
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
    fun Context.doRetrofitExecute() : APIServices{

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
    fun Context.doRetrofitExecuteJSON() : APIServices{
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
    fun doGetDeviceID(ctx : Context) =  Settings.Secure.getString(ctx.contentResolver, Settings.Secure.ANDROID_ID)


    /**
     * usinf for getdevicename
     */
    fun getDeviceName() = Build.DEVICE +" "+ Build.BRAND +" "+ Build.MANUFACTURER

}