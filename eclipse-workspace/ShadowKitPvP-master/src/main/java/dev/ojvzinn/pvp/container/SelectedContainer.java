package dev.ojvzinn.pvp.container;

import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.slickcollections.kiwizin.database.data.DataContainer;
import dev.slickcollections.kiwizin.database.data.interfaces.AbstractContainer;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class SelectedContainer extends AbstractContainer {

    public SelectedContainer(DataContainer dataContainer) {
        super(dataContainer);
        if (this.dataContainer.getAsString().equals("{}")) {
            JSONObject object = new JSONObject();
            for (CosmeticType type : CosmeticType.values()) {
                object.put(String.valueOf(type.getId()), "0");
            }

            this.dataContainer.set(object.toJSONString());
        } else {
            JSONObject object = this.dataContainer.getAsJsonObject();
            List<CosmeticType> types = Arrays.stream(CosmeticType.values()).filter(type -> !object.containsKey(String.valueOf(type.getId()))).collect(Collectors.toList());
            for (CosmeticType type : types) {
                object.put(String.valueOf(type.getId()), "0");
            }

            this.dataContainer.set(object.toJSONString());
        }
    }


    public boolean hasSelectedCosmetic(String id, String cosmeticID) {
        return this.dataContainer.getAsJsonObject().get(id).equals(cosmeticID);
    }

    public void setSelectedCosmetic(String id, String cosmeticID) {
        JSONObject cosmetics = this.dataContainer.getAsJsonObject();
        cosmetics.replace(id, cosmeticID);
        this.dataContainer.set(cosmetics.toJSONString());
    }

    public void removeSelectedCosmetic(String id, String cosmeticID) {
        JSONObject cosmetics = this.dataContainer.getAsJsonObject();
        cosmetics.replace(id, "0");
        this.dataContainer.set(cosmetics.toJSONString());
    }
}
