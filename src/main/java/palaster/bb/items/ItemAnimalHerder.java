package palaster.bb.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster.bb.libs.LibNBT;

public class ItemAnimalHerder extends ItemModSpecial {

	private final int range = 5;

	public ItemAnimalHerder() {
		super();
		setUnlocalizedName("animalHerder");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			if(stack.getItem() instanceof ItemAnimalHerder && stack.getTagCompound().getBoolean(LibNBT.isSet)) {
				Entity animal = EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag(LibNBT.entityTag), playerIn.worldObj);
				if(animal != null)
					tooltip.add(I18n.format("bb.misc.entityTag") + " : " + animal.getName());
			}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if(!playerIn.worldObj.isRemote) {
			if(target instanceof EntityAnimal) {
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				target.writeToNBTAtomically(nbtTagCompound);
				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setBoolean(LibNBT.isSet, true);
				stack.getTagCompound().setTag(LibNBT.entityTag, nbtTagCompound);
				target.setDead();
				return  true;
			} else if(target instanceof EntityLiving && !(target instanceof EntityAnimal)) {
				List<EntityAnimal> animals = playerIn.worldObj.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(playerIn.posX + range, playerIn.posY + 2, playerIn.posZ + range, playerIn.posX - range, playerIn.posY - 1, playerIn.posZ - range));
				if(animals != null) {
					for(EntityAnimal animal : animals) {
						if(animal.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null) {
							animal.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
							animal.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(.5D);
						}
						if(!animal.tasks.taskEntries.contains(new EntityAIAttackMelee(animal, 1.0D, true)))
							animal.tasks.addTask(4, new EntityAIAttackMelee(animal, 1.0D, true));
						animal.setAttackTarget(target);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			if(stack.hasTagCompound())
				if(stack.getTagCompound() != null && stack.getTagCompound().getBoolean(LibNBT.isSet) && stack.getTagCompound().getCompoundTag(LibNBT.entityTag) != null) {
					Entity animal = EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag(LibNBT.entityTag), worldIn);
					if(animal != null) {
						animal.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
						worldIn.spawnEntityInWorld(animal);
						stack.getTagCompound().setBoolean(LibNBT.isSet, false);
						stack.getTagCompound().setTag(LibNBT.entityTag, new NBTTagCompound());
						return EnumActionResult.SUCCESS;
					}
				}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
