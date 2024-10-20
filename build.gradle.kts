plugins {
    kotlin("jvm") version "2.1.0-Beta2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jetbrains.dokka") version "1.9.+"
    id("com.modrinth.minotaur") version "2.+"
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
    token.set(System.getenv("MODRINTH_SECRET"))
    projectId.set("universalmcapi")
    versionType.set("release")
    uploadFile.set(tasks.shadowJar.get().archiveFile)
    gameVersions.addAll(listOf("1.21", "1.21.1"))
    loaders.addAll(listOf("paper", "folia"))
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

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
    dokkaSourceSets {
        configureEach {
            val baseUrl = "https://runkang10.github.io/universalmcapi/"
            externalDocumentationLink("https://kotlinlang.org/api/latest/jvm/stdlib/")
            moduleName.set("UniversalMCAPI")
            perPackageOption {
                matchingRegex.set(".*")
                externalDocumentationLink(baseUrl)
            }
        }
    }
}

tasks.modrinth {
    dependsOn(tasks.build)
}

tasks.build {
    dependsOn(tasks.dokkaHtml)
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