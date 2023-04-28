package com.kodit.application.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User requestedTo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User requestedBy;
    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;
}
