package com.chummy_backend.serverside.Model.examination;

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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vocabulary {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String word;
    private String meaning;
    @OneToMany(mappedBy="vocabulary")
    private List<question> questions; 
    @OneToMany(mappedBy="vocabulary")
    private List<library_vocabulary> library;
}
