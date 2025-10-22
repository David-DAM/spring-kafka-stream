# Documentación del Proyecto Spring Kafka Stream

## 📋 Descripción General

Este proyecto es una aplicación basada en **Spring Boot** que utiliza **Apache Kafka** para el procesamiento de streams
de datos. El proyecto está construido con **Maven** y utiliza **Java SDK 21** junto con **Jakarta EE** y **Lombok**.

## 🛠️ Tecnologías Utilizadas

- **Java SDK**: 21
- **Spring Boot**: Framework principal
- **Apache Kafka**: Procesamiento de streams
- **Jakarta EE**: Especificaciones empresariales con jakarta imports
- **Lombok**: Reducción de código boilerplate
- **Maven**: Gestión de dependencias y construcción
- **Docker Compose**: Orquestación de servicios

## 📁 Estructura del Proyecto

``` 
spring-kafka-stream/
├── src/
│   ├── main/
│   │   ├── java/com/...    # Código fuente principal
│   │   └── resources/       # Archivos de configuración
│   └── test/               # Pruebas unitarias e integración
├── .mvn/                   # Wrapper de Maven
├── compose.yml             # Configuración Docker Compose
├── pom.xml                 # Configuración Maven
├── mvnw                    # Maven Wrapper (Unix)
├── mvnw.cmd               # Maven Wrapper (Windows)
├── README.md              # Documentación básica
└── .gitignore            # Archivos ignorados por Git
```

## 🚀 Configuración y Ejecución

### Prerrequisitos

- Java 21 o superior
- Docker y Docker Compose (para servicios externos)
- Maven 3.6+ (opcional, se incluye wrapper)

### Ejecución con Maven

``` bash
# Compilar el proyecto
./mvnw clean compile

# Ejecutar las pruebas
./mvnw test

# Ejecutar la aplicación
./mvnw spring-boot:run
```

### Ejecución con Docker Compose

``` bash
# Levantar servicios (Kafka, Zookeeper, etc.)
docker-compose up -d
```

## 📦 Características Principales

- **Procesamiento de Streams**: Utiliza Kafka Streams para el procesamiento en tiempo real
- **Arquitectura Spring Boot**: Configuración automática y gestión de dependencias
- **Jakarta EE**: Implementación de especificaciones empresariales modernas
- **Lombok**: Código más limpio y mantenible
- **Containerización**: Preparado para despliegue con Docker

## 🔧 Configuración

Los archivos de configuración se encuentran en `src/main/resources/`:

- `application.properties` o : Configuración de Spring Boot `application.yml`
- Configuraciones de Kafka (brokers, topics, etc.)

## 📝 Desarrollo

### Convenciones de Código

- Uso de anotaciones Lombok para reducir boilerplate
- Imports Jakarta EE en lugar de javax
- Seguimiento de las convenciones de Spring Boot

### Testing

- Las pruebas se encuentran en `src/test/`
- Incluye pruebas unitarias e integración para componentes Kafka