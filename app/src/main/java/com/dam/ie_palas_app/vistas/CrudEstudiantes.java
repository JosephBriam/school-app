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

public class CrudEstudiantes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_estudiantes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void frmManInsertEst(View view){
        Intent x=new Intent(this, frmRegEst.class);
        startActivity(x);
    }
    public void frmManListEst(View view){
        Intent x=new Intent(this, frmListEst.class);
        startActivity(x);
    }
    public void frmManElimEst(View view){
        Intent x=new Intent(this, frmEliEst.class);
        startActivity(x);
    }
    public void frmManActuatEst(View view){
        Intent x=new Intent(this, frmModEst.class);
        startActivity(x);
    }
    public  void frmManAsignarEstudiantePadre(View view){
        Intent x=new Intent(this, frmAsigPadreEstu.class);
        startActivity(x);
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}