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
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SistemesFr2 extends Fragment {
	public static boolean resoldresistema(int n, double[][] a, double[] b,
			double[] r) {
		double tolerancia = 0.0000000001;
		double m = 0;
		for (int i = 0; i < n - 1; i++) {
			// Search the biggest number in the column
			double masgrande = Math.abs(a[i][i]);
			int fc = i;
			for (int l = i + 1; l < n; l++) {
				if (Math.abs(a[l][i]) > masgrande) {
					masgrande = Math.abs(a[l][i]);
					fc = l;
				}
			}
			if (fc != i) {
				// Permute rows i and fc
				double aux;
				for (int c = i; c < n; c++) {
					aux = a[i][c];
					a[i][c] = a[fc][c];
					a[fc][c] = aux;
				}
				aux = b[i];
				b[i] = b[fc];
				b[fc] = aux;
			}
			if (Math.abs(a[i][i]) < tolerancia) {
				return false;
			}
			
			for (int k = i + 1; k < n; k++) {
				m = a[k][i] / a[i][i];
				for (int j = i + 1; j < n; j++) {
					a[k][j] = a[k][j] - m * a[i][j];
				}
				a[k][i] = 0;
				b[k] = b[k] - m * b[i];
			}
		}
		if (Math.abs(a[n - 1][n - 1]) < tolerancia) {
			return false;
		}
		// Solve the, now triangular, system
		for (int i = n - 1; i >= 0; i--) {
			double sum = 0;
			for (int j = i; j < n; j++) {
				sum += a[i][j] * r[j];
			}
			r[i] = (b[i] - sum) / a[i][i];
		}
		return true;
	}

	public int dimension;
	public double[][] v;
	public double[] x;
	public double[] r;

	public TableLayout tbl;

	public void mostra(String s) {
		Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		tbl = (TableLayout) getView().findViewById(R.id.tbl);
		TableRow.LayoutParams lpar = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < dimension; i++) {
			TableRow tbr = new TableRow(getActivity());
			for (int j = 0; j < dimension; j++) {
				EditText et = new EditText(getActivity());
				et.setId(i * dimension + j);
				et.setMinimumWidth(100);
				et.setInputType(InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL
						| InputType.TYPE_NUMBER_FLAG_SIGNED);
				tbr.addView(et);
				Spanned s;
				if (j == (dimension - 1)) {
					s = Html.fromHtml("x <sub><small>" + String.valueOf(j + 1)
							+ "</small></sub>" + " = ");
				} else {
					s = Html.fromHtml("x <sub><small>" + String.valueOf(j + 1)
							+ "</small></sub>" + " + ");
				}
				TextView tv = new TextView(getActivity());
				int height_in_pixels = tv.getLineHeight() * 2;
				tv.setHeight(height_in_pixels);
				tv.setText(s);
				tbr.addView(tv);
			}
			EditText et = new EditText(getActivity());
			et.setId(dimension * dimension + i);
			et.setMinimumWidth(100);
			et.setInputType(InputType.TYPE_CLASS_NUMBER
					| InputType.TYPE_NUMBER_FLAG_DECIMAL
					| InputType.TYPE_NUMBER_FLAG_SIGNED);
			tbr.addView(et);
			tbr.setLayoutParams(lpar);
			tbl.addView(tbr);
		}

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		dimension = getArguments().getInt("dimension");
		v = new double[dimension][dimension];
		x = new double[dimension];
		r = new double[dimension];

		return inflater.inflate(R.layout.sistemeslayout2, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		final Button boto = (Button) getView().findViewById(R.id.botosistemes);
		boto.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View view) {
				int id = 0;
				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
						id = (i * dimension + j);
						double aux = 0;
						EditText et = (EditText) getView().findViewById(id);
						try {
							aux = Double.valueOf(et.getText().toString());
						} catch (NumberFormatException e) {
							mostra(getString(com.tdr.mat.R.string.nombrenovalid));
							return;
						}
						v[i][j] = aux;
					}
					id = (dimension * dimension + i);
					double aux = 0;
					EditText et = (EditText) getView().findViewById(id);
					try {
						aux = Double.valueOf(et.getText().toString());
					} catch (NumberFormatException e) {
						mostra(getString(com.tdr.mat.R.string.nombrenovalid));
						return;
					}
					x[i] = aux;
				}
				if (resoldresistema(dimension, v, x, r)) {
					String solucions = "";
					for (int i = 0; i < dimension; i++) {
						solucions += "x <sub><small>" + (String.valueOf(i + 1))
								+ "</small></sub> = " + (r[i]) + "<br>";
					}
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage(Html.fromHtml(solucions)).setTitle(getString(com.tdr.mat.R.string.solsis));
					builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
						}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					mostra(getString(com.tdr.mat.R.string.sistemaincompatible));
				}
			}

		});
	}

}
