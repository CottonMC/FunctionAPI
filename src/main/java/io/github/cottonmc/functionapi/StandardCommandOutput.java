package io.github.cottonmc.functionapi;

import net.minecraft.server.command.CommandOutput;
import net.minecraft.text.Text;

import java.util.*;


public class StandardCommandOutput implements CommandOutput {

    public static CommandOutput INSTANCE = new StandardCommandOutput();

    @Override
    public void sendSystemMessage(Text text, UUID uUID){
        System.out.println(text.asString());
    }

    @Override
    public boolean shouldReceiveFeedback(){
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
