package com.example.course_service.service;

import com.example.course_service.client.ContentServiceClient;
import com.example.course_service.client.NotificationServiceClient;
import com.example.course_service.dto.ContentDTO;
import com.example.course_service.dto.CourseDTO;
import com.example.course_service.dto.NotificationDTO;
import com.example.course_service.model.Course;
import com.example.course_service.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ContentServiceClient contentServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public CourseDTO createCourse(CourseDTO courseDTO, String adminId) {
        // Validate content IDs
        if (courseDTO.getContentIds() != null) {
            for (Long contentId : courseDTO.getContentIds()) {
                ContentDTO content = contentServiceClient.getContentById(contentId);
                if (content == null) {
                    throw new RuntimeException("Content not found: " + contentId);
                }
            }
        }

        // Save course
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setContentIds(courseDTO.getContentIds());
        course = courseRepository.save(course);

        // Convert to DTO
        courseDTO.setId(course.getId());

        // Publish notification
        NotificationDTO notification = new NotificationDTO();
        notification.setMessage("New course created: " + course.getTitle());
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);

        return courseDTO;
    }

    public CourseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setContentIds(course.getContentIds());
        return courseDTO;
    }
}
