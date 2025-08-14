package com.hawkscheck.hawkscheck.service;


import com.hawkscheck.hawkscheck.dto.MessageRequestDTO;
import com.hawkscheck.hawkscheck.dto.MessageResponseDTO;
import com.hawkscheck.hawkscheck.dto.UserResponseDTO;
import com.hawkscheck.hawkscheck.model.Message;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.MessageRepository;
import com.hawkscheck.hawkscheck.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final UserRepository userRepository; 

    
    public MessageResponseDTO sendMessage(MessageRequestDTO dto, Principal principal) {
        User sender = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário remetente não encontrado"));

        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário destinatário não encontrado"));

        Message message = new Message();
        message.setContent(dto.getContent());
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setTimestamp(java.time.LocalDateTime.now());
        message.setCreatedAt(java.time.LocalDateTime.now());

        Message saved = messageRepository.save(message);

        return toResponseDTO(saved);
    }

    public List<MessageResponseDTO> getSentMessages(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        List<Message> messages = messageRepository.findBySenderOrderByTimestampDesc(user);

        return messages.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private MessageResponseDTO toResponseDTO(Message message) {
        MessageResponseDTO dto = new MessageResponseDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSenderId(message.getSender().getId());
        dto.setSenderName(message.getSender().getName());
        dto.setRecipientId(message.getRecipient().getId());
        dto.setRecipientName(message.getRecipient().getName());
        dto.setTimestamp(message.getTimestamp());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }

    public List<MessageResponseDTO> getInbox(Principal principal) {
        // Busca o usuário logado
        User currentUser = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca mensagens onde ele é o destinatário
        List<Message> messages = messageRepository.findByRecipient(currentUser);

        // Converte para DTO
        return messages.stream()
                .map(msg -> new MessageResponseDTO(
                        msg.getId(),
                        msg.getContent(),
                        msg.getSender().getId(),
                        msg.getSender().getName(),
                        msg.getRecipient().getId(),
                        msg.getRecipient().getName(),
                        msg.getTimestamp(),
                        msg.getCreatedAt()
                ))
                .toList();
    }

    public List<UserResponseDTO> getContacts(Principal principal) {
        // Busca o usuário logado pelo email do principal
        User currentUser = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca quem enviou mensagem para o usuário atual
        List<User> senders = messageRepository.findSendersToUser(currentUser);

        // Busca quem recebeu mensagem do usuário atual
        List<User> recipients = messageRepository.findRecipientsFromUser(currentUser);

        // Une as duas listas, removendo duplicados com Set
        Set<User> contactsSet = new HashSet<>();
        contactsSet.addAll(senders);
        contactsSet.addAll(recipients);

        // Converte para DTO para enviar na API
        return contactsSet.stream()
            .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPaper()))
            .collect(Collectors.toList());
    }

    




    
}
