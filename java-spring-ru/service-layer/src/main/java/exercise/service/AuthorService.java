package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::map)
                .toList();
    }

    public AuthorDTO findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + "not found"));
        AuthorDTO authorDTO = authorMapper.map(author);
        return authorDTO;
    }

    public AuthorDTO create(AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.map(authorCreateDTO);
        authorRepository.save(author);
        AuthorDTO authorDTO = authorMapper.map(author);
        return authorDTO;
    }

    public AuthorDTO update(AuthorUpdateDTO authorUpdateDTO, Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author with id " + id + "not found"));
        authorMapper.update(authorUpdateDTO, author);
        authorRepository.save(author);
        AuthorDTO authorDTO = authorMapper.map(author);
        return authorDTO;
    }

    public void delete(long id) {
        authorRepository.deleteById(id);
    }
    // END
}
