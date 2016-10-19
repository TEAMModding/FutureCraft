package com.team.futurecraft.network;

import com.team.futurecraft.world.TeleportHandler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Handles teleport messages being sent to the server. And sends a big fat null to clients.
 * 
 * @author Joseph
 */
public class TeleportMessageHandler implements IMessageHandler<TeleportMessage, IMessage>
{
	public IMessage onMessage(final TeleportMessage message, MessageContext ctx) {
		if (ctx.side != Side.SERVER) {
			System.err.println("TeleportMessage received on wrong side:" + ctx.side);
			return null;
		}

		final EntityPlayerMP sendingPlayer = ctx.getServerHandler().playerEntity;
		if (sendingPlayer == null) {
			System.err.println("EntityPlayerMP was null when TeleportMessage was received");
			return null;
		}

		final WorldServer playerWorldServer = sendingPlayer.getServerForPlayer();
		playerWorldServer.addScheduledTask(new Runnable() {
			public void run() {
				processMessage(message, sendingPlayer);
			}
		});
		return null;
	}
  
	void processMessage(TeleportMessage message, EntityPlayerMP player)
	{
		int dim = message.getDimension();
	  
		if (player.dimension == dim) System.out.println("attempted to teleport in a dimension that you ar allready in!");
		else {
				MinecraftServer mServer = MinecraftServer.getServer();
				player.mcServer.getConfigurationManager().transferPlayerToDimension(player, dim, new TeleportHandler(mServer.worldServerForDimension(dim)));
		}
	}
}