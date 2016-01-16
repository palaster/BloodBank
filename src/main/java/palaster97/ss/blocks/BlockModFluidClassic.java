package palaster97.ss.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import palaster97.ss.core.CreativeTabSS;
import palaster97.ss.libs.LibMod;

public class BlockModFluidClassic extends BlockFluidClassic {

    public BlockModFluidClassic(Fluid fluid, Material material) {
        super(fluid, material);
        setUnlocalizedName(fluid.getName());
    }

    @Override
    public Block setUnlocalizedName(String name) {
        GameRegistry.registerBlock(this, name);
        return super.setUnlocalizedName(name);
    }
}
