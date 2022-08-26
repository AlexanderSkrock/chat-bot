package dev.skrock.chatbot.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class Sound {

    @Id
    private String name;

    private String mimeType;

    @Lob
    private byte[] data;
}

