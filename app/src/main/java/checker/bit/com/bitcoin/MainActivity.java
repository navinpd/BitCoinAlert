package checker.bit.com.bitcoin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import checker.bit.com.bitcoin.pojo.MyPojo;
import checker.bit.com.bitcoin.pojo.ZebPay;

public class MainActivity extends AppCompatActivity {
    private EditText currency;
    private EditText priceToWatch;
    private Button submitButton;
    private String TAG = "MainActivity";
    private Timer timer;
    private EditText zebpayPrice;
    private volatile double zebAmountToCheck = 179000;
    private volatile double coinAmountToCheck = 3400;
    private int counter = 0;
    private TextView logsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton = (Button) findViewById(R.id.submit_button);
        priceToWatch = (EditText) findViewById(R.id.amount_to_watch);
        currency = (EditText) findViewById(R.id.currency_to_check);
        zebpayPrice = (EditText) findViewById(R.id.zebpay_price);
        logsText = (TextView) findViewById(R.id.logs_text);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = null;
                timer = new Timer();
                zebAmountToCheck = Double.valueOf(zebpayPrice.getText().toString());
                coinAmountToCheck = Double.valueOf(priceToWatch.getText().toString());
                timer.cancel();
                scheduleTime();

            }
        });

//        scheduleTime();
    }

    public void scheduleTime() {

        timer = null;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        }, 0, 60 * 1000);

    }

    public void getData() {
        String coinDesk = "http://api.coindesk.com/v1/bpi/currentprice/sgd.json";
        String zebPay = "https://api.zebpay.com/api/v1/ticker?currencyCode=INR";
        getResponse(coinDesk, coinAmountToCheck);

        if (counter > 0 && counter < 5) {
            counter++;
            return;
        }
        counter = 0;
        getResponse(zebPay, zebAmountToCheck);
        counter++;
    }

    private void getResponse(String urlString, final double compareAmount) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        URL url;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET"); //Your method here
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            if (urlString.contains("zebpay")) {

                ZebPay zebPay = gson.fromJson(reader, ZebPay.class);
                final double zebAmount = Double.valueOf(zebPay.getSell());

                if (compareAmount <= zebAmount) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createNotification("Indian amount is " + String.valueOf(zebAmount));
                        }
                    });
                }

                Log.d(TAG, "Value is " + zebAmount + "    saved zebAmount " + compareAmount);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (logsText.length() > 400) {
                            logsText.setText("");
                        }
                        logsText.setText(logsText.getText() + "\n" + "Current " + zebAmount + " saved zebAmount " + compareAmount);

                    }
                });

            } else {

                MyPojo myPojo = gson.fromJson(reader, MyPojo.class);
                final double coinHAmount = Double.valueOf(myPojo.getBpi().getSGD().getRate_float());

                if (compareAmount >= coinHAmount) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createNotification("Singapore amount is " + String.valueOf(coinHAmount));
                        }
                    });
                }
                Log.d(TAG, "Value is " + coinHAmount + " saved coinHackoAmount " + compareAmount);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (logsText.length() > 400) {
                            logsText.setText("");
                        }
                        logsText.setText(logsText.getText() + "\n" + "Current " + coinHAmount + " saved coinHAmount " + compareAmount);
                    }
                });

            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createNotification(String value) {
        long[] pattern = {2000, 1000, 1000};
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("THE AMOUNT IS " + value)
                .setContentText("Buy bro")
                .setAutoCancel(true)
                .build();
        notification.vibrate = pattern;
        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ringsound);


        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("some data", "txt");  // for extra data if needed..

        Random generator = new Random();

        PendingIntent i = PendingIntent.getActivity(MainActivity.this, generator.nextInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.contentIntent = i;
        notification.defaults = Notification.DEFAULT_LIGHTS;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        priceToWatch.setText(String.valueOf(coinAmountToCheck));
//        zebpayPrice.setText(String.valueOf(zebAmountToCheck));
    }
}
