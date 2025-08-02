package com.example.painting_app.dto;

import lombok.Data;
import java.util.List;

@Data
public class PaintingDto {
    private String title;
    private List<ShapeDto> shapes;
}