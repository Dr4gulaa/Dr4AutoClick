package com.Dr4gula.Dr4AutoClick.tasks;

import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.PacketPlayOutAnimation;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AutoClickTask extends BukkitRunnable {

    private final Player player;

    public AutoClickTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            cancel();
            return;
        }

        Entity target = getNearestEntityInSight(player, 1);
        if (target instanceof LivingEntity && isCustomNPC(target)) {
            attackEntity(player, (LivingEntity) target);
        }
    }

    private void attackEntity(Player player, LivingEntity target) {
        PacketPlayOutAnimation swingPacket = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(swingPacket);

        EntityLiving nmsTarget = ((CraftLivingEntity) target).getHandle();
        ((CraftPlayer) player).getHandle().attack(nmsTarget);
    }

    private Entity getNearestEntityInSight(Player player, int range) {
        List<Entity> nearbyEntities = player.getNearbyEntities(range, range, range);
        Entity nearestEntity = null;
        double closestDistanceSquared = Double.MAX_VALUE;

        for (Entity entity : nearbyEntities) {
            if (entity.equals(player)) continue;

            double distanceSquared = player.getLocation().distanceSquared(entity.getLocation());
            if (distanceSquared < closestDistanceSquared) {
                closestDistanceSquared = distanceSquared;
                nearestEntity = entity;
            }
        }

        return nearestEntity;
    }

    private boolean isCustomNPC(Entity entity) {
        try {

            Class<?> nmsClass = ((CraftEntity) entity).getHandle().getClass();

            return nmsClass.getName().startsWith("noppes.npcs.entity");
        } catch (Exception e) {
            return false;
        }
    }
}
