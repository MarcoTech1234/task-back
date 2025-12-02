package com.pw.taskmanager.modules.task.listener;

import com.pw.taskmanager.modules.task.controller.response.TaskResponseDto;
import com.pw.taskmanager.modules.task.dto.task.TaskUpdateDto;
import com.pw.taskmanager.modules.task.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class TaskListener {

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.to}")
    private String to;

    @KafkaListener(topics = "email-processor", containerFactory = "kafkaListenerContainerFactory")
    public void listen(TaskResponseDto taskResponseDto) {

        String body = """
        Olá!

        A tarefa foi atualizada com sucesso.

        Detalhes da tarefa:
        • ID: %d
        • Nome: %s
        • Descrição: %s
        • Data: %s
        • Status: %s
        • Prioridade: %s
        • Categoria: %s

        Obrigado por utilizar nosso sistema!
        """.formatted(
                taskResponseDto.id(),
                taskResponseDto.nome(),
                taskResponseDto.descricao(),
                taskResponseDto.data(),
                taskResponseDto.status(),
                taskResponseDto.priority(),
                taskResponseDto.category().nome()
        );

        mailService.sendMail(to, "Tarefa Finalizada", body);
    }
}
