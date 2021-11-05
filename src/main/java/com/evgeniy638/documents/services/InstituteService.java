package com.evgeniy638.documents.services;

import com.evgeniy638.documents.dto.InstituteDTO;
import com.evgeniy638.documents.modules.Institute;
import com.evgeniy638.documents.repository.InstituteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstituteService {
    private final InstituteRepository instituteRepository;

    public void createInstitute(InstituteDTO instituteDTO) {
        Institute institute = new Institute();

        institute.setTitle(instituteDTO.getTitle());

        instituteRepository.save(institute);
    }

    public List<Institute> getInstitutes() {
        return instituteRepository.findAll();
    }
}
