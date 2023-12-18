package dev.ojvzinn.pvp.cosmetics;

import dev.ojvzinn.pvp.cosmetics.collections.Kit;

public enum CosmeticType {

    KIT("Kits", 1, Kit.class);

    private final String name;
    private final int id;
    private final Class<? extends Cosmetic> cosmeticClass;

    CosmeticType(String name, int id, Class<? extends Cosmetic> cosmeticClass) {
        this.name = name;
        this.id = id;
        this.cosmeticClass = cosmeticClass;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Class<? extends Cosmetic> getCosmeticClass() {
        return cosmeticClass;
    }
}
