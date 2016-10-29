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
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Menudefragments extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle SavedInstanceState) {
		return inflater.inflate(R.layout.menudefragments, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		final Button esg = (Button) getView().findViewById(R.id.button1);
		final Button det = (Button) getView().findViewById(R.id.button2);
		final Button seq = (Button) getView().findViewById(R.id.button3);
		final Button nwt = (Button) getView().findViewById(R.id.button4);
		final Button est = (Button) getView().findViewById(R.id.button5);
		final Button tri = (Button) getView().findViewById(R.id.button6);

		esg.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.aparece, R.animator.desaparece);
				ft.replace(R.id.fragment_container, new Esg());
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		det.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.aparece, R.animator.desaparece);
				ft.replace(R.id.fragment_container, new Determinantsfragment());
				ft.addToBackStack(null);
				ft.commit();
			}

		});

		seq.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.aparece,R.animator.desaparece);
				ft.replace(R.id.fragment_container, new SistemesFr());
				ft.addToBackStack(null);
				ft.commit();
			}

		});

		nwt.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.aparece,R.animator.desaparece);
				ft.replace(R.id.fragment_container, new NewtonFr());
				ft.addToBackStack(null);
				ft.commit();
			}

		});

		est.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.aparece,R.animator.desaparece);
				ft.replace(R.id.fragment_container, new EstadisticaFr());
				ft.addToBackStack(null);
				ft.commit();

			}

		});

		tri.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.aparece,R.animator.desaparece);
				ft.replace(R.id.fragment_container, new TrianglesFr());
				ft.addToBackStack(null);
				ft.commit();
			}

		});

	}

}
