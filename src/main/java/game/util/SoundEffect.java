package game.util;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * SoundEffect handles the game's SFX.
 * Classes that want to use SFX will call the static variables in this enum and
 * play them via the play() method.
 */
public enum SoundEffect {
	BACKMUSIC("src/main/resources/music/On_start_page_background_music.wav"),
	GAMEMUSIC("src/main/resources/music/In_game_bk.wav"),
	LOOSEMUSIC("src/main/resources/music/loose_game.wav"),
	WINMUSIC("src/main/resources/music/win_music.wav"),

	HEAL_SEED("src/main/resources/music/Heal_seed.wav"),
	MEET_TRAP("src/main/resources/music/meet_trap.wav"),
	MEETOM_SMILE("src/main/resources/music/meettom_smile.wav");



	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}
	
	public static Volume volume = Volume.LOW;
	
	private Clip clip;
	
	SoundEffect(String soundFileName) {
		// sets the sound effect
		try {
			 File file = new File(soundFileName);
			URL url = file.toURL();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		// plays the sound effect
		if (volume != Volume.MUTE) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void setToLoop() {
		// sets whether or not the sound effect loops
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
	}
	
	public void setToLoud() {
		// sets volume to be loud
		volume = Volume.HIGH;
	}
}
