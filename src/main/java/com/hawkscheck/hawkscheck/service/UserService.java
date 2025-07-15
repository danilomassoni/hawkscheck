package com.hawkscheck.hawkscheck.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.dto.UserRequestDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.model.PaperEnum;
import com.hawkscheck.hawkscheck.model.Team;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private TeamService teamService;



    public UserResponseDTO createUser(UserRequestDTO dto, Principal principal) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPaper(dto.getPaper());

        // Se o papel for STUDENT, associar à equipe do mentor autenticado
        if (dto.getPaper() == PaperEnum.STUDENT) {
            User mentor = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Mentor não encontrado"));

            Team mentorTeam = mentor.getTeam();
            if (mentorTeam == null) {
                throw new RuntimeException("Mentor não está vinculado a nenhuma equipe");
            }

            user.setTeam(mentorTeam); // vincula o aluno à equipe do mentor
        }

        User saved = userRepository.save(user);

        return new UserResponseDTO(
            saved.getId(),
            saved.getName(),
            saved.getEmail(),
            saved.getPaper()
        );
    }

    public List<UserResponseDTO> findAllUser() {
        return userRepository.findAll().stream()
            .map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPaper()
                ))
            .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> findById(Long id){
        return userRepository.findById(id)
            .map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPaper()
            ));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserResponseDTO> findAllStudents() {
    List<User> students = userRepository.findByPaper(PaperEnum.STUDENT);
        return students.stream()
            .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail()))
            .collect(Collectors.toList());
    }

    public UserResponseDTO createStudent(UserRequestDTO dto, Principal principal) {
    // Buscar o mentor logado pelo email (que vem do token)
    User mentor = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("Mentor não encontrado"));

    // Criar novo usuário com papel STUDENT
    User student = new User();
    student.setName(dto.getName());
    student.setEmail(dto.getEmail());
    student.setPassword(dto.getPassword()); // se tiver senha
    student.setPaper(PaperEnum.STUDENT);
    student.setMentor(mentor); // ⚠️ Vinculando mentor ao aluno

    userRepository.save(student);

    return new UserResponseDTO(student.getId(), student.getName(), student.getEmail());
    }


    public List<UserResponseDTO> findStudentsByMentor(String mentorEmail) {
    List<User> students = userRepository.findByPaperAndMentorEmail(PaperEnum.STUDENT, mentorEmail);
    return students.stream()
                   .map(UserResponseDTO::new)
                   .toList();
    }




}
