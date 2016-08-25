package palaster.bb.items;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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

	public ItemLeacher(ToolMaterial material) {
		super(material);
		setUnlocalizedName("leacher");
		setCreativeTab(CreativeTabBB.tabBB);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onLivingKill(LivingDeathEvent e) {
		if(!e.getEntityLiving().worldObj.isRemote)
			if(e.getEntityLiving() instanceof EntityLiving || e.getEntityLiving() instanceof EntityPlayer) {
				if(e.getSource() != null && e.getSource().getSourceOfDamage() instanceof EntityPlayer) {
					EntityPlayer p = (EntityPlayer) e.getSource().getSourceOfDamage();
					if(p.getHeldItemMainhand() != null && p.getHeldItemMainhand().getItem() instanceof ItemLeacher) {
						if(!p.getHeldItemMainhand().hasTagCompound())
							p.getHeldItemMainhand().setTagCompound(new NBTTagCompound());
						p.getHeldItemMainhand().getTagCompound().setInteger(TAG_INT_KILLS, ((EntityPlayer) e.getSource().getSourceOfDamage()).getHeldItemMainhand().getTagCompound().getInteger(TAG_INT_KILLS) + 1);
						boolean wasBloodBottleFound = false;
						if(e.getEntityLiving().worldObj.rand.nextInt(5) == 0)
							for(int i = 0; i < p.inventory.getSizeInventory(); i++)
								if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() == BBItems.bloodBottle) {
									p.inventory.getStackInSlot(i).damageItem(-50, p);
									wasBloodBottleFound = true;
									break;
								}
						if(wasBloodBottleFound && p.inventory.hasItemStack(new ItemStack(Items.GLASS_BOTTLE)))
							if(e.getEntityLiving().worldObj.rand.nextInt(10) == 0)
								for(int i = 0; i < p.inventory.getSizeInventory(); i++)
									if(p.inventory.getStackInSlot(i) != null && p.inventory.getStackInSlot(i).getItem() == Items.GLASS_BOTTLE) {
										if(p.inventory.getStackInSlot(i).stackSize > 1) {
											p.inventory.getStackInSlot(i).stackSize--;
											ItemStack bloodBottle = new ItemStack(BBItems.bloodBottle, 1, BBItems.bloodBottle.getMaxDamage() - 50);
											if(!p.inventory.addItemStackToInventory(bloodBottle))
												p.worldObj.spawnEntityInWorld(new EntityItem(p.worldObj, p.posX, p.posY, p.posZ, bloodBottle));
											break;
										} else if(p.inventory.getStackInSlot(i).stackSize == 1) {
											p.inventory.setInventorySlotContents(i, null);
											ItemStack bloodBottle = new ItemStack(BBItems.bloodBottle, 1, BBItems.bloodBottle.getMaxDamage() - 50);
											p.inventory.setInventorySlotContents(i, bloodBottle);
											break;
										}
									}
					}
				}
			}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			tooltip.add(I18n.format("bb.misc.leacher") + " : " + stack.getTagCompound().getInteger(TAG_INT_KILLS));
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!worldIn.isRemote) {
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
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
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if(stack.hasTagCompound())
			if(slot == EntityEquipmentSlot.MAINHAND) {
				int lvl = stack.getTagCompound().getInteger(TAG_INT_KILLS) / 40;
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6 + lvl, 0));
	            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.9D, 0));
			}
		return multimap;
	}
}
