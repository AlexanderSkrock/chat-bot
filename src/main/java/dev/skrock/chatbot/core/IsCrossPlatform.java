package dev.skrock.chatbot.core;

import java.util.Set;

public interface IsCrossPlatform extends HasPlatform {

    @Override
    default Platform getPlatform() {
        return Platform.CROSS_PLATFORM;
    }

    Set<Platform> getSupportedPlatforms();
}
