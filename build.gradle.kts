plugins {
    id("java")
    application
}

group = "org.p3_segundo_parcial"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Dependencias para lombok, inclusive las de test (por si acaso)
    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")
    testCompileOnly("org.projectlombok:lombok:1.18.46")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.46")

    // Dependencias para JPA (Hibernate) y Base de Datos H2
    implementation("org.hibernate.orm:hibernate-core:6.4.10.Final")
    runtimeOnly("com.h2database:h2:2.4.240")
}

application {
    mainClass.set("org.p3_segundo_parcial.Main")
}

tasks.test {
    useJUnitPlatform()
}