// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Archivo: build.gradle (Project Level)


        // esta linea es importante para agregar KSP
        id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
        // ^^^ Reemplaza por la versión actual de KSP, compatible con tu versión de Kotlin
}