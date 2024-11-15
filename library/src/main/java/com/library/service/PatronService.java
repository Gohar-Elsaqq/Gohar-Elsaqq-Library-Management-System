package com.library.service;

import com.library.dto.BookDTO;
import com.library.dto.PatronDTO;
import com.library.entity.Patron;
import com.library.exception.DaplicateRecoredException;
import com.library.exception.ResourceNotFoundException;
import com.library.mapper.PatronMapper;
import com.library.repository.BookRepository;
import com.library.repository.BorrowingRecordRepository;
import com.library.repository.PatronRepository;
import com.library.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private PatronMapper patronMapper;
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private BookRepository bookRepository;

    public ResponseEntity<?> getAllPatron() throws Exception {
        List<PatronDTO> patronDTOS = patronRepository.findAll()
                .stream()
                .map(patron -> {
                    PatronDTO patronDTO = patronMapper.toDTO(patron);

                    if (patronDTO.getContactInformation() != null) {
                        if (patronDTO.getContactInformation().getAddress() == null) {
                            patronDTO.getContactInformation().setAddress("");
                        }
                        if (patronDTO.getContactInformation().getPhone() == null) {
                            patronDTO.getContactInformation().setPhone("");
                        }
                        if (patronDTO.getContactInformation().getEmail() == null) {
                            patronDTO.getContactInformation().setEmail("");
                        }
                        if (patronDTO.getContactInformation().getBin() == null) {
                            patronDTO.getContactInformation().setBin("");
                        }
                    }
                    return patronDTO;
                })
                    .collect(Collectors.toList());
        if (patronDTOS.isEmpty()) {
            throw new ResourceNotFoundException("Books not found.");
        }
        SuccessResponse<List<PatronDTO>> successResponse = new SuccessResponse<>(
                "patron return data.",
                patronDTOS
        );
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }


    public PatronDTO getById(Long id) throws ResourceNotFoundException {
        return patronRepository.findById(id)
                .map(patronMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Patron with ID " + id + " not found."));
    }


    public PatronDTO save(PatronDTO patronDTO) throws Exception {

        if (patronDTO.getId() != null) {
            var patronId = patronRepository.findById(patronDTO.getId());
            if (patronId.isPresent()) {
                var patron = patronMapper.toEntity(patronDTO);
                var updated = patronRepository.save(patron);
                return patronMapper.toDTO(updated);
            } else {
                throw new ResourceNotFoundException("Book with ID " + patronDTO.getId() + " not found.");
            }
        } else {
            if(patronRepository.findByEmail(patronDTO.getContactInformation().getEmail()).isPresent()){
                throw new DaplicateRecoredException("Email already exists");
            }
            var patron = patronMapper.toEntity(patronDTO);
            if (patron.getName() == null || patron.getName().trim().isEmpty() || patron.getName().length() <= 7) {
                throw new Exception("Name is missing or too short (should be more than 7 characters) in Patron data.");
            }

            if (patron.getContactInformation() == null) {
                throw new Exception("Contact Information is missing.");
            } else {
                if (patron.getContactInformation().getAddress() == null || patron.getContactInformation().getAddress().trim().isEmpty()) {
                    throw new Exception("Address is missing in Contact Information.");
                }

                if (patron.getContactInformation().getEmail() == null || patron.getContactInformation().getEmail().trim().isEmpty()) {
                    throw new Exception("Email is missing in Contact Information.");
                }
                if (patron.getContactInformation().getPhone() == null || patron.getContactInformation().getPhone().trim().isEmpty()) {
                    throw new Exception("Phone is missing in Contact Information.");
                }
            }
                var newPatron = patronRepository.save(patron);
                return patronMapper.toDTO(newPatron);
            }
        }
    public ResponseEntity<?> deleteById(Long id) throws Exception {
            if (patronRepository.existsById(id)) {
                borrowingRecordRepository.deleteByPatronId(id);
                patronRepository.deleteById(id);
//                List<String> details = new ArrayList<>();
//                details.add("Patron with ID " + id + " has been deleted.");
//                details.add("No associated borrowing records left for this patron.");
                SuccessResponse<?> successResponse = new SuccessResponse<>(
                        "deleted successfully.","Book with ID " + id + " deleted successfully.");
                return new ResponseEntity<>(successResponse, HttpStatus.OK);
            } else {
                throw new ResourceNotFoundException("Book with ID " + id + " not found.");
            }
    }
    // GEY PATRON BY PHONE
    public PatronDTO getByPhone(String phone) throws ResourceNotFoundException {
        return patronRepository.findByPhone(phone)
                .map(patronMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Patron with phone " + phone + " not found."));
    }
}
