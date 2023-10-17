

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target)
        {
            val libs = the<LibrariesForLibs>()
            pluginManager.apply {
                apply("dagger.hilt.android.plugin")
                apply ("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation",libs.hilt.android)
                add("ksp",libs.hilt.compiler)
                add("ksp",libs.androidx.hilt.compiler)
                add("kspAndroidTest",libs.hilt.compiler)
                add("implementation" ,libs.androidx.navigation.compose)
            }


        }
    }
}

