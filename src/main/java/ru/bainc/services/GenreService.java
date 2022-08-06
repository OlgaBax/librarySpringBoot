package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.model.Genre;
import ru.bainc.repositories.GenreRepository;
import java.util.List;

@Slf4j
@Service
@Transactional
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService (GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAll(){
        return genreRepository.findAll();
    }

    public Genre getById (Long id){
        return genreRepository.getById(id);
    }

    public Genre getByGenreTitle(String genreTitle){
        Genre genre = genreRepository.findByGenreTitle(genreTitle);
        log.info("Genre with title {} found", genreTitle);
        return  genre;
    }

    @Transactional
    public Genre addGenre(Genre genre){
        Genre genre1 = genreRepository.save(genre);
        return genre1;
    }

    @Transactional
    public void deleteGenreById (Long id){
        genreRepository.deleteById(id);
    }

    @Transactional
    public void deleteGenreByName(Genre genre){
        genreRepository.delete(genre);
    }
}
