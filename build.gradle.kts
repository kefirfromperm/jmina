plugins {
    java
    `maven-publish`
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
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.jmina"
            artifactId = "jmina"
            version = "0.1.0"

            from(components["java"])

            pom {
                name = "JMina for unit tests"
                description = "A simple tool to verify log calls during tests"
                url = "https://jmina.dev"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "kefirfromperm"
                        name = "Vitalii Samolovskikh"
                        email = "kefirfromperm@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:ssh://git@github.com:kefirfromperm/jmina.git"
                    url = "https://github.com/kefrifromperm/jmina"
                }
            }
        }
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

