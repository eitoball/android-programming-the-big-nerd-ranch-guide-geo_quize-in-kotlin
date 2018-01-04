package org.eitoball.geoquiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextView

class CheatActivity : AppCompatActivity() {
    private var mAnswerIsTrue: Boolean = false
    private var mAnswerTextView: TextView? = null
    private var mShowAnswerButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        mAnswerTextView = findViewById(R.id.answer_text_view) as TextView

        mShowAnswerButton = findViewById(R.id.show_answer_button) as Button
        mShowAnswerButton?.setOnClickListener() {
            if (mAnswerIsTrue) {
                mAnswerTextView?.setText(R.string.true_button)
            } else {
                mAnswerTextView?.setText(R.string.false_button)
            }
            setAnswerShownResult(true)

            val cx = mShowAnswerButton?.width ?: 2 / 2
            val cy = mShowAnswerButton?.height ?: 2 / 2
            val radius = (mShowAnswerButton?.width ?: 1).toFloat()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0F)
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        mShowAnswerButton?.visibility = View.INVISIBLE
                    }
                })
                anim.start()
            } else {
                mShowAnswerButton?.visibility = View.INVISIBLE
            }
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(RESULT_OK, data)
    }

    companion object {
        private val EXTRA_ANSWER_IS_TRUE = "org.eitoball.geoquiz.answer_is_true"
        private val EXTRA_ANSWER_SHOWN = "org.eitoball.geoquiz.answer_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }

        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        }
    }
}
