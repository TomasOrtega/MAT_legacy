/*
 * Copyright (C) 2014 Tomàs Ortega
 *
 * Licensed under the GPL License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tdr.mat;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

public class Activitatdemenu extends Activity {
	public AlertDialog dialog;
	public String idi = "";

	public void canviallengua() {
		dialog.dismiss();
		SharedPreferences prefs = getSharedPreferences("llengua", 0);
		prefs.edit().putString("codi", idi).commit();
		Intent refresh = new Intent(this, Activitatdemenu.class);
		refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(refresh);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = getSharedPreferences("llengua", 0);
		idi = prefs.getString("codi", "");
		if (!idi.isEmpty()) {
			Locale myLocale = new Locale(idi);
			Resources res = getResources();
			DisplayMetrics dm = res.getDisplayMetrics();
			Configuration conf = res.getConfiguration();
			conf.locale = myLocale;
			res.updateConfiguration(conf, dm);
		}

		setContentView(R.layout.activity_activitatdemenu);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState != null) {
			return;
		}
		// Create a new Fragment to be placed in the activity layout
		Menudefragments firstFragment = new Menudefragments();

		// Add the fragment to the 'fragment_container' FrameLayout
		getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activitatdemenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			final CharSequence[] items = { "Català", "Español", "English" };
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.idioma);
			builder.setSingleChoiceItems(items, 3,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					if (item == 0) {
						idi = "ca";
						canviallengua();
					}
					if (item == 1) {
						idi = "es";
						canviallengua();
					}
					if (item == 2) {
						idi = "en";
						canviallengua();
					}
				}
			});
			builder.setNeutralButton(R.string.cancela,new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			dialog = builder.create();
			dialog.show();
			return true;
			// Respond to the action bar's Up/Home button
		case android.R.id.home:
			FragmentManager fm = getFragmentManager();
			int n = fm.getBackStackEntryCount();
			if (n < 1)
				System.exit(1);
			for (int i = 0; i < n; ++i) {
				fm.popBackStack();
			}
			try {
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (Exception e) {
				return true;
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences prefs = getSharedPreferences("llengua", 0);
		prefs.edit().putString("codi", idi).commit();
	}

}
