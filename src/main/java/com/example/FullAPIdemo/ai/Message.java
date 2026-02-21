package com.example.FullAPIdemo.ai;

import com.ethlo.time.DateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;

@Entity
public class Message {
@Id
    private int id;
@Column
private String message;
@Column
private DateTime timestamp;
}
