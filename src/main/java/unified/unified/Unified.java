package unified.unified;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Unified implements ModInitializer {
    public static Properties map = new Properties(); // Format is tag=item
    public static Object2ObjectArrayMap<Tag<Item>, Item> converter = new Object2ObjectArrayMap<>();

    @Override
    public void onInitialize() {
        System.out.println("ELLO! I am here to modify all your ItemStacks!");

        try {
            FabricLoader.getInstance().getConfigDir().resolve("unifier.properties").toFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            map.load(new FileInputStream(FabricLoader.getInstance().getConfigDir().resolve("unifier.properties").toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        map.forEach((key, val) -> converter.put(getTag(key), getItem(val)));

    }

    private Tag<Item> getTag(Object o) {
        String id = "";
        if (o instanceof String) id = ((String) o).replace("-", ":");
        return TagRegistry.item(Identifier.tryParse(id));
    }

    private Item getItem(Object o) {
        String id = "";
        if (o instanceof String) id = ((String) o).replace("-", ":");
        return Registry.ITEM.get(Identifier.tryParse(id));
    }
}
