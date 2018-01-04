package org.eitoball.geoquiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION.SDK
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class CheatActivity : AppCompatActivity() {
    private var mAnswerIsTrue: Boolean = false
    private var mCheatToken: Int = 0
    private var mAnswerTextView: TextView? = null
    private var mShowAnswerButton: Button? = null
    private var mCheatTokenTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        mCheatToken = intent.getIntExtra(EXTRA_CHEAT_TOKEN, 0)

        mAnswerTextView = findViewById(R.id.answer_text_view) as TextView

        mCheatTokenTextView = findViewById(R.id.cheat_token_text_view) as TextView
        mCheatTokenTextView?.text = mCheatToken.toString()


        mShowAnswerButton = findViewById(R.id.show_answer_button) as Button
        if (mCheatToken <= 0) {
            mShowAnswerButton?.isEnabled = false
        } else {
            mShowAnswerButton?.setOnClickListener() {
                mCheatToken -= 1
                mCheatTokenTextView?.text = mCheatToken.toString()
                mAnswerTextView?.text = if (mAnswerIsTrue) {
                    "True"
                } else {
                    "False"
                }
                setAnswerShownResult(mCheatToken)

                val cx = mShowAnswerButton?.width ?: 2 / 2
                val cy = mShowAnswerButton?.height ?: 2 / 2
                val radius = (mShowAnswerButton?.width ?: 1).toFloat()
                if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

        val apiLevelText = findViewById(R.id.api_level_text) as TextView
        apiLevelText.text = "API Level ${Build.VERSION.SDK_INT}"
    }

    private fun setAnswerShownResult(cheatToken: Int = 0) {
        val data = Intent()
        data.putExtra(EXTRA_CHEAT_TOKEN, cheatToken)
        setResult(RESULT_OK, data)
    }

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE = "org.eitoball.geoquiz.answer_is_true"
        private const val EXTRA_CHEAT_TOKEN = "org.eitoball.geoquiz.cheat_token"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatToken: Int = 0): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            intent.putExtra(EXTRA_CHEAT_TOKEN, cheatToken)
            return intent
        }

        fun cheatToken(result: Intent) = result.getIntExtra(EXTRA_CHEAT_TOKEN, 3)
    }
}
