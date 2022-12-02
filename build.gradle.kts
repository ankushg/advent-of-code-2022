import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    kotlin("jvm") version "1.7.22"
    id("com.diffplug.spotless") version "6.12.0"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}

dependencies {
    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta9")
}

configure<SpotlessExtension> {
    kotlin {
        ktlint()
    }
}
