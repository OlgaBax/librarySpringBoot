package ru.bainc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bainc.dto.GenreDto;
import ru.bainc.model.Genre;
import ru.bainc.repositories.GenreRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional
    public Optional<Genre> getById(Long id) {
        return genreRepository.findById(id);
    }

    @Transactional
    public Genre getByGenreTitle(String genreTitle) {
        Genre genre = genreRepository.findByGenreTitle(genreTitle);
        log.info("Genre with title {} found", genreTitle);
        return genre;
    }

    @Transactional
    public Genre addGenre(Genre genre) {
        Genre genre1 = genreRepository.save(genre);
        return genre1;
    }

    @Transactional
    public void deleteGenreById(Long id) throws Exception {
        try {
            genreRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Что-то пошло не так");
        }
    }

    @Transactional
    public void deleteGenreByTitle(Genre genre) {
        genreRepository.delete(genre);
    }

    @Transactional
    public ResponseEntity<List<GenreDto>> getAllGenresToFront() {
        return new ResponseEntity<>(getAll()
                .stream().map(genre -> new GenreDto(genre)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<GenreDto> getByIdToFront(Long id) {
        Genre genre = getById(id).orElse(null);
        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new GenreDto(genre), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<GenreDto> getByTitleToFront(GenreDto genreDto) {
        Genre genre = getByGenreTitle(genreDto.getTitle());
        if (genre != null) {
            return new ResponseEntity<>(new GenreDto(getByGenreTitle(genreDto.getTitle())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    public ResponseEntity<GenreDto> addGenreFromFront(GenreDto genreDto) {
        Genre genre = getByGenreTitle(genreDto.getTitle());
        if (genre != null) {
            log.warn("Жанр с таким именем уже существует");
            return new ResponseEntity<>(new GenreDto(genre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenreDto(addGenre(new Genre(genreDto.getTitle()))), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteByIdFromFront(Long id) {
        try {
            deleteGenreById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteByTitleToFront(GenreDto genreDto) {
        Genre genre = getByGenreTitle(genreDto.getTitle());
        if (genre != null) {
            deleteGenreByTitle(genre);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
