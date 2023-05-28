package com.intermediate.substory.customview

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.intermediate.substory.R
import com.intermediate.substory.isEmailValid

class EmailCustom : AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(ctx: Context, attrs: AttributeSet, attr: Int) : super(ctx, attrs, attr) {
        init()
    }


    private fun init(){
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validEmail()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }


    private fun validEmail() {
        val emailUser = text?.trim()
        error = if (emailUser.isNullOrEmpty()) {
            resources.getString(R.string.email_required)
        } else if (!isEmailValid(emailUser.toString())) {
            resources.getString(R.string.invalid_email)
        } else {
            return
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) validEmail()
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }
}