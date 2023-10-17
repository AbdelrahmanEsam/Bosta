import com.android.build.api.dsl.ApplicationExtension
import com.wagbat.buildlogic.configureAndroidCompose
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target)
        {
            val libs = the<LibrariesForLibs>()

            pluginManager.apply {
                apply("com.android.application")
            }
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
            dependencies {

                add("implementation", platform(libs.compose.bom))
                add("implementation", libs.bundles.compose)
                add("implementation", libs.androidx.hilt.navigation.compose)
                add("implementation", libs.androidx.navigation.compose)
                add("implementation", libs.coil)
                add("implementation", libs.sdp)
                add("implementation", libs.ssp)
            }

        }


    }
}