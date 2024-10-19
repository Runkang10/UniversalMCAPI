plugins {
    kotlin("jvm") version "2.1.0-Beta2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.sysapp.runkang10.universalMCAPI"
version = "1.3.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

modrinth {
    token = "mrp_JADW7lR2mJOEYslbfMq59zj8VJKZYyGekgiDSRM9rvfZjjdSaD9a5sZaqX4p"
    projectId = "universalmcapi"
    versionType = "release"
    uploadFile = jar
    gameVersions = ["1.21", "1.21.1"]
    loaders = ["paper"]
    syncBodyFrom = rootProject.file("README.md").text
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}
