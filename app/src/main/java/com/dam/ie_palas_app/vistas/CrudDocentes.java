package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;

public class CrudDocentes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_docentes);
    }

    public void frmManInsertDoc(View view){
        Intent x=new Intent(this, frmRegDoc.class);
        startActivity(x);
    }

    public void frmManListDoc(View view){
        Intent x=new Intent(this, frmListDoc.class);
        startActivity(x);
    }
    public void frmManElimDoc(View view){
        Intent x=new Intent(this, frmEliDoc.class);
        startActivity(x);
    }
    public void frmManActuatDoc(View view){
        Intent x=new Intent(this, frmModDoc.class);
        startActivity(x);
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}