package ru.semenovmy.learning.rupassword;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainActivityTest {

    @Mock
    private MainActivity mainActivity;
    @Mock
    private View copyButton;
    @Mock
    private View copyButtonSecond;
    @Mock
    private TextView generatedTextView;
    @Mock
    private TextView resultTextView;
    @Mock
    private View generatedTextView1;
    @Mock
    private EditText sourceEditText;

    @Before
    public void setUp() throws Exception {
        mainActivity = mock(MainActivity.class);
        copyButtonSecond = mock(View.class);
        generatedTextView = mock(TextView.class);
        resultTextView = mock(TextView.class);
        sourceEditText = mock(EditText.class);
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