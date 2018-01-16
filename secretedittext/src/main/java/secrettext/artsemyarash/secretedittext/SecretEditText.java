package secrettext.artsemyarash.secretedittext;

import android.content.Context;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.TransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by artsemyarash on 1/15/18.
 */

public class SecretEditText extends EditText implements SpannedSecretEditText {
    private ArrayList<Boolean> secretCharsPositions;

    public SecretEditText(Context context) {
        super(context);
        init();
    }

    public SecretEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecretEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SecretEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        secretCharsPositions = new ArrayList<>();
        for (int i = 0; i < length(); i++) {
            secretCharsPositions.add(false);
        }
        this.setCustomSelectionActionModeCallback(new SecretSelectionActionModeCallback(this));
        this.addTextChangedListener(new SecretTextWatcher(this));
        //setTransformationMethod(new SecretTextTransformationMethod(secretCharsPositions));
    }


    @Override
    public void fillTextColor(int start, int end, int color) {
        SpannableString spannableString = new SpannableString(getText());
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int selectionPosition = getSelectionStart();
        setText(spannableString);
        setSelection(selectionPosition);
    }

    @Override
    public int getSelectionStartPosition() {
        return getSelectionStart();
    }

    @Override
    public int getSelectionEndPosition() {
        return getSelectionEnd();
    }

    @Override
    public ArrayList<Boolean> getSecretCharsPositions() {
        return secretCharsPositions;
    }
}
