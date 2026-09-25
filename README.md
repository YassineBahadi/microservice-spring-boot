# Microservice Compte

Microservice de gestion des comptes bancaires développé avec **Spring Boot** dans le cadre d'une activité pratique sur les architectures distribuées et les microservices.

## Technologies utilisées

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Data REST
* Spring GraphQL
* H2 Database
* Lombok
* Swagger / OpenAPI
* Maven

## Architecture

L'application suit une architecture en couches :

```text
Client
   │
   ├── REST API
   │
   ├── Spring Data REST
   │
   └── GraphQL
          │
          ▼
     Controller
          │
          ▼
       Service
     (logique métier)
          │
          ▼
        Mapper
          │
          ▼
       Repository
          │
          ▼
     H2 Database
```

## Structure du projet

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── yassine/
    │           └── microservice/
    │               ├── MicroserviceApplication.java
    │               │
    │               ├── entities/
    │               │   ├── Compte.java
    │               │   └── TypeCompte.java
    │               │
    │               ├── repositories/
    │               │   └── CompteRepository.java
    │               │
    │               ├── controllers/
    │               │   ├── CompteController.java
    │               │   └── CompteGraphQLController.java
    │               │
    │               ├── dtos/
    │               │   └── CompteDTO.java
    │               │
    │               ├── mappers/
    │               │   └── CompteMapper.java
    │               │
    │               ├── services/
    │               │   ├── CompteService.java
    │               │   └── CompteServiceImpl.java
    │               │
    │               ├── projections/
    │               │   └── CompteProjection.java
    │               │
    │               └── config/
    │                   └── OpenAPIConfig.java
    │
    └── resources/
        ├── application.properties
        └── graphql/
            └── schema.graphqls
```

## 1. Entité Compte

L'entité `Compte` représente un compte bancaire dans la base de données.

```java
@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double solde;

    private String dateCreation;

    @Enumerated(EnumType.STRING)
    private TypeCompte type;
}
```

Les types de comptes disponibles sont :

```java
public enum TypeCompte {
    COURANT,
    EPARGNE
}
```

## 2. Repository

Le repository utilise Spring Data JPA :

```java
public interface CompteRepository extends JpaRepository<Compte, Long> {
}
```

Il permet notamment d'utiliser automatiquement :

* `save()`
* `findAll()`
* `findById()`
* `deleteById()`
* `existsById()`

## 3. API REST

Une API REST a été créée avec Spring Web.

Base URL :

```text
http://localhost:8080/api/comptes
```

### Récupérer tous les comptes

```http
GET /api/comptes
```

### Récupérer un compte

```http
GET /api/comptes/{id}
```

### Créer un compte

```http
POST /api/comptes
Content-Type: application/json
```

Exemple :

```json
{
  "solde": 15000,
  "dateCreation": "25/09/2026",
  "type": "EPARGNE"
}
```

### Modifier un compte

```http
PUT /api/comptes/{id}
```

### Supprimer un compte

```http
DELETE /api/comptes/{id}
```

## 4. Spring Data REST

Spring Data REST permet d'exposer automatiquement le repository sous forme d'API REST.

Endpoint :

```text
http://localhost:8080/comptes
```

Exemples :

```http
GET /comptes
```

```http
GET /comptes/1
```

Spring Data REST génère automatiquement les endpoints à partir de `CompteRepository`.

## 5. Projection

Une projection a été créée afin de contrôler les données exposées :

```java
@Projection(name = "compteProjection", types = Compte.class)
public interface CompteProjection {

    Long getId();

    double getSolde();

    String getDateCreation();
}
```

Utilisation :

```http
GET /comptes/1?projection=compteProjection
```

La projection permet de retourner uniquement les propriétés sélectionnées.

## 6. DTO

Un `CompteDTO` a été créé afin de séparer les données exposées par l'API de l'entité JPA.

```java
public class CompteDTO {

