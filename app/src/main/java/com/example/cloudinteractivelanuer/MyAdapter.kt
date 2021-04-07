package com.example.cloudinteractivelanuer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class MyAdapter(var context: Context, var datas: ArrayList<Map<String, Any>>, var bit: ArrayList<Bitmap?>) : BaseAdapter(){

    private var picture: Bitmap? = null

    inner class MyHolder(){
        lateinit var ivBg : ImageView
        lateinit var tvId : TextView
        lateinit var tvTitle : TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View ?= null
        var myHolder:MyHolder ?= null

        if(convertView == null){
            myHolder = MyHolder()
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
            myHolder.ivBg = view.findViewById(R.id.iv_bg)
            myHolder.tvId = view.findViewById(R.id.tv_id)
            myHolder.tvTitle = view.findViewById(R.id.tv_title)
            view.tag = myHolder
        }else{
            view = convertView
            myHolder = view.tag as MyHolder
        }

        myHolder.tvId.setText(datas[position]["id"].toString())
        myHolder.tvTitle.setText(datas[position]["title"].toString())
//        EndActivity.Companion.DownLoadImageTask(myHolder.ivBg).execute(datas.get(position)["url"].toString())
//        myHolder.ivBg.setImageBitmap(bit[position])
//        Log.e("testlog", bit.size.toString())

//        myHolder.ivBg.setImageBitmap(SecondActivity.getBitmapFromURL(datas.get(position)["url"].toString()))
        /*thread {
            myHolder.ivBg.post {
//            var bitmap = SecondActivity.getBitmapFromURL(datas.get(position)["url"].toString())
                var bitmap = getBitmapFromURL("https://via.placeholder.com/150/92c952.jpg")
                myHolder.ivBg.setImageBitmap(bitmap)
            }
        }*/
        /*Thread(Runnable {
            picture = loadPicture("https://via.placeholder.com/150/92c952.jpg")
            myHolder.ivBg.setImageBitmap(picture)
        }).start()*/

//        myHolder.ivBg.setImageResource(android.R.color.holo_red_light)

        return view!!
    }

    override fun getItem(position: Int): Any {
        //获取指定位置(position)上的item对象，通常不需要修改
        return datas.get(position)
    }

    override fun getItemId(position: Int): Long {
        // 获取指定位置(position)上的item的id，通常不需要修改
        return position.toLong()
    }

    override fun getCount(): Int {
        //返回一个整数,就是要在listview中现实的数据的条数
        return datas.size
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val url = URL(src)
            val conn = url.openConnection() as HttpURLConnection
            conn.connect()
            val input = conn.inputStream
            Log.e("testlog", "ok")

            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("testlog", "error：" + e.toString())
        }

        return null
    }

    fun loadPicture(src: String): Bitmap? {
        // 开启一个单独线程进行网络读取
        Thread(Runnable {
            var bitmap: Bitmap? = null
            try {
                val url = URL(src)
                // 根据URL 实例， 获取HttpURLConnection 实例
                var httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                // 设置读取 和 连接 time out 时间
                httpURLConnection.readTimeout = 2000
                httpURLConnection.connectTimeout = 2000
                // 获取图片输入流
                var inputStream = httpURLConnection.inputStream
                // 获取网络响应结果
                var responseCode = httpURLConnection.responseCode

                // 获取正常
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 解析图片
                    bitmap = BitmapFactory.decodeStream(inputStream)
                }
            } catch (e: IOException) { // 捕获异常 (例如网络异常)
//                Log.d(TAG, "loadPicture - error: ${e?.toString()}")
                Log.e("testlog", e.toString())
            }

            this.picture = bitmap
        }).start()
        // 返回的图片可能为空- 多线程 - 上面的线程还没跑完，已经返回 结果了
        return picture
    }
}