package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping(path = "/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDTO> showPosts() {
        var posts = postRepository.findAll();
        var postsDTO = posts.stream().map(this::toPostDTO).toList();
        return postsDTO;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDTO showPost(@PathVariable long id) {
        var post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post with id "+id+" not found"));
        var postDTO = toPostDTO(post);
        return postDTO;


    }

    private PostDTO toPostDTO(Post post) {
        var postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());
        postDTO.setComments(commentRepository
                .findByPostId(post.getId())
                .stream()
                .map(this::toCommentDTO)
                .toList());
        return postDTO;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        var commentDTO = new CommentDTO();
        commentDTO.setBody(comment.getBody());
        commentDTO.setId(comment.getId());
        return commentDTO;
    }
}
// END
