package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String ANSWER_SHOWN_KEY = "answer shown";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String TAG = "CheatActivity";

    Button cheatButton;
    TextView answerTextView;
    Boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        answerTextView = findViewById(R.id.answer_text_view);
        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean(ANSWER_SHOWN_KEY);
            if (mIsCheater) {
                answerTextView.setText((getIntent()
                        .getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false))
                        ? R.string.true_button : R.string.false_button);
                setAnswerShownResult(mIsCheater);
            }
        }


        (cheatButton = findViewById(R.id.show_answer_button)).setOnClickListener(v -> {
            answerTextView.setText((getIntent()
                    .getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false))
                    ? R.string.true_button : R.string.false_button);
            setAnswerShownResult(true);
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
        mIsCheater = isAnswerShown;
    }

    public static Intent newIntent(Context context, boolean answerIsTrue) {
        return new Intent(context, CheatActivity.class).putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putBoolean(ANSWER_SHOWN_KEY, mIsCheater);
    }
}
