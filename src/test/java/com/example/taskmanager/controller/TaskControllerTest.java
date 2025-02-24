// Implementando pruebas unitarias usando JUnit para Spring Boot

package com.example.taskmanager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() throws Exception {
        // Datos de prueba
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Tarea 1");
        task1.setDescription("Descripción 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Tarea 2");
        task2.setDescription("Descripción 2");

        List<Task> tasks = Arrays.asList(task1, task2);

        // Mock del repositorio
        when(taskRepository.findAll()).thenReturn(tasks);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/tasks"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].title").value("Tarea 1"))
               .andExpect(jsonPath("$[1].title").value("Tarea 2"));
    }
}