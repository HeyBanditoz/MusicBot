package com.jagrosh.jmusicbot.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.audio.AudioHandler;
import com.jagrosh.jmusicbot.commands.DJCommand;
import com.jagrosh.jmusicbot.utils.FormatUtil;
import com.jagrosh.jmusicbot.utils.TimeUtil;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class PosCmd extends DJCommand
{
    public PosCmd(Bot bot)
    {
        super(bot);
        this.name = "pos";
        this.help = "sets the position of the current playing song";
        this.arguments = "<position>";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        AudioTrack track = handler.getPlayer().getPlayingTrack();
        try
        {
            long dur = Long.parseLong(event.getArgs()) * 1000L;
            if (dur> track.getDuration())
            {
                event.replyWarning(FormatUtil.filter(" Trying to set track position to `" + TimeUtil.formatTime(dur)
                        + "`, but the track is only `" + TimeUtil.formatTime(track.getDuration()) + "` long! Skipping."));
                return;
            }
            track.setPosition(dur);
            event.replySuccess("Setting position to `" + TimeUtil.formatTime(dur) + "`.");
        } catch (NumberFormatException ex) {
            event.replyError(ex.getMessage());
        }
    }
}
