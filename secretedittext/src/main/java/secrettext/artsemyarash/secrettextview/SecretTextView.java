package secrettext.artsemyarash.secrettextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;

import secrettext.artsemyarash.secretedittext.R;

/**
 * Created by artsemyarash on 1/17/18.
 */

public class SecretTextView extends TextView {
    private ArrayList<Boolean> secretSymbols;
    private String originalText;
    private String secretMask;

    public SecretTextView(Context context) {
        super(context);
    }

    public SecretTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SecretTextView,
                0, 0);
        try {
            secretMask = a.getString(R.styleable.SecretTextView_secret_mask);
        } finally {
            a.recycle();
        }
    }

    public void setSecretChars(ArrayList<Boolean> secretSymbols) {
        this.secretSymbols = secretSymbols;
        makeSecret();
    }

    private void makeSecret() {
        ArrayList<Pair<Integer, Integer>> intervals = new ArrayList<>();
        int i = 0;
        while (i < secretSymbols.size()) {
            if (secretSymbols.get(i)) {
                intervals.add(new Pair<>(i, i = findEndOfInterval(i)));
            } else {
                i++;
            }
        }
        if (TextUtils.isEmpty(originalText)) {
            originalText = getText().toString();
        }
        StringBuilder sb = new StringBuilder(getText());
        if (intervals.size() > 0) {
            for (i = intervals.size() - 1; i >= 0; i--) {
                Pair<Integer, Integer> interval = intervals.get(i);
                sb.replace(interval.first, interval.second, secretMask);
            }
        }
        setText(sb.toString());
    }

    public void showOriginalText() {
        setText(originalText);
    }

    private int findEndOfInterval(int start) {
        if (start == secretSymbols.size() || !secretSymbols.get(start))
            return start;
        return findEndOfInterval(start + 1);
    }


    public void setSecretMask(String secretMask) {
        this.secretMask = secretMask;
    }
}
