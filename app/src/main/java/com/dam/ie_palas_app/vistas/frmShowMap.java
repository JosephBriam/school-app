package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.dam.ie_palas_app.R;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import  com.dam.ie_palas_app.controlador.cLugar;
import com.dam.ie_palas_app.controlador.cEstudiante;
import  com.dam.ie_palas_app.modelo.Lugar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;

public class frmShowMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner spinnerCodPerUbica;
    private cLugar lugarController;
    private cEstudiante estudianteController;

    // ExecutorService para operaciones en segundo plano
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_show_map);

        // Inicializar los controladores
        lugarController = new cLugar(this);
        estudianteController = new cEstudiante(this);

        // Inicializar el Spinner
        spinnerCodPerUbica = findViewById(R.id.spinnerCodPerUbica);

        // Cargar códigos de estudiantes en el Spinner
        cargarCodigosEstudiantes();

        // Configurar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btnRegresar = findViewById(R.id.btnSalirUbi);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Finaliza la actividad actual
            }
        });
    }

    private void cargarCodigosEstudiantes() {
        // Ejecutar la carga de códigos en segundo plano
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> codigosEstudiantes = estudianteController.obtenerCodigosEstudiantes();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Actualizar el Spinner en el hilo principal
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(frmShowMap.this, android.R.layout.simple_spinner_item, codigosEstudiantes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCodPerUbica.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        // Centrar el mapa en una ubicación inicial
        LatLng sitio = new LatLng(-12.1807885, -77.0021045);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sitio, 16.75f));
    }

    // Método para mostrar ubicaciones de la estudiante seleccionada
    public void marcar(View view) {
        mMap.clear(); // Limpiar los marcadores actuales del mapa

        // Obtener el código de la estudiante seleccionada en el Spinner
        String codigoPersonaStr = (String) spinnerCodPerUbica.getSelectedItem();
        if (codigoPersonaStr == null || codigoPersonaStr.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona un código de estudiante.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int codPersona = Integer.parseInt(codigoPersonaStr);

            // Ejecutar la obtención de lugares en segundo plano
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // Obtener los lugares visitados por la estudiante
                    List<Lugar> lugaresVisitados = lugarController.obtenerLugarPorPersona(codPersona);

                    if (lugaresVisitados.isEmpty()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(frmShowMap.this, "No hay ubicaciones registradas para esta estudiante.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    // Añadir un marcador para cada lugar visitado
                    for (Lugar lugar : lugaresVisitados) {
                        LatLng ubicacion = new LatLng(lugar.getLatitud(), lugar.getLongitud());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.addMarker(new MarkerOptions()
                                        .position(ubicacion)
                                        .title("Lugar: " + lugar.getLugar())
                                        .snippet("Código: " + lugar.getCodigo()));
                            }
                        });
                    }

                    // Centrar el mapa en la primera ubicación encontrada
                    LatLng primeraUbicacion = new LatLng(lugaresVisitados.get(0).getLatitud(), lugaresVisitados.get(0).getLongitud());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(primeraUbicacion, 14.0f));
                        }
                    });
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Código de estudiante no válido.", Toast.LENGTH_SHORT).show();
        }
    }
}