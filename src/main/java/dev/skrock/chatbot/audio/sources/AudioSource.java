package dev.skrock.chatbot.audio.sources;

public interface AudioSource {

    AudioSearchResult search(AudioDescriptor descriptor);
}
