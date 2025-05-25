package com.example.batch_service.service;

import com.example.batch_service.client.*;
import com.example.batch_service.dto.*;
import com.example.batch_service.model.Batch;
import com.example.batch_service.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {
    private final BatchRepository batchRepository;
    private final CourseServiceClient courseServiceClient;
    private final InstructorServiceClient instructorServiceClient;
    private final StudentServiceClient studentServiceClient;
    private final ScheduleServiceClient scheduleServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public BatchDTO createBatch(BatchDTO batchDTO, String adminId) {
        // Validate course
        CourseDTO course = courseServiceClient.getCourseById(batchDTO.getCourseId());
        if (course == null) {
            throw new RuntimeException("Course not found");
        }

        // Validate instructor
        InstructorDTO instructor = instructorServiceClient.getInstructorById(batchDTO.getInstructorId());
        if (instructor == null) {
            throw new RuntimeException("Instructor not found");
        }

        // Validate students
        if (batchDTO.getStudentIds() != null) {
            for (String studentId : batchDTO.getStudentIds()) {
                StudentDTO student = studentServiceClient.getStudentById(studentId);
                if (student == null) {
                    throw new RuntimeException("Student not found: " + studentId);
                }
            }
        }

        // Save batch
        Batch batch = new Batch();
        batch.setName(batchDTO.getName());
        batch.setCourseId(batchDTO.getCourseId());
        batch.setInstructorId(batchDTO.getInstructorId());
        batch.setStudentIds(batchDTO.getStudentIds());
        batch.setScheduleIds(batchDTO.getScheduleIds());
        batch = batchRepository.save(batch);

        // Convert to DTO
        batchDTO.setId(batch.getId());
        return batchDTO;
    }

    public BatchDTO getBatchById(Long batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        BatchDTO batchDTO = new BatchDTO();
        batchDTO.setId(batch.getId());
        batchDTO.setName(batch.getName());
        batchDTO.setCourseId(batch.getCourseId());
        batchDTO.setInstructorId(batch.getInstructorId());
        batchDTO.setStudentIds(batch.getStudentIds());
        batchDTO.setScheduleIds(batch.getScheduleIds());
        return batchDTO;
    }

    public void createSchedule(Long batchId, ScheduleDTO scheduleDTO) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        // Validate schedule
        if (!batchId.equals(scheduleDTO.getBatchId())) {
            throw new IllegalArgumentException("Batch ID mismatch");
        }

        // Create schedule via Schedule Service
        ScheduleDTO createdSchedule = scheduleServiceClient.createSchedule(scheduleDTO);

        // Update batch with schedule ID
        batch.getScheduleIds().add(createdSchedule.getId());
        batchRepository.save(batch);

        // Publish notification event
        NotificationDTO notification = new NotificationDTO();
        notification.setMessage("Class scheduled for batch: " + batch.getName());
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);
    }
}