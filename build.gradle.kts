buildscript {
    extra.apply {
        set("compose_version",libs.versions.composeCompilerVersion)
    }
    repositories {
        google()
    }
}
plugins {
    alias(libs.plugins.com.android.application) apply  false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false

}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
