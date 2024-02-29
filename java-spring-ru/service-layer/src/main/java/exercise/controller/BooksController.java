package exercise.controller;

import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping(path = "")
    List<BookDTO> index() {
        return bookService.getAll();
    }

    @GetMapping(path = "/{id}")
    BookDTO show(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    BookDTO create(@Valid @RequestBody BookCreateDTO authorCreateDTO) {
        return bookService.create(authorCreateDTO);
    }

    @PutMapping(path = "/{id}")
    BookDTO update(@Valid @RequestBody BookUpdateDTO authorUpdateDTO, @PathVariable Long id) {
        return bookService.update(authorUpdateDTO, id);
    }

    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
    // END
}
