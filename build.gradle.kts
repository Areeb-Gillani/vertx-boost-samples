plugins {
    id("java")
}

group = "io.github.areebgillani"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenCentral()
    // To include Boost libraries
    maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    // Core Vertx libraries
    implementation("io.vertx:vertx-web:4.4.5")
    implementation("io.vertx:vertx-config:4.4.5")
    implementation("io.vertx:vertx-core:4.4.5")
    // Boost libraries
    implementation("com.github.Areeb-Gillani:vertx-boost-db:0.0.3")
    implementation("com.github.Areeb-Gillani:vertx-boost:0.0.6")
    // For database inclusion
    implementation("io.vertx:vertx-sql-client-templates:4.4.5")
    implementation("io.vertx:vertx-mysql-client:4.4.5")
    // To generate mapper from @DataObject annotation
    compileOnly("io.vertx:vertx-codegen:4.4.5")
    annotationProcessor("io.vertx:vertx-codegen:4.4.5:processor")
    annotationProcessor("io.vertx:vertx-sql-client-templates:4.4.5")

}
/*sourceSets {
    create("generated") {
        java.srcDir("${projectDir}/src/generated/java")
    }
}*/
tasks.create<JavaCompile>("VertxCodeGenProcessor"){

    source = sourceSets.main.get().allJava
    classpath =  configurations.compileClasspath.get() + configurations.annotationProcessor.get()
    options.annotationProcessorPath = configurations.annotationProcessor.get()
    options.compilerArgs = listOf(
            "-proc:only",
            "-processor", "io.vertx.codegen.CodeGenProcessor",
            "-Acodegen.output=${projectDir}/src/main"
    )
    destinationDirectory = file("${projectDir}/src/main/java")
}
tasks.compileJava{
    dependsOn("VertxCodeGenProcessor")
    //source += sourceSets.getByName("generated").java
    options.compilerArgs = listOf("-proc:none")
}
/*tasks.clean {
    delete(sourceSets.getByName("generated").java.srcDirs)
}*/
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
