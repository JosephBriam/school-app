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

public class CrudApoderados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_apoderados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void frmManInsertarPad(View view){
        Intent x=new Intent(this, frmRegApo.class);
        startActivity(x);
    }

    public void frmManListPad(View view){
        Intent x=new Intent(this, frmListApo.class);
        startActivity(x);
    }
    public void frmManElimPad(View view){
        Intent x=new Intent(this, frmEliApo.class);
        startActivity(x);
    }
    public void frmManActuatPad(View view){
        Intent x=new Intent(this, frmModApo.class);
        startActivity(x);
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}