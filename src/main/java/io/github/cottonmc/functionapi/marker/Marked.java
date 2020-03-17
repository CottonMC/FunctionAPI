package io.github.cottonmc.functionapi.marker;

import net.minecraft.util.Identifier;

import java.util.List;

public interface Marked {
    boolean hasMarker(Identifier identifier);
    List<Identifier> getMarkers();
}
