package com.openclassrooms.safetynetApi.service;

import com.openclassrooms.safetynetApi.model.MedicalRecord;
import com.openclassrooms.safetynetApi.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getMedicalRecord() throws Exception {
       return medicalRecordRepository.getMedicalRecords();
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) throws IOException {
       medicalRecordRepository.addMedicalRecord(medicalRecord);
    }

    public void editMedicalRecord(MedicalRecord medicalRecord) throws IOException {
        medicalRecordRepository.editMedicalRecord(medicalRecord);
    }

    public void deleteMedicalRecord(String firstName, String lastName) throws IOException {
        medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    }


}
