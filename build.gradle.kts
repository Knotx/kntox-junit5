/*
 * Copyright (C) 2019 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.nosphere.apache.rat.RatTask

group = "io.knotx"

plugins {
    `java-library`
    id("io.knotx.java-library") version "0.1.0"
    id("io.knotx.maven-publish") version "0.1.0"
    id("io.knotx.jacoco") version "0.1.0"

    id("org.nosphere.apache.rat") version "0.4.0"
}

repositories {
    jcenter()
    mavenLocal()
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://oss.sonatype.org/content/groups/staging/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    implementation(platform("io.knotx:knotx-dependencies:${project.version}"))
    implementation(group = "io.vertx", name = "vertx-core")
    implementation(group = "io.vertx", name = "vertx-rx-java2")
    implementation(group = "io.vertx", name = "vertx-service-proxy")
    implementation(group = "io.vertx", name = "vertx-rx-java2")
    implementation(group = "io.vertx", name = "vertx-config")
    implementation(group = "io.vertx", name = "vertx-config-hocon")
    implementation(group = "io.vertx", name = "vertx-junit5")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(group = "com.google.guava", name = "guava")
    implementation(group = "org.junit.jupiter", name = "junit-jupiter-api")
    implementation(group = "org.junit.jupiter", name = "junit-jupiter-params")
    implementation(group = "org.junit.jupiter", name = "junit-jupiter-migrationsupport")
    implementation(group = "org.mockito", name = "mockito-core")
    implementation(group = "org.mockito", name = "mockito-junit-jupiter")
    implementation(group = "com.github.tomakehurst", name = "wiremock")
    implementation(group = "commons-io", name = "commons-io")
    implementation(group = "org.jsoup", name = "jsoup", version = "1.11.2")

    testImplementation(group = "io.rest-assured", name = "rest-assured", version = "3.3.0")
    testImplementation(group = "io.vertx", name = "vertx-web")

    testRuntime("io.knotx:knotx-launcher:${project.version}")
    testRuntime(group = "org.junit.jupiter", name = "junit-jupiter-engine")
}

tasks {
    named<RatTask>("rat") {
        excludes.addAll("**/*.md", "**/build/*", "**/out/*", "**/*.conf", "**/*.json", "gradle", "gradle.properties", ".travis.yml", ".idea")
    }
    getByName("build").dependsOn("rat")
}

publishing {
    publications {
        withType(MavenPublication::class) {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                developers {
                    developer {
                        id.set("tMaxx")
                        name.set("Mikolaj Wawrowski")
                    }
                }
            }
        }
    }
}
