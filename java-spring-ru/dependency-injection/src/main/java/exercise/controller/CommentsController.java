package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
//Реализуйте полный CRUD сущности Comment. Необходимо реализовать следующие маршруты:
//
//GET /comments — список всех комментариев
//GET /comments/{id} – просмотр конкретного комментария
//POST /comments – создание нового комментария. При успешном создании возвращается статус 201
//PUT /comments/{id} – обновление комментария
//DELETE /comments/{id} – удаление комментария
//В классе контроллера добавьте инъекцию репозитория с комментариями.
@RestController
@RequestMapping(path = "/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> showComments() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment showComment(@PathVariable Long id) {
        return commentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Comment with id "+id+" not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment commentData) {
        var comment = commentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Comment with id "+id+" not found"));
        comment.setBody(commentData.getBody());
        commentRepository.save(comment);
        return  comment;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
// END
