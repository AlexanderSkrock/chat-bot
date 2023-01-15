package dev.skrock.chatbot.storage;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class SoundEntity {

    @Id
    private String name;

    private String mimeType;

    @Lob
    private byte[] data;
}

