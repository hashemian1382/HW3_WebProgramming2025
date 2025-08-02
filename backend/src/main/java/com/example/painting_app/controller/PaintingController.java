package com.example.painting_app.controller;

import com.example.painting_app.dto.PaintingDto;
import com.example.painting_app.service.PaintingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/painting")
@RequiredArgsConstructor
public class PaintingController {

    private final PaintingService paintingService;

    @GetMapping("/{username}")
    public ResponseEntity<PaintingDto> getPainting(@PathVariable String username) {
        try {
            return ResponseEntity.ok(paintingService.getPaintingForUser(username));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> savePainting(@PathVariable String username, @RequestBody PaintingDto paintingDto) {
        try {
            paintingService.savePaintingForUser(username, paintingDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}