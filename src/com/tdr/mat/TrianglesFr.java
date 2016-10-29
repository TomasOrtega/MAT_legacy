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
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class TrianglesFr extends Fragment {

	public double tol = 0.000000001;

	public void ca(double[] amb, double alpha, double a, double b) {
		double betados;
		double aux = b * Math.sin(Math.toRadians(alpha)) / a;
		if (aux >= 1) {
			for (int i = 0; i < 12; i++) {
				amb[i] = -1;
			}
			return;
		}
		double betau = ((float)Math.round(100000*Math.toDegrees(Math.asin(aux))))/100000;
		betados = 180 - betau;
		if (betau + alpha >= 180) {
			amb[0] = amb[1] = amb[2] = amb[3] = amb[4] = amb[5] = -1;
		} else {
			amb[0] = a;
			amb[1] = b;
			amb[3] = alpha;
			amb[4] = betau;
			amb[5] = 180 - alpha - betau;
			amb[2] = a * Math.sin(Math.toRadians(amb[5]))
					/ Math.sin(Math.toRadians(amb[3]));
		}
		if (betados + alpha >= 180 || betados==betau) {
			amb[6] = amb[7] = amb[8] = amb[9] = amb[10] = amb[11] = -1;
		} else {
			amb[6] = a;
			amb[7] = b;
			amb[9] = alpha;
			amb[10] = betados;
			amb[11] = 180 - alpha - betados;
			amb[8] = a * Math.sin(Math.toRadians(amb[11]))
					/ Math.sin(Math.toRadians(amb[9]));
		}
		return;
	}

	public void mostra(String s) {
		Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.triangleslayout, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		final EditText atext = (EditText) getView().findViewById(R.id.editText1);
		final EditText btext = (EditText) getView().findViewById(R.id.editText2);
		final EditText ctext = (EditText) getView().findViewById(R.id.editText3);
		final EditText alphatext = (EditText) getView().findViewById(R.id.editText4);
		final EditText betatext = (EditText) getView().findViewById(R.id.editText5);
		final EditText gammatext = (EditText) getView().findViewById(R.id.editText6);
		final ImageButton button = (ImageButton) getView().findViewById(R.id.imageButton1);
		final Button buidar = (Button) getView().findViewById(R.id.button1);

		buidar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				atext.setText("");
				btext.setText("");
				ctext.setText("");
				alphatext.setText("");
				betatext.setText("");
				gammatext.setText("");
			}
		});

		button.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View view) {

				InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

				double a = 0, b = 0, c = 0, alpha = 0, beta = 0, gamma = 0;
				int ncostats = 3, nangles = 3;
				boolean costata = false, costatb = false, anglea = false, angleb = false;

				try {
					String sa = atext.getText().toString();
					if (sa.isEmpty()) {
						ncostats--;
					} else {
						a = Double.parseDouble(sa);
						if (a == 0) {
							atext.setText("");
							ncostats--;
							mostra(getString(com.tdr.mat.R.string.uncostatnopotserzero));
						} else {
							costata = true;
						}
					}
				} catch (NumberFormatException e) {
					atext.setText("");
					ncostats--;
				}

				try {
					String sb = btext.getText().toString();
					if (sb.isEmpty()) {
						ncostats--;
					} else {
						b = Double.parseDouble(sb);
						if (b == 0) {
							btext.setText("");
							ncostats--;
							mostra(getString(com.tdr.mat.R.string.uncostatnopotserzero));
						} else {
							costatb = true;
						}
					}
				} catch (NumberFormatException e) {
					btext.setText("");
					ncostats--;
				}

				try {
					String sc = ctext.getText().toString();
					if (sc.isEmpty()) {
						ncostats--;
					} else {
						c = Double.parseDouble(sc);
						if (c == 0) {
							ctext.setText("");
							ncostats--;
							mostra(getString(com.tdr.mat.R.string.uncostatnopotserzero));
						}
					}
				} catch (NumberFormatException e) {
					ctext.setText("");
					ncostats--;
				}

				if (ncostats == 3) {

					double[] v = { a, b, c };
					Arrays.sort(v);
					if (v[0] + v[1] <= v[2]) {
						mostra(getString(com.tdr.mat.R.string.untrianglenopottenir));
						return;

					} else {

						alpha = Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c)));
						beta = Math.toDegrees(Math.acos((b * b - a * a - c * c) / (-2 * a * c)));
						gamma = 180 - alpha - beta;

						alphatext.setText(String.valueOf(alpha));
						betatext.setText(String.valueOf(beta));
						gammatext.setText(String.valueOf(gamma));

					}

				}

				else {

					try {
						String salpha = alphatext.getText().toString();
						if (salpha.isEmpty()) {
							nangles--;
						} else {
							alpha = Double.parseDouble(salpha);
							if (alpha == 0) {
								alphatext.setText("");
								nangles--;
								mostra(getString(com.tdr.mat.R.string.unanglenopotserzero));
							} else {
								anglea = true;
							}
						}
					} catch (NumberFormatException e) {
						alphatext.setText("");
						nangles--;
					}

					try {
						String sbeta = betatext.getText().toString();
						if (sbeta.isEmpty()) {
							nangles--;
						} else {
							beta = Double.parseDouble(sbeta);
							if (beta == 0) {
								betatext.setText("");
								nangles--;
								mostra(getString(com.tdr.mat.R.string.unanglenopotserzero));
							} else {
								angleb = true;
							}
						}
					} catch (NumberFormatException e) {
						betatext.setText("");
						nangles--;
					}

					try {
						String sgamma = gammatext.getText().toString();
						if (sgamma.isEmpty()) {
							nangles--;
						} else {
							gamma = Double.parseDouble(sgamma);
							if (gamma == 0) {
								gammatext.setText("");
								nangles--;
								mostra(getString(com.tdr.mat.R.string.unanglenopotserzero));
							}
						}
					} catch (NumberFormatException e) {
						gammatext.setText("");
						nangles--;
					}

					if (nangles == 2) {
						if (!anglea) {
							alpha = 180 - beta - gamma;
							alphatext.setText(String.valueOf(alpha));
							nangles++;
							anglea = true;
						} else if (!angleb) {
							beta = 180 - alpha - gamma;
							betatext.setText(String.valueOf(beta));
							nangles++;
							angleb = true;
						} else {
							gamma = 180 - alpha - beta;
							gammatext.setText(String.valueOf(gamma));
							nangles++;
						}
						if (alpha <= 0 || beta <= 0 || gamma <= 0) {
							mostra(getString(com.tdr.mat.R.string.nombrenovalid));
							return;
						}
					}

					double sumadangles = alpha + beta + gamma;
					if (sumadangles > 180 + tol) {
						mostra(getString(com.tdr.mat.R.string.elsanglessumen));
						return;
					}

					if (ncostats + nangles < 3) {
						mostra(getString(com.tdr.mat.R.string.faltainfo));
						return;
					}

					if (nangles == 3 && sumadangles < 180 - tol) {
						mostra(getString(com.tdr.mat.R.string.elsanglessumenmenys));
						return;
					}

					if (ncostats == 0) {
						c = 1;
						ctext.setText("1.0");
						ncostats++;
						mostra(getString(com.tdr.mat.R.string.totselstriangles));
					}

					if (ncostats == 1) {
						if (costata) {
							double salpha = Math.sin(Math.toRadians(alpha));
							btext.setText(String.valueOf(a* Math.sin(Math.toRadians(beta)) / salpha));
							ctext.setText(String.valueOf(a* Math.sin(Math.toRadians(gamma)) / salpha));
						} else if (costatb) {
							double sbeta = Math.sin(Math.toRadians(beta));
							atext.setText(String.valueOf(b* Math.sin(Math.toRadians(alpha)) / sbeta));
							ctext.setText(String.valueOf(b* Math.sin(Math.toRadians(gamma)) / sbeta));
						} else {
							double sgamma = Math.sin(Math.toRadians(gamma));
							atext.setText(String.valueOf(c* Math.sin(Math.toRadians(alpha)) / sgamma));
							btext.setText(String.valueOf(c* Math.sin(Math.toRadians(beta)) / sgamma));
						}
					}

					if (ncostats == 2) {
						// 2 sides and 1 angle opposed to one of them is ambiguous
						double[] aux = new double[12];
						if (!costata) {
							// Having b and c
							if (anglea) {
								a = Math.sqrt(b * b + c * c - 2 * b * c	* Math.cos(Math.toRadians(alpha)));
								beta = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(alpha)) * b / a));
								gammatext.setText(String.valueOf(180-alpha-beta));
								atext.setText(String.valueOf(a));
								betatext.setText(String.valueOf(beta));
							} else if (angleb) {
								ca(aux, beta, b, c);
								double[] triangles = new double[12];
								triangles[0] = aux[1];
								triangles[1] = aux[2];
								triangles[2] = aux[0];
								triangles[3] = aux[4];
								triangles[4] = aux[5];
								triangles[5] = aux[3];
								triangles[6] = aux[7];
								triangles[7] = aux[8];
								triangles[8] = aux[6];
								triangles[9] = aux[10];
								triangles[10] = aux[11];
								triangles[11] = aux[9];
								solucions(triangles);
							} else {
								ca(aux, gamma, c, b);
								double[] triangles = new double[12];
								triangles[0] = aux[2];
								triangles[1] = aux[1];
								triangles[2] = aux[0];
								triangles[3] = aux[5];
								triangles[4] = aux[4];
								triangles[5] = aux[3];
								triangles[6] = aux[8];
								triangles[7] = aux[7];
								triangles[8] = aux[6];
								triangles[9] = aux[11];
								triangles[10] = aux[10];
								triangles[11] = aux[9];
								solucions(triangles);
							}
						} else if (!costatb) {
							// Having a and c
							if (anglea) {
								ca(aux, alpha, a, c);
								double[] triangles = new double[12];
								triangles[0] = aux[0];
								triangles[1] = aux[2];
								triangles[2] = aux[1];
								triangles[3] = aux[3];
								triangles[4] = aux[5];
								triangles[5] = aux[4];
								triangles[6] = aux[0];
								triangles[7] = aux[2];
								triangles[8] = aux[1];
								triangles[9] = aux[3];
								triangles[10] = aux[5];
								triangles[11] = aux[4];
								solucions(triangles);
							} else if (angleb) {
								b = Math.sqrt(a * a + c * c - 2 * a * c	* Math.cos(Math.toRadians(beta)));
								alpha = Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c)));
								gammatext.setText(String.valueOf(180-alpha-beta));
								btext.setText(String.valueOf(b));
								alphatext.setText(String.valueOf(alpha));
							} else {
								ca(aux, gamma, c, a);
								double[] triangles = new double[12];
								triangles[0] = aux[2];
								triangles[1] = aux[0];
								triangles[2] = aux[1];
								triangles[3] = aux[5];
								triangles[4] = aux[3];
								triangles[5] = aux[4];
								triangles[6] = aux[2];
								triangles[7] = aux[0];
								triangles[8] = aux[1];
								triangles[9] = aux[5];
								triangles[10] = aux[3];
								triangles[11] = aux[4];
								solucions(triangles);
							}
						} else {
							// Having a and b
							if (anglea) {
								ca(aux, alpha, a, b);
								solucions(aux);
							} else if (angleb) {
								ca(aux, beta, b, a);
								double[] triangles = new double[12];
								triangles[0] = aux[1];
								triangles[1] = aux[0];
								triangles[2] = aux[2];
								triangles[3] = aux[4];
								triangles[4] = aux[3];
								triangles[5] = aux[5];
								triangles[6] = aux[7];
								triangles[7] = aux[6];
								triangles[8] = aux[8];
								triangles[9] = aux[10];
								triangles[10] = aux[9];
								triangles[11] = aux[11];
								solucions(triangles);
							} else {
								c = Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(gamma)));
								alpha = Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c)));
								betatext.setText(String.valueOf(180-alpha-gamma));
								ctext.setText(String.valueOf(c));
								alphatext.setText(String.valueOf(alpha));
							}
						}
					}
				}

			}

		});
	}

	public void solucions(double[] triangles) {
		final EditText atext = (EditText) getView().findViewById(R.id.editText1);
		final EditText btext = (EditText) getView().findViewById(R.id.editText2);
		final EditText ctext = (EditText) getView().findViewById(R.id.editText3);
		final EditText alphatext = (EditText) getView().findViewById(R.id.editText4);
		final EditText betatext = (EditText) getView().findViewById(R.id.editText5);
		final EditText gammatext = (EditText) getView().findViewById(R.id.editText6);
		boolean soluciou = true, soluciodos = true;
		if (triangles[0] == -1)
			soluciou = false;
		if (triangles[6] == -1)
			soluciodos = false;
		if (soluciou) {
			atext.setText(String.valueOf(triangles[0]));
			btext.setText(String.valueOf(triangles[1]));
			ctext.setText(String.valueOf(triangles[2]));
			alphatext.setText(String.valueOf(triangles[3]));
			betatext.setText(String.valueOf(triangles[4]));
			gammatext.setText(String.valueOf(triangles[5]));
			if (soluciodos) {
				mostra(getString(com.tdr.mat.R.string.atencioaquesttriangle));
				String sdos = ("A=" + String.valueOf(triangles[6])) + "\n"
						+ "B=" + (String.valueOf(triangles[7])) + "\n" + "C="
						+ (String.valueOf(triangles[8])) + "\n" + "α="
						+ (String.valueOf(triangles[9])) + "\n" + "β="
						+ (String.valueOf(triangles[10])) + "\n" + "γ="
						+ (String.valueOf(triangles[11]));
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(sdos).setTitle(getString(com.tdr.mat.R.string.soluciodos));
				builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		} else if (soluciodos) {
			atext.setText(String.valueOf(triangles[6]));
			btext.setText(String.valueOf(triangles[7]));
			ctext.setText(String.valueOf(triangles[8]));
			alphatext.setText(String.valueOf(triangles[9]));
			betatext.setText(String.valueOf(triangles[10]));
			gammatext.setText(String.valueOf(triangles[11]));
		} else {
			mostra(getString(com.tdr.mat.R.string.aquesttrianglenotesolucio));
			return;
		}

	}

}