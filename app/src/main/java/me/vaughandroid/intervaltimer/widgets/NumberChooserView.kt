package me.vaughandroid.intervaltimer.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_number_chooser.view.*
import me.vaughandroid.intervaltimer.R

class NumberChooserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_number_chooser, this, true)

        incrementTextView.setOnClickListener { incrementListener?.invoke(value) }
        decrementTextView.setOnClickListener { decrementListener?.invoke(value) }
    }

    var title: String
        get() = titleTextView.text.toString()
        set(value) {
            titleTextView.text = value
        }

    var value: String
        get() = valueTextView.text.toString()
        set(value) {
            valueTextView.text = value
        }

    var incrementListener: ((String) -> Unit)? = null

    var decrementListener: ((String) -> Unit)? = null

}