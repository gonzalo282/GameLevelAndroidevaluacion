package com.tunombre.gamelevelandroid.data.local



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "email", index = true) val email: String,
    @ColumnInfo(name = "telefono") val telefono: String,
    @ColumnInfo(name = "direccion") val direccion: String,

    // Guardamos la contraseña hasheada (no en texto plano)
    @ColumnInfo(name = "password_hash") val passwordHash: String,
    @ColumnInfo(name = "foto_perfil") val fotoPerfil: String? = null
)
