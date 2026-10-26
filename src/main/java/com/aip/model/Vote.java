package com.aip.model;

import jakarta.persistence.*;
import java.util.Date;

/** One row per vote. The UNIQUE constraint on voter guarantees one vote per user even under race conditions. */
@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(columnNames = "voter"))
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String voter;

    @Column(nullable = false)
    private Long candidateId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date castAt = new Date();

    public Vote() {}
    public Vote(String voter, Long candidateId) { this.voter = voter; this.candidateId = candidateId; }

    public Long getId() { return id; }
    public String getVoter() { return voter; }
    public void setVoter(String voter) { this.voter = voter; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
    public Date getCastAt() { return castAt; }
    public void setCastAt(Date castAt) { this.castAt = castAt; }
}
