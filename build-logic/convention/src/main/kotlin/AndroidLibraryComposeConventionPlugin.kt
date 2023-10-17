

import com.android.build.gradle.LibraryExtension
import com.wagbat.buildlogic.configureAndroidCompose
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.plugins
import org.gradle.kotlin.dsl.the

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = the<LibrariesForLibs>()
            pluginManager.apply {
               // apply("com.android.library")

            }
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)





            dependencies {
                add("implementation",libs.bundles.livecycle)
                add("implementation",libs.androidx.activity.compose)
                add("implementation",libs.androidx.hilt.navigation.compose)
                add("api",libs.androidx.navigation.compose)
                add("implementation",libs.coil)
                add("implementation",libs.sdp)
                add("implementation",libs.ssp)
                add("implementation",libs.bundles.compose)
            }
        }



    }

}