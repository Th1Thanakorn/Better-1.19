package com.thana.better119.core;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class ModMixinConnector implements IMixinConnector {

    public void connect() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.better119.json");
    }
}
