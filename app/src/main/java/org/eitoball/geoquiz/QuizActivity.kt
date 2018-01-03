package org.eitoball.geoquiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {
    var mTrueButton: Button? = null
    var mFalseButton: Button? = null
    var mPrevButton: ImageButton? = null
    var mNextButton: ImageButton? = null
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
        mQuestionTextView?.setOnClickListener { updateQuestion() }

        mTrueButton = findViewById(R.id.true_button) as Button
        mTrueButton?.setOnClickListener { checkAnswer(true) }
        mFalseButton = findViewById(R.id.false_button) as Button
        mFalseButton?.setOnClickListener { checkAnswer(false) }

        mPrevButton = findViewById(R.id.prev_button) as ImageButton
        mPrevButton?.setOnClickListener { updateQuestion(-1) }
        mNextButton = findViewById(R.id.next_button) as ImageButton
        mNextButton?.setOnClickListener { updateQuestion() }

        updateQuestion()
    }

    private fun updateQuestion(direction: Int = 1) {
        mCurrentIndex += direction
        if (mCurrentIndex < 0) {
            mCurrentIndex = 0
        }
        mCurrentIndex %= mQuestionBank.size
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
