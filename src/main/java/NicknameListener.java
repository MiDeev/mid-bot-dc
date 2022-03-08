import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class NicknameListener extends ListenerAdapter {
    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        String name = event.getNewNickname();
        assert name != null;
        if (name.contains("!")) {
            event.getMember().modifyNickname(name.replace("!", "")).queue();
        }
    }
}

