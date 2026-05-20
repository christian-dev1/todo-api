package com.todoapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 1, max = 100, message = "Le titre doit contenir entre 1 et 100 caractères")
    private String titre;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @NotNull(message = "Le statut est obligatoire")
    private TaskStatus statut;
}
