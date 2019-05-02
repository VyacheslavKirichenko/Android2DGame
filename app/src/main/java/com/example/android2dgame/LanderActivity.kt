package com.example.android2dgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_lander.*
import kotlinx.android.synthetic.main.activity_lander.view.*

class LanderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lander)

    }


    fun playClick(view: View) {
        var play = findViewById(R.id.play_button) as Button
        play.isClickable=false
        play.visibility= View.INVISIBLE

        mycanvas.startGame()

    }

    fun stopClick(view: View){
        var play = findViewById(R.id.play_button) as Button
        play.isClickable=true
        play.visibility= View.VISIBLE

        mycanvas.stopGame()
    }
}
