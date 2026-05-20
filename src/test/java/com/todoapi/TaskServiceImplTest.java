package com.todoapi;

import com.todoapi.exception.TaskNotFoundException;
import com.todoapi.model.Task;
import com.todoapi.model.TaskDTO;
import com.todoapi.model.TaskStatus;
import com.todoapi.repository.TaskRepository;
import com.todoapi.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .titre("Ma première tâche")
                .description("Description de test")
                .statut(TaskStatus.A_FAIRE)
                .build();

        taskDTO = TaskDTO.builder()
                .titre("Ma première tâche")
                .description("Description de test")
                .statut(TaskStatus.A_FAIRE)
                .build();
    }

    @Test
    @DisplayName("Créer une tâche avec succès")
    void creerTache_Success() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.creerTache(taskDTO);

        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Ma première tâche");
        assertThat(result.getStatut()).isEqualTo(TaskStatus.A_FAIRE);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Obtenir toutes les tâches")
    void obtenirToutesTaches_Success() {
        Task task2 = Task.builder()
                .id(2L)
                .titre("Deuxième tâche")
                .statut(TaskStatus.EN_COURS)
                .build();

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task, task2));

        List<Task> result = taskService.obtenirToutesTaches();

        assertThat(result).hasSize(2);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Filtrer les tâches par statut")
    void obtenirTachesParStatut_Success() {
        when(taskRepository.findByStatut(TaskStatus.A_FAIRE)).thenReturn(List.of(task));

        List<Task> result = taskService.obtenirTachesParStatut(TaskStatus.A_FAIRE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatut()).isEqualTo(TaskStatus.A_FAIRE);
    }

    @Test
    @DisplayName("Obtenir une tâche par ID - trouvée")
    void obtenirTacheParId_Found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.obtenirTacheParId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Obtenir une tâche par ID - non trouvée")
    void obtenirTacheParId_NotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.obtenirTacheParId(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Mettre à jour une tâche avec succès")
    void mettreAJourTache_Success() {
        TaskDTO updateDTO = TaskDTO.builder()
                .titre("Titre modifié")
                .description("Nouvelle description")
                .statut(TaskStatus.TERMINE)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.mettreAJourTache(1L, updateDTO);

        assertThat(result).isNotNull();
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Supprimer une tâche avec succès")
    void supprimerTache_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.supprimerTache(1L);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("Supprimer une tâche inexistante - exception")
    void supprimerTache_NotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.supprimerTache(99L))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