    private Long id;
    private double solde;
    private String dateCreation;
    private TypeCompte type;
}
```

L'architecture devient :

```text
Entity ↔ Mapper ↔ DTO
```

## 7. Mapper

Le `CompteMapper` assure les conversions :

```text
Compte → CompteDTO
CompteDTO → Compte
```

Exemple :

```java
public static CompteDTO fromCompte(Compte compte) {
    return CompteDTO.builder()
            .id(compte.getId())
            .solde(compte.getSolde())
            .dateCreation(compte.getDateCreation())
            .type(compte.getType())
            .build();
}
```

## 8. Couche Service

La logique métier est centralisée dans `CompteService`.

```java
public interface CompteService {

    CompteDTO saveCompte(CompteDTO compteDTO);

    CompteDTO getCompte(Long id);

    List<CompteDTO> getAllComptes();

    CompteDTO updateCompte(Long id, CompteDTO compteDTO);

    void deleteCompte(Long id);
}
```

L'implémentation utilise `CompteRepository`.

Architecture :

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

## 9. Documentation Swagger / OpenAPI

Swagger permet de visualiser et tester l'API REST.

### Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI

```text
http://localhost:8080/v3/api-docs
```

Swagger permet notamment de :

* consulter les endpoints ;
* voir les paramètres ;
* consulter les modèles ;
* tester les requêtes directement depuis l'interface.

## 10. API GraphQL

Une API GraphQL a également été ajoutée.

Endpoint :

```text
http://localhost:8080/graphql
```

Interface GraphiQL :

```text
http://localhost:8080/graphiql
```

### Query : récupérer les comptes

```graphql
query {
    comptes {
        id
        solde
        dateCreation
        type
    }
}
```

### Query : récupérer un compte

```graphql
query {
    compte(id: 1) {
        id
        solde
        type
    }
}
```

### Mutation : créer un compte

```graphql
mutation {
    saveCompte(
        solde: 25000,
        dateCreation: "25/09/2026",
        type: "COURANT"
    ) {
        id
        solde
        dateCreation
        type
    }
}
```

### Mutation : supprimer un compte

```graphql
mutation {
    deleteCompte(id: 3)
}
```

L'un des principaux avantages de GraphQL est que le client peut demander uniquement les champs dont il a besoin.

Exemple :

```graphql
query {
    comptes {
        id
        solde
    }
}
```

## 11. Base de données H2

L'application utilise une base de données H2 en mémoire.

Configuration :

```properties
spring.datasource.url=jdbc:h2:mem:microdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

La base est recréée au démarrage de l'application.

## 12. Lancement du projet

Cloner le projet :

```bash
git clone <URL_DU_REPOSITORY>
```

Accéder au projet :

```bash
cd <NOM_DU_PROJET>
```

Lancer l'application avec Maven :

```bash
mvn spring-boot:run
```

Ou compiler le projet :

```bash
mvn clean package
```

Puis lancer le fichier JAR :

```bash
java -jar target/*.jar
```

L'application est accessible sur :

```text
http://localhost:8080
```

## 13. Tests

Les APIs peuvent être testées avec :

* Postman
* Swagger UI
* GraphiQL
* navigateur pour les requêtes GET

Exemples :

```text
REST
http://localhost:8080/api/comptes

Spring Data REST
http://localhost:8080/comptes

Swagger
http://localhost:8080/swagger-ui/index.html

GraphiQL
http://localhost:8080/graphiql
```

## 14. Historique des étapes

Le projet a été réalisé progressivement avec Git :

```text
chore: initialize Spring Boot microservice

feat: add Compte JPA entity

feat: add Compte repository

test: verify account DAO operations

feat: add REST API for accounts

test: validate REST API with Postman

docs: add Swagger OpenAPI documentation

feat: expose accounts with Spring Data REST projections

feat: add account DTO and mapper

feat: add service layer and business logic

feat: add GraphQL API for accounts
```

## Conclusion

Ce projet met en œuvre les principaux éléments d'un microservice Spring Boot :

```text
Spring Boot
    │
    ├── REST API
    ├── Spring Data REST
    ├── Swagger / OpenAPI
    ├── DTOs
    ├── Mappers
    ├── Service Layer
    ├── GraphQL
    ├── Spring Data JPA
    └── H2 Database
```
