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
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EstadisticaFr extends Fragment {

	public int n;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.estadisticalayout, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		final EditText nombredemostres = (EditText) getView().findViewById(	R.id.editText1);
		final Button ok = (Button) getView().findViewById(R.id.button1);

		ok.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				n = 0;
				try {
					n = Integer.parseInt(nombredemostres.getText().toString());
					nombredemostres.setText("");
				} catch (NumberFormatException e) {
					Toast.makeText(getActivity(), getString(com.tdr.mat.R.string.nombrenovalid), Toast.LENGTH_LONG).show();
					return;
				}
				if (n < 1) {
					Toast.makeText(getActivity(), getString(com.tdr.mat.R.string.nombrenovalid), Toast.LENGTH_LONG).show();
					return;
				}
				
				if (n>99){
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage(com.tdr.mat.R.string.withanumberthisbig);
					builder.setNegativeButton(com.tdr.mat.R.string.cancela, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Fragment fragment = new NombreMostresFr();
							Bundle bundle = new Bundle();
							bundle.putInt("Nombremostres", n);
							fragment.setArguments(bundle);
							
							FragmentTransaction ft = getFragmentManager().beginTransaction();
							ft.setCustomAnimations(R.animator.aparece, R.animator.desaparece);
							ft.replace(R.id.fragment_container, fragment);
							ft.addToBackStack(null);
							ft.commit();
						}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				}else{
					Fragment fragment = new NombreMostresFr();
					Bundle bundle = new Bundle();
					bundle.putInt("Nombremostres", n);
					fragment.setArguments(bundle);
					
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.animator.aparece, R.animator.desaparece);
					ft.replace(R.id.fragment_container, fragment);
					ft.addToBackStack(null);
					ft.commit();
				}				
			}
		});
	}

}
