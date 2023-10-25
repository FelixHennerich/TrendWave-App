plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}


kotlin {
    androidTarget()

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("media.kamel:kamel-image:0.7.1") // Image API
                implementation("io.ktor:ktor-client-core:2.3.3") // Image API
                

                //Moko Dependency

                implementation("dev.icerock.moko:mvvm-core:0.16.1")
                implementation("dev.icerock.moko:mvvm-compose:0.16.1")
                implementation("dev.icerock.moko:mvvm-flow:0.16.1")
                implementation("dev.icerock.moko:mvvm-flow-compose:0.16.1")

                /*
                DatePicker

                implementation("io.github.epicarchitect:calendar-compose-basis:1.0.4")
                implementation("io.github.epicarchitect:calendar-compose-ranges:1.0.4") // includes basis
                implementation("io.github.epicarchitect:calendar-compose-pager:1.0.4") // includes basis
                implementation("io.github.epicarchitect:calendar-compose-datepicker:1.0.4") // includes pager + ranges
                */
                /**
                 *
                 *
                 * --------------------------------------------------------------
                 *
                 *
                 *
                 *          NEW DEPENDENCIES WILL BE ADDED HERE!!!!
                 *
                 *
                 *
                 * --------------------------------------------------------------
                 *
                 *
                 */

                /*
                Icon Packs
                 */
                implementation("br.com.devsrsouza.compose.icons:tabler-icons:1.1.0") // Icon Lib
                implementation("br.com.devsrsouza.compose.icons:font-awesome:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:simple-icons:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:feather:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:eva-icons:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:octicons:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:linea:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:line-awesome:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:erikflowers-weather-icons:1.1.0")
                implementation("br.com.devsrsouza.compose.icons:css-gg:1.1.0")
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.6.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.9.0")
                implementation("io.ktor:ktor-client-android:2.3.3")
                implementation("io.ktor:ktor-client-okhttp:2.3.3")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies{
                implementation("io.ktor:ktor-client-darwin:2.3.3")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }

}

dependencies {
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")

}


