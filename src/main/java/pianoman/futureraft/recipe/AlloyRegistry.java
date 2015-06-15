package pianoman.futureraft.recipe;

import java.util.Vector;

import net.minecraft.item.ItemStack;

/**
 * Registry for alloys, similar to block and crafting registries.
 * 
 * @author Joseph
 *
 */
public class AlloyRegistry 
{
	private static Vector<AlloyRecipe> recipes = new Vector<AlloyRecipe>();
	
	public static void registerAlloyRecipe(ItemStack item1, ItemStack item2, ItemStack result)
	{
		recipes.add(new AlloyRecipe(item1, item2, result));
	}
	
	public static AlloyRecipe getRecipeFromItems(ItemStack item1, ItemStack item2)
	{
		Object[] recipeArray = recipes.toArray();
		
		for (int i = 0; i < recipeArray.length; i++)
		{
			AlloyRecipe recipe = (AlloyRecipe)recipeArray[i];
			
			if (item1.getItem() == recipe.getStack1().getItem() && item2.getItem() == recipe.getStack2().getItem())
			{
				if (item1.getItemDamage() == recipe.getStack1().getItemDamage() && item2.getItemDamage() == recipe.getStack2().getItemDamage());
				{
					return recipe;
				}
			}
			else if (item2.getItem() == recipe.getStack1().getItem() && item1.getItem() == recipe.getStack2().getItem())
			{
				if (item2.getItemDamage() == recipe.getStack1().getItemDamage() && item1.getItemDamage() == recipe.getStack2().getItemDamage());
				{
					return recipe;
				}
			}
		}
		return null;
	}
}
