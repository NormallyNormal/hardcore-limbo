package com.normallynormal.hardcorelimbo;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoulBinderTileRenderer extends BlockEntityRenderer<SoulBinderEntity> {
    // A jukebox itemstack
    private ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public SoulBinderTileRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(SoulBinderEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        ItemStack stack1 = new ItemStack(Items.AIR, 1);
        ItemStack stack2 = new ItemStack(Items.AIR, 1);
        ItemStack stack3 = new ItemStack(Items.AIR, 1);
        ItemStack stack4 = new ItemStack(Items.AIR, 1);
        World world = blockEntity.getWorld();
        BlockPos pos = blockEntity.getPos();
        BlockState blockState = world.getBlockState(pos);
        SoulBinder soulBinder = (SoulBinder)blockState.getBlock();

        if (soulBinder.getFormTier(blockState) == 1) {
            stack1 = new ItemStack(Items.IRON_BLOCK, 1);
            matrices.translate(0.85, 0.875, 0.5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(12));
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack1, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-12));
            matrices.translate(-0.85, -0.875, -0.5);
        }
        else if (soulBinder.getFormTier(blockState) == 2) {
            matrices.translate(0.85, 0.95, 0.4);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(12));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            stack1 = new ItemStack(Items.DIAMOND, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack1, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-12));
            matrices.translate(-0.85, -0.95, -0.4);
        }
        else if (soulBinder.getFormTier(blockState) == 3) {
            matrices.translate(0.95, 0.95, 0.5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-78));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            stack1 = new ItemStack(Items.NETHERITE_INGOT, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack1, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(78));
            matrices.translate(-0.95, -0.95, -0.5);
        } else MinecraftClient.getInstance().getItemRenderer().renderItem(stack1, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);

        if (soulBinder.getEnergyTier(blockState) == 1) {
            matrices.translate(0.4, 0.95, 0.85);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(50));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            stack2 = new ItemStack(Items.COAL, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-50));
            matrices.translate(-0.4, -0.95, -0.85);
        }
        else if (soulBinder.getEnergyTier(blockState) == 2) {
            matrices.translate(0.4, 0.95, 0.8);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(50));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            stack2 = new ItemStack(Items.BLAZE_ROD, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-50));
            matrices.translate(-0.4, -0.95, -0.8);
        }
        else if (soulBinder.getEnergyTier(blockState) == 3) {
            matrices.translate(0.4, 0.95, 0.85);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(100));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            stack2 = new ItemStack(Items.DRAGON_BREATH, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-100));
            matrices.translate(-0.4, -0.95, -0.85);
        } else MinecraftClient.getInstance().getItemRenderer().renderItem(stack2, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);

        if (soulBinder.getMindTier(blockState) == 1) {
            matrices.translate(0.07, 0.95, 0.5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(70));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            stack3 = new ItemStack(Items.REDSTONE, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack3, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-70));
            matrices.translate(-0.07, -0.95, -0.5);
        }
        else if (soulBinder.getMindTier(blockState) == 2) {
            matrices.translate(0.15, 0.875, 0.5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(20));
            stack3 = new ItemStack(Items.EMERALD_BLOCK, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack3, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-20));
            matrices.translate(-0.15, -0.875, -0.5);
        }
        else if (soulBinder.getMindTier(blockState) == 3) {
            matrices.translate(0.15, 0.98, 0.5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-30));
            matrices.scale(0.7f,0.7f,0.7f);
            stack3 = new ItemStack(Items.DRAGON_HEAD, 1);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack3, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.scale(1.42857142857f,1.42857142857f,1.42857142857f);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(30));
            matrices.translate(-0.15, -0.98, -0.5);
        } else MinecraftClient.getInstance().getItemRenderer().renderItem(stack3, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);

        if (soulBinder.getCatalystTier(blockState) == 1) {
            stack4 = new ItemStack(Items.GOLDEN_APPLE, 1);
            matrices.translate(0.5, 0.95, 0.04);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-20));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack4, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(20));
            matrices.translate(-0.5, -0.95, -0.04);
        }
        else if (soulBinder.getCatalystTier(blockState) == 2) {
            stack4 = new ItemStack(Items.HEART_OF_THE_SEA, 1);
            matrices.translate(0.5, 0.95, 0.00);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-20));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack4, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(20));
            matrices.translate(-0.5, -0.95, -0.00);
        }
        else if (soulBinder.getCatalystTier(blockState) == 3) {
            stack4 = new ItemStack(Items.NETHER_STAR, 1);
            matrices.translate(0.55, 0.95, 0.11);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-40));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack4, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(40));
            matrices.translate(-0.55, -0.95, -0.11);
        } else MinecraftClient.getInstance().getItemRenderer().renderItem(stack4, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers);

        matrices.pop();
    }
}

