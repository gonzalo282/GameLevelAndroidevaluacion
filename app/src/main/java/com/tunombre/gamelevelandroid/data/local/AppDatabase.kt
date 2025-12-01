package com.tunombre.gamelevelandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    // Subimos la versión a 2 para forzar el cambio
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gamelevel.db"
                )
                    // --- ¡AQUÍ ESTÁ EL ARREGLO! ---
                    // Esto borra la base de datos vieja si cambia la versión
                    .fallbackToDestructiveMigration()
                    // -----------------------------
                    .build().also { INSTANCE = it }
            }
        }
    }
}