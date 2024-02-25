package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    //GET /tasks – просмотр списка всех задач
    //GET /tasks/{id} – просмотр конкретной задачи
    //POST /tasks – создание новой задачи
    //PUT /tasks/{id} – редактирование задачи. При редактировании мы должны иметь возможность поменять название, описание задачи и ответственного разработчика
    //DELETE /tasks/{id} – удаление задачи

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> showTasks() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO showTask(@PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));
        return taskMapper.map(task);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        var task = taskMapper.map(taskCreateDTO);
        var user = userRepository.findById(taskCreateDTO.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User with not found"));
        task.setAssignee(user);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateTask(@PathVariable long id, @RequestBody @Valid TaskUpdateDTO taskUpdateDTO) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));
        taskMapper.update(taskUpdateDTO, task);
        var user = userRepository.findById(taskUpdateDTO.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        task.setAssignee(user);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));
        taskRepository.deleteById(id);
    }
    // END
}
