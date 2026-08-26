package com.zhouzhi.emeraldcraft.procedures.compress;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
/**
 * 该类用于读取和记录数据。
 */
public class TagChange {
    // region ItemStack
    // region Int
    public static int getOrCreateComponent(ItemStack stack, String key, int normalData) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        if (customData.contains(key, Tag.TAG_INT)) {
            return customData.getInt(key);
        }

        return normalData;
    }

    public static void saveComponent(ItemStack stack, String key, int data) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        customData.putInt(key, data);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customData));
    }
    // endregion
    // region String
    public static String getOrCreateComponent(ItemStack stack, String key, String normalData) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        if (customData.contains(key, Tag.TAG_STRING)) {
            return customData.getString(key);
        }

        return normalData;
    }

    public static void saveComponent(ItemStack stack, String key, String data) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        customData.putString(key, data);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customData));
    }
    // endregion
    // region Boolean
    public static boolean getOrCreateComponent(ItemStack stack, String key, boolean normalData) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        if (customData.contains(key, Tag.TAG_BYTE)) {
            return customData.getBoolean(key);
        }

        return normalData;
    }

    public static void saveComponent(ItemStack stack, String key, boolean data) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        customData.putBoolean(key, data);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customData));
    }
    // endregion
    // region Double
    public static double getOrCreateComponent(ItemStack stack, String key, double normalData) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        if (customData.contains(key, Tag.TAG_DOUBLE)) {
            return customData.getDouble(key);
        }

        return normalData;
    }

    public static void saveComponent(ItemStack stack, String key, double data) {
        CompoundTag customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        customData.putDouble(key, data);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customData));
    }
    // endregion
    // endregion
    // region BlockEntity
    // region Int
    public static int getOrCreateComponent(BlockEntity blockEntity, String key, int normalData) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_INT)) {
            return persistentData.getInt(key);
        }
        return normalData;
    }

    public static void saveComponent(BlockEntity blockEntity, String key, int data) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        persistentData.putInt(key, data);
    }
    // endregion
    // region String
    public static String getOrCreateComponent(BlockEntity blockEntity, String key, String normalData) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_STRING)) {
            return persistentData.getString(key);
        }
        return normalData;
    }

    public static void saveComponent(BlockEntity blockEntity, String key, String data) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        persistentData.putString(key, data);
    }
    // endregion
    // region Boolean
    public static boolean getOrCreateComponent(BlockEntity blockEntity, String key, boolean normalData) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_BYTE)) {
            return persistentData.getBoolean(key);
        }
        return normalData;
    }

    public static void saveComponent(BlockEntity blockEntity, String key, boolean data) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        persistentData.putBoolean(key, data);
    }
    // endregion
    // region Double
    public static double getOrCreateComponent(BlockEntity blockEntity, String key, double normalData) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_DOUBLE)) {
            return persistentData.getDouble(key);
        }
        return normalData;
    }

    public static void saveComponent(BlockEntity blockEntity, String key, double data) {
        CompoundTag persistentData = blockEntity.getPersistentData();
        persistentData.putDouble(key, data);
    }
    // endregion
    // endregion
    // region Entity
    // region Int
    public static int getOrCreateComponent(Entity entity, String key, int normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_INT)) {
            return persistentData.getInt(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, int data) {
        entity.getPersistentData().putInt(key, data);
    }
    // endregion
    // region String
    public static String getOrCreateComponent(Entity entity, String key, String normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_STRING)) {
            return persistentData.getString(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, String data) {
        entity.getPersistentData().putString(key, data);
    }
    // endregion
    // region Boolean
    public static boolean getOrCreateComponent(Entity entity, String key, boolean normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_BYTE)) {
            return persistentData.getBoolean(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, boolean data) {
        entity.getPersistentData().putBoolean(key, data);
    }
    // endregion
    // region Double
    public static double getOrCreateComponent(Entity entity, String key, double normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_DOUBLE)) {
            return persistentData.getDouble(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, double data) {
        entity.getPersistentData().putDouble(key, data);
    }
    // endregion
    // region Long
    public static long getOrCreateComponent(Entity entity, String key, long normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_LONG)) {
            return persistentData.getLong(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, long data) {
        entity.getPersistentData().putLong(key, data);
    }
    // endregion
    // endregion
}
