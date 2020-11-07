package com.normallynormal.hardcorelimbo;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public class GoldenSoulGuidingLantern extends Block{

    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public GoldenSoulGuidingLantern(Settings settings) {
        super(FabricBlockSettings.of(Material.GLASS).nonOpaque().hardness(0.5f).luminance(createLightLevelFromBlockState(15)));
        setDefaultState(getStateManager().getDefaultState().with(LIT, false));
    }



    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LIT);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.1875f, 0f, 0.1875f, 0.8125f, 0.875f, 0.8125f);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.up(), Direction.UP) || sideCoversSmallSquare(world, pos.down(), Direction.DOWN);
    }

    private static ToIntFunction<BlockState> createLightLevelFromBlockState(int litLevel) {
        return (blockState) -> (Boolean)blockState.get(Properties.LIT) ? litLevel : 0;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (state.get(LIT)) {
            if(player.getScoreboardTags().contains("formless2")) {
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                BlockPos deathLocation = new BlockPos((int) (((PlayerEntityExt) player).getLastDeathLocation().getX()), (int) (((PlayerEntityExt) player).getLastDeathLocation().getY()), (int) (((PlayerEntityExt) player).getLastDeathLocation().getZ()));
                passedData.writeBoolean(true);
                passedData.writeFloat(0.05f);
                passedData.writeBlockPos(pos);
                passedData.writeBlockPos(deathLocation);
                ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, HardcoreLimbo.LEADING_PARTICLE, passedData);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.6f, 1f);
            world.setBlockState(pos, state.with(LIT, false));
        }
        else if (!state.get(LIT) && player.getStackInHand(hand).getItem() == Items.FLINT_AND_STEEL) {
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1f, 1f);
            world.setBlockState(pos, state.with(LIT, true));
            player.getStackInHand(hand).damage(1, (ServerPlayerEntity)player, (Consumer<ServerPlayerEntity>)((p) -> {
                p.sendToolBreakStatus(hand);
            }));
        }
        return ActionResult.SUCCESS;
    }



}
