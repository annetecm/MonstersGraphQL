# 📚 Documentación del Proyecto Spring GraphQL
# Creditos para: DavinchiCoder

## Descripción General

Este proyecto es una aplicación **Spring Boot** que implementa una API GraphQL para la gestión de posts/publicaciones.
La aplicación utiliza las siguientes tecnologías principales:

- **Spring Boot 3.5.6**
- **Spring GraphQL**
- **Java 21**
- **Lombok**
- **Maven** como gestor de dependencias

## 🏗️ Arquitectura del Proyecto

La aplicación sigue una arquitectura de capas bien definida:

``` 
src/main/java/com/davinchicoder/springgraphql/
├── controller/     # Controladores GraphQL
├── dto/           # Data Transfer Objects
├── entity/        # Entidades del dominio
├── mapper/        # Mapeadores de datos
├── repository/    # Capa de acceso a datos
└── SpringGraphqlApplication.java  # Clase principal
```

## 📋 Componentes Principales

### 1. **PostController**

Controlador GraphQL que maneja las operaciones CRUD de posts:

#### Queries disponibles:

- `getAllPosts()`: Obtiene todos los posts
- `getRecentPosts(count, offset)`: Obtiene posts recientes con paginación
- `getPostById(id)`: Obtiene un post específico por ID

#### Mutations disponibles:

- `savePost(postDto)`: Crea un nuevo post
- `deletePostById(id)`: Elimina un post por ID

### 2. **Entidad Post**

La entidad principal que representa una publicación con los siguientes campos:

- `id`: Identificador único
- `title`: Título del post
- `content`: Contenido del post
- `author`: Autor del post
- `imageUrl`: URL de la imagen
- : Fecha de creación `createdAt`
- : Fecha de actualización `updatedAt`
- : Fecha de eliminación (soft delete) `deletedAt`

### 3. **PostDto**

DTO utilizado para la creación y actualización de posts:

- `title`: Título
- `content`: Contenido
- `author`: Autor
- `imageUrl`: URL de la imagen

### 4. **Schema GraphQL**

Definido en , incluye: `schema.graphqls`

- Tipo con todos sus campos `Post`
- Input para operaciones de escritura `PostDto`
- Queries para lectura de datos
- Mutations para modificación de datos

## 🚀 Configuración y Ejecución

### Prerrequisitos

- Java 21
- Maven 3.x

### Comandos de ejecución

``` bash
# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
```

### Acceso a GraphQL

Una vez iniciada la aplicación, puedes acceder a:

- **GraphQL Playground**: `http://localhost:8080/graphiql`
- **Endpoint GraphQL**: `http://localhost:8080/graphql`

## 📊 Ejemplos de Uso

### Query - Obtener todos los posts

``` graphql
query {
  getAllPosts {
    id
    title
    content
    author
    imageUrl
    createdAt
  }
}
```

### Query - Obtener posts recientes

``` graphql
query {
  getRecentPosts(count: 10, offset: 0) {
    id
    title
    author
    createdAt
  }
}
```

### Mutation - Crear un nuevo post

``` graphql
mutation {
  savePost(postDto: {
    title: "Mi nuevo post"
    content: "Contenido del post"
    author: "Autor"
    imageUrl: "https://ejemplo.com/imagen.jpg"
  }) {
    id
    title
    createdAt
  }
}
```

### Mutation - Eliminar un post

``` graphql
mutation {
  deletePostById(id: "1") {
    id
    title
    deletedAt
  }
}
```

## 🔧 Configuración Adicional

### Maven

El proyecto utiliza las siguientes dependencias principales:

- `spring-boot-starter-graphql`
- `spring-boot-starter-web`
- `lombok`
- `spring-boot-starter-test`
- `spring-graphql-test`

### Lombok

Se configura el procesador de anotaciones de Lombok para generar automáticamente getters, setters y constructores.

## 📁 Estructura de Archivos

``` 
spring-graphql/
├── src/
│   ├── main/
│   │   ├── java/com/davinchicoder/springgraphql/
│   │   └── resources/
│   │       ├── graphql/schema.graphqls
│   │       └── application.yml
│   └── test/
├── pom.xml
├── README.md
└── otros archivos de configuración
```

## 🎯 Funcionalidades Implementadas

- ✅ **API GraphQL** completa para gestión de posts
- ✅ **Operaciones CRUD** (Create, Read, Update, Delete)
- ✅ **Paginación** en consultas
- ✅ **Mapeo de datos** entre DTOs y entidades
- ✅ **Manejo de excepciones** básico
- ✅ **Tests unitarios** configurados
- ✅ **Configuración Maven** optimizada

Esta documentación proporciona una visión completa del proyecto y debe servir como guía tanto para el desarrollo como
para el mantenimiento de la aplicación.