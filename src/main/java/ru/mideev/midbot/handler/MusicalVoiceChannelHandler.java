package ru.mideev.midbot.handler;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Timer;
import java.util.TimerTask;

public class MusicalVoiceChannelHandler extends ListenerAdapter {
    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;
    private TimerTask cancelConnectionTask;
    private VoiceChannel channel;

    public MusicalVoiceChannelHandler() {
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(playerManager);
        this.player = playerManager.createPlayer();
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        playerManager.loadItem("file.mp3", new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                player.playTrack(firstTrack);
            }

            @Override
            public void noMatches() {
                System.out.println("Аудиофайл не найден.");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("Не удалось считать аудиофайл.");
            }

        });
        player.addListener(new AudioEventAdapter() {
            @Override
            public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
                if (endReason.mayStartNext) {
                    player.stopTrack();
                    player.playTrack(track.makeClone());
                }
            }
        });
    }
    
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Timer timer = new Timer();

        Guild guild = event.getGuild();
        VoiceChannel voiceChannel = guild.getVoiceChannelById("1126241597923807384");
        AudioManager audioManager = guild.getAudioManager();

        if (event.getChannelJoined() != null && event.getChannelJoined().getId().equals("1126241597923807384")) {
            if (cancelConnectionTask != null) {
                cancelConnectionTask.cancel();
                cancelConnectionTask = null;
            }
            audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
            audioManager.openAudioConnection(voiceChannel);
        }

        if (event.getChannelLeft() != null && event.getChannelLeft().getId().equals("1126241597923807384")) {
            cancelConnectionTask = new TimerTask() {
                @Override
                public void run() {
                    audioManager.setSendingHandler(null);
                    audioManager.closeAudioConnection();
                    channel.modifyStatus("ЗАХОДИ, ЧТОБЫ ЗАИГРАЛА МУЗЫКА").submit();
                }
            };
            timer.schedule(cancelConnectionTask, 10000);
        }
    }
}