    package com.example.student_service.service;

    import com.example.student_service.client.BatchServiceClient;
    import com.example.student_service.client.CourseServiceClient;
    import com.example.student_service.client.NotificationServiceClient;
    import com.example.student_service.config.RabbitMQConfig;
    import com.example.student_service.dto.BatchDTO;
    import com.example.student_service.dto.CourseDTO;
    import com.example.student_service.dto.NotificationDTO;
    import com.example.student_service.dto.QuizSubmissionDTO;
    import com.example.student_service.model.Student;
    import com.example.student_service.repository.StudentRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.amqp.rabbit.core.RabbitTemplate;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class StudentService {
        private final RabbitTemplate rabbitTemplate;
        private final StudentRepository studentRepository;
        private final BatchServiceClient batchServiceClient;
        private final CourseServiceClient courseServiceClient;
        private final NotificationServiceClient notificationServiceClient;

        public Student getStudentProfile(String userId) {
            return studentRepository.findByUserId(userId);
        }

        public void enrollInBatch(String userId, Long batchId) {
            Student student = studentRepository.findByUserId(userId);
            if (student == null) {
                throw new RuntimeException("Student not found");
            }

            // Validate batch exists via Batch Service
            BatchDTO batch = batchServiceClient.getBatchById(batchId);
            if (batch == null) {
                throw new RuntimeException("Batch not found");
            }

            // Add batch to student's enrolled batches
            student.getEnrolledBatchIds().add(batchId);
            studentRepository.save(student);

            // Publish event to RabbitMQ
            NotificationDTO notification = new NotificationDTO();
            notification.setUserId(userId);
            notification.setMessage("Successfully enrolled in batch: " + batch.getName());
            notification.setType("EMAIL");
            rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_QUEUE, notification);
        }

        public CourseDTO getEnrolledCourse(String userId, Long courseId) {
            Student student = studentRepository.findByUserId(userId);
            if (student == null || !student.getEnrolledCourseIds().contains(courseId)) {
                throw new RuntimeException("Student not enrolled in course");
            }
            return courseServiceClient.getCourseById(courseId);
        }

        public void submitQuiz(String userId, Long quizId, QuizSubmissionDTO submission) {
            Student student = studentRepository.findByUserId(userId);
            if (student == null) {
                throw new RuntimeException("Student not found");
            }

            if (!quizId.equals(submission.getQuizId())) {
                throw new IllegalArgumentException("Quiz ID mismatch");
            }

            // Publish quiz submission event to RabbitMQ
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUIZ_SUBMISSION_QUEUE, submission);
        }
    }