package com.chummy_backend.serverside.Model.general;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Classes {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String className;

    @OneToMany(mappedBy = "classes")
    private List<Class_Partipation> participations;
}
