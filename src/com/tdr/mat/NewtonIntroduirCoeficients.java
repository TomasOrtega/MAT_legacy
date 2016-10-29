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
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewtonIntroduirCoeficients extends Fragment {

	public double[] v, inp;
	public int grau;
	public LinearLayout lin;
	public Button boto;

	protected double evaluar(double[] v2, double a) {
		int j = v2.length - 1;
		double sumatori = v2[0];
		for (int z = 1; z <= j; z++) {
			sumatori += Math.pow(a, z) * v2[z];
		}
		return sumatori;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		lin = (LinearLayout) getView().findViewById(R.id.lin);
		for (int i = 0; i <= grau + 1; i++) {
			EditText mostra = new EditText(getActivity());
			Spanned s;
			if (i == grau + 1) {
				s = Html.fromHtml(getString(com.tdr.mat.R.string.introdueixpuntdinici));
			} else {
				s = Html.fromHtml(getString(com.tdr.mat.R.string.introdueixelcoeficient)
						+ "<sup><small>" + i + "</small></sup>");
			}
			mostra.setHint(s);
			mostra.setId(i);
			mostra.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL
					| InputType.TYPE_NUMBER_FLAG_SIGNED);
			mostra.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			lin.addView(mostra);
		}

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		grau = getArguments().getInt("ExtraGrau");
		return inflater.inflate(R.layout.newtonlayout2, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		boto = (Button) getView().findViewById(R.id.button1);
		inp = new double[grau + 2];
		v = new double[grau + 1];

		boto.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
				double auxiliar = 0;
				for (int i = 0; i <= grau + 1; i++) {
					EditText et = (EditText) getView().findViewById(i);
					try {
						String s = et.getText().toString();
						if (s.isEmpty()) {
							Toast.makeText(getActivity(),com.tdr.mat.R.string.nombrenovalid, Toast.LENGTH_LONG).show();
							return;
						} else {
							auxiliar = Double.parseDouble(s);
						}
					} catch (NumberFormatException e) {
						Toast.makeText(getActivity(), com.tdr.mat.R.string.nombrenovalid, Toast.LENGTH_LONG).show();
						return;
					}
					inp[i] = auxiliar;
				}

				// Newton's method
				for (int i = 0; i <= grau; i++) {
					v[i] = inp[i];
				}
				
				// d is v', v is the original polynomial
				double guess = inp[grau + 1];
				double[] d = new double[grau];
				for (int k = 1; k <= grau; k++) {
					d[k - 1] = v[k] * k;
				}
			
				// Read system time
				
				boolean solucio=true;
				double startt = System.currentTimeMillis();
				// The first condition is the tolerance, the second is the timer
				while (Math.abs(evaluar(v, guess)) > 0.000000000001 && System.currentTimeMillis() - startt < 1000 && solucio) {
					double aux = evaluar(d, guess);
					if (Math.abs(aux) < 0.000000000001) {
						// If v'(guess) is 0, the method does not converge with the given starting point
						solucio=false;
					} else {
						guess = guess - evaluar(v, guess) / aux;
					}
				}

				if (System.currentTimeMillis() - startt < 1000 && solucio) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage(String.valueOf(((float)Math.round(100000000*guess))/100000000)).setTitle(getString(com.tdr.mat.R.string.larreltrobada));
					builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									dialog.dismiss();
								}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					Toast.makeText(getActivity(), com.tdr.mat.R.string.noshantrobat, Toast.LENGTH_LONG).show();
				}
			}
		});
	}

}
