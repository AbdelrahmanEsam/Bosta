import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.Packaging
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.wagbat.buildlogic.configureKotlinAndroid
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        with(target)
        {
            val libs = the<LibrariesForLibs>()

            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.apply {
                    targetSdk = libs.versions.targetSDK.get().toInt()
                    applicationId = "com.bosta"
                    versionCode = libs.versions.codeVersion.get().toInt()
                    versionName = libs.versions.codeVersion.get().toDouble().toString()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                extensions.configure<BaseAppModuleExtension> {
                    android {
                        buildTypes {
                            debug {
                                isMinifyEnabled = false

                                buildFeatures {
                                    buildConfig = true
                                }
                                proguardFiles(
                                    getDefaultProguardFile("proguard-android-optimize.txt"),
                                    "proguard-rules.pro"
                                )
                            }

                            release {
                                isMinifyEnabled = true
                                buildFeatures {
                                    buildConfig = true
                                }
                                proguardFiles(
                                    getDefaultProguardFile("proguard-android-optimize.txt"),
                                    "proguard-rules.pro"
                                )
                            }

                            forEach {
                                it.buildConfigField(
                                    "String",
                                    "API_BASE",
                                    "\"https://jsonplaceholder.typicode.com/\""
                                )
                            }
                        }
                    }
                }



                @Suppress("UNUSED_EXPRESSION")
                fun Packaging.() {
                    resources {
                        excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                    }
                }

                namespace = "com.bosta"

            }

            dependencies {
                add("implementation", libs.androidx.window.manager)
                add("implementation", libs.androidx.core.splash)
                add("implementation", libs.core.ktx)
                add("implementation", libs.bundles.livecycle)
                add("implementation", libs.bundles.retrofit)
                add("implementation", libs.bundles.lottie)
                add("androidTestImplementation", libs.junit)
            }

        }
    }
}

fun Project.`android`(configure: Action<BaseAppModuleExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("android", configure)