# 📋 API de Gestion de Tâches (To-Do List)

API REST développée avec **Spring Boot** permettant de gérer une liste de tâches avec les opérations CRUD complètes.

## 🛠 Stack Technologique

| Technologie | Usage |
|---|---|
| **Java 17** | Langage |
| **Spring Boot 3.2** | Framework |
| **Spring Data JPA** | Accès aux données |
| **H2 Database** | Base de données en mémoire (dev) |
| **MySQL** | Base de données (production) |
| **Lombok** | Réduction du boilerplate |
| **Springdoc OpenAPI** | Documentation Swagger |
| **Maven** | Gestion des dépendances |

## 🚀 Lancer l'API

### Prérequis

- **Java 17+** installé ([télécharger](https://adoptium.net/))
- **Maven 3.8+** installé ([télécharger](https://maven.apache.org/download.cgi))

### Démarrage rapide (H2 - mode développement)

```bash
# 1. Cloner le projet
git clone https://github.com/<votre-username>/todo-api.git
cd todo-api

# 2. Compiler et lancer
mvn spring-boot:run
```

L'API est disponible sur **http://localhost:8080**

### Démarrage avec MySQL (production)

```bash
# 1. Créer la base de données
mysql -u root -p -e "CREATE DATABASE tododb;"

# 2. Modifier les identifiants dans application-mysql.properties si nécessaire

# 3. Lancer avec le profil MySQL
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## 📖 Documentation API (Swagger)

Une fois l'API lancée, accéder à :

| Ressource | URL |
|---|---|
| **Swagger UI** | http://localhost:8080/swagger-ui.html |
| **OpenAPI JSON** | http://localhost:8080/api-docs |
| **Console H2** | http://localhost:8080/h2-console |

## 📡 Endpoints

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

## 🏗 Structure du projet

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

## 🧪 Tests

```bash
mvn test
```

## 👤 Auteur

**KFOKAM48** — Projet de fin de formation Fullstack
