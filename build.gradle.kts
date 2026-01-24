plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.gradleup.shadow") version "9.2.2"
    id("org.danilopianini.gradle-java-qa") version "1.153.0"
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")

    // Maven dependencies are composed by a group name, a name and a version, separated by colons

    /*
     * Simple Logging Facade for Java (SLF4J) with Apache Log4j
     * See: http://www.slf4j.org/
     */
    val slf4jVersion = "2.0.17"
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation ("org.jbox2d:jbox2d-library:2.2.1.1")
    // Logback backend for SLF4J
    runtimeOnly("ch.qos.logback:logback-classic:1.5.20")

    /*
     * JBox2D: A Java Physics Engine
     * See: https://www.jbox2d.org/
     */
    val jbox2dVersion="2.2.1.1"
    implementation("org.jbox2d:jbox2d-library:$jbox2dVersion")

    // JUnit API and testing engine
    testImplementation(platform("org.junit:junit-bom:6.0.1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    //spotbugs annotations
    testCompileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")
}

application {
    // Define the main class for the application.
    mainClass.set("it.unibo.geometrybash.view.menus.MainMenuView")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform() // Enables the engine of JUnit 5/6
    testLogging { // Additional Options
        // Display all events (test started, succeeded, failed...)
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.entries.toTypedArray())
        showStandardStreams = true // Show the standard output
    }
}
