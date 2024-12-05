package com.dam.ie_palas_app.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import  com.dam.ie_palas_app.controlador.cLugar;
import com.dam.ie_palas_app.controlador.cEstudiante;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.dam.ie_palas_app.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class frmInsertarLugar extends FragmentActivity implements OnMapReadyCallback {

    private EditText txtCodEstudiante;
    private EditText txtNomLug;
    private Button btnGuardarVisita, btnCancelarVis;
    private cLugar lugarController;
    private cEstudiante estudianteController;
    private GoogleMap mMap;
    private LatLng selectedLocation;

    // ExecutorService para operaciones en segundo plano
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_insertar_lugar);

        // Inicializar los controles
        txtCodEstudiante = findViewById(R.id.txtCodEstudiante);
        txtNomLug = findViewById(R.id.txtNomLug);
        btnGuardarVisita = findViewById(R.id.btnGuardarVisita);
        btnCancelarVis = findViewById(R.id.btnCancelarVis);

        // Inicializar los controladores
        lugarController = new cLugar(this);
        estudianteController = new cEstudiante(this);

        // Obtener el ID del estudiante pasado por Intent
        int estudianteId = getIntent().getIntExtra("estudianteId", -1);

        // Asignar el estudianteId al EditText
        if (estudianteId != -1) {
            txtCodEstudiante.setText(String.valueOf(estudianteId));
        } else {
            // En caso de que no se haya pasado el estudianteId, manejar la situación
            Toast.makeText(this, "ID del estudiante no disponible", Toast.LENGTH_SHORT).show();
        }

        // Configurar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapContainer);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Configurar los listeners para los botones
        btnGuardarVisita.setOnClickListener(v -> guardarLugar());
        btnCancelarVis.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        // Centrar el mapa en una ubicación inicial
        LatLng sitio = new LatLng(-12.1807885, -77.0021045);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sitio, 16.75f));

        // Listener para el clic en el mapa
        mMap.setOnMapClickListener(latLng -> {
            mMap.clear(); // Limpiar cualquier marcador anterior
            selectedLocation = latLng; // Actualizar la ubicación seleccionada

            // Agregar un marcador en la ubicación seleccionada
            mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación seleccionada"));
        });
    }

    private void guardarLugar() {
        // Verificar si una ubicación fue seleccionada en el mapa
        if (selectedLocation == null) {
            Toast.makeText(this, "Por favor, selecciona una ubicación en el mapa.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener los valores de los campos
        String nombreLugar = txtNomLug.getText().toString();
        String estudianteIdStr = txtCodEstudiante.getText().toString();

        // Validar que los campos obligatorios no estén vacíos
        if (estudianteIdStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el ID del estudiante.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nombreLugar.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el nombre del lugar.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convertir el ID del estudiante a tipo int
            int estudianteId = Integer.parseInt(estudianteIdStr);
            double latitud = selectedLocation.latitude;
            double longitud = selectedLocation.longitude;

            // Usar ExecutorService para realizar la operación en segundo plano
            executorService.execute(() -> {
                // Este código se ejecutará en un hilo secundario
                lugarController.insertarLugar(estudianteId, nombreLugar, latitud, longitud);

                // Después de la operación, actualizar la UI en el hilo principal
                runOnUiThread(() -> {
                    // Mostrar mensaje de éxito
                    Toast.makeText(frmInsertarLugar.this, "Lugar guardado correctamente.", Toast.LENGTH_SHORT).show();
                    // Limpiar los campos después de guardar
                    limpiarCampos();
                });
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error al convertir el valor. Asegúrate de que los datos son correctos.", Toast.LENGTH_SHORT).show();
        }
    }


    private void limpiarCampos() {
        txtNomLug.setText("");
        selectedLocation = null;
        if (mMap != null) {
            mMap.clear(); // Limpiar el marcador del mapa
        }
    }

    public void Cancelar(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}

