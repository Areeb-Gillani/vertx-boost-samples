plugins {
    id("java")
}

group = "io.github.areebgillani"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenLocal()
    mavenCentral()
    // To include Boost libraries
    maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    // Core Vertx libraries
    implementation("io.vertx:vertx-web:4.5.8")
    implementation("io.vertx:vertx-config:4.5.8")
    implementation("io.vertx:vertx-core:4.5.8")
    // Boost libraries (use local versions first, fallback to jitpack)
    implementation("io.github.areebgillani:vertx-boost-db:1.1.0")
    implementation("io.github.areebgillani:vertx-boost:1.1.0")
    // For database inclusion
    implementation("io.vertx:vertx-sql-client-templates:4.5.8")
    implementation("io.vertx:vertx-mysql-client:4.5.8")
    // To generate mapper from @DataObject annotation
    compileOnly("io.vertx:vertx-codegen:4.5.8")
    annotationProcessor("io.vertx:vertx-codegen:4.5.8:processor")
    annotationProcessor("io.vertx:vertx-sql-client-templates:4.5.8")
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
