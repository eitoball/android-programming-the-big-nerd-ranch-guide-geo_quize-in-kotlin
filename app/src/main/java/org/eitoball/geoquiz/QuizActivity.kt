package org.eitoball.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.View
import android.widget.Button
import android.widget.Toast

class QuizActivity : AppCompatActivity() {
    var mTrueButton: Button? = null
    var mFalseButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mTrueButton = findViewById(R.id.true_button) as Button
        mTrueButton?.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
        })
        mFalseButton = findViewById(R.id.false_button) as Button
        mFalseButton?.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        })
    }
}
