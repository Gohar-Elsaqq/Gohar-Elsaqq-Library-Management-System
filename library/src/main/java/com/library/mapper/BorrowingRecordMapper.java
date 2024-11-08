package com.library.mapper;

import com.library.dto.BorrowingRecordDTO;
import com.library.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {
    BorrowingRecordMapper INSTANCE = Mappers.getMapper(BorrowingRecordMapper.class);
    BorrowingRecord toEntity(BorrowingRecordDTO borrowingRecordDTO);
    BorrowingRecordDTO toDTO(BorrowingRecord borrowingRecord);


}
