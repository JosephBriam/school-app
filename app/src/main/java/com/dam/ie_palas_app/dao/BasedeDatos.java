package com.dam.ie_palas_app.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BasedeDatos extends SQLiteOpenHelper{
    // Nombre de la base de datos y versión
    public static final String DATABASE_NAME = "PalasAtenea.db";
    public static final int DATABASE_VERSION = 2;

    // Definir las tablas
    public static final String TABLE_ESTUDIANTES = "Estudiantes";
    public static final String TABLE_CURSOS = "Cursos";
    public static final String TABLE_ASISTENCIAS = "Asistencias";
    public static final String TABLE_GRADOS = "Grados";
    public static final String TABLE_ESTUDIANTES_CURSOS = "EstudiantesCursos";
    public static final String TABLE_ANUNCIO = "Anuncio";
    public static final String TABLE_DOCENTE = "Docente";
    public static final String TABLE_PADRES = "Padres";
    public static final String TABLE_PADRES_ESTUDIANTES = "PadresEstudiantes";
    public static final String TABLE_ADMINISTRADOR = "Administrador";
    public static final String TABLE_LUGARES = "Lugares";

    // Crear tablas
    public static final String CREATE_TABLE_ESTUDIANTES = "CREATE TABLE " + TABLE_ESTUDIANTES + " ("
            + "EstudianteId INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "Nombres TEXT NOT NULL, "
            + "Apellidos TEXT NOT NULL, "
            + "Usuario TEXT NOT NULL, "
            + "Contraseña TEXT NOT NULL, "
            + "Grado INTEGER NOT NULL);";

    public static final String CREATE_TABLE_LUGARES = "CREATE TABLE " + TABLE_LUGARES + " ("
            + "codigo INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "cod_estudiante INTEGER, "
            + "lugar TEXT NOT NULL, "
            + "latitud REAL NOT NULL, "
            + "longitud REAL NOT NULL, "
            + "FOREIGN KEY(cod_estudiante) REFERENCES " + TABLE_ESTUDIANTES + "(EstudianteId));";

    public static final String CREATE_TABLE_CURSOS = "CREATE TABLE " + TABLE_CURSOS + " ("
            + "IdCurso INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "NombreCurso TEXT NOT NULL);";

    public static final String CREATE_TABLE_GRADOS = "CREATE TABLE " + TABLE_GRADOS + " ("
            + "IdGrado INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "Nombre TEXT NOT NULL);";

    public static final String CREATE_TABLE_ESTUDIANTES_CURSOS = "CREATE TABLE " + TABLE_ESTUDIANTES_CURSOS + " ("
            + "EstudianteId INTEGER, "
            + "CursoId INTEGER, "
            + "PRIMARY KEY (EstudianteId, CursoId), "
            + "FOREIGN KEY (EstudianteId) REFERENCES " + TABLE_ESTUDIANTES + "(EstudianteId), "
            + "FOREIGN KEY (CursoId) REFERENCES " + TABLE_CURSOS + "(IdCurso));";

    public static final String CREATE_TABLE_ASISTENCIAS = "CREATE TABLE " + TABLE_ASISTENCIAS + " ("
            + "Id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "EstudianteId INTEGER NOT NULL, "
            + "Fecha TEXT NOT NULL, "
            + "Estado TEXT NOT NULL, "
            + "GradoID INTEGER NOT NULL, "
            + "CursoID INTEGER NOT NULL, "
            + "FOREIGN KEY (EstudianteId) REFERENCES " + TABLE_ESTUDIANTES + "(EstudianteId), "
            + "FOREIGN KEY (GradoID) REFERENCES " + TABLE_GRADOS + "(IdGrado), "
            + "FOREIGN KEY (CursoID) REFERENCES " + TABLE_CURSOS + "(IdCurso));";

    // Tabla para los anuncios
    public static final String CREATE_TABLE_ANUNCIO = "CREATE TABLE " + TABLE_ANUNCIO + " ("
            + "IdAnuncio INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "Titulo TEXT NOT NULL, "
            + "Descripcion TEXT NOT NULL, "
            + "Fecha TEXT NOT NULL);"; // Puedes cambiar el formato de fecha si lo prefieres

    // Tabla para los docentes
    public static final String CREATE_TABLE_DOCENTE = "CREATE TABLE " + TABLE_DOCENTE + " ("
            + "IdDocente INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "Nombres TEXT NOT NULL, "
            + "Apellidos TEXT NOT NULL, "
            + "Usuario TEXT NOT NULL, "
            + "Contraseña TEXT NOT NULL);";

    // Tabla para los administradores
    public static final String CREATE_TABLE_ADMINISTRADOR = "CREATE TABLE " + TABLE_ADMINISTRADOR + " ("
            + "IdAdmin INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "Nombres TEXT NOT NULL, "
            + "Apellidos TEXT NOT NULL, "
            + "Usuario TEXT NOT NULL, "
            + "Contraseña TEXT NOT NULL);";
    // Tabla para los padres
    public static final String CREATE_TABLE_PADRES = "CREATE TABLE " + TABLE_PADRES + " ("
            + "IdPadre INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "Nombres TEXT NOT NULL, "
            + "Apellidos TEXT NOT NULL, "
            + "Usuario TEXT NOT NULL, "
            + "Contraseña TEXT NOT NULL);";

    // Tabla de relación Padres-Estudiantes
    public static final String CREATE_TABLE_PADRES_ESTUDIANTES = "CREATE TABLE " + TABLE_PADRES_ESTUDIANTES + " ("
            + "PadreId INTEGER, "
            + "EstudianteId INTEGER, "
            + "PRIMARY KEY (PadreId, EstudianteId), "
            + "FOREIGN KEY (PadreId) REFERENCES " + TABLE_PADRES + "(IdPadre), "
            + "FOREIGN KEY (EstudianteId) REFERENCES " + TABLE_ESTUDIANTES + "(EstudianteId));";

    public BasedeDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas en la base de datos
        db.execSQL(CREATE_TABLE_ESTUDIANTES);
        db.execSQL(CREATE_TABLE_LUGARES);
        db.execSQL(CREATE_TABLE_CURSOS);
        db.execSQL(CREATE_TABLE_GRADOS);
        db.execSQL(CREATE_TABLE_ESTUDIANTES_CURSOS);
        db.execSQL(CREATE_TABLE_ASISTENCIAS);
        db.execSQL(CREATE_TABLE_ANUNCIO);
        db.execSQL(CREATE_TABLE_DOCENTE);
        db.execSQL(CREATE_TABLE_ADMINISTRADOR);
        db.execSQL(CREATE_TABLE_PADRES);
        db.execSQL(CREATE_TABLE_PADRES_ESTUDIANTES);

        // Insertar los datos iniciales
        insertarDatosIniciales(db);
    }
    public void insertarDatosIniciales(SQLiteDatabase db) {
        // Insertar cursos
        insertarCurso(db, "Matemáticas");
        insertarCurso(db, "Deportes");
        insertarCurso(db, "Letras");
        insertarCurso(db, "Ciencias");
        insertarCurso(db, "Inglés");

        // Insertar grados
        insertarGrado(db, "Primero");
        insertarGrado(db, "Segundo");
        insertarGrado(db, "Tercero");
        insertarGrado(db, "Cuarto");
        insertarGrado(db, "Quinto");
        insertarGrado(db, "Sexto");

        // Insertar estudiantes
        insertarEstudiante(db, "Juan", "Pérez", "PA001", "pass123", 1);
        insertarEstudiante(db, "Camilo", "García", "PA002", "pass123", 1);
        insertarEstudiante(db, "Luis", "Martínez", "PA003", "pass123", 1);
        insertarEstudiante(db, "Marta", "Rodríguez", "PA004", "pass123", 1);
        insertarEstudiante(db, "Carlos", "Lopez", "PA005", "pass123", 1);
        insertarEstudiante(db, "Laura", "Fernández", "PA006", "pass123", 1);
        insertarEstudiante(db, "Miguel", "Gómez", "PA007", "pass123", 1);
        insertarEstudiante(db, "Sofía", "Ramírez", "PA008", "pass123", 1);

        insertarEstudiante(db, "Valeria", "Castro", "PA009", "pass123", 2);
        insertarEstudiante(db, "Pedro", "Vargas", "PA0010", "pass123", 2);
        insertarEstudiante(db, "Gabriela", "Morales", "PA0011", "pass123", 2);
        insertarEstudiante(db, "Ricardo", "Ortiz", "PA0012", "pass123", 2);
        insertarEstudiante(db, "Omar", "Álvarez", "PA0013", "pass123", 2);
        insertarEstudiante(db, "Felipe", "Cruz", "PA0014", "pass123", 2);

        insertarEstudiante(db, "Tomás", "Torres", "PA0015", "pass123", 3);
        insertarEstudiante(db, "Daniela", "Jiménez", "PA0016", "pass123", 3);
        insertarEstudiante(db, "Andrés", "Paredes", "PA0017", "pass123", 3);
        insertarEstudiante(db, "Isabella", "Jiménez", "PA0018", "pass123", 3);
        insertarEstudiante(db, "Jorge", "Martínez", "PA0019", "pass123", 3);
        insertarEstudiante(db, "Natalia", "Reyes", "PA0020", "pass123", 3);
        insertarEstudiante(db, "Sebastián", "Herrera", "PA0021", "pass123", 3);
        insertarEstudiante(db, "Paola", "Guerrero", "PA0022", "pass123", 3);

        insertarEstudiante(db, "Mauricio", "Pérez", "PA0023", "pass123", 4);
        insertarEstudiante(db, "Carla", "Vásquez", "PA0024", "pass123", 4);
        insertarEstudiante(db, "Simón", "García", "PA0025", "pass123", 4);
        insertarEstudiante(db, "Valentina", "Zapata", "PA0026", "pass123", 4);
        insertarEstudiante(db, "Felipe", "Cárdenas", "PA0027", "pass123", 4);
        insertarEstudiante(db, "Melissa", "Hernández", "PA0028", "pass123", 4);
        insertarEstudiante(db, "David", "Salazar", "PA0029", "pass123", 4);

        insertarEstudiante(db, "Alejandro", "Serrano", "PA0030", "pass123", 5);
        insertarEstudiante(db, "Mónica", "Paredes", "PA0031", "pass123", 5);
        insertarEstudiante(db, "Felipe", "Sánchez", "PA0032", "pass123", 5);
        insertarEstudiante(db, "Claudia", "Moreno", "PA0033", "pass123", 5);
        insertarEstudiante(db, "Santiago", "Mendoza", "PA0034", "pass123", 5);
        insertarEstudiante(db, "Laura", "Valencia", "PA0035", "pass123", 5);
        insertarEstudiante(db, "Oscar", "Jiménez", "PA0036", "pass123", 5);

        insertarEstudiante(db, "Daniel", "Suárez", "PA0037", "pass123", 6);
        insertarEstudiante(db, "Gabriela", "Camacho", "PA0038", "pass123", 6);
        insertarEstudiante(db, "Ricardo", "Gamarra", "PA0039", "pass123", 6);
        insertarEstudiante(db, "Fernando", "Paredes", "PA0040", "pass123", 6);
        insertarEstudiante(db, "Carolina", "Ortiz", "PA0041", "pass123", 6);
        insertarEstudiante(db, "Sara", "Ríos", "PA0042", "pass123", 6);


        // Insertar relaciones entre estudiantes y cursos
        for (int i = 1; i <= 42; i++) {
            for (int j = 1; j <= 5; j++) {
                insertarEstudianteCurso(db, i, j);
            }
        }

        // Insertar padres
        insertarPadre(db, "Padre/Madre", "Pérez", "PF001", "pass123");
        insertarPadre(db, "Padre/Madre", "García", "PF002", "pass123");
        insertarPadre(db, "Padre/Madre", "Martínez", "PF003", "pass123");
        insertarPadre(db, "Padre/Madre", "Ortiz", "PF004", "pass123");
        insertarPadre(db, "Padre/Madre", "Jiménez", "PF005", "pass123");
        insertarPadre(db, "Padre/Madre", "Paredes", "PF006", "pass123");
        insertarPadre(db, "Padre/Madre", "Rodríguez", "PF007", "pass123");
        insertarPadre(db, "Padre/Madre", "Lopez", "PF008", "pass123");
        insertarPadre(db, "Padre/Madre", "Fernández", "PF009", "pass123");
        insertarPadre(db, "Padre/Madre", "Gómez", "PF0010", "pass123");
        insertarPadre(db, "Padre/Madre", "Ramírez", "PF0011", "pass123");
        insertarPadre(db, "Padre/Madre", "Castro", "PF0012", "pass123");
        insertarPadre(db, "Padre/Madre", "Vargas", "PF0013", "pass123");
        insertarPadre(db, "Padre/Madre", "Morales", "PF0014", "pass123");
        insertarPadre(db, "Padre/Madre", "Álvarez", "PF0015", "pass123");
        insertarPadre(db, "Padre/Madre", "Cruz", "PF0016", "pass123");
        insertarPadre(db, "Padre/Madre", "Torres", "PF0017", "pass123");
        insertarPadre(db, "Padre/Madre", "Reyes", "PF0018", "pass123");
        insertarPadre(db, "Padre/Madre", "Herrera", "PF0019", "pass123");
        insertarPadre(db, "Padre/Madre", "Guerrero", "PF0020", "pass123");
        insertarPadre(db, "Padre/Madre", "Vásquez", "PF0021", "pass123");
        insertarPadre(db, "Padre/Madre", "Zapata", "PF0022", "pass123");
        insertarPadre(db, "Padre/Madre", "Cárdenas", "PF0023", "pass123");
        insertarPadre(db, "Padre/Madre", "Hernández", "PF0024", "pass123");
        insertarPadre(db, "Padre/Madre", "Salazar", "PF0025", "pass123");
        insertarPadre(db, "Padre/Madre", "Serrano", "PF0026", "pass123");
        insertarPadre(db, "Padre/Madre", "Sánchez", "PF0027", "pass123");
        insertarPadre(db, "Padre/Madre", "Moreno", "PF0028", "pass123");
        insertarPadre(db, "Padre/Madre", "Mendoza", "PF0029", "pass123");
        insertarPadre(db, "Padre/Madre", "Valencia", "PF0030", "pass123");
        insertarPadre(db, "Padre/Madre", "Suárez", "PF0031", "pass123");
        insertarPadre(db, "Padre/Madre", "Camacho", "PF0032", "pass123");
        insertarPadre(db, "Padre/Madre", "Gamarra", "PF0033", "pass123");
        insertarPadre(db, "Padre/Madre", "Ríos", "PF0034", "pass123");

        //perez
        insertarPadreEstudiante(db, 1, 1);
        insertarPadreEstudiante(db, 1, 23);
        //garcia
        insertarPadreEstudiante(db, 2, 2);
        insertarPadreEstudiante(db, 2, 25);
        // martinez
        insertarPadreEstudiante(db, 3, 3);
        insertarPadreEstudiante(db, 3, 19);
        //ortiz
        insertarPadreEstudiante(db, 4, 12);
        insertarPadreEstudiante(db, 4, 41);
        //jimenez
        insertarPadreEstudiante(db, 5, 16);
        insertarPadreEstudiante(db, 5, 18);
        insertarPadreEstudiante(db, 5, 36);
        //paredes
        insertarPadreEstudiante(db, 6, 17);
        insertarPadreEstudiante(db, 6, 31);
        insertarPadreEstudiante(db, 6, 40);
        //rodriguez
        insertarPadreEstudiante(db, 7, 4);
        //lopez
        insertarPadreEstudiante(db, 8, 5);
        //fernandez
        insertarPadreEstudiante(db, 9, 6);
        //gomez
        insertarPadreEstudiante(db, 10, 7);
        //ramirez
        insertarPadreEstudiante(db, 11, 8);
        //castro
        insertarPadreEstudiante(db, 12, 9);
        //vargas
        insertarPadreEstudiante(db, 13, 10);
        //morales
        insertarPadreEstudiante(db, 14, 11);
        //alvarez
        insertarPadreEstudiante(db, 15, 13);
        //cruz
        insertarPadreEstudiante(db, 16, 14);
        //torres
        insertarPadreEstudiante(db, 17, 15);
        //reyes
        insertarPadreEstudiante(db, 18, 20);
        //herrera
        insertarPadreEstudiante(db, 19, 21);
        //guerrero
        insertarPadreEstudiante(db, 20, 22);
        //vasquez
        insertarPadreEstudiante(db, 21, 24);
        //zapata
        insertarPadreEstudiante(db, 22, 26);
        //cardenas
        insertarPadreEstudiante(db, 23, 27);
        //hernandez
        insertarPadreEstudiante(db, 24, 28);
        //salazar
        insertarPadreEstudiante(db, 25, 29);
        //serrano
        insertarPadreEstudiante(db, 26, 30);
        //sanchez
        insertarPadreEstudiante(db, 27, 32);
        //moreno
        insertarPadreEstudiante(db, 28, 33);
        //mendoza
        insertarPadreEstudiante(db, 29, 34);
        //valencia
        insertarPadreEstudiante(db, 30, 35);
        //suarez
        insertarPadreEstudiante(db, 31, 37);
        //camacho
        insertarPadreEstudiante(db, 32, 38);
        //gamarra
        insertarPadreEstudiante(db, 33, 39);
        //rios
        insertarPadreEstudiante(db, 34, 42);

        // Insertar un docente y administrador
        insertarAdministrador(db, "Admin", "Admin", "admin", "admin123");
        insertarDocente(db, "Cesar", "Aguirre", "DC001", "pass123");

    }
    public void insertarCurso(SQLiteDatabase db, String nombreCurso) {
        ContentValues values = new ContentValues();
        values.put("NombreCurso", nombreCurso);
        db.insert(TABLE_CURSOS, null, values);
    }
    public void insertarGrado(SQLiteDatabase db, String nombreGrado) {
        ContentValues values = new ContentValues();
        values.put("Nombre", nombreGrado);
        db.insert(TABLE_GRADOS, null, values);
    }
    public void insertarLugar(SQLiteDatabase db, int codPersona, String lugar, double latitud, double longitud) {
        ContentValues values = new ContentValues();
        values.put("cod_persona", codPersona);  // Código de la persona
        values.put("lugar", lugar);            // Nombre del lugar
        values.put("latitud", latitud);        // Latitud del lugar
        values.put("longitud", longitud);      // Longitud del lugar
        db.insert(TABLE_LUGARES, null, values); // Inserta los valores en la tabla
    }
    public void insertarEstudiante(SQLiteDatabase db, String nombres, String apellidos, String usuario, String contraseña, int grado) {
        ContentValues values = new ContentValues();
        values.put("Nombres", nombres);
        values.put("Apellidos", apellidos);
        values.put("Usuario", usuario);
        values.put("Contraseña", contraseña);
        values.put("Grado", grado);
        db.insert(TABLE_ESTUDIANTES, null, values);
    }
    public void insertarEstudianteCurso(SQLiteDatabase db, int estudianteId, int cursoId) {
        ContentValues values = new ContentValues();
        values.put("EstudianteId", estudianteId);
        values.put("CursoId", cursoId);
        db.insert(TABLE_ESTUDIANTES_CURSOS, null, values);
    }
    public void insertarDocente(SQLiteDatabase db, String nombres, String apellidos, String usuario, String contraseña) {
        ContentValues values = new ContentValues();
        values.put("Nombres", nombres);
        values.put("Apellidos", apellidos);
        values.put("Usuario", usuario);
        values.put("Contraseña", contraseña);
        db.insert(TABLE_DOCENTE, null, values);
    }
    public void insertarAdministrador(SQLiteDatabase db, String nombres, String apellidos, String usuario, String contraseña) {
        ContentValues values = new ContentValues();
        values.put("Nombres", nombres);
        values.put("Apellidos", apellidos);
        values.put("Usuario", usuario);
        values.put("Contraseña", contraseña);
        db.insert(TABLE_ADMINISTRADOR, null, values);
    }
    public void insertarPadre(SQLiteDatabase db, String nombres, String apellidos, String usuario, String contraseña) {
        ContentValues values = new ContentValues();
        values.put("Nombres", nombres);
        values.put("Apellidos", apellidos);
        values.put("Usuario", usuario);
        values.put("Contraseña", contraseña);
        db.insert(TABLE_PADRES, null, values);
    }
    public void insertarPadreEstudiante(SQLiteDatabase db, int padreId, int estudianteId) {
        ContentValues values = new ContentValues();
        values.put("PadreId", padreId);
        values.put("EstudianteId", estudianteId);
        db.insert(TABLE_PADRES_ESTUDIANTES, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURSOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTES_CURSOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASISTENCIAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANUNCIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PADRES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PADRES_ESTUDIANTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMINISTRADOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LUGARES);
        onCreate(db);
    }
}
