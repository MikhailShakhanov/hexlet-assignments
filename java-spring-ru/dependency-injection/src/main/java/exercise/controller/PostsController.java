package exercise.controller;

import exercise.repository.CommentRepository;
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

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
//Реализуйте полный CRUD сущности Post. Необходимо реализовать следующие маршруты:
//
//GET /posts — список всех постов
//GET /posts/{id} – просмотр конкретного поста
//POST /posts – создание нового поста. При успешном создании возвращается статус 201
//PUT /posts/{id} – обновление поста
//DELETE /posts/{id} – удаление поста. При удалении поста удаляются все комментарии этого поста
//В классе контроллера добавьте инъекцию нужных репозиториев.
// Используйте метод deleteByPostId() в репозитории комментариев для удаления комментариев по id поста.
@RestController
@RequestMapping(path = "posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> showPosts() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post showPost(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post with id "+id+" not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post postData) {
        return postRepository.save(postData);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@PathVariable Long id, @RequestBody Post postData) {
        var post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post with id "+id+" not found"));
        post.setBody(postData.getBody());
        post.setTitle(postData.getTitle());
        postRepository.save(post);
        return post;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePost(@PathVariable Long id) {
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }
}
// END
