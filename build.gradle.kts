plugins {
    java
    id("com.github.ben-manes.versions") version "0.51.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.opentest4j:opentest4j:1.3.0")

    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.slf4j:slf4j-simple:2.0.13")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

tasks {
    test {
        useJUnitPlatform()
        systemProperty("slf4j.provider", "dev.jmina.log.MinaServiceProvider")
        systemProperty("jmina.delegate.provider", "org.slf4j.simple.SimpleServiceProvider")
        systemProperty("jmina.context.global", "false")
    }

    named(
        "dependencyUpdates",
        com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask::class.java
    ).configure {
        rejectVersionIf {
            candidate.version.contains(Regex("-((m\\d+)|(rc(-|\\d)*)|(beta)|(alpha))", RegexOption.IGNORE_CASE))
        }
    }
}

