package com.example.Film_project_new.controller;

import com.example.Film_project_new.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/films/{filename}")
    public String watchMovie(@PathVariable String filename,
                             @RequestParam(required = false) Long start,
                             @RequestParam(required = false) Long end, Model model) {
        model.addAttribute("movie", filename);
        model.addAttribute("start", start != null ? start : 0);
        model.addAttribute("end", end != null ? end : 0);
        return "movie";
    }

    @GetMapping("/api/movies/{filename}")
    public ResponseEntity<byte[]> streamMovie(@PathVariable String filename, HttpServletRequest request) throws IOException {
        return movieService.streamMovie(filename, request);
    }


}