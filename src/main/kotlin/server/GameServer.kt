package com.godker.server

import com.godker.connection.GameChannelInitializer
import com.godker.connection.SessionRegistry
import com.godker.connection.packets.ClientPackets
import com.godker.connection.packets.IncomingHandler
import com.godker.connection.protocol.*

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

class GameServer (private val port: Int, private val context: ServerContext, private val sessionRegistry: SessionRegistry) {

    fun start() {

        registerIncomingPackets()
        
        val bootstrap = ServerBootstrap()

        bootstrap
            .group(NettyTransport.bossGroup, NettyTransport.workerGroup)
            .channel(NettyTransport.channelClass)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childHandler(GameChannelInitializer(context, sessionRegistry))

        bootstrap.bind(port).sync()

        println("Server started on port $port")
    }

    fun registerIncomingPackets(){
        println("Registering packets....")

        IncomingHandler.register(ClientPackets.LOGINEXISTINGCHAR, LoginPacket)
        IncomingHandler.register(ClientPackets.THROWDICES, ThrowDicePacket)
        IncomingHandler.register(ClientPackets.LOGINNEWCHAR, LoginCreatePacket)
        IncomingHandler.register(ClientPackets.TALK, TalkPacket)
        IncomingHandler.register(ClientPackets.YELL, YellPacket)
        IncomingHandler.register(ClientPackets.WHISPER, WhisperPacket)
        IncomingHandler.register(ClientPackets.WALK, WalkPacket)
        IncomingHandler.register(ClientPackets.REQUESTPOSITIONUPDATE, UpdatePosPacket)
        IncomingHandler.register(ClientPackets.ATTACK, AttackPacket)
        IncomingHandler.register(ClientPackets.PICKUP, PickupPacket)
        IncomingHandler.register(ClientPackets.SAFETOGGLE, ToggleSafePacket)
        IncomingHandler.register(ClientPackets.RESURRECTSAFETOGGLE, ToggleResurrectSafePacket)
        IncomingHandler.register(ClientPackets.REQUESTGUILDLEADERINFO, RequestGuildLeaderPacket)
        IncomingHandler.register(ClientPackets.REQUESTATRIBUTES, RequestAttributesPacket)
        IncomingHandler.register(ClientPackets.REQUESTFAME, RequestFamePacket)
        IncomingHandler.register(ClientPackets.REQUESTSKILLS, RequestSkillsPacket)
        IncomingHandler.register(ClientPackets.REQUESTMINISTATS, RequestMiniStatsPacket)
        IncomingHandler.register(ClientPackets.COMMERCEEND, CommerceEndPacket)
        IncomingHandler.register(ClientPackets.USERCOMMERCEEND, CommerceUserEndPacket)
        IncomingHandler.register(ClientPackets.USERCOMMERCECONFIRM, CommerceUserConfirmPacket)
        IncomingHandler.register(ClientPackets.COMMERCECHAT, CommerceChatPacket)
        IncomingHandler.register(ClientPackets.BANKEND, BankEndPacket)
        IncomingHandler.register(ClientPackets.USERCOMMERCEOK, CommerceUserOkPacket)
        IncomingHandler.register(ClientPackets.USERCOMMERCEREJECT, CommerceUserRejectPacket)
        IncomingHandler.register(ClientPackets.DROP, DropPacket)
        IncomingHandler.register(ClientPackets.CASTSPELL, CastSpellPacket)
        IncomingHandler.register(ClientPackets.LEFTCLICK, LeftClickPacket)
        IncomingHandler.register(ClientPackets.DOUBLECLICK, DoubleClickPacket)
        IncomingHandler.register(ClientPackets.WORK, WorkPacket)
        IncomingHandler.register(ClientPackets.USESPELLMACRO, UseSpellMacroPacket)
        IncomingHandler.register(ClientPackets.USEITEM, UseItemPacket)
        IncomingHandler.register(ClientPackets.CRAFTBLACKSMITH, CraftBlacksmithPacket)
        IncomingHandler.register(ClientPackets.CRAFTCARPENTER, CraftCarpenterPacket)
        IncomingHandler.register(ClientPackets.WORKLEFTCLICK, WorkLeftClickPacket)
        IncomingHandler.register(ClientPackets.CREATENEWGUILD, GuildCreateNewPacket)
        IncomingHandler.register(ClientPackets.SPELLINFO, SpellInfoPacket)
        IncomingHandler.register(ClientPackets.EQUIPITEM, ItemEquipPacket)
        IncomingHandler.register(ClientPackets.CHANGEHEADING, ChangeHeadingPacket)
        IncomingHandler.register(ClientPackets.MODIFYSKILLS, SkillModifyPacket)
        IncomingHandler.register(ClientPackets.TRAIN, TrainPacket)
        IncomingHandler.register(ClientPackets.COMMERCEBUY, CommerceBuyPacket)
        IncomingHandler.register(ClientPackets.BANKEXTRACTITEM, BankExtractItemPacket)
        IncomingHandler.register(ClientPackets.COMMERCESELL, CommerceSellPacket)
        IncomingHandler.register(ClientPackets.BANKDEPOSIT, BankDepositPacket)
        IncomingHandler.register(ClientPackets.FORUMPOST, ForumPostPacket)
        IncomingHandler.register(ClientPackets.MOVESPELL, SpellMovePacket)
        IncomingHandler.register(ClientPackets.MOVEBANK, BankMovePacket)
        IncomingHandler.register(ClientPackets.CLANCODEXUPDATE, GuildCodexUpdatePacket)
        IncomingHandler.register(ClientPackets.USERCOMMERCEOFFER, CommerceUserOfferPacket)
        IncomingHandler.register(ClientPackets.GUILDACCEPTPEACE, GuildAcceptPeacePacket)
        IncomingHandler.register(ClientPackets.GUILDREJECTALLIANCE, GuildRejectAlliancePacket)
        IncomingHandler.register(ClientPackets.GUILDREJECTPEACE, GuildRejectPeacePacket)
        IncomingHandler.register(ClientPackets.GUILDACCEPTALLIANCE, GuildAcceptAlliancePacket)
        IncomingHandler.register(ClientPackets.GUILDOFFERPEACE, GuildOfferPeacePacket)
        IncomingHandler.register(ClientPackets.GUILDOFFERALLIANCE, GuildOfferAlliancePacket)
        IncomingHandler.register(ClientPackets.GUILDALLIANCEDETAILS, GuildAllianceDetailsPacket)
        IncomingHandler.register(ClientPackets.GUILDPEACEDETAILS, GuildPeaceDetailsPacket)
        IncomingHandler.register(ClientPackets.GUILDREQUESTJOINERINFO, GuildRequestApplicantInfoPacket)
        IncomingHandler.register(ClientPackets.GUILDALLIANCEPROPLIST, GuildAlliancePropListPacket)
        IncomingHandler.register(ClientPackets.GUILDPEACEPROPLIST, GuildPeacePropListPacket)
        IncomingHandler.register(ClientPackets.GUILDDECLAREWAR, GuildDeclareWarePacket)
        IncomingHandler.register(ClientPackets.GUILDNEWWEBSITE, GuildNewSitePacket)
        IncomingHandler.register(ClientPackets.GUILDACCEPTNEWMEMBER, GuildAcceptNewMemberPacket)
        IncomingHandler.register(ClientPackets.GUILDREJECTNEWMEMBER, GuildRejectNewMemberPacket)
        IncomingHandler.register(ClientPackets.GUILDKICKMEMBER, GuildKickMemberPacket)
        IncomingHandler.register(ClientPackets.GUILDUPDATENEWS, GuildUpdateNewsPacket)
        IncomingHandler.register(ClientPackets.GUILDMEMBERINFO, GuildMemberInfoPacket)
        IncomingHandler.register(ClientPackets.GUILDOPENELECTIONS, GuildOpenElectionsPacket)
        IncomingHandler.register(ClientPackets.GUILDREQUESTMEMBERSHIP, GuildRequestMembershipPacket)
        IncomingHandler.register(ClientPackets.GUILDREQUESTDETAILS, GuildRequestDetailsPacket)
        IncomingHandler.register(ClientPackets.ONLINE, OnlinePacket)
        IncomingHandler.register(ClientPackets.QUIT, QuitPacket)
        IncomingHandler.register(ClientPackets.GUILDLEAVE, GuildLeavePacket)
        IncomingHandler.register(ClientPackets.REQUESTACCOUNTSTATE, RequestAccountStatePacket)
        IncomingHandler.register(ClientPackets.PETSTAND, PetStandPacket)
        IncomingHandler.register(ClientPackets.PETFOLLOW, PetFollowPacket)
        IncomingHandler.register(ClientPackets.RELEASEPET, ReleasePetPacket)
        IncomingHandler.register(ClientPackets.TRAINLIST, TrainListPacket)
        IncomingHandler.register(ClientPackets.REST, RestPacket)
        IncomingHandler.register(ClientPackets.MEDITATE, MeditatePacket)
        IncomingHandler.register(ClientPackets.RESUCITATE, ResurrectPacket)
        IncomingHandler.register(ClientPackets.HEAL, HealPacket)
        IncomingHandler.register(ClientPackets.HELP, HelpPacket)
        IncomingHandler.register(ClientPackets.REQUESTSTATS, RequestStatsPacket)
        IncomingHandler.register(ClientPackets.COMMERCESTART, CommerceStartPacket)
        IncomingHandler.register(ClientPackets.BANKSTART, BankStartPacket)
        IncomingHandler.register(ClientPackets.ENLIST, EnlistPacket)
        IncomingHandler.register(ClientPackets.INFORMATION, InformationPacket)
        IncomingHandler.register(ClientPackets.REWARD, RewardPacket)
        IncomingHandler.register(ClientPackets.REQUESTMOTD, RequestMOTDPacket)
        IncomingHandler.register(ClientPackets.UPTIME, UptimePacket)
        IncomingHandler.register(ClientPackets.PARTYLEAVE, PartyLeavePacket)
        IncomingHandler.register(ClientPackets.PARTYCREATE, PartyCreatePacket)
        IncomingHandler.register(ClientPackets.PARTYJOIN, PartyJoinPacket)
        IncomingHandler.register(ClientPackets.INQUIRY, InquiryPacket)
        IncomingHandler.register(ClientPackets.GUILDMESSAGE, GuildMessagePacket)
        IncomingHandler.register(ClientPackets.PARTYMESSAGE, PartyMessagePacket)
        IncomingHandler.register(ClientPackets.CENTINELREPORT, CentinelReportPacket)
        IncomingHandler.register(ClientPackets.GUILDONLINE, GuildOnlinePacket)
        IncomingHandler.register(ClientPackets.PARTYONLINE, PartyOnlinePacket)
        IncomingHandler.register(ClientPackets.COUNCILMESSAGE, CouncilMessagePacket)
        IncomingHandler.register(ClientPackets.ROLEMASTERREQUEST, RoleMasterRequestPacket)
        IncomingHandler.register(ClientPackets.GMREQUEST, GMRequestPacket)
        IncomingHandler.register(ClientPackets.BUGREPORT, BugReportPacket)
        IncomingHandler.register(ClientPackets.CHANGEDESCRIPTION, ChangeDescriptionPacket)
        IncomingHandler.register(ClientPackets.GUILDVOTE, GuildVotePacket)
        IncomingHandler.register(ClientPackets.PUNISHMENTS, PunishmentsPacket)
        IncomingHandler.register(ClientPackets.CHANGEPASSWORD, ChangePasswordPacket)
        IncomingHandler.register(ClientPackets.GAMBLE, GamblePacket)
        IncomingHandler.register(ClientPackets.INQUIRYVOTE, InquiryVotePacket)
        IncomingHandler.register(ClientPackets.LEAVEFACTION, FactionLeavePacket)
        IncomingHandler.register(ClientPackets.BANKEXTRACTGOLD, BankExtractGoldPacket)
        IncomingHandler.register(ClientPackets.BANKDEPOSITGOLD, BankDepositGoldPacket)
        IncomingHandler.register(ClientPackets.DENOUNCE, DenouncePacket)
        IncomingHandler.register(ClientPackets.GUILDFUNDATE, GuildFundatePacket)
        IncomingHandler.register(ClientPackets.GUILDFUNDATION, GuildFoundationPacket)
        IncomingHandler.register(ClientPackets.PARTYKICK, PartyKickPacket)
        IncomingHandler.register(ClientPackets.PARTYSETLEADER, PartySetLeaderPacket)
        IncomingHandler.register(ClientPackets.PARTYACCEPTMEMBER, PartyAcceptMemberPacket)
        IncomingHandler.register(ClientPackets.PING, PingPacket)
        IncomingHandler.register(ClientPackets.REQUESTPARTYFORM, PartyFormRequestPacket)
        IncomingHandler.register(ClientPackets.ITEMUPGRADE, ItemUpgradePacket)
        IncomingHandler.register(ClientPackets.GMCOMMANDS, GMCommandsPacket)
        IncomingHandler.register(ClientPackets.INITCRAFTING, CraftInitPacket)
        IncomingHandler.register(ClientPackets.HOME, HomePacket)
        IncomingHandler.register(ClientPackets.SHOWGUILDNEWS, GuildShowNewsPacket)
        IncomingHandler.register(ClientPackets.SHARENPC, NpcSharePacket)
        IncomingHandler.register(ClientPackets.STOPSHARINGNPC, NpcStopSharingPacket)
        IncomingHandler.register(ClientPackets.CONSULTATION, ConsultationPacket)
        IncomingHandler.register(ClientPackets.MOVEITEM, ItemMovePacket)
        println("Ok... ${IncomingHandler.count()} packets registered.")
    }
}