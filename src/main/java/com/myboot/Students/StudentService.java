package com.myboot.Students;

import com.myboot.Courses.Courses;
import com.myboot.Courses.CoursesRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.annotations.Recurring;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService {
    private StudentRepository studentRepository;
    private CoursesRepo coursesRepo;
    private final StringRedisTemplate redis;
    private final JobScheduler jobScheduler;
    private final EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    public StudentService(StudentRepository studentRepository, CoursesRepo coursesRepo,
                          JobScheduler jobScheduler, StringRedisTemplate redis, EntityManager entityManager) {
        this.studentRepository = studentRepository;
        this.coursesRepo = coursesRepo;
        this.jobScheduler = jobScheduler;
        this.redis = redis;
        this.entityManager = entityManager;
    }

    @Recurring (id = "daily-student-count", cron = "*/20 * * * * *")
    @Job (name = "daily student count")
    public void scheduledStudents(){
        long conut = studentRepository.count();
        redis.opsForValue().set("KeyCount", String.valueOf(conut));
        String cachedCount = redis.opsForValue().get("KeyCount");
        log.info("Students Count (from Redis): {}", cachedCount);
    }
   /* @PostConstruct
    public void JobStarter() {
        log.info("Server timezone: {}", java.time.ZoneId.systemDefault());
        schedualStudentCount();
    } */

     /* public void schedualStudentCount() {
        jobScheduler.scheduleRecurrently(
                "daily-student-count",
                "0 * * * * *",
                this::scheduledStudents
        );
    } */
    public void softDeleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }


    public List<Student> findAllStudents(boolean softDelete) {
       Session session = entityManager.unwrap(Session.class);
       Filter filter = session.enableFilter("deletedStudentFilter");
       filter.setParameter("isDeleted", softDelete);
       List<Student> students = studentRepository.findAll();
       session.disableFilter("deletedStudentFilter");
       return students;
    }


    public void saveStudent(Student students) {
      studentRepository.save(students);
    }
    public List<Student> findStudentsOlderthan(int age) {
        Specification<Student> spec = SpecsFilters.ageGreaterThan(age);
        return studentRepository.findAll(spec);


    }
    @Transactional
    public Student findStudentById(Integer id) {

        return studentRepository.findByIdWithCourses(id).orElse(null);
    }

    @Transactional
    public Student assingCourseStudent(Integer stdId, Integer crsId) {
        Student student = studentRepository.findById(stdId).orElse(null);
        if (student == null) {
            return null;
        }
        Courses courses = coursesRepo.findById(crsId).orElse(null);
        if (courses == null) {
            return null;
        }
        Set<Courses> coursesSet = student.getAssignedCourses();
        if (coursesSet == null) {
            coursesSet = new HashSet<>();
        }
        coursesSet.add(courses);
        courses.getStudents().add(student);
        student.setAssignedCourses(coursesSet);
        return studentRepository.save(student);
    }

    public void deleteStudent() {
        studentRepository.deleteAll();
    }
}

