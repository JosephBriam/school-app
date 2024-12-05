package com.dam.ie_palas_app.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.controlador.cApoderado;
import com.dam.ie_palas_app.controlador.cDocente;
import com.dam.ie_palas_app.controlador.cEstudiante;
import com.dam.ie_palas_app.dao.BasedeDatos;
import com.dam.ie_palas_app.modelo.Apoderado;
import com.dam.ie_palas_app.modelo.Docente;
import com.dam.ie_palas_app.modelo.Estudiante;

import java.util.ArrayList;

public class frmAsigPadreEstu extends AppCompatActivity {

    private EditText txtcodEstuAsig, txtcodPadreAsig, listaEstAsig, listaPadreAsig;
    private cApoderado controlador;
    private Estudiante estudianteEncontrado;
    private Apoderado apoderadoEncontrado;
    private BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_asig_padre_estu);

        txtcodEstuAsig=findViewById(R.id.txtcodEstuAsig);
        txtcodPadreAsig=findViewById(R.id.txtcodPadreAsig);
        listaEstAsig = findViewById(R.id.listaEstAsig);
        listaPadreAsig= findViewById(R.id.listaPadreAsig);
        controlador = new cApoderado(this);
        basedeDatos = new BasedeDatos(this); // Inicializa la base de datos

    }
    public void buscarEstAsig(View view) {
        String idStr = txtcodEstuAsig.getText().toString().trim();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del Estudiante", Toast.LENGTH_LONG).show();
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un ID válido", Toast.LENGTH_LONG).show();
            return;
        }

        cEstudiante controladorEst = new cEstudiante(this);
        ArrayList<Estudiante> estudiantes = controladorEst.obtenerEstudiante();

        // Busca el estudiante con el ID especificado
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getEstudianteId() == id) {
                estudianteEncontrado = estudiante;

                // Muestra los datos del estudiante encontrado en el EditText listaEstAsig
                String datosEstudiante = "Nombre: " + estudiante.getNombres() + "\n" +
                        "Apellido: " + estudiante.getApellidos();
                listaEstAsig.setText(datosEstudiante);

                Toast.makeText(this, "Estudiante encontrado", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        estudianteEncontrado = null; // No encontrado
        txtcodEstuAsig.setText("");
        listaEstAsig.setText("");
        Toast.makeText(this, "Estudiante no encontrado", Toast.LENGTH_LONG).show();
    }

    public void buscarApoAsig(View view) {
        String codigoStr = txtcodPadreAsig.getText().toString().trim();

        if (codigoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del Apoderado", Toast.LENGTH_LONG).show();
            return;
        }
        int codigo;
        try {
            codigo = Integer.parseInt(codigoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un ID válido", Toast.LENGTH_LONG).show();
            return;
        }

        // Busca el apoderado con el ID especificado
        Apoderado apoderado = controlador.obtenerPorId(codigo);

        if (apoderado != null) {
            apoderadoEncontrado = apoderado; // Asigna el apoderado encontrado a la variable

            // Muestra los datos del apoderado encontrado en el EditText listaPadreAsig
            String datosApoderado = "Nombre: " + apoderado.getNombres() + "\n" +
                    "Apellido: " + apoderado.getApellidos();
            listaPadreAsig.setText(datosApoderado);

            Toast.makeText(this, "Apoderado encontrado", Toast.LENGTH_SHORT).show();
        } else {
            txtcodPadreAsig.setText("");
            listaPadreAsig.setText(""); // Limpia el campo si no se encuentra el apoderado
            Toast.makeText(this, "Apoderado no encontrado", Toast.LENGTH_LONG).show();
        }
    }

    public void asignarEstudianteApoderado(View view) {
        if (estudianteEncontrado == null) {
            Toast.makeText(this, "Por favor, busque y seleccione un estudiante", Toast.LENGTH_LONG).show();
            return;
        }

        if (apoderadoEncontrado == null) {
            Toast.makeText(this, "Por favor, busque y seleccione un apoderado", Toast.LENGTH_LONG).show();
            return;
        }

        SQLiteDatabase db = basedeDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("PadreId", apoderadoEncontrado.getIdPadre());
        values.put("EstudianteId", estudianteEncontrado.getEstudianteId());

        long resultado = db.insert(BasedeDatos.TABLE_PADRES_ESTUDIANTES, null, values);

        if (resultado != -1) {
            Toast.makeText(this, "Relación asignada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al asignar relación", Toast.LENGTH_LONG).show();
        }

        db.close();
    }
    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }

}