package com.godker.server

import com.godker.connection.GameChannelInitializer
import com.godker.connection.PlayerSessionFactory
import com.godker.connection.SessionRegistry

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.ServerChannel
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.kqueue.KQueue
import io.netty.channel.kqueue.KQueueEventLoopGroup
import io.netty.channel.kqueue.KQueueServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

//TODO: move?
object NettyTransport {
    val bossGroup: EventLoopGroup
    val workerGroup: EventLoopGroup

    val channelClass: Class<out ServerChannel>

    init {
        when {
            Epoll.isAvailable() -> {
                bossGroup = EpollEventLoopGroup(1)
                workerGroup = EpollEventLoopGroup()
                channelClass = EpollServerSocketChannel::class.java
                println("Using EPOLL transport")
            }

            KQueue.isAvailable() -> {
                bossGroup = KQueueEventLoopGroup(1)
                workerGroup = KQueueEventLoopGroup()
                channelClass = KQueueServerSocketChannel::class.java
                println("Using KQueue transport")
            }
            else -> {
                bossGroup = NioEventLoopGroup(1)
                workerGroup = NioEventLoopGroup()
                channelClass = NioServerSocketChannel::class.java
                println("Using NIO transport")
            }
        }
    }
}

class GameServer (private val port: Int, private val sessionRegistry: SessionRegistry, private val sessionFactory: PlayerSessionFactory) {

    fun start() {
        val bootstrap = ServerBootstrap()

        bootstrap
            .group(NettyTransport.bossGroup, NettyTransport.workerGroup)
            .channel(NettyTransport.channelClass)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childHandler(GameChannelInitializer(sessionRegistry, sessionFactory))

        bootstrap.bind(port).sync()

        println("Server started on port $port")
    }
}