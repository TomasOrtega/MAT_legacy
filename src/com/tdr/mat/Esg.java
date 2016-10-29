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

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Esg extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle SavedInstanceState) {
		return inflater.inflate(R.layout.esg_fragment, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		final EditText atext = (EditText) getView().findViewById(R.id.editText1);
		final EditText btext = (EditText) getView().findViewById(R.id.editText2);
		final EditText ctext = (EditText) getView().findViewById(R.id.editText3);
		final Button button = (Button) getView().findViewById(R.id.button1);
		final TextView Resultatescrit1 = (TextView) getView().findViewById(R.id.textView2);
		final TextView Resultatescrit2 = (TextView) getView().findViewById(R.id.textView3);

		button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
				
				//Empty the TextViews
				Resultatescrit1.setText("");
				Resultatescrit2.setText("");

				// Read a, b, c
				// If empty, they are replaced by 0
				double a, b, c;
				try {
					String sa = atext.getText().toString();
					if (sa.isEmpty()) {
						a = 0;
						atext.setText("0");
					} else {
						a = Double.parseDouble(sa);
					}
				} catch (NumberFormatException e) {
					atext.setText("0");
					a = 0;
				}

				try {
					String sb = btext.getText().toString();
					if (sb.isEmpty()) {
						b = 0;
						btext.setText("0");
					} else {
						b = Double.parseDouble(sb);
					}
				} catch (NumberFormatException e) {
					btext.setText("0");
					b = 0;
				}

				try {
					String sc = ctext.getText().toString();
					if (sc.isEmpty()) {
						c = 0;
						ctext.setText("0");
					} else {
						c = Double.parseDouble(sc);
					}
				} catch (NumberFormatException e) {
					ctext.setText("0");
					c = 0;
				}

				// Calculate discriminant
				final double discriminant = (b * b - 4 * a * c);
				if (a == 0) {
					Resultatescrit1.setText("");
					if (b == 0) {
						Resultatescrit1.setText(com.tdr.mat.R.string.nox);
					} else {
						Resultatescrit2.setText(Double.toString(-c / b));
					}
				} else if (discriminant < 0) {

					// If negative, there are no real solutions
					Resultatescrit1.setText(com.tdr.mat.R.string.norealx);
				} else {

					// Calculate the solutions
					final double arrel = Math.sqrt(discriminant);
					Double resultat1 = ((-b + arrel) / (2 * a));
					Double resultat2 = ((-b - arrel) / (2 * a));
					Resultatescrit1.setText(resultat1.toString());
					Resultatescrit2.setText(resultat2.toString());

				}
			}
		});
	}

}
