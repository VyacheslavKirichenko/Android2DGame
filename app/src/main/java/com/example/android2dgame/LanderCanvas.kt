package com.example.android2dgame

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lander.view.*
import stanford.androidlib.graphics.*


class LanderCanvas(context: Context, attrs: AttributeSet) : GCanvas(context, attrs) {
    companion object {
        private const val FRAMES_PER_SECOND = 30
        private const val MAX_SAVE_LANDING_VELOCITY = 5.0f
        private const val ASTEROID_VELOCITY = -12.0f
        private const val GRAVITY_ACCELERATION = .5f
        private const val THRUST_ACCELERATION = -.3f
    }

    private lateinit var rocketImage: Bitmap
    private var rocketImageThrust = ArrayList<Bitmap>()
    private var rocketBoomImage = ArrayList<Bitmap>()
    private lateinit var rocket: GSprite
    private lateinit var moonSurface: GSprite
    private var isCrush: Boolean = false
    private var countBoomFrame = 0

    override fun init() {
        inits()
    }

    private fun inits() {
        isCrush = false
        mycanvas.removeAll()
        backgroundColor = GColor.BLACK
        loadMoonSurface()
        loadRocketImageTrust()
        loadRockedBoomImage()
        loadRockedImage()

        setOnTouchListener { _, event ->
            handleTouchEvent(event)
            true
        }
    }

    private fun handleTouchEvent(event: MotionEvent) {
        if (isCrush!=true) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                rocket.accelerationY = THRUST_ACCELERATION
                rocket.bitmaps = rocketImageThrust
                rocket.framesPerBitmap = FRAMES_PER_SECOND / 6
            } else if (event.action == MotionEvent.ACTION_UP) {
                rocket.accelerationY = GRAVITY_ACCELERATION
                rocket.bitmap = rocketImage
            }
        }
    }

    private fun tick() {
        rocket.update()
        doCollisions()
        if (isCrush) {
            if (countBoomFrame == 3) {
                animationStop()
            }
            countBoomFrame++
        }
    }

    fun doCollisions() {
        if ((rocket.collidesWith(moonSurface)) && isCrush == false) {
            if (rocket.velocityY <= MAX_SAVE_LANDING_VELOCITY) {
                Toast.makeText(context, "You Win!!!", Toast.LENGTH_SHORT).show()
                backgroundColor = GColor.GREEN
                animationStop()
            } else {
                Toast.makeText(context, "You Lose!!!", Toast.LENGTH_SHORT).show()
                isCrush = true
                rocket.bitmaps = rocketBoomImage
                rocket.framesPerBitmap = 1

            }
            rocket.velocityY = 0f
            rocket.accelerationY = 0f
        }
    }

    private fun loadRockedImage() {
        rocketImage = BitmapFactory.decodeResource(resources, R.drawable.rocket0)
        rocketImage = rocketImage.scaleToWidth(this.width / 8f)

        rocket = GSprite(rocketImage)
        rocket.accelerationY = GRAVITY_ACCELERATION
        rocket.setX((mycanvas.width.toFloat() / 2) - (rocket.width / 2))
        add(rocket)
    }

    private fun loadMoonSurface() {
        var moonSurfaceImage = BitmapFactory.decodeResource(resources, R.drawable.moon_surface)
        moonSurfaceImage = moonSurfaceImage.scaleToWidth(this.width.toFloat())
        moonSurface = GSprite(moonSurfaceImage)
        moonSurface.bottomY = this.height.toFloat()
        moonSurface.collisionMarginTop = (moonSurface.height / 5)
        add(moonSurface)
    }

    private fun loadRocketImageTrust() {
        var rocketImageThrust1 = BitmapFactory.decodeResource(resources, R.drawable.rocketthrust1)
        rocketImageThrust1 = rocketImageThrust1.scaleToWidth(this.width / 8f)
        var rocketImageThrust2 = BitmapFactory.decodeResource(resources, R.drawable.rocketthrust2)
        rocketImageThrust2 = rocketImageThrust2.scaleToWidth(this.width / 8f)
        var rocketImageThrust3 = BitmapFactory.decodeResource(resources, R.drawable.rocketthrust3)
        rocketImageThrust3 = rocketImageThrust3.scaleToWidth(this.width / 8f)
        var rocketImageThrust4 = BitmapFactory.decodeResource(resources, R.drawable.rocketthrust4)
        rocketImageThrust4 = rocketImageThrust4.scaleToWidth(this.width / 8f)

        rocketImageThrust.add(rocketImageThrust1)
        rocketImageThrust.add(rocketImageThrust2)
        rocketImageThrust.add(rocketImageThrust3)
        rocketImageThrust.add(rocketImageThrust4)
    }

    private fun loadRockedBoomImage() {
        var rocketBoomImage1 = BitmapFactory.decodeResource(resources, R.drawable.rocketboom1)
        rocketBoomImage1 = rocketBoomImage1.scaleToWidth(this.width / 8f)
        var rocketBoomImage2 = BitmapFactory.decodeResource(resources, R.drawable.rocketboom2)
        rocketBoomImage2 = rocketBoomImage2.scaleToWidth(this.width / 8f)
        var rocketBoomImage3 = BitmapFactory.decodeResource(resources, R.drawable.rocketboom3)
        rocketBoomImage3 = rocketBoomImage3.scaleToWidth(this.width / 8f)
        var rocketBoomImage4 = BitmapFactory.decodeResource(resources, R.drawable.rocketboom4)
        rocketBoomImage4 = rocketBoomImage4.scaleToWidth(this.width / 8f)

        rocketBoomImage.add(rocketBoomImage1)
        rocketBoomImage.add(rocketBoomImage2)
        rocketBoomImage.add(rocketBoomImage3)
        rocketBoomImage.add(rocketBoomImage4)
    }

    fun startGame() {
        countBoomFrame = 0
        animate(FRAMES_PER_SECOND) {
            tick()
        }
    }

    fun stopGame() {
        animationStop()
        inits()
    }

}