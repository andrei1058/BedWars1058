package dev.andrei1058.bedwars.common.api.messaging;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class AbstractMessagingPacket implements MessagingPacket, TargetedPacket {

    private String sender;
    private String target;

    public AbstractMessagingPacket(@Nullable String sender, @Nullable String target) {
        this.sender = sender;
        this.target = target;
    }
}
