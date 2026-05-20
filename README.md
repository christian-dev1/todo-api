# API de Gestion de Tâches (To-Do List)

API REST développée avec **Spring Boot** permettant de gérer une liste de tâches avec les opérations CRUD complètes.

## Stack Technologique

| Technologie | Usage |
|---|---|
| **Java 17** | Langage |
| **Spring Boot 3.2** | Framework |
| **Spring Data JPA** | Accès aux données |
| **PostgreSQL 16** | Base de données |
| **Docker / Docker Compose** | Conteneurisation |
| **Lombok** | Réduction du boilerplate |
| **Springdoc OpenAPI** | Documentation Swagger |
| **Maven** | Gestion des dépendances |

## Lancer l'API

### Prérequis

- **Docker** et **Docker Compose** installés

### Démarrage avec Docker (recommandé)

```bash
# 1. Cloner le projet
git clone https://github.com/christian-dev1/todo-api.git
cd todo-api

# 2. Lancer l'application et la base de données
docker compose up -d
```

L'API est disponible sur **http://localhost:8080**

### Démarrage sans Docker

Prérequis : Java 17+, Maven 3.8+, PostgreSQL en local

```bash
# Définir les variables d'environnement
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/tododb
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=votre_mot_de_passe

# Lancer
mvn spring-boot:run
```

## Documentation API (Swagger)

Une fois l'API lancée :

| Ressource | URL |
|---|---|
| **Swagger UI** | http://localhost:8080/swagger-ui.html |
| **OpenAPI JSON** | http://localhost:8080/api-docs |

## Endpoints

| Méthode | URL | Description |
|---|---|---|
| `POST` | `/api/taches` | Créer une tâche |
| `GET` | `/api/taches` | Lire toutes les tâches |
| `GET` | `/api/taches?statut=A_FAIRE` | Filtrer par statut |
| `GET` | `/api/taches/{id}` | Lire une tâche par ID |
| `PUT` | `/api/taches/{id}` | Mettre à jour une tâche |
| `DELETE` | `/api/taches/{id}` | Supprimer une tâche |

### Statuts disponibles

- `A_FAIRE` — À faire
- `EN_COURS` — En cours
- `TERMINE` — Terminé

### Exemples de requêtes

#### Créer une tâche

```bash
curl -X POST http://localhost:8080/api/taches \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Apprendre Spring Boot",
    "description": "Suivre le tutoriel officiel",
    "statut": "A_FAIRE"
  }'
```

#### Lire toutes les tâches

```bash
curl http://localhost:8080/api/taches
```

#### Filtrer par statut

```bash
curl http://localhost:8080/api/taches?statut=EN_COURS
```

#### Mettre à jour une tâche

```bash
curl -X PUT http://localhost:8080/api/taches/1 \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Apprendre Spring Boot",
    "description": "Tutoriel terminé !",
    "statut": "TERMINE"
  }'
```

#### Supprimer une tâche

```bash
curl -X DELETE http://localhost:8080/api/taches/1
```

## Structure du projet

```
src/main/java/com/todoapi/
├── TodoApiApplication.java          # Point d'entrée
├── config/
│   └── SwaggerConfig.java           # Configuration Swagger
├── controller/
│   └── TaskController.java          # Endpoints REST
├── exception/
│   ├── GlobalExceptionHandler.java  # Gestion globale des erreurs
│   └── TaskNotFoundException.java   # Exception personnalisée
├── model/
│   ├── Task.java                    # Entité JPA
│   ├── TaskDTO.java                 # Objet de transfert
│   └── TaskStatus.java              # Enum des statuts
├── repository/
│   └── TaskRepository.java          # Interface JPA
└── service/
    ├── TaskService.java             # Interface service
    └── TaskServiceImpl.java         # Implémentation
```

## Tests

```bash
mvn test
```

## Auteur

**KFOKAM48** — Projet de spécialisation Java