package dev.ojvzinn.pvp.container;

import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.slickcollections.kiwizin.database.data.DataContainer;
import dev.slickcollections.kiwizin.database.data.interfaces.AbstractContainer;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class CosmeticContainer extends AbstractContainer {

    public CosmeticContainer(DataContainer dataContainer) {
        super(dataContainer);
        if (this.dataContainer.getAsString().equals("{}")) {
            JSONObject object = new JSONObject();
            for (CosmeticType type : CosmeticType.values()) {
                JSONObject subObject = new JSONObject();
                for (Cosmetic cosmetic : Cosmetic.listAllCosmetics(type)) {
                    subObject.put(cosmetic.getId(), false);
                }

                object.put(String.valueOf(type.getId()), subObject);
            }

            this.dataContainer.set(object.toJSONString());
        } else {
            JSONObject object = this.dataContainer.getAsJsonObject();
            for (CosmeticType type : CosmeticType.values()) {
                if (!object.containsKey(String.valueOf(type.getId()))) {
                    JSONObject subObject = new JSONObject();
                    for (Cosmetic cosmetic : Cosmetic.listAllCosmetics(type)) {
                        subObject.put(cosmetic.getId(), false);
                    }

                    object.put(String.valueOf(type.getId()), subObject);
                } else {
                    JSONObject subObject = (JSONObject) object.get(String.valueOf(type.getId()));
                    for (Cosmetic cosmetic : Cosmetic.listAllCosmetics(type)) {
                        if (!subObject.containsKey(cosmetic.getId())) {
                            subObject.put(cosmetic.getId(), false);
                        }
                    }

                    object.replace(String.valueOf(type.getId()), subObject);
                }
            }

            this.dataContainer.set(object.toJSONString());
        }
    }

    public JSONObject listAllCosmeticsByID(String id) {
        JSONObject object = this.dataContainer.getAsJsonObject();
        return (JSONObject) object.get(id);
    }

    public boolean hasCosmetic(String id, String cosmeticID) {
        return (Boolean) listAllCosmeticsByID(id).get(cosmeticID);
    }

    public void addCosmetic(String id, String cosmeticID) {
        JSONObject cosmetics = this.dataContainer.getAsJsonObject();
        JSONObject object = listAllCosmeticsByID(id);
        object.replace(cosmeticID, true);
        cosmetics.replace(id, object);
        this.dataContainer.set(cosmetics.toJSONString());
    }

    public void removeCosmetic(String id, String cosmeticID) {
        JSONObject cosmetics = this.dataContainer.getAsJsonObject();
        JSONObject object = listAllCosmeticsByID(id);
        object.replace(cosmeticID, false);
        cosmetics.replace(id, object);
        this.dataContainer.set(cosmetics.toJSONString());
    }
}
