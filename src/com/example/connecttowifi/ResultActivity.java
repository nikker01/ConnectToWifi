package com.example.connecttowifi;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

public class ResultActivity extends Activity {

	private String mApName = "DS-AP5";
	private String mApPwd = "66c2fb5951";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i("ResultActivity", "onCreate BEGIN");
		
		showConnectDialog();
	}

	private void showConnectDialog() {
		// TODO Auto-generated method stub
		Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("");
		alertDialog.setMessage("Connect to this AP:" +mApName);
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//activity.retryAPIRequest();
						connectToAp();
					}
				});
		alertDialog.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});

		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	protected void connectToAp() {
		// TODO Auto-generated method stub
		Log.i("ResultActivity", "connectToAp BEGIN");
		
		WifiConfiguration conf = new WifiConfiguration();
		conf.SSID = "\"" + mApName + "\"";
		conf.preSharedKey = "\"" + mApPwd + "\"";
		
		WifiManager wiFiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wiFiManager.addNetwork(conf);
		List<WifiConfiguration> list = wiFiManager.getConfiguredNetworks();
		if (list != null) {
			for (WifiConfiguration i : list) {
				if (i.SSID != null && i.SSID.equals("\"" + mApName + "\"")) {
					wiFiManager.enableNetwork(i.networkId, true);
					Log.i("ResultActivity", "reconnect wifi " + wiFiManager.reassociate());
				}
			}
		}
	}
}
