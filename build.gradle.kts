import java.io.ByteArrayOutputStream
import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    kotlin("jvm") version "2.1.0-Beta2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jetbrains.dokka") version "1.9.+"
    id("com.modrinth.minotaur") version "2.+"
    id("io.papermc.hangar-publish-plugin") version "0.1.+"
}

fun getCurrentBranch(): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim()
}

fun setReleaseType(publisher: String): String {
    val currentBranch = getCurrentBranch();

    return when (publisher) {
        "modrinth" -> when (currentBranch) {
            "main" -> "release"
            "dev" -> "beta"
            else -> "alpha"
        }
        "hangar" -> when (currentBranch) {
            "main" -> "Release"
            "dev" -> "dev"
            else -> "alpha"
        }
        else -> "alpha"
    }
}

val projectName: String = project.findProperty("projectName") as String
val projectGroup: String = project.findProperty("projectGroup") as String
val projectID: String = project.findProperty("projectID") as String
val projectVersion: String = project.findProperty("projectVersion") as String
val supportedMCVersions: String = project.findProperty("supportedMCVersions") as String
val supportedPlatforms: String = project.findProperty("supportedPlatforms") as String
val projectSupportedMCVersions: List<String> = supportedMCVersions.split(" ")
val projectSupportedPlatforms: List<String> = supportedPlatforms.split(" ")

val modrinthVersionType: String = setReleaseType("modrinth")
val hangarVersionType: String = setReleaseType("hangar")

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_SECRET"))
    projectId.set(projectID)
    versionNumber.set("v${projectVersion}")
    versionType.set(modrinthVersionType)
    uploadFile.set(tasks.shadowJar.get().archiveFile)
    gameVersions.addAll(projectSupportedMCVersions)
    loaders.addAll(projectSupportedPlatforms)
    changelog.set(
        "**Full changelog**: " +
        "[https://github.com/Runkang10/UniversalMCAPI/releases/tag/v${projectVersion}]" +
        "(https://github.com/Runkang10/UniversalMCAPI/releases/tag/v${projectVersion})"
    )

    syncBodyFrom.set(rootProject.file("README.md").readText())
}

hangarPublish {
    publications.register("plugin") {
        version.set("v${projectVersion}")
        channel.set(hangarVersionType)
        id.set(projectID)
        apiKey.set(System.getenv("HANGAR_API_TOKEN"))
        platforms {
            register(Platforms.PAPER) {
                jar.set(tasks.shadowJar.flatMap { it.archiveFile })
                platformVersions.set(projectSupportedMCVersions)
            }
        }
    }
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

tasks.publishAllPublicationsToHangar {
    dependsOn(tasks.build)
}

tasks.shadowJar {
    archiveVersion.set(projectVersion)
    archiveClassifier.set("projectClassifier")
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf(
        "version" to projectVersion,
        "group" to projectVersion
    )
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}