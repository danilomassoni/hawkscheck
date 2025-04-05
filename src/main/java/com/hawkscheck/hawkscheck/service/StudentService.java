package com.hawkscheck.hawkscheck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.model.Student;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.repository.StudentRepository;
import com.hawkscheck.hawkscheck.repository.TeamRepository;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id){
        return studentRepository.findById(id);
    }

    public List<Student> getStudentsByTeamId(Long teamId){
        Optional<Team> team = teamRepository.findById(teamId);
        return team.map(studentRepository::findByTeam).orElse(List.of());
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    


}
