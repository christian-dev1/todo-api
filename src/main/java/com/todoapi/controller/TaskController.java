package com.todoapi.controller;

import com.todoapi.model.Task;
import com.todoapi.model.TaskDTO;
import com.todoapi.model.TaskStatus;
import com.todoapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
@Tag(name = "Tâches", description = "API de gestion des tâches (To-Do List)")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Créer une tâche", description = "Crée une nouvelle tâche avec un titre, une description et un statut")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tâche créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Task> creerTache(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskService.creerTache(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    @Operation(summary = "Lire toutes les tâches", description = "Retourne toutes les tâches, avec possibilité de filtrer par statut")
    @ApiResponse(responseCode = "200", description = "Liste des tâches récupérée avec succès")
    public ResponseEntity<List<Task>> obtenirTaches(
            @Parameter(description = "Filtrer par statut : A_FAIRE, EN_COURS, TERMINE")
            @RequestParam(required = false) TaskStatus statut) {

        List<Task> taches;
        if (statut != null) {
            taches = taskService.obtenirTachesParStatut(statut);
        } else {
            taches = taskService.obtenirToutesTaches();
        }
        return ResponseEntity.ok(taches);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lire une tâche par ID", description = "Retourne une tâche spécifique par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tâche trouvée"),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    public ResponseEntity<Task> obtenirTacheParId(@PathVariable Long id) {
        Task task = taskService.obtenirTacheParId(id);
        return ResponseEntity.ok(task);
    }

    // ==================== UPDATE ====================

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une tâche", description = "Modifie le titre, la description ou le statut d'une tâche existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tâche mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    public ResponseEntity<Task> mettreAJourTache(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskService.mettreAJourTache(id, taskDTO);
        return ResponseEntity.ok(task);
    }

    // ==================== DELETE ====================

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche", description = "Supprime une tâche par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tâche supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
    })
    public ResponseEntity<Void> supprimerTache(@PathVariable Long id) {
        taskService.supprimerTache(id);
        return ResponseEntity.noContent().build();
    }
}
