package hu.frontrider.functionapi;

import net.minecraft.server.command.CommandOutput;
import net.minecraft.text.Text;


public class EventCommandOutput implements CommandOutput {

    public static CommandOutput INSTANCE = new EventCommandOutput();

    @Override
    public void sendMessage(Text text) {
        System.out.println(text.asString());
    }

    @Override
    public boolean sendCommandFeedback() {
        return false;
    }

    @Override
    public boolean shouldTrackOutput() {
        return false;
    }

    @Override
    public boolean shouldBroadcastConsoleToOps() {
        return false;
    }
}
