package secrettext.artsemyarash.secretedittext;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by artsemyarash on 1/15/18.
 */

public class SecretSelectionActionModeCallback implements ActionMode.Callback {
    private SpannedSecretEditText view;

    public SecretSelectionActionModeCallback(SpannedSecretEditText view) {
        this.view = view;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.extended_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        MenuItem hide = menu.findItem(R.id.hide_selected);
        MenuItem show = menu.findItem(R.id.show_selected);
        hide.setVisible(isHideAction());
        show.setVisible(!isHideAction());
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.hide_selected || item.getItemId() == R.id.show_selected) {
            boolean isHideAction = isHideAction();
            int startSelection = view.getSelectionStartPosition();
            int endSelection = view.getSelectionEndPosition();
            for (int i = 0; i < endSelection - startSelection; i++) {
                view.getSecretCharsPositions().set(startSelection + i, isHideAction);
            }
            view.fillTextColor(startSelection, endSelection, isHideAction ? Color.RED : Color.BLACK);
            mode.finish();
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    private boolean isHideAction() {
        boolean isHideAction = false;
        int startSelection = view.getSelectionStartPosition();
        int endSelection = view.getSelectionEndPosition();
        for (int i = startSelection; i < endSelection; i++) {
            isHideAction = isHideAction || !view.getSecretCharsPositions().get(i);
        }
        return isHideAction;
    }
}
