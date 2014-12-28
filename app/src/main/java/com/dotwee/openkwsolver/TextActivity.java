package com.dotwee.openkwsolver;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TextActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        Button buttonBeginn = (Button) findViewById(R.id.buttonLeft);

        buttonBeginn.setText("Beginn");
        buttonBeginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearImageViewAndEditText(); // First of all, clear the ImageView and EditText (just to be save)

                final String CaptchaID = pullOnlyTextCaptchaID();

                if (!CaptchaID.matches(regex)) {
                    pullCaptchaPicture(CaptchaID); // Pull the Captcha picture and display it

                    // TODO Progressbar Countdown

                    Button buttonSend = (Button) findViewById(R.id.buttonRight);
                    buttonSend.setText("Send");
                    buttonSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText EditTextCaptchaAnswer = (EditText) findViewById(R.id.editTextAnswer);
                            String CaptchaAnswer = EditTextCaptchaAnswer.getText().toString();
                            sendCaptchaAnswerText(CaptchaAnswer, CaptchaID);
                            clearImageViewAndEditText();
                        }
                    });

                } else
                    Toast.makeText(getApplicationContext(), "Housten, we got a problem with CaptchaID " + CaptchaID, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    // read the 9kw API-Key from previously saved file
    private String pullKeyFromFile() {

        String apikey = null;

        try {

            InputStream inputStream = openFileInput("apikey.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                apikey = stringBuilder.toString();
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Housten, couldn't find API-Key. You may want to enter it again.", Toast.LENGTH_LONG).show();
            ;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apikey;
    }
}
