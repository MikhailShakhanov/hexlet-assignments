package exercise.controller.users;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public  class PostsController {
    private List<Post> posts = Data.getPosts();
    @GetMapping("/users/{id}/posts") // Список страниц
    public ResponseEntity<List<Post>> showUserPosts(@PathVariable String id
            //,@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "1") Integer page
    ) {
        var result = posts.stream()
                .filter(p -> String.valueOf(p.getUserId()).equals(id))
                //.skip((long) (page - 1) * limit)
                //.limit(limit)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);
    }

    @PostMapping("/users/{id}/posts") // Создание страницы
    public ResponseEntity<Post> create(@PathVariable String id, @RequestBody Post post) {
        post.setUserId(Integer.parseInt(id));
        posts.add(post);
        URI location = URI.create("/users/"+id+"/posts");
        return ResponseEntity
                //.status(HttpStatus.CREATED)
                .created(location)
                .body(post);
    }
}
// END
