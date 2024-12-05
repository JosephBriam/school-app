package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;

public class PantallaPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    // Método para el botón de Administrador
    public void administrador(View view) {
        Intent intent = new Intent(PantallaPrincipalActivity.this, SesionAdmin.class);
        startActivity(intent);  // Inicia SesionDocente
    }
    // Método para el botón de Estudiante
    public void docente(View view) {
        Intent intent = new Intent(PantallaPrincipalActivity.this, SesionDocente.class);
        startActivity(intent);  // Inicia SesionEstudianteActivity
    }

    // Método para el botón de Estudiante
    public void estudiante(View view) {
        Intent intent = new Intent(PantallaPrincipalActivity.this, SesionEstudianteActivity.class);
        startActivity(intent);  // Inicia SesionEstudianteActivity
    }

    // Método para el botón de Padre
    public void padre(View view) {
        Intent intent = new Intent(PantallaPrincipalActivity.this, SesionPadreActivity.class);
        startActivity(intent);  // Inicia SesionPadreActivity
    }
    public void salir(View view){
        finish();
    }

}