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

import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NombreMostresFr extends Fragment {

	public int n;
	public double[] v;
	public LinearLayout lin;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		lin = (LinearLayout) getView().findViewById(R.id.lin);
		for (int i = 0; i < n; i++) {
			EditText mostra = new EditText(getActivity());
			mostra.setHint(getString(com.tdr.mat.R.string.introdueixmostra) + " "+ (i + 1));
			mostra.setId(i);
			mostra.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL
					| InputType.TYPE_NUMBER_FLAG_SIGNED);
			mostra.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			lin.addView(mostra);
		}

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		n = getArguments().getInt("Nombremostres");
		return inflater.inflate(R.layout.estadisticalayout2, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		final Button ok = (Button) getView().findViewById(R.id.button1);
		final Button afegirmostra = (Button) getView().findViewById(R.id.afegirmostra);
		final Button treumostra = (Button) getView().findViewById(R.id.treumostra);

		afegirmostra.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				n++;
				EditText mostra = new EditText(getActivity());
				mostra.setHint(getString(com.tdr.mat.R.string.introdueixmostra)+ " " + (n));
				mostra.setId(n - 1);
				mostra.setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL
						| InputType.TYPE_NUMBER_FLAG_SIGNED);
				mostra.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				lin.addView(mostra);
			}

		});

		treumostra.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (n == 1)	getFragmentManager().popBackStack();
				EditText mostra = (EditText) getView().findViewById(n - 1);
				lin.removeView(mostra);
				n--;
			}

		});

		ok.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				v = new double[n];
				for (int i = 0; i < n; i++) {
					EditText mostra = (EditText) getView().findViewById(i);
					try {
						v[i] = Double.parseDouble(mostra.getText().toString());
					} catch (NumberFormatException e) {
						Toast.makeText(getActivity(), getString(com.tdr.mat.R.string.nombrenovalid), Toast.LENGTH_LONG).show();
						return;
					}
				}
				double sumatori=0, sumatorib = 0;
				for (int i = 0; i < n; i++) {
					sumatori += v[i];
					sumatorib += Math.pow(v[i], 2);
				}
				double mitjana = sumatori / n;
				double desviaciotipica = Math.sqrt((sumatorib/n)-(Math.pow(mitjana, 2)));

				Arrays.sort(v);
				double mediana = 0;
				if (n % 2 == 0) {
					mediana = (v[(n / 2) - 1] + v[(n / 2)]) / 2;
				} else {
					mediana = v[((n + 1) / 2) - 1];
				}

				String solucions = getString(com.tdr.mat.R.string.mitjanadospunts)
						+ " " + String.valueOf(mitjana) + "\n"
						+ getString(com.tdr.mat.R.string.dtdospunts) + " "
						+ String.valueOf(desviaciotipica) + "\n"
						+ getString(com.tdr.mat.R.string.medianadospunts) + " "
						+ String.valueOf(mediana);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(solucions).setTitle(getString(com.tdr.mat.R.string.mmdttitol));
				builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
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
