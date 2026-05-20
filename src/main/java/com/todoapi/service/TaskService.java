package com.todoapi.service;

import com.todoapi.model.Task;
import com.todoapi.model.TaskDTO;
import com.todoapi.model.TaskStatus;

import java.util.List;

public interface TaskService {

    Task creerTache(TaskDTO taskDTO);

    List<Task> obtenirToutesTaches();

    List<Task> obtenirTachesParStatut(TaskStatus statut);

    Task obtenirTacheParId(Long id);

    Task mettreAJourTache(Long id, TaskDTO taskDTO);

    void supprimerTache(Long id);
}
