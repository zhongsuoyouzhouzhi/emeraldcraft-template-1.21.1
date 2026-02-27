package com.zhouzhi.emeraldcraft.procedures.compress;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TagChange {
    //ItemStack
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
    }//int

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
    }//String

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
    }//boolean

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
    }//double

    // BlockEntity
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
    }//int

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
    }//String

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
    }//boolean

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
    }//double

    //Entity
    public static int getOrCreateComponent(Entity entity, String key, int normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_INT)) {
            return persistentData.getInt(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, int data) {
        entity.getPersistentData().putInt(key, data);
    }//int

    public static String getOrCreateComponent(Entity entity, String key, String normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_STRING)) {
            return persistentData.getString(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, String data) {
        entity.getPersistentData().putString(key, data);
    }//String

    public static boolean getOrCreateComponent(Entity entity, String key, boolean normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_BYTE)) {
            return persistentData.getBoolean(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, boolean data) {
        entity.getPersistentData().putBoolean(key, data);
    }//boolean

    public static double getOrCreateComponent(Entity entity, String key, double normalData) {
        CompoundTag persistentData = entity.getPersistentData();
        if (persistentData.contains(key, Tag.TAG_DOUBLE)) {
            return persistentData.getDouble(key);
        }
        return normalData;
    }

    public static void saveComponent(Entity entity, String key, double data) {
        entity.getPersistentData().putDouble(key, data);
    }//double
}
