package com.dam.ie_palas_app.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SesionEstudianteActivity extends AppCompatActivity {


    private EditText etUsuario;
    private EditText etContraseña;
    private Button btnIniciarSesion;
    private TextView tvOlvidasteContraseña;

    // Variable para acceder a la base de datos
    private BasedeDatos basedeDatos;

    // ExecutorService para tareas en segundo plano
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sesion_estudiante);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar la base de datos
        basedeDatos = new BasedeDatos(this);

        etUsuario = findViewById(R.id.txtusuario2);
        etContraseña = findViewById(R.id.txtcontraseña2);
        btnIniciarSesion = findViewById(R.id.btnIniciarsesion2);
        tvOlvidasteContraseña = findViewById(R.id.txtOlvidasteContraseña2);

        btnIniciarSesion.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String contraseña = etContraseña.getText().toString();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(SesionEstudianteActivity.this, "Por favor, ingresa usuario y contraseña.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar inicio de sesión en un hilo de fondo
            executorService.execute(() -> validarInicioSesionEstudiante(usuario, contraseña));
        });

        tvOlvidasteContraseña.setOnClickListener(v -> {
            Intent intent = new Intent(SesionEstudianteActivity.this, CambiarContrasenaActivity.class);
            intent.putExtra("isAdmin", false); // Indica que es un estudiante
            startActivity(intent);
        });
    }

    public void validarInicioSesionEstudiante(String usuario, String contraseña) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        String query = "SELECT EstudianteId FROM " + BasedeDatos.TABLE_ESTUDIANTES + " WHERE Usuario = ? AND Contraseña = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario, contraseña});

        if (cursor.moveToFirst()) {
            int estudianteId = cursor.getInt(0); // Obtener el ID del estudiante
            Log.d("Debug", "Estudiante ID desde la base de datos: " + estudianteId);

            // Pasar al hilo principal para interactuar con la UI
            mainHandler.post(() -> {
                Intent intent = new Intent(SesionEstudianteActivity.this, PlataformaEstudianteActivity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("estudianteId", estudianteId); // Pasar el ID del estudiante
                startActivity(intent);
                finish(); // Cerrar la actividad actual
            });
        } else {
            // Notificar error en el hilo principal
            mainHandler.post(() ->
                    Toast.makeText(SesionEstudianteActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
            );
        }

        cursor.close();
        db.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Cerrar el executor para liberar recursos
    }

    public void RegresarPaginaAnterior(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}
