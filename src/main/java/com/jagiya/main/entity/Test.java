package com.jagiya.main.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "test")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Test VO")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Test ID")
    private Long id;

    @Schema(description = "Name ID")
    private String name;

    @Builder
    public Test(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
