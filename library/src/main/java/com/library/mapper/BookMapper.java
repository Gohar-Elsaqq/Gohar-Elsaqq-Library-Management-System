package com.library.mapper;


import com.library.dto.BookDTO;
import com.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BorrowingRecordMapper.class})
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    Book toEntity(BookDTO bookDTO);
    BookDTO toDTO(Book book);
}