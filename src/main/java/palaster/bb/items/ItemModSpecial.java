package palaster.bb.items;

public class ItemModSpecial extends ItemMod {
	
	public ItemModSpecial(String unlocalizedName) { this(unlocalizedName, 0, 1); }

	public ItemModSpecial(String unlocalizedName, int maxDamage) { this(unlocalizedName, maxDamage, 1); }
	
	public ItemModSpecial(String unlocalizedName, int maxDamage, int maxStackSize) {
		super(unlocalizedName);
		setMaxDamage(maxDamage);
		setMaxStackSize(maxStackSize <= 0 ? 1 : maxStackSize);
	}
}
