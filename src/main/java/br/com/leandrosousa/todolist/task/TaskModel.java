package br.com.leandrosousa.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String descrição;
    @Column(length = 50)
    private String titulo;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private String prioridade;

    @CreationTimestamp
    private String createdAt;

    private UUID idUser;

    public void setTitulo(String titulo) throws Exception {
        if (titulo.length() > 50) {
            throw new Exception("O campo titulo deve ter ate 50 caracteres");
        }
        this.titulo = titulo;
    }
}
