package com.example.notificationproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;

public class MainActivity extends AppCompatActivity {

    private static final String CHANEL_ID = "CODING";
    private static final String CHANEL_NAME = "CODING2";

    private static final String CHANEL_DESC = "CODING4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //To Check if the user will allow notifcations
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }
        //Notification Handler
            findViewById(R.id.notifButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        displayNotification();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
                    }
                }
            });
    }
    //Notification Registration
    private void displayNotification(){
        NotificationCompat.Builder mbuilder =
                new NotificationCompat.Builder(this, CHANEL_ID)
                        .setSmallIcon(R.drawable.bird)
                        .setContentTitle("WOrking na")
                        .setContentText("CONTEXXXTTTT")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                ;

        //Notification Manager
        NotificationManagerCompat nManagerCompat = NotificationManagerCompat.from(this);
        try {
            nManagerCompat.notify(1, mbuilder.build());
        } catch (SecurityException e) {
            // Handle the exception, log the error, or inform the user
            e.printStackTrace();
        }
    }

    //IF allowed, notification will run
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayNotification();
            } else {
                // Permission denied
            }
        }
    }

}