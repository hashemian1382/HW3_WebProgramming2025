package com.example.painting_app.service;

import com.example.painting_app.dto.PaintingDto;
import com.example.painting_app.dto.ShapeDto;
import com.example.painting_app.model.Painting;
import com.example.painting_app.model.User;
import com.example.painting_app.repository.PaintingRepository;
import com.example.painting_app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaintingService {

    private final UserRepository userRepository;
    private final PaintingRepository paintingRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public PaintingDto getPaintingForUser(String username) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        return paintingRepository.findByUser(user)
                .map(this::convertEntityToDto)
                .orElseGet(() -> {
                    PaintingDto emptyDto = new PaintingDto();
                    emptyDto.setTitle("New Painting");
                    emptyDto.setShapes(Collections.emptyList());
                    return emptyDto;
                });
    }

    @Transactional
    public void savePaintingForUser(String username, PaintingDto paintingDto) throws JsonProcessingException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Painting painting = paintingRepository.findByUser(user)
                .orElse(new Painting());

        painting.setUser(user);
        painting.setTitle(paintingDto.getTitle());
        String shapesAsJson = objectMapper.writeValueAsString(paintingDto.getShapes());
        painting.setShapes(shapesAsJson);

        paintingRepository.save(painting);
    }

    private PaintingDto convertEntityToDto(Painting painting) {
        PaintingDto dto = new PaintingDto();
        dto.setTitle(painting.getTitle());
        try {
            dto.setShapes(objectMapper.readValue(painting.getShapes(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ShapeDto.class)));
        } catch (IOException e) {
            dto.setShapes(Collections.emptyList());
        }
        return dto;
    }
}