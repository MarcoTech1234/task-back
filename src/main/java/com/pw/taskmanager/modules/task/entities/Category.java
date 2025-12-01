package com.pw.taskmanager.modules.task.entities;

import com.pw.taskmanager.modules.task.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity()
@Table(name = "category")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks;

}
