package com.example.Film_project_new.controller;

import com.example.Film_project_new.model.Film;
import com.example.Film_project_new.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/films")
    public String getAllFilms(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        return "ListFilm";
    }

    @GetMapping("/films/add")
    public String addFilmForm(Model model) {
        model.addAttribute("film", new Film());
        return "add-film";
    }

    @PostMapping("/films/add")
    public String addFilm(Film film) {
        filmService.saveFilm(film);
        return "redirect:/films";
    }
}