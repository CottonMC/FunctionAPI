package io.github.cottonmc.functionapi.data;

import com.google.gson.*;
import io.github.cottonmc.functionapi.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.resource.*;
import net.minecraft.util.*;
import net.minecraft.util.profiler.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class BlockPropertyPackLoader implements SimpleResourceReloadListener<Map<Identifier, List<RawBlockMaterialData>>> {
    private static final String DATA_TYPE = "block_data_override/";
    private static final String EXTENSION = ".json";

    @Override
    public CompletableFuture<Map<Identifier,  List<RawBlockMaterialData>>> load(ResourceManager manager, Profiler profiler, Executor executor) {
        Gson gson = new Gson();
        return CompletableFuture.supplyAsync(() -> {
            HashMap<Identifier, List<RawBlockMaterialData>> result = new HashMap<>();
            Collection<Identifier> resources = manager.findResources(DATA_TYPE, (name) -> name.endsWith(EXTENSION));
            for(Identifier resource : resources){
                try{
                    List<Resource> allResources = manager.getAllResources(resource);
                    for(Resource resourceItem : allResources){
                        RawBlockMaterialData rawBlockMaterialData = gson.fromJson(new InputStreamReader(resourceItem.getInputStream()), RawBlockMaterialData.class);
                        List<RawBlockMaterialData> blockMaterialData = result.getOrDefault(resource, new LinkedList<>());
                        blockMaterialData.add(rawBlockMaterialData);
                        result.put(resource,blockMaterialData);
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            return result;
        });
    }

    @Override
    public CompletableFuture<Void> apply(Map<Identifier, List<RawBlockMaterialData>> dicts, ResourceManager manager, Profiler profiler, Executor executor) {
        BlockPropertyManager.blockMaterials.clear();
        BlockPropertyManager.tagMaterials.clear();
        return CompletableFuture.runAsync(() -> {
            dicts.values().stream().flatMap(Collection::stream).forEach((rawBlockMaterialData -> {
                BlockMaterialData blockMaterialData = new BlockMaterialData();
                blockMaterialData.hardness = rawBlockMaterialData.hardness;
                blockMaterialData.indestructible = rawBlockMaterialData.indestructible;
            }));
        });
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(FunctionAPI.MODID, "block_override_loader");
    }
}