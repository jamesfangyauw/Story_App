package com.intermediate.substory.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.intermediate.substory.R

class PasswordCustom : AppCompatEditText, View.OnTouchListener {
    private lateinit var icon: Drawable


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_24) as Drawable
        transformationMethod = PasswordTransformationMethod.getInstance()
        setOnTouchListener(this)
        showIcon()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                showIcon()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validationPass()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun showIcon() {
        setButtonDrawables(endOfTheText = icon)
    }


    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )

    }

    private fun validationPass() {
        val pass = text?.trim()
        error = when {
            pass.isNullOrEmpty() -> {
                resources.getString(R.string.password_required)
            }
            pass.length < 8 -> {
                resources.getString(R.string.pass_length)
            }
            else -> {
                return
            }
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) validationPass()
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isVisibilityButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (icon.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isVisibilityButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - icon.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isVisibilityButtonClicked = true
                }
            }
            if (isVisibilityButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        icon = ContextCompat.getDrawable(
                            context,
                            com.google.android.material.R.drawable.design_ic_visibility_off
                        ) as Drawable
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        icon = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_visibility_24
                        ) as Drawable
                        when {
                            text != null -> transformationMethod =
                                PasswordTransformationMethod.getInstance()
                        }
                        showIcon()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false

    }
}