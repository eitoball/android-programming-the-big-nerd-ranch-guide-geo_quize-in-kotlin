package org.eitoball.geoquiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {
    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"

    private var mTrueButton: Button? = null
    private var mFalseButton: Button? = null
    private var mNextButton: Button? = null
    private var mQuestionTextView: TextView? = null
    private val mQuestionBank = listOf(
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
        Log.d(TAG, "onCreate(Bundle) called")
        setContentView(R.layout.activity_quiz)

        mCurrentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        mQuestionTextView = findViewById(R.id.question_text_view) as TextView

        mTrueButton = findViewById(R.id.true_button) as Button
        mTrueButton?.setOnClickListener { checkAnswer(true) }
        mFalseButton = findViewById(R.id.false_button) as Button
        mFalseButton?.setOnClickListener { checkAnswer(false) }

        mNextButton = findViewById(R.id.next_button) as Button
        mNextButton?.setOnClickListener { updateQuestion() }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState?.putInt(KEY_INDEX, mCurrentIndex)
    }
    private fun updateQuestion() {
        Log.d(TAG, "Current question index: $mCurrentIndex")
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
        try {
            val question = mQuestionBank[mCurrentIndex]
            mQuestionTextView?.setText(question.textResId)
            listOf(mTrueButton, mFalseButton).forEach {
                it?.isEnabled = !question.answered
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.e(TAG, "Index was out of bounds", e)
        }
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        var question = mQuestionBank[mCurrentIndex]
        question.answered = true
        val messageResId = if (userPressedTrue == question.answerType) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
