package net.minecraft.network.handshake;

import net.minecraft.network.INetHandler;
import net.minecraft.network.handshake.client.CHandshakePacket;

public interface IHandshakeNetHandler extends INetHandler {
   /**
    * There are two recognized intentions for initiating a handshake: logging in and acquiring server status. The
    * NetworkManager's protocol will be reconfigured according to the specified intention
    */
   void processHandshake(CHandshakePacket packetIn);
}