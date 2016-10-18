package palaster.bb.items;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.core.CreativeTabBB;
import palaster.bb.libs.LibMod;

public class ItemLeacher extends ItemSword {
	
	public static final String TAG_INT_KILLS = "LeacherKills";
	public static final String TAG_INT_LEVEL = "LeacherLevel";

	public ItemLeacher(ToolMaterial material) {
		super(material);
		setUnlocalizedName("leacher");
		setCreativeTab(CreativeTabBB.tabBB);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingKill(LivingDeathEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getEntityLiving() instanceof EntityMob || e.getEntityLiving() instanceof EntityPlayer)
				if(e.getSource() != null && e.getSource().getSourceOfDamage() instanceof EntityPlayer) {
					EntityPlayer p = (EntityPlayer) e.getSource().getSourceOfDamage();
					if(p.getHeldItemMainhand() != null && p.getHeldItemMainhand().getItem() instanceof ItemLeacher) {
						if(!p.getHeldItemMainhand().hasTagCompound())
							p.getHeldItemMainhand().setTagCompound(new NBTTagCompound());
						if(p.getHeldItemMainhand().getTagCompound().getInteger(TAG_INT_LEVEL) < 54) {
							p.getHeldItemMainhand().getTagCompound().setInteger(TAG_INT_KILLS, p.getHeldItemMainhand().getTagCompound().getInteger(TAG_INT_KILLS) + 1);
							if(p.getHeldItemMainhand().getTagCompound().getInteger(TAG_INT_KILLS) >= (int) Math.pow(p.getHeldItemMainhand().getTagCompound().getInteger(TAG_INT_LEVEL), 2)) {
								p.getHeldItemMainhand().getTagCompound().setInteger(TAG_INT_LEVEL, p.getHeldItemMainhand().getTagCompound().getInteger(TAG_INT_LEVEL) + 1);
								p.getHeldItemMainhand().getTagCompound().setInteger(TAG_INT_KILLS, 0);
							}
						}
					}
				}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound()) {
			tooltip.add(I18n.format("bb.misc.level") + " : " + stack.getTagCompound().getInteger(TAG_INT_LEVEL));
			tooltip.add(I18n.format("bb.misc.leacher") + " : " + stack.getTagCompound().getInteger(TAG_INT_KILLS));
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger(TAG_INT_LEVEL, 1);
			stack.getTagCompound().setInteger(TAG_INT_KILLS, 0);
		}
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		setRegistryName(new ResourceLocation(LibMod.MODID, unlocalizedName));
		GameRegistry.register(this);
		return super.setUnlocalizedName(LibMod.MODID + ":" + unlocalizedName);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
		if(stack.hasTagCompound())
			if(slot == EntityEquipmentSlot.MAINHAND) {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6 + stack.getTagCompound().getInteger(TAG_INT_LEVEL), 0));
	            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.9D, 0));
			}
		return multimap;
	}
}
