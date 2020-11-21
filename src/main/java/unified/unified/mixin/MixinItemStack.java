package unified.unified.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import unified.unified.Unified;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Mutable
    @Shadow @Final @Deprecated private Item item;

    @Shadow public abstract void inventoryTick(World world, Entity entity, int slot, boolean selected);

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V")
    private void changeItemFromConstr(ItemConvertible item, int count, CallbackInfo ci) {
        changeItem();
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V")
    private void changeItemFromTag(CompoundTag tag, CallbackInfo ci) {
        changeItem();
    }

    @Unique
    private void changeItem() {
        //this.item = Items.ITEM_FRAME;
        
        Unified.converter.keySet().forEach((k) -> {
            if (k.contains(this.item)) {
                this.item = Unified.converter.get(k);
            }
        });

    }

}
