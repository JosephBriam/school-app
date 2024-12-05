package com.dam.ie_palas_app.vistas;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.ie_palas_app.R;
import com.dam.ie_palas_app.dao.BasedeDatos;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CrearAnuncio_admin extends AppCompatActivity {

    private EditText tituloAnuncio, descripcionAnuncio;
    private BasedeDatos basedeDatos;
    private ListView listViewAnuncios;
    private long selectedAnuncioId = -1; // Para saber si estamos modificando un anuncio existente
    private long fechaAnuncio; // Para almacenar la fecha seleccionada
    private TextView tvFechaAnuncio; // TextView para mostrar la fecha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_anuncio_admin);

        tituloAnuncio = findViewById(R.id.tituloAnuncio);
        descripcionAnuncio = findViewById(R.id.descripcionAnuncio);
        listViewAnuncios = findViewById(R.id.listViewAnuncios);
        tvFechaAnuncio = findViewById(R.id.tvFechaAnuncio);
        Button btnCrearAnuncio = findViewById(R.id.btnCrearAnuncio);
        Button btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha);

        basedeDatos = new BasedeDatos(this);

        // Mostrar los anuncios existentes en el ListView
        cargarAnuncios();

        btnCrearAnuncio.setOnClickListener(v -> {
            if (selectedAnuncioId == -1) {
                crearAnuncio();
            } else {
                modificarAnuncio();
            }
        });

        // Configurar el botón para seleccionar la fecha
        btnSeleccionarFecha.setOnClickListener(v -> showDatePickerDialog());

        // Configurar el ListView para seleccionar un anuncio y permitir editarlo o eliminarlo
        listViewAnuncios.setOnItemClickListener((parent, view, position, id) -> {
            selectedAnuncioId = id;
            cargarAnuncioSeleccionado(id);
        });

        listViewAnuncios.setOnItemLongClickListener((parent, view, position, id) -> {
            eliminarAnuncio(id);
            return true;
        });
    }

    private void cargarAnuncios() {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT IdAnuncio as _id, Titulo, Descripcion FROM Anuncio", null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"Titulo", "Descripcion"},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );

        listViewAnuncios.setAdapter(adapter);
    }

    private void crearAnuncio() {
        String titulo = tituloAnuncio.getText().toString();
        String descripcion = descripcionAnuncio.getText().toString();

        if (titulo.isEmpty() || descripcion.isEmpty() || fechaAnuncio == 0) {
            Toast.makeText(this, "Por favor, llena todos los campos y selecciona una fecha", Toast.LENGTH_SHORT).show();
            return;  // Asegúrate de que no esté vacío
        }

        SQLiteDatabase db = basedeDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Titulo", titulo);
        values.put("Descripcion", descripcion);
        values.put("Fecha", String.valueOf(fechaAnuncio)); // Guarda la fecha seleccionada

        long result = db.insert("Anuncio", null, values);

        if (result == -1) {
            Toast.makeText(this, "Error al crear anuncio", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Anuncio creado", Toast.LENGTH_SHORT).show();
            cargarAnuncios();  // Refrescar la lista de anuncios
        }
    }

    private void cargarAnuncioSeleccionado(long id) {
        SQLiteDatabase db = basedeDatos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Titulo, Descripcion FROM Anuncio WHERE IdAnuncio = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            String titulo = cursor.getString(0);
            String descripcion = cursor.getString(1);
            tituloAnuncio.setText(titulo);
            descripcionAnuncio.setText(descripcion);
        }
        cursor.close();
    }

    private void modificarAnuncio() {
        String titulo = tituloAnuncio.getText().toString();
        String descripcion = descripcionAnuncio.getText().toString();

        SQLiteDatabase db = basedeDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Titulo", titulo);
        values.put("Descripcion", descripcion);
        db.update("Anuncio", values, "IdAnuncio = ?", new String[]{String.valueOf(selectedAnuncioId)});

        Toast.makeText(this, "Anuncio modificado", Toast.LENGTH_SHORT).show();
        cargarAnuncios(); // Refrescar lista de anuncios

        // Limpiar selección
        selectedAnuncioId = -1;
        tituloAnuncio.setText("");
        descripcionAnuncio.setText("");
        fechaAnuncio = 0; // Reiniciar fecha
        tvFechaAnuncio.setText("Fecha: Ninguna"); // Reiniciar texto de fecha
    }

    private void eliminarAnuncio(long id) {
        SQLiteDatabase db = basedeDatos.getWritableDatabase();
        db.delete("Anuncio", "IdAnuncio = ?", new String[]{String.valueOf(id)});
        Toast.makeText(this, "Anuncio eliminado", Toast.LENGTH_SHORT).show();
        cargarAnuncios(); // Refrescar lista de anuncios
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            fechaAnuncio = new GregorianCalendar(selectedYear, selectedMonth, selectedDay).getTimeInMillis();
            tvFechaAnuncio.setText("Fecha: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);
        datePickerDialog.show();
    }

    public void RegresarPaginaAnterior(View view) {
        finish();  // Cierra la actividad actual y regresa a la anterior.
    }
}
