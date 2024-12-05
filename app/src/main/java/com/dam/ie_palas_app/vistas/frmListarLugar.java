package com.dam.ie_palas_app.vistas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.dam.ie_palas_app.R;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.ie_palas_app.controlador.cLugar;
import com.dam.ie_palas_app.modelo.Lugar;
import com.dam.ie_palas_app.controlador.cEstudiante;
import java.util.List;

public class frmListarLugar extends AppCompatActivity {

    private EditText etCodPerList;
    private Button btnBuscarListPer, btnSalirListLug, btnVerMapa;
    private TableLayout tableLayoutLugares;

    private cLugar lugarController;
    private cEstudiante estudianteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frm_listar_lugar);

        // Inicializar los controles
        etCodPerList = findViewById(R.id.etCodPerList);
        btnBuscarListPer = findViewById(R.id.btnBuscarListPer);
        btnSalirListLug = findViewById(R.id.btnSalirListLug);
        btnVerMapa = findViewById(R.id.btnViewMap);
        tableLayoutLugares = findViewById(R.id.tableLayoutLugares);

        // Inicializar los controladores
        lugarController = new cLugar(this);
        estudianteController = new cEstudiante(this);

        // Obtener el ID del estudiante pasado por Intent
        int estudianteId = getIntent().getIntExtra("estudianteId", -1);

        // Asignar el estudianteId al EditText automáticamente
        if (estudianteId != -1) {
            etCodPerList.setText(String.valueOf(estudianteId)); // Actualiza el EditText con el estudianteId
        } else {
            // En caso de que no se haya pasado el estudianteId
            Toast.makeText(this, "ID del estudiante no disponible", Toast.LENGTH_SHORT).show();
        }

        // Configurar el botón de buscar
        btnBuscarListPer.setOnClickListener(v -> listarVisitas());
        btnVerMapa.setOnClickListener(v -> verMap());

        // Configurar el botón de salir
        btnSalirListLug.setOnClickListener(v -> finish());
    }

    private void listarVisitas() {
        // Verificar que el campo de código de persona no esté vacío
        String codigoEstudiante = etCodPerList.getText().toString();

        if (codigoEstudiante.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el ID del estudiante.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convertir código de persona a entero
            int codEstudiante = Integer.parseInt(codigoEstudiante);

            // Ejecutar la tarea en un hilo
            new Thread(() -> {
                try {
                    // Obtener los lugares visitados por la persona seleccionada
                    List<Lugar> lugares = lugarController.obtenerLugarPorPersona(codEstudiante);

                    // Actualizar la UI en el hilo principal
                    runOnUiThread(() -> {
                        // Limpiar las filas antiguas antes de agregar nuevas
                        tableLayoutLugares.removeViews(1, Math.max(0, tableLayoutLugares.getChildCount() - 1));

                        if (lugares == null || lugares.isEmpty()) {
                            Toast.makeText(this, "No hay visitas registradas para esta persona.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Agregar cada lugar como una fila en la tabla
                            for (Lugar lugar : lugares) {
                                TableRow row = new TableRow(this);

                                TextView codigoTextView = new TextView(this);
                                codigoTextView.setText(String.valueOf(lugar.getCodigo()));
                                codigoTextView.setPadding(8, 8, 8, 8);

                                TextView lugarTextView = new TextView(this);
                                lugarTextView.setText(lugar.getLugar());
                                lugarTextView.setPadding(8, 8, 8, 8);

                                TextView latitudTextView = new TextView(this);
                                latitudTextView.setText(String.valueOf(lugar.getLatitud()));
                                latitudTextView.setPadding(8, 8, 8, 8);

                                TextView longitudTextView = new TextView(this);
                                longitudTextView.setText(String.valueOf(lugar.getLongitud()));
                                longitudTextView.setPadding(8, 8, 8, 8);

                                // Agregar todos los TextViews a la fila
                                row.addView(codigoTextView);
                                row.addView(lugarTextView);
                                row.addView(latitudTextView);
                                row.addView(longitudTextView);

                                // Agregar la fila a la tabla
                                tableLayoutLugares.addView(row);
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Ocurrió un error al listar los lugares.", Toast.LENGTH_SHORT).show();
                    });
                    e.printStackTrace();
                }
            }).start();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El código de la persona no es válido.", Toast.LENGTH_SHORT).show();
        }
    }

    public void verMap() {
        Intent x = new Intent(this, frmShowMap.class);
        startActivity(x);
    }

    public void Regresar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}
