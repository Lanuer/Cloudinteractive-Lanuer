package com.example.cloudinteractivelanuer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.FieldPosition
import kotlin.concurrent.thread

class EndActivity : AppCompatActivity() {

    private lateinit var rl: RelativeLayout
    private lateinit var ivBg: ImageView
    private lateinit var tvId: TextView
    private lateinit var tvTitle: TextView
    private lateinit var url: String
    private lateinit var id: String
    private lateinit var title: String

    private lateinit var position: String
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        findViewById()
        setListener()
        init()
    }

    fun findViewById() {
        rl = findViewById(R.id.rl)
        ivBg = findViewById(R.id.iv_bg)
        tvId = findViewById(R.id.tv_id)
        tvTitle = findViewById(R.id.tv_title)
    }

    fun setListener() {
        rl.setOnClickListener { finish() }
    }

    fun init() {
        var intent = intent
        if (intent != null) {
//            url = intent.getStringExtra("url").toString()
//            id = intent.getStringExtra("id").toString()
//            title = intent.getStringExtra("title").toString()
            position = intent.getStringExtra("position").toString()
            url = SecondActivity.items[position.toInt()]["url"].toString()
            id = SecondActivity.items[position.toInt()]["id"].toString()
            title = SecondActivity.items[position.toInt()]["title"].toString()

//            DownLoadImageTask(ivBg).execute(url)
//            ivBg.setImageBitmap(SecondActivity.itemsBitmap.get(position))
            tvId.text = id
            tvTitle.text = title
            Log.e("testlog", url)

//            getBitMap(url + ".w3c")
        }
    }

    fun getBitMap(urlStr: String) {
        Glide.with(this).load(urlStr).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                if (!urlStr.contains(".gif")) {
                    if (urlStr.contains(".w3c")) {
                        url = url.replace(".w3c", ".webp")
                        handler.post(Runnable {
                            getBitMap(url)
                        })
                        Log.e("testlog", "2")
                    } else if (urlStr.contains(".webp")) {
                        url = url.replace(".webp", ".jpg")
                        handler.post(Runnable {
                            getBitMap(url)
                        })
                        Log.e("testlog", "3")
                    } else if (urlStr.contains(".jpg")) {
                        url = url.replace(".jpg", ".png")
                        handler.post(Runnable {
                            getBitMap(url)
                        })
                        Log.e("testlog", "4")
                    } else if (urlStr.contains(".png")) {
                        url = url.replace(".png", ".jpeg")
                        handler.post(Runnable {
                            getBitMap(url)
                        })
                        Log.e("testlog", "5")
                    } else if (urlStr.contains("jpeg")) {
                        url = url.replace(".jpeg", ".bmp")
                        handler.post(Runnable {
                            getBitMap(url)
                        })
                        Log.e("testlog", "6")
                    } else if (urlStr.contains("bmp")) {
                        url = url.replace(".bmp", ".gif")
                        handler.post(Runnable {
                            getBitMap(url)
                        })
                        Log.e("testlog", "7")
                    } else {
                        url += ".w3c"
                        handler.post(Runnable {
                            getBitMap(url)
                        })
                        Log.e("testlog", "1")
                    }
                } else {
                    Log.e("testlog", "8")
                }
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }).into(ivBg)
    }

    companion object {
        class DownLoadImageTask(internal val imageView: ImageView) :
            AsyncTask<String, Void, Bitmap?>() {

            var urlOfImage: String? = null

            override fun doInBackground(vararg urls: String): Bitmap? {
                if (urlOfImage == null) {
                    urlOfImage = urls[0]
                }

                return try {
//                val inputStream = URL(urlOfImage).openStream()
                    val conn = URL(urlOfImage).openConnection() as HttpURLConnection
                    conn.connect()
                    val inputStream = conn.inputStream
                    BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("testlog", e.toString())
                    null
                }
            }

            override fun onPostExecute(result: Bitmap?) {
                if (result != null) {
                    imageView.setImageBitmap(result)
                    Log.e("testlog", "ok")
                } else {
                    Log.e("testlog", "error 1")
                    if (!urlOfImage!!.contains(".png")) {
                        if (urlOfImage!!.contains(".jpg")) {
                            urlOfImage = urlOfImage!!.replace(".jpg", ".png")
                        } else {
                            urlOfImage = urlOfImage+".jpg"
                        }
                        DownLoadImageTask(imageView).execute(urlOfImage)
                    }
                    Log.e("testlog", "url：" + urlOfImage)
                    Log.e("testlog", "error 2")
                }
            }
        }
    }
}