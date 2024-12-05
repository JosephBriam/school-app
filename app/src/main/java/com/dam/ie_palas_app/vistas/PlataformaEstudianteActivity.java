package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

public class PlataformaEstudianteActivity extends AppCompatActivity {

    private BasedeDatos basedeDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plataforma_estudiante);

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        String estudianteId = intent.getStringExtra("estudianteId");

        // Conectar los campos del layout
        EditText txtNombre = findViewById(R.id.txtnombrecom);
        EditText txtApellidos = findViewById(R.id.txtApellido);
        EditText txtGrado = findViewById(R.id.txtgrado);
        EditText txtUsuario = findViewById(R.id.txtusuario);

        // Cargar datos usando un hilo
        cargarDatosEnHilo(usuario, txtNombre, txtApellidos, txtGrado, txtUsuario);

        // Configurar manejo de las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para cargar datos en un hilo separado
    private void cargarDatosEnHilo(String usuario, EditText txtNombre, EditText txtApellidos, EditText txtGrado, EditText txtUsuario) {
        new Thread(() -> {
            Log.d("Debug", "Buscando estudiante con usuario: " + usuario);
            SQLiteDatabase db = basedeDatos.getReadableDatabase();
            String query = "SELECT Nombres, Apellidos, Grado, Usuario FROM " + BasedeDatos.TABLE_ESTUDIANTES + " WHERE Usuario = ?";
            Cursor cursor = db.rawQuery(query, new String[]{usuario});

            if (cursor.moveToFirst()) {
                String nombre = cursor.getString(0); // Nombres
                String apellidos = cursor.getString(1); // Apellidos
                String grado = cursor.getString(2); // Grado
                String user = cursor.getString(3); // Usuario

                // Actualizar la UI en el hilo principal
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    txtNombre.setText(nombre);
                    txtApellidos.setText(apellidos);
                    txtGrado.setText(grado);
                    txtUsuario.setText(user);
                });
            } else {
                Log.e("DB_ERROR", "Usuario no encontrado: " + usuario);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show());
            }

            cursor.close();
            db.close();
        }).start();
    }

    // Otros métodos no cambiados...
    public void VerCursos(View view) {
        Intent intent = new Intent(PlataformaEstudianteActivity.this, CursosEstudiante.class);
        int estudianteId = getIntent().getIntExtra("estudianteId", -1);
        intent.putExtra("estudianteId", estudianteId);
        startActivity(intent);
    }

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
        finish();
    }

    public void verDirecciones(View view) {
        Intent x = new Intent(this, frmGestionDirecciones.class);

        // Obtener el estudianteId desde el Intent recibido
        int estudianteId = getIntent().getIntExtra("estudianteId", -1);
        x.putExtra("estudianteId", estudianteId); // Pasar estudianteId al formulario

        startActivity(x);
    }

}
