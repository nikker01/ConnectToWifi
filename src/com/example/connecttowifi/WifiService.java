package com.example.connecttowifi;

import java.util.List;

import android.R.drawable;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class WifiService extends Service {

	private String TAG = "WifiScanService";
	private WifiReceiver wifiReceiver;
	private WifiManager wifiManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreate BEGIN");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy BEGIN");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStartCommand BEGIN");

		wifiReceiver = new WifiReceiver(this);
		registerReceiver(wifiReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	class WifiReceiver extends BroadcastReceiver {

		private Context mContext;

		public WifiReceiver(Context context) {
			this.mContext = context;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("WifiReceiver", "onReceive BEGIN");
			List<ScanResult> result_list = wifiManager.getScanResults();
			for (ScanResult result : result_list) {
				if (result.SSID.equals("DS-AP5")) {
					Log.i("WifiReceiver", "ApDetected");

					//generate Notification 
					NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
							mContext).setContentTitle("Connect to my AP?")
							.setSmallIcon(drawable.alert_dark_frame)
							.setContentText("click here to connet to my ap");

					mBuilder.setTicker("Ap detected");
					mBuilder.setAutoCancel(true);

					Intent resultIntent = new Intent(mContext,
							ResultActivity.class);
					PendingIntent resultPendingIntent = PendingIntent
							.getActivity(mContext, 0, resultIntent,
									PendingIntent.FLAG_CANCEL_CURRENT);
					mBuilder.setContentIntent(resultPendingIntent);

					NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(0, mBuilder.build());
				}

			}

		}

	}

}