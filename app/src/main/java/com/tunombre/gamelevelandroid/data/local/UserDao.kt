package com.tunombre.gamelevelandroid.data.local



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity): Long

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserEntity?

    @Query("SELECT * FROM usuarios WHERE id = :userId LIMIT 1")
    suspend fun findById(userId: Int): UserEntity?

    // 👉 ESTA FUNCIÓN ES LA QUE NECESITA TU REPOSITORY
    @Query("SELECT * FROM usuarios WHERE email = :email AND password_hash = :passwordHash LIMIT 1")
    suspend fun auth(email: String, passwordHash: String): UserEntity?

    // --- ¡NUEVO! Actualizar solo la foto ---
    @Query("UPDATE usuarios SET foto_perfil = :fotoUri WHERE id = :userId")
    suspend fun updateFotoPerfil(userId: Int, fotoUri: String)
}
