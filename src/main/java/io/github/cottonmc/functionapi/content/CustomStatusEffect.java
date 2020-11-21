package io.github.cottonmc.functionapi.content;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class CustomStatusEffect extends StatusEffect{

    private boolean isInstant = false;

    private Identifier applyEventID;
    private Identifier removeEventID;
    private Identifier applyInstantEventID;

    protected CustomStatusEffect(StatusEffectType statusEffectType, int i, boolean isInstant){
        super(statusEffectType, i);
        this.isInstant = isInstant;
    }

    @Override
    public void onApplied(LivingEntity livingEntity, AttributeContainer attributeContainer, int i){
        super.onApplied(livingEntity, attributeContainer, i);
        World world = livingEntity.world;
        if(!world.isClient){

            GlobalEventContainer.getInstance().executeEventBlocking((FunctionAPIIdentifier)getApplyEventID(),  ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, livingEntity));
        }
    }

    @Override
    public void onRemoved(LivingEntity livingEntity, AttributeContainer attributeContainer, int i){
        super.onRemoved(livingEntity, attributeContainer, i);
        World world = livingEntity.world;
        if(!world.isClient){
            GlobalEventContainer.getInstance().executeEventBlocking((FunctionAPIIdentifier)getRemoveEventID(),  ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, livingEntity));
        }

    }

    @Override
    public boolean isInstant(){
        return isInstant;
    }

    @Override
    public void applyInstantEffect(@Nullable Entity entity, @Nullable Entity entity2, LivingEntity livingEntity, int i, double d){
        super.applyInstantEffect(entity, entity2, livingEntity, i, d);
        World world = livingEntity.world;
        if(!world.isClient){
            GlobalEventContainer.getInstance().executeEventBlocking((FunctionAPIIdentifier)getApplyInstantEventID(),  ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, livingEntity));
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int i, int j){
        return true;
    }

    /**
     * when the effect is updated, we just call remove and apply.
     */
    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int i){
        AttributeContainer attributes = livingEntity.getAttributes();
        onRemoved(livingEntity, attributes, i);
        onApplied(livingEntity, attributes, i);
    }

    private Identifier getApplyEventID(){
        if(applyEventID == null){
            Identifier id = Registry.STATUS_EFFECT.getId(this);
            applyEventID = new Identifier(id.getNamespace(),id.getPath()+"/apply");
        }
        return applyEventID;
    }

    private Identifier getRemoveEventID(){
        if(removeEventID == null){
            Identifier id = Registry.STATUS_EFFECT.getId(this);
            removeEventID = new Identifier(id.getNamespace(),id.getPath()+"/remove");
        }
        return removeEventID;
    }

    private Identifier getApplyInstantEventID(){
        if(applyInstantEventID == null){
            Identifier id = Registry.STATUS_EFFECT.getId(this);
            applyInstantEventID = new Identifier(id.getNamespace(),id.getPath()+"/instant");
        }
        return applyInstantEventID;
    }

}
