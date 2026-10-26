package com.example.aplicacionandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UsuarioEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpaz_login.db"
                )
                    .addCallback(PrecargaUsuarios)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
private object PrecargaUsuarios : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL("INSERT INTO usuarios (nombre, correo, clave) VALUES ('Ana Soto', 'ana.soto@mpaz.cl', 'ana1234')")
        db.execSQL("INSERT INTO usuarios (nombre, correo, clave) VALUES ('Luis Pérez', 'luis.perez@mpaz.cl', 'luis1234')")
    }
}
