//import org.jetbrains.kotlin.org.fusesource.jansi.AnsiRenderer.test

plugins {
    application
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

group = "com.corbettcode"
version = "1.0-SNAPSHOT"

publishing {
    publications {
        create<MavenPublication>("light-rule-plus") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "light-rule-plus"
            url = uri(layout.buildDirectory.dir("light-rule-plus"))
        }
    }
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
//    compile(kotlin("stdlib"))
//    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.0.0")
}

repositories {
}
