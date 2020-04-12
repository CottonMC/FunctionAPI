package io.github.cottonmc.functionapi;

import com.google.gson.JsonObject;
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType;
import net.minecraft.command.arguments.serialize.ArgumentSerializer;
import net.minecraft.util.PacketByteBuf;

public class FunctionapIdentifierArgumentSerializer implements ArgumentSerializer<FunctionAPIIdentifierArgumentType> {
    @Override
    public void toPacket(FunctionAPIIdentifierArgumentType argumentType, PacketByteBuf packetByteBuf) {

    }

    @Override
    public FunctionAPIIdentifierArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        return new FunctionAPIIdentifierArgumentType();
    }

    @Override
    public void toJson(FunctionAPIIdentifierArgumentType argumentType, JsonObject jsonObject) {

    }
}
