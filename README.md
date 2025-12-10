# Bibliothèque API

API REST pour la gestion d'une bibliothèque permettant de gérer des livres et des auteurs.

## 📋 Table des matières

- [Prérequis](#prérequis)
- [Installation et lancement](#installation-et-lancement)
- [Architecture](#architecture)
- [Endpoints disponibles](#endpoints-disponibles)
- [Exemples de requêtes](#exemples-de-requêtes)
- [Documentation interactive](#documentation-interactive)
- [Base de données](#base-de-données)
- [Sécurité](#sécurité)

## 🔧 Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur

## 🚀 Installation et lancement

### 1. Cloner le projet

```bash
git clone <repository-url>
cd bibliotheque_api
```

### 2. Compiler le projet

```bash
cd demo
mvn clean install
```

### 3. Lancer l'application

```bash
mvn spring-boot:run
```

L'application démarre sur **http://localhost:8080**

## 🏗️ Architecture

Le projet suit l'architecture MVC avec la structure suivante :

```
src/main/java/Makine/IUT/demo/
├── config/          # Configuration (sécurité, OpenAPI, CORS)
├── controller/      # Contrôleurs REST
├── service/         # Logique métier
├── repository/      # Accès aux données (Spring Data JPA)
├── domain/          # Entités JPA
├── dto/             # Objets de transfert et validation
├── exception/       # Gestion des erreurs
└── validator/       # Validateurs personnalisés
```

### Entités principales

- **Author**: `id`, `firstName`, `lastName`, `birthYear`
- **Book**: `id`, `title`, `isbn` (unique), `year`, `category`, `author` (ManyToOne)
- **Category** (enum): `NOVEL`, `ESSAY`, `POETRY`, `OTHER`

## 📡 Endpoints disponibles

### Auteurs

| Méthode | Endpoint | Description | API Key requise |
|---------|----------|-------------|-----------------|
| GET | `/authors` | Liste de tous les auteurs | ❌ |
| GET | `/authors/{id}` | Détail d'un auteur | ❌ |
| POST | `/authors` | Créer un auteur | ✅ |
| PUT | `/authors/{id}` | Modifier un auteur | ✅ |
| DELETE | `/authors/{id}` | Supprimer un auteur | ✅ |

### Livres

| Méthode | Endpoint | Description | API Key requise |
|---------|----------|-------------|-----------------|
| GET | `/books` | Liste paginée des livres avec filtres | ❌ |
| GET | `/books/{id}` | Détail d'un livre | ❌ |
| POST | `/books` | Créer un livre | ✅ |
| PUT | `/books/{id}` | Modifier un livre | ✅ |
| DELETE | `/books/{id}` | Supprimer un livre | ✅ |

### Statistiques

| Méthode | Endpoint | Description | API Key requise |
|---------|----------|-------------|-----------------|
| GET | `/stats/books-per-category` | Nombre de livres par catégorie | ❌ |
| GET | `/stats/top-authors?limit=3` | Top auteurs (par défaut: 3) | ❌ |

## 📝 Exemples de requêtes

### Créer un auteur

```bash
curl -X POST http://localhost:8080/authors \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: secret-api-key-2025" \
  -d '{
    "firstName": "Victor",
    "lastName": "Hugo",
    "birthYear": 1802
  }'
```

### Créer un livre

```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: secret-api-key-2025" \
  -d '{
    "title": "Les Misérables",
    "isbn": "978-2070409228",
    "year": 1862,
    "category": "NOVEL",
    "authorId": 1
  }'
```

### Lister les livres avec filtres

```bash
# Tous les livres (page 0, 10 éléments)
curl http://localhost:8080/books

# Filtrer par titre
curl "http://localhost:8080/books?title=Misérables"

# Filtrer par auteur
curl "http://localhost:8080/books?authorId=1"

# Filtrer par catégorie
curl "http://localhost:8080/books?category=NOVEL"

# Filtrer par plage d'années
curl "http://localhost:8080/books?yearFrom=1800&yearTo=1900"

# Tri par année décroissant
curl "http://localhost:8080/books?sort=year,desc"

# Combinaison de filtres avec pagination
curl "http://localhost:8080/books?category=NOVEL&yearFrom=1800&page=0&size=5&sort=year,desc"
```

### Récupérer les statistiques

```bash
# Livres par catégorie
curl http://localhost:8080/stats/books-per-category

# Top 5 auteurs
curl "http://localhost:8080/stats/top-authors?limit=5"
```

### Modifier un livre

```bash
curl -X PUT http://localhost:8080/books/1 \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: secret-api-key-2025" \
  -d '{
    "title": "Les Misérables (Édition intégrale)",
    "isbn": "978-2070409228",
    "year": 1862,
    "category": "NOVEL",
    "authorId": 1
  }'
```

### Supprimer un livre

```bash
curl -X DELETE http://localhost:8080/books/1 \
  -H "X-API-KEY: secret-api-key-2025"
```

## 📚 Documentation interactive

### Swagger UI

Accédez à la documentation interactive Swagger UI :
**http://localhost:8080/swagger-ui.html**

### OpenAPI JSON

Documentation OpenAPI au format JSON :
**http://localhost:8080/api-docs**

## 💾 Base de données

L'application utilise une base de données **H2 en mémoire** pour faciliter le développement et les tests.

### Console H2

Accédez à la console H2 : **http://localhost:8080/h2-console**

- **JDBC URL**: `jdbc:h2:mem:bibliotheque`
- **Username**: `sa`
- **Password**: *(laisser vide)*

### Migration vers MariaDB/MySQL

Pour utiliser MariaDB en production, modifiez `application.properties` :

```properties
# Remplacer la configuration H2 par :
spring.datasource.url=jdbc:mariadb://localhost:3306/bibliotheque
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 🔐 Sécurité

### Clé API

Les requêtes **POST**, **PUT** et **DELETE** nécessitent une clé API dans le header :

```
X-API-KEY: secret-api-key-2025
```

Les requêtes **GET** sont publiques et ne nécessitent pas de clé API.

### Modifier la clé API

Dans `application.properties` :

```properties
api.key=votre-nouvelle-cle-api
```

## ✅ Validation

### ISBN

- Format accepté : ISBN-10 ou ISBN-13
- Exemples valides :
  - `978-2070409228` (ISBN-13)
  - `0-596-52068-9` (ISBN-10)
  - `2070409228` (sans tirets)

### Année

- Doit être entre **1450** et l'année actuelle

### Champs obligatoires

- **Author** : `firstName`, `lastName`, `birthYear`
- **Book** : `title`, `isbn`, `year`, `category`, `authorId`

## 🐛 Gestion des erreurs

Les erreurs sont retournées en JSON structuré :

### Exemple : Ressource non trouvée (404)

```json
{
  "timestamp": "2025-12-10T10:30:45",
  "status": 404,
  "error": "Not Found",
  "message": "Author not found with id: 999",
  "path": "/authors/999"
}
```

### Exemple : Validation échouée (400)

```json
{
  "timestamp": "2025-12-10T10:30:45",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/books",
  "validationErrors": {
    "isbn": "ISBN format is invalid (must be ISBN-10 or ISBN-13)",
    "title": "Title is required"
  }
}
```

### Exemple : Clé API invalide (401)

```json
{
  "timestamp": "2025-12-10T10:30:45",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or missing API key",
  "path": "/authors"
}
```

### Exemple : ISBN déjà existant (409)

```json
{
  "timestamp": "2025-12-10T10:30:45",
  "status": 409,
  "error": "Conflict",
  "message": "Book with ISBN 978-2070409228 already exists",
  "path": "/books"
}
```

## 📦 Tests avec Postman

Une collection Postman complète est disponible : `Bibliotheque_API.postman_collection.json`

Pour l'importer dans Postman :
1. Ouvrez Postman
2. Cliquez sur **Import**
3. Sélectionnez le fichier JSON
4. La collection contient tous les endpoints avec des exemples

## 🛠️ Technologies utilisées

- **Spring Boot 3.2.1** - Framework backend
- **Spring Data JPA** - Accès aux données
- **Hibernate** - ORM
- **H2 Database** - Base de données en mémoire
- **Spring Validation** - Validation des données
- **Lombok** - Réduction du code boilerplate
- **SpringDoc OpenAPI** - Documentation API
- **Maven** - Gestion des dépendances

## 📄 Licence

Ce projet est fourni à des fins éducatives.