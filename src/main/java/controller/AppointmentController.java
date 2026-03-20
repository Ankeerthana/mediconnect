package com.mediconnect.controller;

import com.mediconnect.entity.*;
import com.mediconnect.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            @RequestBody Map<String, Object> req,
            Principal principal) {

        User patient = userRepository.findByEmail(principal.getName()).get();
        Doctor doctor = doctorRepository.findById(
                Long.valueOf(req.get("doctorId").toString())).get();

        long queuePos = appointmentRepository
                .countByDoctorAndStatus(doctor, Appointment.Status.PENDING) + 1;

        Appointment appt = new Appointment();
        appt.setPatient(patient);
        appt.setDoctor(doctor);
        appt.setReason(req.get("reason").toString());
        appt.setAppointmentTime(LocalDateTime.parse(req.get("time").toString()));
        appt.setStatus(Appointment.Status.PENDING);
        appt.setQueuePosition((int) queuePos);

        appointmentRepository.save(appt);
        return ResponseEntity.ok(Map.of(
                "message", "Appointment booked!",
                "queuePosition", queuePos));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Appointment>> myAppointments(Principal principal) {
        User patient = userRepository.findByEmail(principal.getName()).get();
        return ResponseEntity.ok(appointmentRepository.findByPatient(patient));
    }

    @GetMapping("/queue/{doctorId}")
    public ResponseEntity<?> getQueue(@PathVariable Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        List<Appointment> queue = appointmentRepository.findByDoctor(doctor)
                .stream()
                .filter(a -> a.getStatus() == Appointment.Status.PENDING)
                .toList();
        return ResponseEntity.ok(Map.of(
                "doctorName", doctor.getUser().getName(),
                "queueLength", queue.size(),
                "appointments", queue));
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long appointmentId,
            @RequestBody Map<String, String> req,
            Principal principal) {
        Appointment appt = appointmentRepository.findById(appointmentId).get();
        appt.setStatus(Appointment.Status.valueOf(req.get("status").toUpperCase()));
        appointmentRepository.save(appt);
        return ResponseEntity.ok(Map.of("message", "Status updated to " + req.get("status")));
    }

    @GetMapping("/doctor/my-queue")
    public ResponseEntity<?> getDoctorAppointments(Principal principal) {
        User doctorUser = userRepository.findByEmail(principal.getName()).get();
        Doctor doctor = doctorRepository.findAll()
                .stream()
                .filter(d -> d.getUser().getId().equals(doctorUser.getId()))
                .findFirst()
                .orElse(null);
        if (doctor == null) return ResponseEntity.ok(List.of());
        return ResponseEntity.ok(appointmentRepository.findByDoctor(doctor));
    }
}