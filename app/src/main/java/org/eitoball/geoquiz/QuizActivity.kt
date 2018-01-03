package org.eitoball.geoquiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.time.Duration

class QuizActivity : AppCompatActivity() {
    var mTrueButton: Button? = null
    var mFalseButton: Button? = null
    var mNextButton: Button? = null
    var mQuestionTextView: TextView? = null
    val mQuestionBank = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, false),
            Question(R.string.question_asia, false)
    )
    var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mQuestionTextView = findViewById(R.id.question_text_view) as TextView

        mTrueButton = findViewById(R.id.true_button) as Button
        mTrueButton?.setOnClickListener { checkAnswer(true) }
        mFalseButton = findViewById(R.id.false_button) as Button
        mFalseButton?.setOnClickListener { checkAnswer(false) }

        mNextButton = findViewById(R.id.next_button) as Button
        mNextButton?.setOnClickListener { updateQuestion() }

        updateQuestion()
    }

    private fun updateQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
        mQuestionTextView?.setText(mQuestionBank[mCurrentIndex].textResId)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].answerType
        val messageResId = if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
