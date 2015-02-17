package io.github.twhscs.game.util;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private final Map<String, Sound> SOUND_MAP;
    public SoundManager() {
        SOUND_MAP = new HashMap<String, Sound>();
    }
    public void addSound(String name, SoundBuffer soundBuffer) {
        Sound newSound = new Sound();
        newSound.setBuffer(soundBuffer);
        SOUND_MAP.put(name, newSound);
    }
    public Sound getSound(String name) {
        return SOUND_MAP.get(name);
    }
    public void playSound(String name) {
        SOUND_MAP.get(name).play();
    }
    public void pauseSound(String name) {
        SOUND_MAP.get(name).pause();
    }
    public void stopSound(String name) {
        SOUND_MAP.get(name).stop();
    }
    public void setLoop(String name, boolean toggle) {
        SOUND_MAP.get(name).setLoop(toggle);
    }
}