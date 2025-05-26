package com.example.instructor_service.service;

import com.example.instructor_service.client.BatchServiceClient;
import com.example.instructor_service.client.CourseServiceClient;
import com.example.instructor_service.client.NotificationServiceClient;
import com.example.instructor_service.client.ScheduleServiceClient;
import com.example.instructor_service.dto.*;
import com.example.instructor_service.model.Instructor;
import com.example.instructor_service.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseServiceClient courseServiceClient;
    private final BatchServiceClient batchServiceClient;
    private final ScheduleServiceClient scheduleServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public InstructorDTO createInstructor(InstructorDTO instructorDTO, String adminId) {
        // Validate instructor ID (userId)
        if (instructorDTO.getId() == null) {
            throw new RuntimeException("Instructor ID is required");
        }

        // Save instructor
        Instructor instructor = new Instructor();
        instructor.setId(instructorDTO.getId());
        instructor.setName(instructorDTO.getName());
        instructor.setEmail(instructorDTO.getEmail());
        instructor.setCourseIds(instructorDTO.getCourseIds());
        instructor.setBatchIds(instructorDTO.getBatchIds());
        instructor = instructorRepository.save(instructor);

        // Publish notification
        NotificationDTO notification = new NotificationDTO();
        notification.setUserId(instructor.getId().toString());
        notification.setMessage("Instructor profile created: " + instructor.getName());
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);

        return instructorDTO;
    }

    public InstructorDTO getInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setName(instructor.getName());
        instructorDTO.setEmail(instructor.getEmail());
        instructorDTO.setCourseIds(instructor.getCourseIds());
        instructorDTO.setBatchIds(instructor.getBatchIds());
        return instructorDTO;
    }

    public void assignCourse(Long instructorId, Long courseId, String adminId) {
        // Validate instructor
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        // Validate course
        CourseDTO course = courseServiceClient.getCourseById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }

        // Assign course
        instructor.getCourseIds().add(courseId);
        instructorRepository.save(instructor);

        // Publish notification
        NotificationDTO notification = new NotificationDTO();
        notification.setUserId(instructorId.toString());
        notification.setMessage("Assigned to course: " + course.getTitle());
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);
    }

    public void assignBatch(Long instructorId, Long batchId, String adminId) {
        // Validate instructor
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        // Validate batch
        BatchDTO batch = batchServiceClient.getBatchById(batchId);
        if (batch == null) {
            throw new RuntimeException("Batch not found");
        }

        // Assign batch
        instructor.getBatchIds().add(batchId);
        instructorRepository.save(instructor);

        // Publish notification
        NotificationDTO notification = new NotificationDTO();
        notification.setUserId(instructorId.toString());
        notification.setMessage("Assigned to batch: " + batch.getName());
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);
    }

    public ScheduleDTO getInstructorSchedule(Long instructorId) {
        // Validate instructor
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        // Get schedule for one of the instructor's batches (example)
        if (instructor.getBatchIds() == null || instructor.getBatchIds().isEmpty()) {
            throw new RuntimeException("No batches assigned to instructor");
        }
        Long batchId = instructor.getBatchIds().get(0); // Example: first batch
        ScheduleDTO schedule = scheduleServiceClient.getScheduleByBatchId(batchId);
        if (schedule == null) {
            throw new RuntimeException("No schedule found for batch");
        }

        return schedule;
    }
}