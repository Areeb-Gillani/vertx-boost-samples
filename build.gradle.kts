plugins {
    id("java")
}

group = "io.github.areebgillani"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    // To include Boost libraries
    maven ("https://jitpack.io")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    // Core Vertx libraries
    implementation("io.vertx:vertx-web:4.4.4")
    implementation("io.vertx:vertx-config:4.4.4")
    implementation("io.vertx:vertx-core:4.4.4")

    // Boost libraries
    implementation ("com.github.Areeb-Gillani:vertx-boost-db:0.0.3")
    implementation ("com.github.Areeb-Gillani:vertx-boost:0.0.6")
    // For database inclusion
    implementation("io.vertx:vertx-sql-client-templates:4.4.5")
    implementation ("io.vertx:vertx-mysql-client:4.4.5")
    // To generate mapper from @DataObject annotation
    compileOnly("io.vertx:vertx-codegen:4.4.5")
    annotationProcessor("io.vertx:vertx-codegen:4.4.5:processor")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}