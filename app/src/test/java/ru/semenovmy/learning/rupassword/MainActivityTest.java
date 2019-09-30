package ru.semenovmy.learning.rupassword;


import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class MainActivityTest {

    private MainActivity mainActivity;
    private View copyButton;
    private View copyButtonSecond;
    private TextView generatedTextView;
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    @Before
    public void setUp() throws Exception {
        /*mainActivity = Mockito.mock(MainActivity.class);
        copyButtonSecond = Mockito.mock(View.class);
        generatedTextView = Mockito.mock(TextView.class);
        Mockito.verify(generatedTextView).setText("ladfhlsgflg");*/
    }

    @Test(expected = NullPointerException.class)
    public void copyPasswordForNull() {
        mainActivity.copyPassword(null);
    }

    @Test
    public void copyPasswordForNotNull() {

    }

    @Test
    public void editText() {

    }

    @Test
    public void generatePassword() {

    }

    @Test
    public void setPasswordLength() {
    }
}