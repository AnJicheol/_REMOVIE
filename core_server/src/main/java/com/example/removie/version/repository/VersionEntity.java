package com.example.removie.version.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "release_version")
public class VersionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "version")
    @NotNull
    private Integer version;

    @Column(nullable = false, name = "version_date")
    @NotNull
    private LocalDate versionDate;

    public VersionEntity(@NonNull Integer serialNum, LocalDate versionDate) {
        this.version = serialNum;
        this.versionDate = versionDate;
    }

}
