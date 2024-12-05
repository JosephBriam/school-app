package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;

public class frmGestionDirecciones extends AppCompatActivity {

    int estudianteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_gestion_direcciones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        estudianteId = getIntent().getIntExtra("estudianteId", -1);
    }
    public void RegDireccion(View view) {
        Intent x = new Intent(this, frmInsertarLugar.class);
        x.putExtra("estudianteId", estudianteId);  // Enviar el estudianteId al nuevo formulario
        startActivity(x);
    }
    public void listarDirecciones(View view) {
        Intent x = new Intent(this, frmListarLugar.class);
        x.putExtra("estudianteId", estudianteId);  // Enviar el estudianteId al nuevo formulario
        startActivity(x);
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}