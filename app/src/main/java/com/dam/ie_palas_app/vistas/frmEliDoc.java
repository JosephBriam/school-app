package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cDocente;
import com.dam.ie_palas_app.modelo.Docente;

import java.util.ArrayList;

public class frmEliDoc extends AppCompatActivity {

    private EditText txtCodigoDocEli, txtListDocEliminar;
    private Docente docenteEncontrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_eli_doc);

        txtCodigoDocEli = findViewById(R.id.txtCodigoDocEli);
        txtListDocEliminar = findViewById(R.id.txtlistaest);
    }

    public void buscarDocenteEli(View view) {
        String idStr = txtCodigoDocEli.getText().toString().trim();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del docente", Toast.LENGTH_LONG).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un ID válido", Toast.LENGTH_LONG).show();
            return;
        }

        cDocente controladorDoc = new cDocente(this);
        ArrayList<Docente> docentes = controladorDoc.obtenerDocentes();

        // Busca el docente con el ID especificado
        for (Docente docente : docentes) {
            if (docente.getIdDocente() == id) {
                docenteEncontrado = docente;

                // Muestra los datos del docente en el campo txtListDocEliminar
                String datosDocente = "ID: " + docente.getIdDocente() + "\n"
                        + "Nombre: " + docente.getNombres() + "\n"
                        + "Apellido: " + docente.getApellidos() + "\n"
                        + "Usuario: " + docente.getUsuario();
                txtListDocEliminar.setText(datosDocente);

                Toast.makeText(this, "Docente encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        docenteEncontrado = null; // No encontrado
        txtListDocEliminar.setText(""); // Limpia el campo si no se encuentra
        Toast.makeText(this, "Docente no encontrado", Toast.LENGTH_LONG).show();
    }

    public void eliminarDocente(View view) {
        if (docenteEncontrado == null) {
            Toast.makeText(this, "No se ha encontrado ningún docente para eliminar", Toast.LENGTH_LONG).show();
            return;
        }

        cDocente controladorDoc = new cDocente(this);
        int idDocente = docenteEncontrado.getIdDocente();

        // Llama al método deleteDocente del controlador para eliminar al docente por ID
        controladorDoc.deleteDocente(idDocente);

        // Verificar si la eliminación fue exitosa
        Toast.makeText(this, "Docente eliminado correctamente", Toast.LENGTH_SHORT).show();
        txtListDocEliminar.setText(""); // Limpia los datos después de eliminar
        docenteEncontrado = null;
    }

    public void fmrSalirEliDoc(View view) {
        finish();
    }
}