package com.myboot.events;

import com.myboot.Students.StudentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class kafkaConsumer {

    private final StudentService studentService;


    public kafkaConsumer(StudentService studentService) {
        this.studentService = studentService;
    }

@KafkaListener(topics = "student-events", groupId = "consumer_group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(StudentsEvents event){
        System.out.println("Received student event: " + event.studentName());
       studentService.assingCourseStudent(event.studentId(), event.courseId());

       System.out.println("done assigning mandatory " +
               "class" + event.studentName() + "to student" + event.studentName()) ;



}

}
