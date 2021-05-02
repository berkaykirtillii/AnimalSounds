package com.kirto.animalsounds

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_third_party.*
import java.util.*


class ThirdParty : AppCompatActivity() {
    var mediaPlayer: MediaPlayer?=null
    lateinit var sharedPreferences: SharedPreferences
    var kaydedilenskor: Int?= null
    var highscore: Int?= null
    lateinit var bTTs: TextToSpeech
    private lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_party)

        sharedPreferences=this.getSharedPreferences("com.kirto.animalsounds", Context.MODE_PRIVATE)
        kaydedilenskor= sharedPreferences.getInt("skor",0)
        textSkor3.text = "SCORE ${kaydedilenskor}"

        mediaPlayer = MediaPlayer.create(this,R.raw.kopeksesi)


        timer=object : CountDownTimer(16000,1000){
            override fun onTick(millisUntilFinished: Long) {
                if ((millisUntilFinished/1000)-2 >= 0){
                    textKalan3.text= "Time : ${(millisUntilFinished/1000)-2} sec"}
            }

            override fun onFinish() {

                val intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.start()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        timer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
    override fun onStart() {
        super.onStart()
        bTTs= TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status!= TextToSpeech.ERROR)
            { bTTs.language= Locale.ENGLISH}
        })
        highscore= sharedPreferences.getInt("highskor",0)
        textHigh3.text= "High Score ${highscore}"
    }

    fun playlionfordog(view: View){
        bTTs.speak("Sorry, you chose the wrong animal.Press the button to start over.",TextToSpeech.QUEUE_FLUSH,null)

        val cikisMesaji = AlertDialog.Builder(this)
        cikisMesaji.setMessage("Sorry, you chose the wrong animal.Press the button to start over.")
        cikisMesaji.setPositiveButton("Start Again", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        cikisMesaji.show()
    }
    fun playdog3(view: View) {


        val kullaniciSkor = 3
        sharedPreferences.edit().putInt("skor", kullaniciSkor).apply()
        textSkor3.text = "SCORE ${kullaniciSkor}"
        if (kullaniciSkor > highscore!!) {
            sharedPreferences.edit().putInt("highskor", kullaniciSkor).apply()
        }
        bTTs.speak("You took to a new level, well done!", TextToSpeech.QUEUE_FLUSH,null)
        object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                val intent = Intent(applicationContext,FourthParty::class.java)
                startActivity(intent)
                finish()
            }

        }.start()
    }
    fun listenAgain3(view: View) {
        mediaPlayer?.start()
    }

}