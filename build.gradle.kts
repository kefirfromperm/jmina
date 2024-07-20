plugins {
    java
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
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
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

tasks.test {
    useJUnitPlatform()
    systemProperty("slf4j.provider", "dev.jmina.log.MinaServiceProvider")
    systemProperty("mina.delegate.provider", "org.slf4j.simple.SimpleServiceProvider")
    systemProperty("mina.context.global", "false")
}

