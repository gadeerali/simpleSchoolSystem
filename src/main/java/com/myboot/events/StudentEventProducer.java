package com.myboot.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentEventProducer {

   @Value("${spring.kafka.topic.name}")
   private String topicName;

   private final KafkaTemplate<String, StudentsEvents> kafkaTemplate;

   public StudentEventProducer(KafkaTemplate<String, StudentsEvents> kafkaTemplate) {
      this.kafkaTemplate = kafkaTemplate;
   }

   public void sendStudentEvent(StudentsEvents event) {
      System.out.println("Sending event to topic: " + topicName);
      kafkaTemplate.send(topicName, event)
              .whenComplete((result, ex) -> {
                 if (ex != null) {
                    System.err.println("Failed to send message: " + ex.getMessage());
                    ex.printStackTrace();
                 } else {
                    System.out.println("Message sent successfully to topic: " + result.getRecordMetadata().topic());
                 }
              });
   }
}