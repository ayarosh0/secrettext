package secrettext.artsemyarash.secretedittext;

import java.util.ArrayList;

/**
 * Created by artsemyarash on 1/15/18.
 */

public interface SpannedSecretEditText {
    public void fillTextColor(int start, int end, int color);
    public int getSelectionStartPosition();
    public int getSelectionEndPosition();
    public ArrayList<Boolean> getSecretCharsPositions();
}
