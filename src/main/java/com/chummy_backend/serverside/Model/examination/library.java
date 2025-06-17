package com.chummy_backend.serverside.Model.examination;

import java.util.List;

import com.chummy_backend.serverside.Model.general.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class library {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String lib_name;
    
    @ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private Users ownerID;
    
    @OneToMany(mappedBy = "library")
    private List<library_vocabulary> library_vocabularies;
}
