package com.library.mapper;

import com.library.dto.BorrowingRecordDTO;
import com.library.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {
    BorrowingRecordMapper INSTANCE = Mappers.getMapper(BorrowingRecordMapper.class);

    BorrowingRecordDTO toDTO(BorrowingRecord borrowingRecord);

    BorrowingRecord toEntity(BorrowingRecordDTO borrowingRecordDTO);
}
