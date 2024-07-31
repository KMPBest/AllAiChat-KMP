import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.*

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsCompose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.buildkonfig)
  id("app.cash.sqldelight") version "2.0.2"
}

kotlin {
  androidTarget {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_11)
    }
  }

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
      linkerOpts.add("-lsqlite3")
    }
  }

  sourceSets {

    androidMain.dependencies {
      implementation(compose.preview)
      implementation(libs.androidx.activity.compose)
      api(libs.androidx.lifecycle.runtime.compose)

      api(libs.koin.android)
      implementation(libs.sqldelight.android.driver)
    }
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)

      implementation(libs.navigation.compose)
      implementation(libs.kotlinx.serialization.json)
      implementation(libs.kotlinx.datetime)

      implementation(libs.ktor.client.core)

      implementation(libs.napier)

      implementation(libs.bundles.ktor)

      implementation(libs.coil.core)
      implementation(libs.coil.compose)
      implementation(libs.coil.network)

      implementation(libs.bundles.koin)

      implementation(libs.kotlinx.coroutines)

      // SQLDelight
      implementation(libs.sqldelight.coroutine.ext)
      implementation(libs.sqldelight.primitive.adapters)

      // local storage like share preference
      implementation(libs.multiplatform.settings.no.arg)

      implementation(libs.filekitCore)
      implementation(libs.filekitCompose)

      implementation(libs.moko.permissions.core)
      implementation(libs.moko.permissions.compose)
    }
    nativeMain.dependencies {
      // sqlite
      implementation(libs.sqldelight.native.driver)

//    ktor
      implementation(libs.ktor.client.darwin)

      implementation("co.touchlab:stately-common:2.0.6")
    }
  }
}
//
sqldelight {
  databases {
    create("GeminiApiChatDB") {
      packageName.set("com.ngdang.outcome")
      generateAsync.set(true)
    }
  }
  linkSqlite = true
}

android {
  namespace = "com.ngdang.outcome"
  compileSdk =
    libs.versions.android.compileSdk
      .get()
      .toInt()

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  sourceSets["main"].res.srcDirs("src/androidMain/res")
  sourceSets["main"].resources.srcDirs("src/commonMain/resources")

  defaultConfig {
    applicationId = "com.ngdang.outcome"
    minSdk =
      libs.versions.android.minSdk
        .get()
        .toInt()
    targetSdk =
      libs.versions.android.targetSdk
        .get()
        .toInt()
    versionCode = 1
    versionName = "1.0"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
  }
  dependencies {
    debugImplementation(compose.uiTooling)
  }
}
dependencies {
  implementation(libs.androidx.material3.android)
  implementation(libs.androidx.tools.core)
}

buildkonfig {
  packageName = "com.ngdang.outcome"

  val localProperties =
    Properties().apply {
      val propsFile = rootProject.file("local.properties")
      if (propsFile.exists()) {
        load(propsFile.inputStream())
      }
    }

  defaultConfigs {
    buildConfigField(
      FieldSpec.Type.STRING,
      "GEMINI_API_KEY",
      localProperties["GEMINI_API_KEY"]?.toString() ?: "",
    )
    buildConfigField(
      FieldSpec.Type.BOOLEAN,
      "isDebug",
      "false",
    )
  }
}
