package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cDocente;
import com.dam.ie_palas_app.modelo.Docente;

import java.util.ArrayList;

public class frmListDoc extends AppCompatActivity {

    private EditText editTextDocentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_list_doc);

        editTextDocentes = findViewById(R.id.editTextTextMultiLine4);
        listarDocentes();
    }

    private void listarDocentes(){
        cDocente controlador = new cDocente(this);
        ArrayList<Docente> docentes = controlador.obtenerDocentes();

        if(docentes.isEmpty()){
            editTextDocentes.setText("No hay Docentes registrados.");
        }else{
            mostrarDocentes(docentes);
        }
    }

    private void mostrarDocentes(ArrayList<Docente> docentes){
        StringBuilder sb= new StringBuilder();
        for(Docente docente : docentes){
            sb.append("ID: ").append(docente.getIdDocente()).append("\n")
                    .append("Nombre: ").append(docente.getNombres()).append("\n")
                    .append("Apellido: ").append(docente.getApellidos()).append("\n")
                    .append("Usuario: ").append(docente.getUsuario()).append("\n")
                    .append("Contraseña: ").append(docente.getContraseña()).append("\n\n");
        }
        editTextDocentes.setText(sb.toString());
    }

    public void fmrSalirDocenteList(View view) {
        finish();
    }
}