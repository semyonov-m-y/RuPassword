package ru.semenovmy.learning.rupassword;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String CAPS_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    private static String password;
    private static int symbolsCount;

    private CompoundButton checkCaps;
    private CompoundButton checkDigits;
    private CompoundButton checkSymbols;
    private View copyButton;
    private View copyButtonSecond;
    private View generateButton;
    private TextView generatedTextView;
    private ImageView imageView;
    private PasswordsHelper helper;
    private String[] latins;
    private TextView levelView;
    private TextView passwordLength;
    private Button reportButton;
    private TextView resultTextView;
    private String[] russians;
    private SeekBar seekBar;
    private EditText sourceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkCaps = findViewById(R.id.check_caps);
        checkDigits = findViewById(R.id.check_digits);
        checkSymbols = findViewById(R.id.check_symbols);
        copyButton = findViewById(R.id.button_copy_password);
        copyButtonSecond = findViewById(R.id.second_button_copy_password);
        generateButton = findViewById(R.id.button_generate_pass);
        generatedTextView = findViewById(R.id.generated_text);
        imageView = findViewById(R.id.password_value_scale);
        levelView = findViewById(R.id.password_value);
        passwordLength = findViewById(R.id.password_length);
        reportButton = findViewById(R.id.button_send_pass);
        resultTextView = findViewById(R.id.result_text);
        seekBar = findViewById(R.id.seek_bar);
        sourceEditText = findViewById(R.id.edit_source);

        russians = getResources().getStringArray(R.array.russians);
        latins = getResources().getStringArray(R.array.latins);

        helper = new PasswordsHelper(russians, latins);

        copyPassword(copyButton);
        copyPassword(copyButtonSecond);

        editText();

        setPasswordLength();

        generatePassword();

        sendPassword();
    }

    public void copyPassword(View view) {
        view.setEnabled(false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                if (manager == null){
                    return;
                }

                if (v.equals(copyButton)) {
                    manager.setPrimaryClip(
                            ClipData.newPlainText(
                                    getString(R.string.clipboard_title), resultTextView.getText().toString())
                    );
                }

                if (v.equals(copyButtonSecond)) {
                    manager.setPrimaryClip(
                            ClipData.newPlainText(
                                    getString(R.string.clipboard_title), generatedTextView.getText().toString())
                    );
                }

                Toast.makeText(MainActivity.this, R.string.text_copied, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editText() {
        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultTextView.setText(helper.convert(s));
                copyButton.setEnabled(s.length() > 0);

                int chars_count = s.length();
                if (chars_count <= 2) {
                    levelView.setText(R.string.bad_password);
                    imageView.getDrawable().setLevel(1500);
                } else if (chars_count > 2 && chars_count <= 4) {
                    levelView.setText(R.string.weak_password);
                    imageView.getDrawable().setLevel(4000);
                } else if (chars_count > 4 && chars_count <= 6) {
                    levelView.setText(R.string.good_password);
                    imageView.getDrawable().setLevel(8000);
                } else if (chars_count > 4) {
                    levelView.setText(R.string.excellent_password);
                    imageView.getDrawable().setLevel(10000);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void generatePassword() {
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyButtonSecond.setEnabled(true);
                boolean useCaps = checkCaps.isChecked();
                boolean useDigits = checkDigits.isChecked();
                boolean useSymbols = checkSymbols.isChecked();

                if (useCaps && useDigits && useSymbols) {
                    password = helper.generatePasswordConstructor( LETTERS + CAPS_LETTERS + SPECIAL_CHARS + NUMERIC, symbolsCount);
                    generatedTextView.setText(password);
                } else if (useCaps && useDigits) {
                    password = helper.generatePasswordConstructor( LETTERS + NUMERIC + CAPS_LETTERS, symbolsCount);
                    generatedTextView.setText(password);
                } else if (useCaps && useSymbols) {
                    password = helper.generatePasswordConstructor( LETTERS + SPECIAL_CHARS + CAPS_LETTERS, symbolsCount);
                    generatedTextView.setText(password);
                } else if (useCaps && useSymbols) {
                    password = helper.generatePasswordConstructor( LETTERS + SPECIAL_CHARS + NUMERIC, symbolsCount);
                    generatedTextView.setText(password);
                } else if (useCaps) {
                    password = helper.generatePasswordConstructor(LETTERS + CAPS_LETTERS, symbolsCount);
                    generatedTextView.setText(password);
                } else if (useDigits) {
                    password = helper.generatePasswordConstructor(LETTERS + NUMERIC, symbolsCount);
                    generatedTextView.setText(password);
                } else if (useSymbols) {
                    password = helper.generatePasswordConstructor(LETTERS + SPECIAL_CHARS, symbolsCount);
                    generatedTextView.setText(password);
                } else {
                    password = helper.generatePasswordConstructor(LETTERS, symbolsCount);
                    generatedTextView.setText(password);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.passwords_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        resultTextView.setText("");
        sourceEditText.setText("");
        generatedTextView.setText("");
        return true;
    }

    public void sendPassword() {
        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, generatedTextView.getText().toString());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_password));
                startActivity(i);
            }
        });
    }

    public void setPasswordLength() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordLength.setText(getResources().getQuantityString(R.plurals.symbols_count, progress, progress));
                symbolsCount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
