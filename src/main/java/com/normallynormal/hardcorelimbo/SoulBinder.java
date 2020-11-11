package com.normallynormal.hardcorelimbo;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SoulBinder extends Block implements BlockEntityProvider{

    public static final IntProperty FORM_TIER = IntProperty.of("form_tier", 0, 3);
    public static final IntProperty ENERGY_TIER = IntProperty.of("energy_tier", 0, 3);
    public static final IntProperty MIND_TIER = IntProperty.of("mind_tier", 0, 3);
    public static final IntProperty CATALYST_TIER = IntProperty.of("catalyst_tier", 0, 3);

    public SoulBinder(AbstractBlock.Settings settings) {
        super(FabricBlockSettings.of(Material.WOOD).nonOpaque().hardness(0.5f));
        setDefaultState(getStateManager().getDefaultState().with(FORM_TIER, 0).with(ENERGY_TIER, 0).with(MIND_TIER, 0).with(CATALYST_TIER, 0));
    }

    public int getFormTier(BlockState blockState){
        return blockState.get(FORM_TIER);
    }

    public int getEnergyTier(BlockState blockState){
        return blockState.get(ENERGY_TIER);
    }

    public int getMindTier(BlockState blockState){
        return blockState.get(MIND_TIER);
    }

    public int getCatalystTier(BlockState blockState){
        return blockState.get(CATALYST_TIER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FORM_TIER);
        stateManager.add(ENERGY_TIER);
        stateManager.add(MIND_TIER);
        stateManager.add(CATALYST_TIER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 0.9375f, 1f);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new SoulBinderEntity();
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)blockEntity);
    }

    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (player.getScoreboardTags().contains("formless")) {
            if (player.getScoreboardTags().contains("formless2")) {
                if(this.getFormTier(blockState) >= this.getCatalystTier(blockState) && this.getMindTier(blockState) >= this.getCatalystTier(blockState) && this.getEnergyTier(blockState) >= this.getCatalystTier(blockState)){
                    world.playSound(null, blockPos, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1f, 1f);
                    player.removeScoreboardTag("formless");
                    player.removeScoreboardTag("formless2");
                    switch(this.getFormTier(blockState)){
                        case 1:
                            player.addScoreboardTag("form3hearts");
                            player.damage(DamageSource.GENERIC, 1);
                            break;
                        case 2:
                            player.addScoreboardTag("form6hearts");
                            player.damage(DamageSource.GENERIC, 1);
                            break;
                    }
                    switch(this.getEnergyTier(blockState)){
                        case 1:
                            player.addScoreboardTag("energy3food");
                            break;
                        case 2:
                            player.addScoreboardTag("energy6food");
                            break;
                    }
                    switch(this.getMindTier(blockState)){
                        case 1:
                            player.addScoreboardTag("mind4divide");
                            break;
                        case 2:
                            player.addScoreboardTag("mind2divide");
                            break;
                    }
                    world.setBlockState(blockPos, blockState.with(FORM_TIER, 0).with(MIND_TIER, 0).with(ENERGY_TIER, 0).with(CATALYST_TIER, 0));
                    Inventory blockEntity = (Inventory) world.getBlockEntity(blockPos);
                    for(int i = 0; i < 4; i++){
                        blockEntity.setStack(i, ItemStack.EMPTY);
                    }
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeBoolean(false);
                    ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, HardcoreLimbo.FORMLESS_SHADER, passedData);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 1));
                    System.out.println(world.getBlockState(blockPos));
                }
            }
            return ActionResult.SUCCESS;
        }
        Inventory blockEntity = (Inventory) world.getBlockEntity(blockPos);
        if (!player.getStackInHand(hand).isEmpty()) {
            // Check what is the first open slot and put an item from the player's hand there
            Item inhand = player.getStackInHand(hand).getItem();
            if ((inhand == Items.IRON_BLOCK || inhand == Items.DIAMOND || inhand == Items.NETHERITE_INGOT) && inhand != blockEntity.getStack(0).getItem()) {
                // Put the stack the player is holding into the inventory
                world.playSound(null, blockPos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1f, 1f);
                if(!blockEntity.getStack(0).isEmpty()) player.inventory.offerOrDrop(world, blockEntity.getStack(0));
                blockEntity.removeStack(0);
                blockEntity.setStack(0, player.getStackInHand(hand).getItem().getDefaultStack());
                // Remove the stack from the player's hand
                player.getStackInHand(hand).setCount(player.getStackInHand(hand).getCount() - 1);
                if(inhand == Items.IRON_BLOCK) world.setBlockState(blockPos, blockState.with(FORM_TIER, 1));
                if(inhand == Items.DIAMOND) world.setBlockState(blockPos, blockState.with(FORM_TIER, 2));
                if(inhand == Items.NETHERITE_INGOT) world.setBlockState(blockPos, blockState.with(FORM_TIER, 3));
            } else if ((inhand == Items.COAL || inhand == Items.BLAZE_ROD || inhand == Items.DRAGON_BREATH) && inhand != blockEntity.getStack(1).getItem()) {
                // Put the stack the player is holding into the inventory
                world.playSound(null, blockPos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1f, 1f);
                if(!blockEntity.getStack(1).isEmpty()) player.inventory.offerOrDrop(world, blockEntity.getStack(1));
                blockEntity.removeStack(1);
                blockEntity.setStack(1, player.getStackInHand(hand).getItem().getDefaultStack());
                // Remove the stack from the player's hand
                player.getStackInHand(hand).setCount(player.getStackInHand(hand).getCount() - 1);
                if(inhand == Items.COAL) world.setBlockState(blockPos, blockState.with(ENERGY_TIER, 1));
                if(inhand == Items.BLAZE_ROD) world.setBlockState(blockPos, blockState.with(ENERGY_TIER, 2));
                if(inhand == Items.DRAGON_BREATH) world.setBlockState(blockPos, blockState.with(ENERGY_TIER, 3));
            } else if ((inhand == Items.REDSTONE || inhand == Items.EMERALD_BLOCK || inhand == Items.DRAGON_HEAD) && inhand != blockEntity.getStack(2).getItem()) {
                // Put the stack the player is holding into the inventory
                world.playSound(null, blockPos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1f, 1f);
                if(!blockEntity.getStack(2).isEmpty()) player.inventory.offerOrDrop(world, blockEntity.getStack(2));
                blockEntity.removeStack(2);
                blockEntity.setStack(2, player.getStackInHand(hand).getItem().getDefaultStack());
                // Remove the stack from the player's hand
                player.getStackInHand(hand).setCount(player.getStackInHand(hand).getCount() - 1);
                if(inhand == Items.REDSTONE) world.setBlockState(blockPos, blockState.with(MIND_TIER, 1));
                if(inhand == Items.EMERALD_BLOCK) world.setBlockState(blockPos, blockState.with(MIND_TIER, 2));
                if(inhand == Items.DRAGON_HEAD) world.setBlockState(blockPos, blockState.with(MIND_TIER, 3));
            } else if ((inhand == Items.GOLDEN_APPLE || inhand == Items.HEART_OF_THE_SEA || inhand == Items.NETHER_STAR) && inhand != blockEntity.getStack(3).getItem()) {
                // Put the stack the player is holding into the inventory
                world.playSound(null, blockPos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1f, 1f);
                if(!blockEntity.getStack(3).isEmpty()) player.inventory.offerOrDrop(world, blockEntity.getStack(3));
                blockEntity.removeStack(3);
                blockEntity.setStack(3, player.getStackInHand(hand).getItem().getDefaultStack());
                // Remove the stack from the player's hand
                player.getStackInHand(hand).setCount(player.getStackInHand(hand).getCount() - 1);
                if (inhand == Items.GOLDEN_APPLE) world.setBlockState(blockPos, blockState.with(CATALYST_TIER, 1));
                if (inhand == Items.HEART_OF_THE_SEA) world.setBlockState(blockPos, blockState.with(CATALYST_TIER, 2));
                if (inhand == Items.NETHER_STAR) world.setBlockState(blockPos, blockState.with(CATALYST_TIER, 3));
            }
        } else {
            // If the player is not holding anything we'll get give him the items in the block entity one by one

            // Find the first slot that has an item and give it to the player
            if (!blockEntity.getStack(0).isEmpty()) {
                // Give the player the stack in the inventory
                world.setBlockState(blockPos, blockState.with(FORM_TIER, 0));
                player.inventory.offerOrDrop(world, blockEntity.getStack(0));
                // Remove the stack from the inventory
                blockEntity.removeStack(0);
            } else if (!blockEntity.getStack(1).isEmpty()) {
                world.setBlockState(blockPos, blockState.with(ENERGY_TIER, 0));
                player.inventory.offerOrDrop(world, blockEntity.getStack(1));
                blockEntity.removeStack(1);
            } else if (!blockEntity.getStack(2).isEmpty()) {
                world.setBlockState(blockPos, blockState.with(MIND_TIER, 0));
                player.inventory.offerOrDrop(world, blockEntity.getStack(2));
                blockEntity.removeStack(2);
            } else if (!blockEntity.getStack(3).isEmpty()) {
                world.setBlockState(blockPos, blockState.with(CATALYST_TIER, 0));
                player.inventory.offerOrDrop(world, blockEntity.getStack(3));
                blockEntity.removeStack(3);
            }
        }
        blockEntity.markDirty();
        return ActionResult.SUCCESS;
    }
}
