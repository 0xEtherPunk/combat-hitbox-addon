package com.etherpunk.combathitboxaddon.gui;

import com.etherpunk.combathitboxaddon.config.HitboxFilterConfig;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class HitboxConfigScreen {

    public static Screen create(Screen parent) {
        HitboxFilterConfig config = HitboxFilterConfig.getInstance();

        return YetAnotherConfigLib.createBuilder()
            .title(Text.translatable("combathitboxaddon.title"))
            .save(config::save)

            // 1. GENERAL CATEGORY
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("combathitboxaddon.category.general"))
                .tooltip(Text.translatable("combathitboxaddon.category.general.desc"))
                .option(createBoolOption("enabled", true, () -> config.enabled, val -> config.enabled = val))
                .option(createBoolOption("force_hitboxes", false, () -> config.forceHitboxes, val -> config.forceHitboxes = val))
                .option(createBoolOption("show_eye_height", true, () -> config.showEyeHeight, val -> config.showEyeHeight = val))
                .option(createBoolOption("show_look_vector", true, () -> config.showLookVector, val -> config.showLookVector = val))
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.quick_categories"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.quick_categories.desc")))
                    .option(createBoolOption("show_items", false, () -> config.showItems, val -> config.showItems = val))
                    .option(createBoolOption("show_experience_orbs", false, () -> config.showExperienceOrbs, val -> config.showExperienceOrbs = val))
                    .option(createBoolOption("show_projectiles", true, () -> config.showProjectiles, val -> config.showProjectiles = val))
                    .option(createBoolOption("show_players", true, () -> config.showPlayers, val -> config.showPlayers = val))
                    .option(createBoolOption("show_hostile_mobs", true, () -> config.showHostileMobs, val -> config.showHostileMobs = val))
                    .option(createBoolOption("show_passive_mobs", true, () -> config.showPassiveMobs, val -> config.showPassiveMobs = val))
                    .option(createBoolOption("show_ambient_mobs", false, () -> config.showAmbientMobs, val -> config.showAmbientMobs = val))
                    .option(createBoolOption("show_vehicles_and_objects", true, () -> config.showVehiclesAndObjects, val -> config.showVehiclesAndObjects = val))
                    .option(createBoolOption("show_misc_entities", true, () -> config.showMiscEntities, val -> config.showMiscEntities = val))
                    .build())
                .build())

            // 2. PROJECTILES & ITEMS
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("combathitboxaddon.category.projectiles"))
                .tooltip(Text.translatable("combathitboxaddon.category.projectiles.desc"))
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.items_xp"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.items_xp.desc")))
                    .option(createBoolOption("show_items", false, () -> config.showItems, val -> config.showItems = val))
                    .option(createBoolOption("show_experience_orbs", false, () -> config.showExperienceOrbs, val -> config.showExperienceOrbs = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.arrows_weapons"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.arrows_weapons.desc")))
                    .option(createBoolOption("show_arrows", true, () -> config.showArrows, val -> config.showArrows = val))
                    .option(createBoolOption("show_spectral_arrows", true, () -> config.showSpectralArrows, val -> config.showSpectralArrows = val))
                    .option(createBoolOption("show_tridents", true, () -> config.showTridents, val -> config.showTridents = val))
                    .option(createBoolOption("show_fishing_bobbers", true, () -> config.showFishingBobbers, val -> config.showFishingBobbers = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.throwables"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.throwables.desc")))
                    .option(createBoolOption("show_ender_pearls", true, () -> config.showEnderPearls, val -> config.showEnderPearls = val))
                    .option(createBoolOption("show_snowballs", true, () -> config.showSnowballs, val -> config.showSnowballs = val))
                    .option(createBoolOption("show_eggs", true, () -> config.showEggs, val -> config.showEggs = val))
                    .option(createBoolOption("show_potions", true, () -> config.showPotions, val -> config.showPotions = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.fireballs_magic"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.fireballs_magic.desc")))
                    .option(createBoolOption("show_fireballs", true, () -> config.showFireballs, val -> config.showFireballs = val))
                    .option(createBoolOption("show_wither_skulls", true, () -> config.showWitherSkulls, val -> config.showWitherSkulls = val))
                    .option(createBoolOption("show_shulker_bullets", true, () -> config.showShulkerBullets, val -> config.showShulkerBullets = val))
                    .option(createBoolOption("show_wind_charges", true, () -> config.showWindCharges, val -> config.showWindCharges = val))
                    .build())
                .build())

            // 3. PLAYERS & MONSTERS
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("combathitboxaddon.category.mobs"))
                .tooltip(Text.translatable("combathitboxaddon.category.mobs.desc"))
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.players"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.players.desc")))
                    .option(createBoolOption("show_other_players", true, () -> config.showOtherPlayers, val -> config.showOtherPlayers = val))
                    .option(createBoolOption("show_self_player", false, () -> config.showSelfPlayer, val -> config.showSelfPlayer = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.common_monsters"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.common_monsters.desc")))
                    .option(createBoolOption("show_zombies", true, () -> config.showZombies, val -> config.showZombies = val))
                    .option(createBoolOption("show_skeletons", true, () -> config.showSkeletons, val -> config.showSkeletons = val))
                    .option(createBoolOption("show_creepers", true, () -> config.showCreepers, val -> config.showCreepers = val))
                    .option(createBoolOption("show_spiders", true, () -> config.showSpiders, val -> config.showSpiders = val))
                    .option(createBoolOption("show_endermen", true, () -> config.showEndermen, val -> config.showEndermen = val))
                    .option(createBoolOption("show_piglins", true, () -> config.showPiglins, val -> config.showPiglins = val))
                    .option(createBoolOption("show_slimes", true, () -> config.showSlimes, val -> config.showSlimes = val))
                    .option(createBoolOption("show_phantoms", true, () -> config.showPhantoms, val -> config.showPhantoms = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.nether_raids"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.nether_raids.desc")))
                    .option(createBoolOption("show_nether_monsters", true, () -> config.showNetherMonsters, val -> config.showNetherMonsters = val))
                    .option(createBoolOption("show_illagers", true, () -> config.showIllagers, val -> config.showIllagers = val))
                    .option(createBoolOption("show_breezes", true, () -> config.showBreezes, val -> config.showBreezes = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.bosses"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.bosses.desc")))
                    .option(createBoolOption("show_bosses", true, () -> config.showBosses, val -> config.showBosses = val))
                    .build())
                .build())

            // 4. ANIMALS & AMBIENT
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("combathitboxaddon.category.animals"))
                .tooltip(Text.translatable("combathitboxaddon.category.animals.desc"))
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.farm_animals"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.farm_animals.desc")))
                    .option(createBoolOption("show_farm_animals", true, () -> config.showFarmAnimals, val -> config.showFarmAnimals = val))
                    .option(createBoolOption("show_pets_and_mounts", true, () -> config.showPetsAndMounts, val -> config.showPetsAndMounts = val))
                    .option(createBoolOption("show_villagers", true, () -> config.showVillagers, val -> config.showVillagers = val))
                    .option(createBoolOption("show_sniffers_armadillos", true, () -> config.showSniffersAndArmadillos, val -> config.showSniffersAndArmadillos = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.ambient_aquatic"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.ambient_aquatic.desc")))
                    .option(createBoolOption("show_bats", false, () -> config.showBats, val -> config.showBats = val))
                    .option(createBoolOption("show_squids", true, () -> config.showSquids, val -> config.showSquids = val))
                    .option(createBoolOption("show_fish", true, () -> config.showFish, val -> config.showFish = val))
                    .option(createBoolOption("show_aquatic_misc", true, () -> config.showAquaticMisc, val -> config.showAquaticMisc = val))
                    .build())
                .build())

            // 5. VEHICLES & OBJECTS
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("combathitboxaddon.category.vehicles"))
                .tooltip(Text.translatable("combathitboxaddon.category.vehicles.desc"))
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.combat_explosives"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.combat_explosives.desc")))
                    .option(createBoolOption("show_end_crystals", true, () -> config.showEndCrystals, val -> config.showEndCrystals = val))
                    .option(createBoolOption("show_tnt", true, () -> config.showTnt, val -> config.showTnt = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.transport"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.transport.desc")))
                    .option(createBoolOption("show_boats", true, () -> config.showBoats, val -> config.showBoats = val))
                    .option(createBoolOption("show_minecarts", true, () -> config.showMinecarts, val -> config.showMinecarts = val))
                    .build())
                .group(OptionGroup.createBuilder()
                    .name(Text.translatable("combathitboxaddon.group.utility_objects"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.group.utility_objects.desc")))
                    .option(createBoolOption("show_armor_stands", true, () -> config.showArmorStands, val -> config.showArmorStands = val))
                    .option(createBoolOption("show_item_frames", true, () -> config.showItemFrames, val -> config.showItemFrames = val))
                    .option(createBoolOption("show_paintings", true, () -> config.showPaintings, val -> config.showPaintings = val))
                    .option(createBoolOption("show_falling_blocks", true, () -> config.showFallingBlocks, val -> config.showFallingBlocks = val))
                    .option(createBoolOption("show_display_interaction", true, () -> config.showDisplayAndInteraction, val -> config.showDisplayAndInteraction = val))
                    .build())
                .build())

            // 6. CUSTOM ENTITY FILTER
            .category(ConfigCategory.createBuilder()
                .name(Text.translatable("combathitboxaddon.category.custom"))
                .tooltip(Text.translatable("combathitboxaddon.category.custom.desc"))
                .option(createBoolOption("custom_list_is_whitelist", false, () -> config.customListIsWhitelist, val -> config.customListIsWhitelist = val))
                .option(Option.<String>createBuilder()
                    .name(Text.translatable("combathitboxaddon.option.custom_entity_ids"))
                    .description(OptionDescription.of(Text.translatable("combathitboxaddon.option.custom_entity_ids.desc")))
                    .binding(
                        "",
                        () -> String.join(", ", config.customEntityIds),
                        val -> {
                            config.customEntityIds = Arrays.stream(val.split("[,\\s]+"))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .collect(Collectors.toList());
                        }
                    )
                    .controller(StringControllerBuilder::create)
                    .build())
                .build())

            .build()
            .generateScreen(parent);
    }

    private static Option<Boolean> createBoolOption(
        String key,
        boolean defaultValue,
        Supplier<Boolean> getter,
        Consumer<Boolean> setter
    ) {
        return Option.<Boolean>createBuilder()
            .name(Text.translatable("combathitboxaddon.option." + key))
            .description(OptionDescription.of(Text.translatable("combathitboxaddon.option." + key + ".desc")))
            .binding(defaultValue, getter, setter)
            .controller(BooleanControllerBuilder::create)
            .build();
    }
}
