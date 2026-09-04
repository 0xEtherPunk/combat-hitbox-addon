package com.etherpunk.combathitboxaddon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HitboxFilterConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("CombatHitboxAddon");
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("combat-hitbox-filter.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static HitboxFilterConfig instance;

    // === General Settings ===
    public boolean enabled = true;
    public boolean forceHitboxes = false;
    public boolean showEyeHeight = true;
    public boolean showLookVector = true;

    // === Quick Category Toggles ===
    public boolean showItems = false;               // Dropped items on ground (default off for clean screen)
    public boolean showExperienceOrbs = false;      // XP orbs (default off)
    public boolean showProjectiles = true;          // Projectiles master toggle
    public boolean showPlayers = true;              // Players master toggle
    public boolean showHostileMobs = true;          // Hostile mobs master toggle
    public boolean showPassiveMobs = true;          // Passive mobs master toggle
    public boolean showAmbientMobs = false;         // Ambient & aquatic master toggle
    public boolean showVehiclesAndObjects = true;   // Vehicles & technical master toggle
    public boolean showMiscEntities = true;         // Misc / other entities

    // === Projectiles Detailed ===
    public boolean showArrows = true;
    public boolean showSpectralArrows = true;
    public boolean showTridents = true;
    public boolean showEnderPearls = true;
    public boolean showSnowballs = true;
    public boolean showEggs = true;
    public boolean showPotions = true;
    public boolean showFishingBobbers = true;
    public boolean showFireballs = true;
    public boolean showWitherSkulls = true;
    public boolean showShulkerBullets = true;
    public boolean showWindCharges = true;

    // === Players Detailed ===
    public boolean showSelfPlayer = false;          // Local player hitbox in 3rd person (default off)
    public boolean showOtherPlayers = true;

    // === Hostile Mobs Detailed ===
    public boolean showZombies = true;
    public boolean showSkeletons = true;
    public boolean showCreepers = true;
    public boolean showSpiders = true;
    public boolean showEndermen = true;
    public boolean showPiglins = true;
    public boolean showSlimes = true;
    public boolean showPhantoms = true;
    public boolean showNetherMonsters = true;
    public boolean showIllagers = true;
    public boolean showBreezes = true;
    public boolean showBosses = true;

    // === Passive Animals & NPCs Detailed ===
    public boolean showFarmAnimals = true;
    public boolean showPetsAndMounts = true;
    public boolean showVillagers = true;
    public boolean showSniffersAndArmadillos = true;

    // === Ambient & Aquatic Detailed ===
    public boolean showBats = false;
    public boolean showSquids = true;
    public boolean showFish = true;
    public boolean showAquaticMisc = true;

    // === Vehicles & Objects Detailed ===
    public boolean showEndCrystals = true;
    public boolean showTnt = true;
    public boolean showBoats = true;
    public boolean showMinecarts = true;
    public boolean showArmorStands = true;
    public boolean showItemFrames = true;
    public boolean showPaintings = true;
    public boolean showFallingBlocks = true;
    public boolean showDisplayAndInteraction = true;

    // === Custom Entity ID Rules ===
    public List<String> customEntityIds = new ArrayList<>();
    public boolean customListIsWhitelist = false;

    public static HitboxFilterConfig getInstance() {
        if (instance == null) {
            load();
        }
        return instance;
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                instance = GSON.fromJson(json, HitboxFilterConfig.class);
                if (instance != null) {
                    if (instance.customEntityIds == null) {
                        instance.customEntityIds = new ArrayList<>();
                    }
                    return;
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load combat-hitbox-filter config, reverting to defaults", e);
            }
        }
        instance = new HitboxFilterConfig();
        instance.save();
    }

    public void save() {
        try {
            if (customEntityIds == null) {
                customEntityIds = new ArrayList<>();
            }
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException e) {
            LOGGER.error("Failed to save combat-hitbox-filter config", e);
        }
    }
}
