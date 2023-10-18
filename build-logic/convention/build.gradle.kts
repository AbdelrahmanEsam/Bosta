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
        createPlugin("com.bosta.library.compose", "AndroidLibraryComposeConventionPlugin")
        createPlugin("com.bosta.feature", "AndroidFeatureConventionPlugin")
        createPlugin("com.bosta.application", "AndroidApplicationConventionPlugin")
        createPlugin(
            "com.bosta.application.compose",
            "AndroidApplicationComposeConventionPlugin"
        )
    }
}
