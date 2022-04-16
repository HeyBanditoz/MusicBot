package com.jagrosh.jmusicbot.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.commands.DJCommand;
import com.jagrosh.jmusicbot.utils.FormatUtil;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class JumpCmd extends DJCommand
{
    public JumpCmd(Bot bot)
    {
        super(bot);
        this.name = "jump";
        this.help = "sets the position of the current playing song";
        this.arguments = "<seconds>";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        AudioTrack track = handler.getPlayer().getPlayingTrack();
        try
        {
            long newTime = Long.parseLong(event.getArgs()) * 1000L + track.getPosition();
            if (newTime > track.getDuration())
            {
                event.replyWarning(FormatUtil.filter("Trying to set track position to `" + FormatUtil.formatTime(newTime)
                        + "`, but the track is only `" + FormatUtil.formatTime(track.getDuration()) + "` long! Skipping."));
                return;
            }
            track.setPosition(newTime);
            event.replySuccess("Jumping ahead `" + FormatUtil.formatTime(Long.parseLong(event.getArgs()) * 1000L) + "` seconds to `" + FormatUtil.formatTime(track.getPosition()) + "`.");
        } catch (NumberFormatException ex) {
            event.replyError(ex.getMessage());
        }
    }
}
