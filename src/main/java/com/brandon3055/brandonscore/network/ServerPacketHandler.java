package com.brandon3055.brandonscore.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import com.brandon3055.brandonscore.blocks.TileBCBase;
import com.brandon3055.brandonscore.command.BCUtilCommands.OfflinePlayer;
import com.brandon3055.brandonscore.inventory.ContainerPlayerAccess;
import com.brandon3055.brandonscore.lib.TeleportUtils;
import com.brandon3055.brandonscore.utils.LogHelperBC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ServerPacketHandler implements ICustomPacketHandler.IServerPacketHandler {
    @Override
    public void handlePacket(PacketCustom packet, EntityPlayerMP sender, INetHandlerPlayServer handler) {
        try {
            if (packet.getType() == PacketDispatcher.S_TILE_MESSAGE) {
                BlockPos pos = packet.readPos();
                TileEntity tile = sender.world.getTileEntity(pos);
                if (tile instanceof TileBCBase && ((TileBCBase) tile).verifyPlayerPermission(sender)) {
                    int id = packet.readByte() & 0xFF;
                    ((TileBCBase) tile).receivePacketFromClient(packet, sender, id);
                }
            }
        }
        catch (Throwable e) {
            LogHelperBC.error("Something went wrong while attempting to read a packet sent from this client: " + sender);
            e.printStackTrace();
        }


        if (packet.getType() == PacketDispatcher.S_PLAYER_ACCESS_BUTTON) {
            int button = packet.readByte();
            if (!sender.canUseCommand(3, "bcore_util")) {
                sender.sendMessage(new TextComponentString("You do not have permission to use that command").setStyle(new Style().setColor(TextFormatting.RED)));
                return;
            }
            ContainerPlayerAccess container = sender.openContainer instanceof ContainerPlayerAccess ? (ContainerPlayerAccess) sender.openContainer : null;
            if (container == null) return;
            EntityPlayer other = container.playerAccess;
            switch (button) {
                case 0: //tp to player
                    TeleportUtils.teleportEntity(sender, other.dimension, other.posX, other.posY, other.posZ, other.rotationYaw, other.rotationPitch);
                    break;
                case 1: //tp player to you
                    if (other instanceof OfflinePlayer) {
                        ((OfflinePlayer) other).tpTo(sender);
                    }
                    else {
                        TeleportUtils.teleportEntity(other, sender.dimension, sender.posX, sender.posY, sender.posZ, sender.rotationYaw, sender.rotationPitch);
                    }
                    break;
                case 2: //clear player inventory
                    other.inventory.clear();
                    break;
            }
        }
    }
}