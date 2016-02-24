package com.team.futurecraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * A message that can be sent to the server to teleport from the client.
 * 
 * Note: this is totally hacky and OP, so it's probably gonna get replaced once we have an actual
 * spaceship system.
 * 
 * @author Joseph
 */
public class TeleportMessage implements IMessage
{
	private int dimension;
	
	public TeleportMessage(int dimension)
	{
		this.dimension = dimension;
	}
  
	public TeleportMessage() {}
  
	public int getDimension() {
		return dimension;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dimension = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(dimension);
	}
}