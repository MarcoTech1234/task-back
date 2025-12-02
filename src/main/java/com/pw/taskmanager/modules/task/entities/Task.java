package com.pw.taskmanager.modules.task.entities;

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
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String nome;
    private String descricao;
    private String data;
    private Status status;
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

}
