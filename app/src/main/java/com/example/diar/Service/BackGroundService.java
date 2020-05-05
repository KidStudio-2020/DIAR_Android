package com.example.diar.Service;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import com.example.diar.Activity.MainActivity;
import com.example.diar.Database.DBHelper;
import com.example.diar.Database.LetterDB;
import com.example.diar.R;
import java.util.ArrayList;



public class BackGroundService extends JobService {
    JobParameters params;
    DoItTask doIt;
    private NotificationManagerCompat notificationManager;
    ArrayList<LetterDB> letterList;
    final ArrayList<LetterDB> letterRange = new ArrayList<>();
    LetterDB letter;

    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    Location location;
    double lat = 0;
    double lng = 0;

    @Override
    public boolean onStartJob(JobParameters params) {
        this.params = params;
        doIt = new DoItTask();
        doIt.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (doIt != null)
            doIt.cancel(true);
        return false;
    }

    private class DoItTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            jobFinished(params, false);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            DBHelper dbHelper = new DBHelper(getApplicationContext(), "capsule", null, 3);
            letterList = dbHelper.getAllLetter();

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(isGPSEnabled) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else if(isNetworkEnabled){
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if(location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            double distance;
            Location locationA = new Location("A");
            Location locationB = new Location("B");

            locationA.setLatitude(lat);
            locationA.setLongitude(lng);

            if (letterList != null) {
                if (letterRange != null) {
                    letterRange.clear();
                }
                for (int i = 0; i < letterList.size(); i++) {
                    letter = letterList.get(i);

                    locationB.setLatitude(letter.getLatitude());
                    locationB.setLongitude(letter.getLongitude());

                    distance = locationA.distanceTo(locationB);

                    if ( distance < 300 ) {
                        letterRange.add(letter);
                        if (letterRange.size() != 0) {

                            notificationManager = NotificationManagerCompat.from(getApplicationContext());
                            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                            PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            Notification notification = new NotificationCompat.Builder(getApplicationContext(), "1채")
                                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                                    .setContentTitle("DIAR")
                                    .setContentText("근처에서 당신의 추억 감지되었습니다.")
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .setContentIntent(resultPendingIntent)
                                    .setAutoCancel(true)
                                    .build();

                            notificationManager.notify(1, notification);
                        }
                    }
                }
            }
            return null;
        }
    }
}