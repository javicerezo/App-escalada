plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.proyectoilerna"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.proyectoilerna"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Dependencia de Firebase para poder hacer el manejo de datos (escritura, borrado, ...)
    implementation(platform(libs.firebase.bom))

    // Dependencia de Firebase para manejar las Autenticaciones
    implementation(libs.firebase.auth)

    // Dependencia de Firebase para usar las Analiticas...la comento pues no uso analytic
    //implementation(libs.firebase.analytics)

    // Dpendencia de Firebase para manejar la base de datos
    implementation(libs.firebase.database)

    // Dependencia para usar el Storage de Firebase
    implementation(libs.firebase.firestore)

    // 2 Dependencias para autenticación con Google
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)

    // 2 Dependencias para manejar drawerLayout y CoordinatorLayout para hacer el menú lateral
    implementation(libs.androidx.drawerlayout)
    implementation(libs.androidx.coordinatorlayout)

    // Dependencia para tener control sobre los RecyclerViews
    implementation(libs.androidx.recyclerview)

    // Dpendencia para la geolocalización del teléfono
    implementation(libs.google.play.services.location)

    // Dependencia para Maps
    implementation(libs.play.services.maps)
}