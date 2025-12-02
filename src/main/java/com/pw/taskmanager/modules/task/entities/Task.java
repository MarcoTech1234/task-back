package com.pw.taskmanager.modules.task.entities;

import com.pw.taskmanager.modules.task.dto.task.TaskUpdateDto;
import com.pw.taskmanager.modules.task.enums.Priority;
import com.pw.taskmanager.modules.task.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id()
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String descricao;
    private String data;
    private Status status;
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    public void toUpdate(TaskUpdateDto task) {
        if (task.nome() != null)
            this.nome = task.nome().trim();
        if (task.descricao() != null)
            this.descricao = task.descricao();
        if (task.data() != null)
            this.data = task.data().toString();
        if (task.status() != null)
            this.status = task.status();
        if (task.priority() != null)
            this.priority = task.priority();
    }
}
