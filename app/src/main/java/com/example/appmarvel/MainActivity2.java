package com.example.appmarvel;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {
    FrameLayout frm_fragment;
    BottomNavigationView btnv_navegation;
    inicioFragment Inicio_fragment = new inicioFragment();
    fragment_configuracion fragment_configuracion = new fragment_configuracion();
    fragment_solicitar solicitarFragment = new fragment_solicitar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2); // Asegúrate de que activity_main2 es el nombre correcto del layout
        frm_fragment = findViewById(R.id.frm_fragment);
        btnv_navegation = findViewById(R.id.btnv_navegation);
        cargarFragment(Inicio_fragment);

        btnv_navegation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_inicio){
                    cargarFragment(Inicio_fragment);
                    return true;
                } else if (item.getItemId() == R.id.nav_solicitar) {
                    cargarFragment(fragment_configuracion);
                    return true;
                }else if (item.getItemId() == R.id.nav_confi) {
                    cargarFragment(solicitarFragment);
                    return true;
                }
                return false;
            }
        });
    }
    // Método para cargar fragmentos
    private void cargarFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frm_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}