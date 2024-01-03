package dev.ojvzinn.pvp.game.enums;

import java.util.Arrays;

public enum WarpEnum {

    ARENA("Arena", 0),
    FPS("FPS", 1),
    NENHUM("Nenhum", 2),
    ONE_VS_ONE("1v1", 3);
    public static WarpEnum findByName(String name) {
        return Arrays.stream(WarpEnum.values()).filter(warpEnum -> warpEnum.getName().equalsIgnoreCase(name)).findFirst().orElse(NENHUM);
    }

    private final String name;
    private final Integer id;

    WarpEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
