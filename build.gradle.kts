import org.apache.commons.io.output.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

plugins {
    kotlin("jvm") version "2.1.0-Beta2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jetbrains.dokka") version "1.9.+"
    id("com.modrinth.minotaur") version "2.+"
}

val gitVersion: groovy.lang.Closure<String> by extra

group = "org.sysapp.runkang10.universalMCAPI"
version = "2.0.0"

fun getCurrentGitBranch(): String {
    val output = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
        standardOutput = output
    }

    return String(
        output.toByteArray(),
        StandardCharsets.UTF_8
    )
        .trim()
        .takeIf {
            it.isNotEmpty()
        } ?: "unknown"
}

val currentBranch = getCurrentGitBranch()

val versionType = when (currentBranch) {
    "main" -> "release"
    "dev" -> "dev"
    else -> null
}

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
    token.set(System.getenv("MODRINTH_SECRET"))
    projectId.set("universalmcapi")
    versionType.set(versionType)
    uploadFile.set(tasks.shadowJar.get().archiveFile)
    gameVersions.addAll(listOf("1.21", "1.21.1"))
    loaders.addAll(listOf("paper", "folia", "purpur"))
    changelog.set("**Full changelog**: https:/github.com/Runkang10/UniversalMCAPI/releases/tag/v${version}")

    syncBodyFrom.set(rootProject.file("README.md").readText())
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.dokkaGfm {
    println(getCurrentGitBranch())
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
    dokkaSourceSets {
        configureEach {
            jdkVersion.set(targetJavaVersion)
        }
    }
}

tasks.modrinth {
    dependsOn(tasks.build)
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