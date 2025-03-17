import org.jreleaser.model.Active
import org.jreleaser.model.Signing

plugins {
    `java-library`
    `maven-publish`
    id("com.github.ben-manes.versions") version "0.52.0"
    id("org.jreleaser") version "1.16.0"
}


group = "dev.jmina"
version = "0.1.4"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.opentest4j:opentest4j:1.3.0")

    // Test dependencies
    testImplementation(platform("org.junit:junit-bom:5.12.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")
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
                    connection = "scm:git:git://git@github.com:kefirfromperm/jmina.git"
                    developerConnection = "scm:git:ssh://git@github.com:kefirfromperm/jmina.git"
                    url = "https://github.com/kefirfromperm/jmina"
                }
            }
        }

        repositories {
            maven {
                url = uri(layout.buildDirectory.dir("staging-deploy"))
            }
        }
    }
}

jreleaser {
    signing {
        active = Active.ALWAYS
        armored = true
        mode = Signing.Mode.of(System.getenv("SIGNING_MODE") ?: "MEMORY")
    }
    deploy {
        maven {
            mavenCentral {
                register("sonatype") {
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
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

