package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.MessageRequestDTO;
import com.hawkscheck.hawkscheck.dto.MessageResponseDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.service.MessageService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    

    // Enviar nova mensagem
    @PostMapping
    public ResponseEntity<MessageResponseDTO> sendMessage(
            @Valid @RequestBody MessageRequestDTO dto,
            Principal principal) {
        MessageResponseDTO response = messageService.sendMessage(dto, principal);
        return ResponseEntity.ok(response);
    }

    // Caixa de entrada do usuário autenticado
    @GetMapping("/inbox")
    public ResponseEntity<List<MessageResponseDTO>> getInbox(Principal principal) {
        List<MessageResponseDTO> messages = messageService.getInbox(principal);
        return ResponseEntity.ok(messages);
    }

    // Mensagens enviadas pelo usuário autenticado
    @GetMapping("/sent")
    public ResponseEntity<List<MessageResponseDTO>> getSentMessages(Principal principal) {
        List<MessageResponseDTO> messages = messageService.getSentMessages(principal);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<UserResponseDTO>> getContacts(Principal principal) {
        List<UserResponseDTO> contacts = messageService.getContacts(principal);
        return ResponseEntity.ok(contacts);
    }
    
}
