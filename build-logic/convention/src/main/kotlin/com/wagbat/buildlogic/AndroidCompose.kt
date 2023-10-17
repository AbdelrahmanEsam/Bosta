package com.wagbat.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*,*, *, *, *>,
)
{
    val libs = the<LibrariesForLibs>()

    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
        }

        kotlinOptions {
            jvmTarget = libs.versions.javaVersion.get()
        }
    }

}