/*
 * This file ("AtomicReconstructorRecipeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.blocks.metalists.TheColoredLampColors;
import ellpeck.actuallyadditions.config.values.ConfigCrafting;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReconstructorRecipeHandler{

    public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    public static final int RECIPES_FOR_BOOKLET_PAGE = 12;

    public static void init(){
        //Crystal Blocks
        addRecipe("blockRedstone", "blockCrystalRed", 400);
        addRecipe("blockLapis", "blockCrystalBlue", 400);
        addRecipe("blockDiamond", "blockCrystalLightBlue", 6000);
        addRecipe("blockEmerald", "blockCrystalGreen", 10000);
        addRecipe("blockCoal", "blockCrystalBlack", 600);
        addRecipe("blockIron", "blockCrystalWhite", 800);

        //Crystal Items
        addRecipe("dustRedstone", "crystalRed", 40);
        addRecipe("gemLapis", "crystalBlue", 40);
        addRecipe("gemDiamond", "crystalLightBlue", 600);
        addRecipe("gemEmerald", "crystalGreen", 1000);
        addRecipe("coal", "crystalBlack", 60);
        addRecipe("ingotIron", "crystalWhite", 80);

        //Lenses
        addRecipe("itemLens", "itemColorLens", 5000);
        addRecipe("itemColorLens", "itemLens", 5000);

        //Misc
        if(ConfigCrafting.RECONSTRUCTOR_MISC.isEnabled()){
            addRecipe("sand", "soulSand", 20000);
            addRecipe("blockQuartz", "blockWhiteBrick", 10);
            addRecipe("blockQuartz", "blockGreenBrick", 10, LensType.COLOR);

            //Colors
            for(int i = 0; i < TheColoredLampColors.values().length-1; i++){
                addRecipe("dye"+TheColoredLampColors.values()[i].name, "dye"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                addRecipe("wool"+TheColoredLampColors.values()[i].name, "wool"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                addRecipe("clay"+TheColoredLampColors.values()[i].name, "clay"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
                addRecipe("blockGlass"+TheColoredLampColors.values()[i].name, "blockGlass"+TheColoredLampColors.values()[i+1].name, 2000, LensType.COLOR);
            }
            addRecipe("dye"+TheColoredLampColors.values()[15].name, "dye"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            addRecipe("wool"+TheColoredLampColors.values()[15].name, "wool"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            addRecipe("clay"+TheColoredLampColors.values()[15].name, "clay"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
            addRecipe("blockGlass"+TheColoredLampColors.values()[15].name, "blockGlass"+TheColoredLampColors.values()[0].name, 2000, LensType.COLOR);
        }
    }

    public static void addRecipe(String input, String output, int energyUse){
        addRecipe(input, output, energyUse, LensType.NONE);
    }

    public static void addRecipe(String input, String output, int energyUse, LensType type){
        recipes.add(new Recipe(input, output, energyUse, type));
    }

    public static ArrayList<Recipe> getRecipes(ItemStack input){
        ArrayList<Recipe> possibleRecipes = new ArrayList<Recipe>();
        for(Recipe recipe : recipes){
            int[] ids = OreDictionary.getOreIDs(input);
            for(int id : ids){
                if(Objects.equals(OreDictionary.getOreName(id), recipe.input)){
                    possibleRecipes.add(recipe);
                }
            }
        }
        return possibleRecipes;
    }

    public static class Recipe{

        public String input;
        public String output;
        public int energyUse;
        public LensType type;

        public Recipe(String input, String output, int energyUse, LensType type){
            this.input = input;
            this.output = output;
            this.energyUse = energyUse;
            this.type = type;
        }

        public ItemStack getFirstOutput(){
            List<ItemStack> stacks = OreDictionary.getOres(this.output, false);
            if(stacks != null && !stacks.isEmpty()){
                return stacks.get(0);
            }
            return null;
        }
    }

    public enum LensType{

        NONE,
        COLOR;

        public ItemStack lens;

        public float[] getColor(){
            if(this == COLOR){
                float[] colors = AssetUtil.RGB_WOOL_COLORS[Util.RANDOM.nextInt(AssetUtil.RGB_WOOL_COLORS.length)];
                return new float[]{colors[0]/255F, colors[1]/255F, colors[2]/255F};
            }
            return new float[]{27F/255F, 109F/255F, 1F};
        }

        public void setLens(Item lens){
            this.lens = new ItemStack(lens);
        }
    }

}