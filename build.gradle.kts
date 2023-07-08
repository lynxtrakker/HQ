plugins {
    id("java")

}

group = "lynxtrakker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.dv8tion:JDA:5.0.0-beta.12")
    implementation("club.minnced:discord-webhooks:0.8.2")
    implementation("com.google.code.gson:gson:2.10.1")
    //implementation("io.github.cdimascio:dotenv-java:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}