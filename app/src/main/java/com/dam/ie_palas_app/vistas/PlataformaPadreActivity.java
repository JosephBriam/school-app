package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

public class PlataformaPadreActivity extends AppCompatActivity {

    private BasedeDatos basedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plataforma_padre);

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        // Obtener el usuario de la sesión o pasado por Intent
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        int padreId = intent.getIntExtra("padreId", -1); // Recibir el ID del padre

        // Si el padreId es -1, lo obtenemos de la base de datos
        if (padreId == -1) {
            padreId = obtenerPadreIdDesdeBD(usuario);
        }

        Log.d("Debug", "Padre ID final: " + padreId);

        // Conectar los campos del layout
        EditText txtPadreDe = findViewById(R.id.textViewPadreDe);
        EditText txtApellido = findViewById(R.id.txtApellido);
        EditText txtGradoHijos = findViewById(R.id.txtgradoHijos);


        cargarDatosPadre(padreId, txtPadreDe, txtApellido, txtGradoHijos);
    }

    // Método para obtener el ID del padre desde la base de datos
    private int obtenerPadreIdDesdeBD(String usuario) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        String query = "SELECT IdPadre FROM " + BasedeDatos.TABLE_PADRES + " WHERE Usuario = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario});

        int padreId = -1; // Valor predeterminado
        if (cursor.moveToFirst()) {
            padreId = cursor.getInt(0); // Obtener el ID del padre
        }
        cursor.close();
        db.close();
        return padreId;
    }

    // Método para cargar los datos del padre y los hijos
    private void cargarDatosPadre(int padreId, EditText txtPadreDe, EditText txtApellido, EditText txtGradoHijos) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();

        // Consultar el nombre, apellido y usuario del padre
        String queryPadre = "SELECT Nombres, Apellidos, Usuario FROM " + BasedeDatos.TABLE_PADRES + " WHERE IdPadre = ?";
        Cursor cursorPadre = db.rawQuery(queryPadre, new String[]{String.valueOf(padreId)});

        if (cursorPadre.moveToFirst()) {
            txtPadreDe.setText(cursorPadre.getString(0)); // Nombres del padre
            txtApellido.setText(cursorPadre.getString(1)); // Apellidos del padre

        } else {
            Toast.makeText(this, "Padre no encontrado", Toast.LENGTH_SHORT).show();
        }
        cursorPadre.close();

        // Consultar los hijos relacionados con este padre
        String queryHijos = "SELECT e.Nombres, e.Apellidos, g.Nombre FROM " + BasedeDatos.TABLE_ESTUDIANTES + " e " +
                "JOIN " + BasedeDatos.TABLE_GRADOS + " g ON e.Grado = g.IdGrado " +
                "JOIN " + BasedeDatos.TABLE_PADRES_ESTUDIANTES + " pe ON e.EstudianteId = pe.EstudianteId " +
                "WHERE pe.PadreId = ?";

        Cursor cursorHijos = db.rawQuery(queryHijos, new String[]{String.valueOf(padreId)});
        StringBuilder hijosInfo = new StringBuilder();
        StringBuilder gradosInfo = new StringBuilder();

        while (cursorHijos.moveToNext()) {
            String nombreHijo = cursorHijos.getString(0) + " " + cursorHijos.getString(1);
            String gradoHijo = cursorHijos.getString(2);
            hijosInfo.append(nombreHijo).append(", ");
            gradosInfo.append(gradoHijo).append(", ");
        }

        // Eliminar la última coma y espacio
        if (hijosInfo.length() > 0) {
            hijosInfo.setLength(hijosInfo.length() - 2);
            gradosInfo.setLength(gradosInfo.length() - 2);
        }

        txtGradoHijos.setText(gradosInfo.toString()); // Mostrar los grados de los hijos
        txtPadreDe.setText(hijosInfo.toString()); // Mostrar los nombres de los hijos

        cursorHijos.close();
        db.close();
    }

    // Método para ver los cursos
    public void VerCursos(View view) {
        Intent intent = new Intent(PlataformaPadreActivity.this, CursosPadreActivity.class);
        int padreId = getIntent().getIntExtra("padreId", -1);
        intent.putExtra("padreId", padreId);
        startActivity(intent);
    }

    // Método para ver los anuncios
    public void VerAnuncios(View view) {
        Intent intent = new Intent(this, AnuncioE.class);
        startActivity(intent);
    }

    public void openYouTube(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@i.e.p.palasatenea9839?si=Muuww66EBkZ1IPlm"));
        startActivity(intent);
    }

    public void openInstagram(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/iep_palas_atenea?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=="));
        startActivity(intent);
    }

    public void openFacebook(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/PalasAteneaOficial"));
        startActivity(intent);
    }

    public void Regresar(View view) {
        finish(); // Cierra la actividad actual
    }
}
