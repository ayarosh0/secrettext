package secrettext.artsemyarash.secretedittext;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by artsemyarash on 1/15/18.
 */

public class SecretTextWatcher implements TextWatcher {
    private SpannedSecretEditText view;

    public SecretTextWatcher(SpannedSecretEditText view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //add or insert new symbols
        if (before < count) {
            for (int i = 0; i < count - before; i++) {
                boolean isNeedToSecretSymbol = isNeedToSecretSymbol(s, start);
                view.getSecretCharsPositions().add(start, isNeedToSecretSymbol);
                view.fillTextColor(start, start + count, isNeedToSecretSymbol ? Color.RED : Color.BLACK);
            }
        }
        //remove symbols
        if (before > count) {
            for (int i = 0; i < before - count; i++) {
                view.getSecretCharsPositions().remove(start);
            }
        }
        //replace symbols
        if (before == count && !s.toString().equals(((EditText)view).getText().toString())) {
            for (int i = 0; i < before - count; i++) {
                boolean isNeedToSecretSymbol = isNeedToSecretSymbol(s, start);
                view.getSecretCharsPositions().set(start + i, isNeedToSecretSymbol);
                view.fillTextColor(start, start + count, isNeedToSecretSymbol ? Color.RED : Color.BLACK);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private boolean isNeedToSecretSymbol(CharSequence s, int start) {
        if (view.getSecretCharsPositions().size() > 0) {
            if (start == 0) {
                return view.getSecretCharsPositions().get(1);
            } else if (start == view.getSecretCharsPositions().size()) {
                boolean isNewFirstSymbolSpace = (s.charAt(start) == ' ');
                return view.getSecretCharsPositions().get(view.getSecretCharsPositions().size() - 1) && !isNewFirstSymbolSpace;
            } else {
                return view.getSecretCharsPositions().get(start - 1) || view.getSecretCharsPositions().get(start + 1);
            }
        } else {
            return false;
        }

    }
}
