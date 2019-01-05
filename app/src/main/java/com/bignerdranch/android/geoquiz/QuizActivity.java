package com.bignerdranch.android.geoquiz;

import android.annotation.SuppressLint;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String INDEX_KEY = "index";


    private TextView mQuestionTextView;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;


    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(INDEX_KEY, 0);

        mQuestionTextView = findViewById(R.id.textView);

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> {
            checkAnswer(true);
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> {
            checkAnswer(false);
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(v -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        });

        updateQuestion();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) { // before onStop()
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(INDEX_KEY, mCurrentIndex);
    }

    private void updateQuestion() {
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        findViewById(R.id.false_button).setEnabled(true);
        mTrueButton.setEnabled(true);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean rightAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        if (userPressedTrue == rightAnswer) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
