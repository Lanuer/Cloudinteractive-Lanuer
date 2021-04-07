package com.example.cloudinteractivelanuer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.cloudinteractivelanuer.model.DataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class SecondActivity : AppCompatActivity() {
    private lateinit var gridView: GridView
    private val apiService = AppClientManager.client.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById()
        setListener()
        init()
    }

    fun findViewById() {
        gridView = findViewById(R.id.gridView)
    }

    fun setListener() {

    }

    fun init() {
//        var items = ArrayList<Map<String, Any>>()
        apiService.getData().enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
                var list = response.body()
                for (d in list!!) {
                    var item = HashMap<String, Any>()
                    item["id"] = d.id.toString()
                    item["title"] = d.title.toString()
                    item["url"] = d.thumbnailUrl.toString()
                    items.add(item)
                }
                /*for (i in items) {
                    DownLoadImageTask().execute(i["url"].toString())
                }*/

                gridView.adapter = MyAdapter(this@SecondActivity, items, itemsBitmap)
                gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    gridView.post{
                        var intent = Intent(this@SecondActivity, EndActivity::class.java)
                        /*intent.putExtra("id", items[position]["id"].toString())
                        intent.putExtra("title", items[position]["title"].toString())
                        intent.putExtra("url", items[position]["url"])*/
                        intent.putExtra("position", position.toString())
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {

            }
        })
    }

    private class DownLoadImageTask() :
        AsyncTask<String, Void, Bitmap>() {

        var urlOfImage: String? = null

        override fun doInBackground(vararg urls: String): Bitmap? {
            if (urlOfImage == null) {
                urlOfImage = urls[0]
            }

            return try {
                val inputStream = URL(urlOfImage).openStream()
                /*val conn = URL(urlOfImage).openConnection() as HttpURLConnection
                conn.connect()
                val inputStream = conn.inputStream*/
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("testlog", e.toString())
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            var bit: Bitmap? = null
            if (result != null) {
                bit = result
                Log.e("testlog", "ok")
            } else {
                Log.e("testlog", "error 1")
                if (!urlOfImage!!.contains(".png")) {
                    if (urlOfImage!!.contains(".jpg")) {
                        urlOfImage = urlOfImage!!.replace(".jpg", ".png")
                    } else {
                        urlOfImage = urlOfImage+".jpg"
                    }
                    DownLoadImageTask().execute(urlOfImage)
                }
                Log.e("testlog", "url：" + urlOfImage)
                Log.e("testlog", "error 2")
            }
            itemsBitmap.add(bit)
        }
    }

    companion object {
        val url: String = "https://jsonplaceholder.typicode.com/photos/"
        var items = ArrayList<Map<String, Any>>()
        var itemsBitmap = ArrayList<Bitmap?>()

        /*fun getBitmapFromURL(src: String): Bitmap? {
            try {
                val url = URL(src)
                val conn = url.openConnection() as HttpURLConnection
                conn.connect()
                val input = conn.inputStream
                return BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }*/

        /*fun getBitmapFromURL(src: String?): Bitmap? {
            return try {
                val url = URL(src)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.setDoInput(true)
                connection.connect()
                val input: InputStream = connection.getInputStream()
                BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                null
            }
        }*/
    }
}