package arewethereyet.edu.washington.kylep9.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by iguest on 2/23/15.
 */
public class RepeatingAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String number = intent.getStringExtra("number");
        Toast.makeText(context, number + ": " + message, Toast.LENGTH_SHORT).show();
    }
}
