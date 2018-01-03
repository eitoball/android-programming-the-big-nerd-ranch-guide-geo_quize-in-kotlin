package org.eitoball.geoquiz

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
