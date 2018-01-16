package secrettext.artsemyarash.secrettext;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import secrettext.artsemyarash.secretedittext.SecretEditText;

public class MainActivity extends AppCompatActivity {
    SecretEditText secretEditText;
    Button button;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secretEditText = findViewById(R.id.edittext);
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);
        text.setTransformationMethod(new SecretTextTransformationMethod(secretEditText.getSecretCharsPositions()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(secretEditText.getText().toString());
            }
        });

    }

    public class SecretTextTransformationMethod implements TransformationMethod {
        ArrayList<Boolean> secretCharsPositions;
        public SecretTextTransformationMethod(ArrayList<Boolean> secretCharsPositions) {
            this.secretCharsPositions = secretCharsPositions;
        }

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new SecretCharSequence(source);
        }

        @Override
        public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {

        }


        private class SecretCharSequence implements CharSequence {
            private final char DOT = '\u2022';
            private CharSequence mSource;

            public SecretCharSequence(CharSequence mSource) {
                this.mSource = mSource;
            }

            @Override
            public int length() {
                return mSource.length();
            }

            @Override
            public char charAt(int index) {
                if (index < secretCharsPositions.size() && secretCharsPositions.get(index)) {
                    return DOT;
                } else {
                    return mSource.charAt(index);
                }
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }
        }
    }
}
