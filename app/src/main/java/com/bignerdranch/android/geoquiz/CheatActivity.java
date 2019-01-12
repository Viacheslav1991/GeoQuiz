package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String ANSWER_SHOWN_KEY = "answer shown";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String TAG = "CheatActivity";

    Button mShowAnswerButton;
    TextView answerTextView;
    TextView mApiVersionTextView;
    Boolean mIsCheater;

    @SuppressLint("DefaultLocale")
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


        (mShowAnswerButton = findViewById(R.id.show_answer_button)).setOnClickListener(v -> {
            answerTextView.setText((getIntent()
                    .getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false))
                    ? R.string.true_button : R.string.false_button);
            setAnswerShownResult(true);

            int cx = mShowAnswerButton.getWidth() / 2;
            int cy = mShowAnswerButton.getHeight() / 2;
            float radius = mShowAnswerButton.getWidth();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animator animator = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mShowAnswerButton.setVisibility(View.INVISIBLE);
                    }
                });
                animator.start();
            }else
                mShowAnswerButton.setVisibility(View.INVISIBLE);

        });
        mApiVersionTextView = findViewById(R.id.api_version_text_view);
        mApiVersionTextView.setText(String.format("Api version %d", Build.VERSION.SDK_INT));

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
