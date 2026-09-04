package com.etherpunk.combathitboxaddon;

import com.etherpunk.combathitboxaddon.config.HitboxFilterConfig;
import com.etherpunk.combathitboxaddon.gui.HitboxConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatHitboxAddonClient implements ClientModInitializer {
    public static final String MOD_ID = "combathitboxaddon";
    public static final Logger LOGGER = LoggerFactory.getLogger("CombatHitboxAddon");

    public static KeyBinding keyOpenConfig;
    public static KeyBinding keyToggleFilter;
    public static KeyBinding keyToggleHitboxes;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Combat Hitbox Filter Addon...");

        // Load config
        HitboxFilterConfig config = HitboxFilterConfig.getInstance();

        // Register keybindings
        keyOpenConfig = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.combathitboxaddon.open_config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.combathitboxaddon"
        ));

        keyToggleFilter = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.combathitboxaddon.toggle_filter",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.combathitboxaddon"
        ));

        keyToggleHitboxes = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.combathitboxaddon.toggle_hitboxes",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.combathitboxaddon"
        ));

        // Tick events for key presses and force hitboxes
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null) return;

            while (keyOpenConfig.wasPressed()) {
                client.setScreen(HitboxConfigScreen.create(client.currentScreen));
            }

            while (keyToggleFilter.wasPressed()) {
                config.enabled = !config.enabled;
                config.save();
                if (client.player != null) {
                    client.player.sendMessage(
                        Text.translatable(config.enabled ? "combathitboxaddon.msg.filter_enabled" : "combathitboxaddon.msg.filter_disabled"),
                        true
                    );
                }
            }

            while (keyToggleHitboxes.wasPressed()) {
                if (client.getEntityRenderDispatcher() != null) {
                    boolean newState = !client.getEntityRenderDispatcher().shouldRenderHitboxes();
                    client.getEntityRenderDispatcher().setRenderHitboxes(newState);
                    if (client.player != null) {
                        client.player.sendMessage(
                            Text.translatable(newState ? "combathitboxaddon.msg.hitboxes_on" : "combathitboxaddon.msg.hitboxes_off"),
                            true
                        );
                    }
                }
            }

            // Force hitboxes feature
            if (config.forceHitboxes && client.world != null && client.getEntityRenderDispatcher() != null) {
                if (!client.getEntityRenderDispatcher().shouldRenderHitboxes()) {
                    client.getEntityRenderDispatcher().setRenderHitboxes(true);
                }
            }
        });

        LOGGER.info("Combat Hitbox Filter Addon initialized successfully!");
    }
}
