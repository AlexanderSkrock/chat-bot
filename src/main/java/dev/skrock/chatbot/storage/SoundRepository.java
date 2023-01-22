package dev.skrock.chatbot.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoundRepository extends JpaRepository<SoundEntity, String> {

    public Optional<SoundEntity> findByNameIgnoreCase(String name);
}
