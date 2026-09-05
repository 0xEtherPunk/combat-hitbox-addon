package com.etherpunk.combathitboxaddon.filter;

import com.etherpunk.combathitboxaddon.config.HitboxFilterConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.InteractionEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.projectile.thrown.*;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class HitboxFilter {

    public static boolean shouldRender(Entity entity) {
        if (entity == null) {
            return false;
        }

        HitboxFilterConfig config = HitboxFilterConfig.getInstance();
        if (!config.enabled) {
            return true;
        }

        Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
        String idStr = (id != null) ? id.toString() : "";
        if (!config.customEntityIds.isEmpty()) {
            boolean inList = config.customEntityIds.contains(idStr) || config.customEntityIds.contains(id.getPath());
            if (config.customListIsWhitelist) {
                if (inList) return true;
                return false;
            } else {
                if (inList) return false;
            }
        }

        if (entity instanceof ItemEntity) {
            return config.showItems;
        }

        if (entity instanceof ExperienceOrbEntity) {
            return config.showExperienceOrbs;
        }

        if (entity instanceof PlayerEntity) {
            if (!config.showPlayers) {
                return false;
            }
            if (entity == MinecraftClient.getInstance().player) {
                return config.showSelfPlayer;
            }
            return config.showOtherPlayers;
        }

        if (entity instanceof ProjectileEntity) {
            if (!config.showProjectiles) {
                return false;
            }
            if (entity instanceof ArrowEntity) return config.showArrows;
            if (entity instanceof SpectralArrowEntity) return config.showSpectralArrows;
            if (entity instanceof TridentEntity) return config.showTridents;
            if (entity instanceof EnderPearlEntity) return config.showEnderPearls;
            if (entity instanceof SnowballEntity) return config.showSnowballs;
            if (entity instanceof EggEntity) return config.showEggs;
            if (entity instanceof PotionEntity || entity instanceof ExperienceBottleEntity) return config.showPotions;
            if (entity instanceof FishingBobberEntity) return config.showFishingBobbers;
            if (entity instanceof FireballEntity || entity instanceof SmallFireballEntity || entity instanceof DragonFireballEntity) return config.showFireballs;
            if (entity instanceof WitherSkullEntity) return config.showWitherSkulls;
            if (entity instanceof ShulkerBulletEntity) return config.showShulkerBullets;
            if (entity instanceof AbstractWindChargeEntity) return config.showWindCharges;
            return true;
        }

        if (entity instanceof Monster || entity instanceof SlimeEntity || entity instanceof PhantomEntity || entity instanceof GhastEntity) {
            if (!config.showHostileMobs) {
                return false;
            }
            if (entity instanceof EnderDragonEntity || entity instanceof WitherEntity || entity instanceof WardenEntity || entity instanceof ElderGuardianEntity) {
                return config.showBosses;
            }
            if (entity instanceof ZombieEntity) return config.showZombies;
            if (entity instanceof AbstractSkeletonEntity) return config.showSkeletons;
            if (entity instanceof CreeperEntity) return config.showCreepers;
            if (entity instanceof SpiderEntity) return config.showSpiders;
            if (entity instanceof EndermanEntity) return config.showEndermen;
            if (entity instanceof AbstractPiglinEntity || entity instanceof ZombifiedPiglinEntity) return config.showPiglins;
            if (entity instanceof SlimeEntity) return config.showSlimes;
            if (entity instanceof PhantomEntity) return config.showPhantoms;
            if (entity instanceof SilverfishEntity) return config.showSilverfish;
            if (entity instanceof EndermiteEntity) return config.showEndermites;
            if (entity instanceof VexEntity) return config.showVexes;
            if (entity instanceof GuardianEntity) return config.showGuardians;
            if (entity instanceof ShulkerEntity) return config.showShulkers;
            if (entity instanceof BlazeEntity || entity instanceof GhastEntity || entity instanceof HoglinEntity || entity instanceof ZoglinEntity) return config.showNetherMonsters;
            if (entity instanceof IllagerEntity || entity instanceof WitchEntity || entity instanceof RavagerEntity) return config.showIllagers;
            if (entity instanceof BreezeEntity) return config.showBreezes;
            return true;
        }

        if (entity instanceof AnimalEntity || entity instanceof VillagerEntity || entity instanceof WanderingTraderEntity || entity instanceof GolemEntity) {
            if (!config.showPassiveMobs) {
                return false;
            }
            if (entity instanceof CowEntity || entity instanceof SheepEntity || entity instanceof PigEntity || entity instanceof ChickenEntity || entity instanceof RabbitEntity) {
                return config.showFarmAnimals;
            }
            if (entity instanceof AbstractHorseEntity || entity instanceof TameableEntity || entity instanceof ParrotEntity || entity instanceof CamelEntity) {
                return config.showPetsAndMounts;
            }
            if (entity instanceof VillagerEntity || entity instanceof WanderingTraderEntity || entity instanceof GolemEntity) {
                return config.showVillagers;
            }
            if (entity instanceof SnifferEntity || entity instanceof ArmadilloEntity) {
                return config.showSniffersAndArmadillos;
            }
            return true;
        }

        if (entity instanceof AmbientEntity || entity instanceof WaterCreatureEntity) {
            if (!config.showAmbientMobs) {
                return false;
            }
            if (entity instanceof BatEntity) return config.showBats;
            if (entity instanceof SquidEntity) return config.showSquids;
            if (entity instanceof FishEntity) return config.showFish;
            if (entity instanceof DolphinEntity || entity instanceof TurtleEntity || entity instanceof AxolotlEntity) return config.showAquaticMisc;
            return true;
        }

        if (entity instanceof EndCrystalEntity) return config.showEndCrystals;
        if (entity instanceof TntEntity) return config.showTnt;
        if (entity instanceof BoatEntity) return config.showBoats;
        if (entity instanceof AbstractMinecartEntity) return config.showMinecarts;
        if (entity instanceof ArmorStandEntity) return config.showArmorStands;
        if (entity instanceof ItemFrameEntity) return config.showItemFrames;
        if (entity instanceof PaintingEntity) return config.showPaintings;
        if (entity instanceof FallingBlockEntity) return config.showFallingBlocks;
        if (entity instanceof DisplayEntity || entity instanceof InteractionEntity) return config.showDisplayAndInteraction;

        return config.showMiscEntities;
    }
}
