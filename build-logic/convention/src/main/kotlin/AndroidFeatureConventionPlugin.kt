

import com.android.build.gradle.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.the

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
                val libs = the<LibrariesForLibs>()
                pluginManager.apply {
                   apply("com.bosta.library")
                   apply("com.bosta.hilt")
                }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }


            dependencies {
                add("implementation",libs.core.ktx)
                add("implementation",libs.bundles.livecycle)
                add("implementation",project(":core:ui"))
                add("api",project(":core:utils"))
                add("api",project(":core:designsystem"))
            }
        }


    }
}