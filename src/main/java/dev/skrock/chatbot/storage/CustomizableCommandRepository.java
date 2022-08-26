package dev.skrock.chatbot.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizableCommandRepository extends JpaRepository<CustomizableCommand, String> {
}
