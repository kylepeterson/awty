package arewethereyet.edu.washington.kylep9.arewethereyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.telephony.PhoneNumberFormattingTextWatcher;


public class MainActivity extends ActionBarActivity {
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize alarm objects
        final AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // get button and set click handler
        final Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get EditTexts Views
                EditText messageBox = (EditText) findViewById(R.id.message);
                EditText phoneBox = (EditText) findViewById(R.id.phone);
                EditText minuteBox = (EditText) findViewById(R.id.interval);
                phoneBox.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
                // Get EditTexts Contents
                String message = messageBox.getText().toString();
                String phoneNumber = phoneBox.getText().toString();
                String minuteIntervalString = minuteBox.getText().toString();
                //Initialize intents
                Intent alarmIntent = new Intent(MainActivity.this, RepeatingAlarm.class);
                alarmIntent.putExtra("message", message);
                alarmIntent.putExtra("number", phoneNumber);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
                if(submit.getText().equals("Start")) {
                    if(message.length() > 0 && phoneNumber.length() > 0 && minuteIntervalString.length() > 0) {
                        submit.setText("Stop");
                        int minuteInterval = Integer.parseInt(minuteIntervalString);
                        int interval = minuteInterval * 60 * 1000;

                        // Set up alarm repeating
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
                        Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Messaging has started, button currently says Stop
                    submit.setText("Start");
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
                }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
