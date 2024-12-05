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

public class CrudCursos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_cursos);
    }

    public void frmManInsertCurso(View view){
        Intent x=new Intent(this,frmRegCur.class);
        startActivity(x);
    }
    public void frmManListCurso(View view){
        Intent x=new Intent(this,frmListCur.class);
        startActivity(x);
    }
    public void frmManEliCurso(View view){
        Intent x=new Intent(this,frmEliCur.class);
        startActivity(x);
    }
    public void frmManActuatCurso(View view){
        Intent x=new Intent(this,frmModCur.class);
        startActivity(x);
    }

}