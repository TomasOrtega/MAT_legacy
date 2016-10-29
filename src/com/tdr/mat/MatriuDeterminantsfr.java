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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MatriuDeterminantsfr extends Fragment {

	public double[] v;
	public int dimensio;
	public TableLayout tbl;

	public double determinant(int dimensio, double[] v) {

		// Create an alternating integer that goes from 1 to -1
		int alternat = 1;
		double dresultat = 0;

		// If the matrix is dimension 1, return the determinant
		// Else, start the recursive method
		if (dimensio == 1) {
			dresultat = v[0];
		} else {
			for (int i = 0; i < dimensio; i++) {

				//v2 is the new matrix
				double v2[] = new double[(dimensio - 1) * (dimensio - 1)];
				int contador = 0;
				int contadorfiles = 0;

				for (int j = dimensio; j < (dimensio * dimensio); j++) {

					contadorfiles = j / dimensio;

					if (j != i + (dimensio * contadorfiles)) {
						v2[contador] = v[j];
						contador += 1;
					}

				}

				dresultat += (determinant((dimensio - 1), v2)) * alternat
						* v[i];

				alternat = -alternat;

			}
		}
		return dresultat;
	}

	public void mostra(String s) {
		Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		tbl = (TableLayout) getView().findViewById(R.id.tbl);
		TableRow.LayoutParams lpar = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < dimensio; i++) {
			TableRow tbr = new TableRow(getActivity());
			for (int j = 0; j < dimensio; j++) {
				EditText et = new EditText(getActivity());
				et.setId(i * dimensio + j);
				et.setMinimumWidth(100);
				et.setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL
						| InputType.TYPE_NUMBER_FLAG_SIGNED);
				Spanned s = Html.fromHtml("a <sub><small>"
						+ String.valueOf(i + 1) + "</small></sub>" + " "
						+ "<sub><small>" + String.valueOf(j + 1)
						+ "</small></sub>");
				et.setHint(s);
				tbr.addView(et);
			}
			tbr.setLayoutParams(lpar);
			tbl.addView(tbr);
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {

		dimensio = getArguments().getInt("dimensio");
		v = new double[dimensio * dimensio];

		return inflater.inflate(R.layout.determinantslayout2, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		final Button boto = (Button) getView().findViewById(R.id.button1);

		boto.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
				double asdf = dimensio * dimensio;
				double auxiliar = 0;
				for (int i = 0; i < asdf; i++) {
					EditText et = (EditText) getView().findViewById(i);
					try {
						auxiliar = Double.valueOf(et.getText().toString());
					} catch (NumberFormatException e) {
						mostra(getString(com.tdr.mat.R.string.nombrenovalid));
						return;
					}
					v[i] = auxiliar;
				}

				Double determinantresultant = determinant(dimensio, v);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(String.valueOf(determinantresultant)).setTitle(getString(com.tdr.mat.R.string.eldeterminantdelamatriues));
				builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}

}
