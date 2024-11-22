package com.example.appmarvel;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_configuracion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_configuracion extends Fragment {

    private Switch swiche;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        // Cargar el estado del tema guardado
        sharedPreferences = getActivity().getSharedPreferences("night_mode_prefs", getContext().MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("isNightMode", false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        swiche = view.findViewById(R.id.swiche);

        // Establecer el switch en el estado actual del tema
        swiche.setChecked(isNightMode);

        swiche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                // Guardar el estado del tema en shared preferences
                editor = sharedPreferences.edit();
                editor.putBoolean("isNightMode", isChecked);
                editor.apply();
            }
        });
        return view;
    }
}
