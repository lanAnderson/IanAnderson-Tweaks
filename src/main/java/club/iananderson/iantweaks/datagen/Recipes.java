package club.iananderson.iantweaks.datagen;

import club.iananderson.iantweaks.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.CONTROLLER.get())
                .pattern("wlw")
                .pattern("fge")
                .pattern("wdw")
                .define('w', ItemTags.PLANKS)
                .define('l', Items.LIGHTNING_ROD)
                .define('f',Items.FIRE_CHARGE)
                .define('g',Tags.Items.GLASS_PANES)
                .define('e',Items.ENDER_EYE)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .group("iantweaks")
                .unlockedBy("has_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Tags.Items.GEMS_DIAMOND).build()))
                .save(consumer);
    }
}
