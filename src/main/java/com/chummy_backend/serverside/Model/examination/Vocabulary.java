package com.chummy_backend.serverside.Model.examination;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain= true)
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"word","meaning"})})
public class Vocabulary {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false)
    private String word;
    @Column(nullable=false)
    private String meaning;
    @OneToMany(mappedBy="vocabulary")
    private List<question> questions; 
    @OneToMany(mappedBy="vocabulary")
    private List<library_vocabulary> library;
}
