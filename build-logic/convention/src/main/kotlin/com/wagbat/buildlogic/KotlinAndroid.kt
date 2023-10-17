package com.wagbat.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*,*, *, *, *>,
) {
    val libs = the<LibrariesForLibs>()
    commonExtension.apply {

        compileSdk = libs.versions.compileSDK.get().toInt()

        defaultConfig {
            minSdk = libs.versions.minSDK.get().toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
//            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            jvmTarget = libs.versions.javaVersion.get()
        }


    }

//    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
//        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

fun CommonExtension<*,*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}




