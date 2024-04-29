package net.wavemc.core;

import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Wave {

    @Getter
    private final static Wave instance = new Wave();
    @Getter
    private final ExecutorService executorService = Executors.newFixedThreadPool(50);
    private final LuckPerms luckPerms = LuckPermsProvider.get();

    }

