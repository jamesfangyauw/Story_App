package com.intermediate.substory.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.intermediate.substory.R

class BtnCustom : AppCompatButton {

    private lateinit var enableBg: Drawable
    private lateinit var disableBg: Drawable
    private var colorTxt: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isEnabled) enableBg else disableBg
        setTextColor(colorTxt)
        textSize = 13f
        gravity = Gravity.CENTER
    }

    private fun init() {
        colorTxt = ContextCompat.getColor(context, android.R.color.background_light)
        enableBg = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disableBg =
            ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}