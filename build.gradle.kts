plugins {
    id("java")
    // Adds the `aggregate` task that builds the Serenity HTML report
    id("net.serenity-bdd.serenity-gradle-plugin") version "5.4.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

val serenityVersion = "5.4.2"
val cucumberVersion = "8.0.4"
val junitVersion = "6.1.3"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Screenplay (tasks, questions, interactions, userInterface live in src/main/java)
    implementation("net.serenity-bdd:serenity-core:$serenityVersion")
    implementation("net.serenity-bdd:serenity-screenplay:$serenityVersion")
    implementation("net.serenity-bdd:serenity-screenplay-webdriver:$serenityVersion")
    implementation("net.serenity-bdd:serenity-ensure:$serenityVersion")

    // Cucumber + Serenity integration (runners and stepsDefinitions live in src/test/java)
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation("net.serenity-bdd:serenity-cucumber:$serenityVersion")
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")

    // JUnit Platform (runs Cucumber through a @Suite runner)
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Logging backend for Serenity's SLF4J output
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("aggregate")
}
