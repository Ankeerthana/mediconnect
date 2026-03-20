package com.mediconnect.controller;

import com.mediconnect.entity.Doctor;
import com.mediconnect.entity.User;
import com.mediconnect.repository.DoctorRepository;
import com.mediconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    @PostMapping("/register-profile")
    public ResponseEntity<?> registerDoctorProfile(
            @RequestBody Map<String, Object> req,
            Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialization(req.get("specialization").toString());
        doctor.setQualification(req.get("qualification").toString());
        doctor.setConsultationFee(Integer.valueOf(req.get("consultationFee").toString()));
        doctorRepository.save(doctor);
        return ResponseEntity.ok(Map.of("message", "Doctor profile created!"));
    }
}