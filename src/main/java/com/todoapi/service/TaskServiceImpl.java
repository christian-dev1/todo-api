package com.todoapi.service;

import com.todoapi.exception.TaskNotFoundException;
import com.todoapi.model.Task;
import com.todoapi.model.TaskDTO;
import com.todoapi.model.TaskStatus;
import com.todoapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task creerTache(TaskDTO taskDTO) {
        Task task = Task.builder()
                .titre(taskDTO.getTitre())
                .description(taskDTO.getDescription())
                .statut(taskDTO.getStatut())
                .build();
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> obtenirToutesTaches() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> obtenirTachesParStatut(TaskStatus statut) {
        return taskRepository.findByStatut(statut);
    }

    @Override
    @Transactional(readOnly = true)
    public Task obtenirTacheParId(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task mettreAJourTache(Long id, TaskDTO taskDTO) {
        Task task = obtenirTacheParId(id);
        task.setTitre(taskDTO.getTitre());
        task.setDescription(taskDTO.getDescription());
        task.setStatut(taskDTO.getStatut());
        return taskRepository.save(task);
    }

    @Override
    public void supprimerTache(Long id) {
        Task task = obtenirTacheParId(id);
        taskRepository.delete(task);
    }
}
