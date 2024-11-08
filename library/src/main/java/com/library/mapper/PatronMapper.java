package com.library.mapper;


import com.library.dto.PatronDTO;
import com.library.entity.Patron;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {BorrowingRecordMapper.class})
public interface PatronMapper {

    PatronMapper INSTANCE = Mappers.getMapper(PatronMapper.class);
    Patron toEntity(PatronDTO patronDTO);
    PatronDTO toDTO(Patron patron);


}
