plugins {
    id("com.android.library")
    id("digital.wup.android-maven-publish")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(15)
        targetSdkVersion(28)
        versionCode = 2
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

val sourceJar = task<Jar>("sourceJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.sourceFiles)
}

publishing {
    repositories {
        maven {
            val artifactory_url: String by project
            setUrl(artifactory_url)
            credentials {
                val artifactory_user: String by project
                val artifactory_password: String by project

                username = artifactory_user
                password = artifactory_password
            }
        }
    }

    publications {
        create<MavenPublication>("aar") {
            groupId = "se.eelde"
            artifactId = project.name

            val CIVersionName: String by project
            val CIVersionNameSufix: String by project

            version = CIVersionName + CIVersionNameSufix

            from(components["android"])
            artifact(sourceJar)
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.0.2")
    api("pub.devrel:easypermissions:3.0.0")
}
