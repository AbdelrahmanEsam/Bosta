plugins {
    `kotlin-dsl`

}

group = "com.bosta.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.args.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)

    // Enables using type-safe accessors to reference plugins from the [plugins] block defined in version catalogs.
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {

    fun createPlugin(id: String, className: String) {
        plugins.create(id) {
            this.id = id
            implementationClass = className
        }
    }
    plugins {
        createPlugin("com.bosta.hilt", "AndroidHiltConventionPlugin")
        createPlugin("com.bosta.library", "AndroidLibraryConventionPlugin")
        createPlugin("com.bosta.library.compose", "AndroidLibraryComposeConventionPlugin")
        createPlugin("com.bosta.feature", "AndroidFeatureConventionPlugin")
        createPlugin("com.bosta.application", "AndroidApplicationConventionPlugin")
        createPlugin(
            "com.bosta.application.compose",
            "AndroidApplicationComposeConventionPlugin"
        )
        createPlugin("com.bosta.test", "AndroidTestConventionPlugin")
    }
}
