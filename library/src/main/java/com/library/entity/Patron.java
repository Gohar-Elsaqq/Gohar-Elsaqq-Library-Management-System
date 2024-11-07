package com.library.entity;

import com.library.converter.ContactInfoConverter;
import com.library.dto.ContactInfoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "patron")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Convert(converter = ContactInfoConverter.class)
    private ContactInfoDTO contactInformation;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BorrowingRecord> borrowingRecords;
}