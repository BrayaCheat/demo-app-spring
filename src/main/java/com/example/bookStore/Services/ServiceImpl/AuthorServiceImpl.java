package com.example.bookStore.Services.ServiceImpl;

import com.example.bookStore.Exceptions.AuthorNotFoundException;
import com.example.bookStore.Models.Author;
import com.example.bookStore.Repositories.AuthorRepository;
import com.example.bookStore.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static String notFoundMessage = "Author not found with this id: ";
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(Author author) {
        Optional<Author> existingAuthor = authorRepository.findByName(author.getName());
        if(existingAuthor.isPresent()){
            throw new RuntimeException("Author with name: " + author.getName() + " is already exist");
        }
        return authorRepository.save(author);
    }

    @Override
    public List<Author> listAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthor(Integer id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException(notFoundMessage + id));
    }

    @Override
    public void deleteAuthor(Integer authorId) {
        if(!authorRepository.existsById(authorId)){
            throw new RuntimeException("Author with id" + authorId + " not found");
        }
        authorRepository.deleteById(authorId);
    }

    @Override
    public Author updateAuthor(Author author, Integer id) {
        try{
            Author checkAuthor = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(notFoundMessage + id));
            checkAuthor.setName(author.getName());
            checkAuthor.setGender(author.getGender());
            checkAuthor.setUpdatedAt(LocalDateTime.now());
            return authorRepository.save(checkAuthor);
        }catch(RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
