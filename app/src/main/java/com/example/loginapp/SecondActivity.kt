package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.databinding.ActivitySecondBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginManager
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class SecondActivity : AppCompatActivity() {
    private lateinit var secondBinding: ActivitySecondBinding
    private lateinit var accessToken: AccessToken
    private lateinit var request : GraphRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(secondBinding.root)
        accessToken = AccessToken.getCurrentAccessToken()!!

        val request = GraphRequest.newMeRequest(
            accessToken,
            object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(
                    `object`: JSONObject?,
                    response: GraphResponse?

                ) {
                    // Application code
                    try {
                       var fullname =  `object`!!.getString("name")
                        val url = `object`.getJSONObject("picture").getJSONObject("data").getString("url")

                        secondBinding.name.text = fullname
                        Picasso.get().load(url).into(secondBinding.image)
                    }catch (e : JSONException){

                    }
                }
            })
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()


        secondBinding.logout.setOnClickListener {
            LoginManager.getInstance().logOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}

