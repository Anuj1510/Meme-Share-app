package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

// data agr kisi server mai hai aur mujhe apne app me lana ho to mai API ka use karunga or bhot source pe free API mill sakti hai

class MainActivity : AppCompatActivity() {

    var currenturl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme(){

        progressbar.visibility = View.VISIBLE // progress bar visible hoga jub function call ho raha hai
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            { response -> // ye response dega jiski request ki hai
                currenturl = response.getString("url")

                Glide.with(this).load(currenturl).listener(object : RequestListener<Drawable>{  //ye pura code progress bar lane ke liye hai -> override waale dono

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }
                }).into(MemeImageView)

            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
                // ye error dega agr internet error aya ya phir api kharab hui
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun ShareMeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain" // type matlab hame kis type ka cheez share krna hai aur ye use karne ke baad app mai automatically ussi type ke apps aa jayenge jiske through wo type share ho sakta hai for ex:- image ya music ke liye kuch aur hoga
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this cool meme I got for redit $currenturl")
        val chooser = Intent.createChooser(intent,"Share this meme using....") // chooser isliye ki hame kiske sath share krna hai like insta ya whtsapp
        startActivity(chooser)

    }
    fun NextMeme(view: View) {
        loadMeme()


    }
}