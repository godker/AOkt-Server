package com.godker.connection.protocol

import com.godker.connection.PlayerSession
import com.godker.connection.packets.IncomingPacket
import com.godker.game.City
import com.godker.game.Class
import com.godker.game.Gender
import com.godker.game.Race
import com.godker.game.player.Attributes
import com.godker.readVBString
import io.netty.buffer.ByteBuf
import kotlin.math.max

object LoginPacket : IncomingPacket {

    override suspend fun handle(session: PlayerSession, data: ByteBuf) {
        val name = data.readVBString()
        val password = data.readVBString()

        println("Login of $name identified by $password with client version ${data.readUnsignedByte()}.${data.readUnsignedByte()}.${data.readUnsignedByte()}")

        //TODO: proccess the login
        val player =
            session.context.playerService.login(name, password) ?: //TODO: session.send(ErrorMsg)
            return

        session.player = player
        session.send(LoggedPacket(player.archetype))
    }
}

object ThrowDicePacket : IncomingPacket {
    override suspend fun handle(session: PlayerSession, data: ByteBuf) {

        session.pendingDiceThrowing[Attributes.STRENGTH.ordinal] = //Fuerza
            max(15, 13 + (0..3).random() + (0..2).random())
        session.pendingDiceThrowing[Attributes.DEXTERITY.ordinal] = //Agilidad
            max(15, 12 + (0..3).random() + (0..3).random())
        session.pendingDiceThrowing[Attributes.INTELLIGENCE.ordinal] = //Inteligencia
            max(16, 13 + (0..3).random() + (0..2).random())
        session.pendingDiceThrowing[Attributes.CHARISMA.ordinal] = //Carisma
            max(15, 12 + (0..3).random() + (0..3).random())
        session.pendingDiceThrowing[Attributes.CONSTITUTION.ordinal] = //Constitución
            16 + (0..1).random() + (0..1).random()

        session.send(DiceRollPacket(session.pendingDiceThrowing))
    }
}

object LoginCreatePacket : IncomingPacket {
    override suspend fun handle(session: PlayerSession, data: ByteBuf) {
        val userName = data.readVBString()
        val userPassword = data.readVBString()

        //TODO: version validation
        data.readUnsignedByte()
        data.readUnsignedByte()
        data.readUnsignedByte()

        val userRace = data.readByte().toInt()
        val userGender = data.readByte().toInt()
        val userClass = data.readByte().toInt()
        val userHead = data.readShort().toInt()
        val userMail = data.readVBString()

        val userHome = data.readByte().toInt()

        //TODO: Dice validation

        println("Creating a char named $userName")

        val player = session.context.playerService.create(
            userName, userPassword, userMail,
            Class of userClass,
            Race of userRace,
            Gender of userGender,
            City of userHome,
            userHead,
            session.pendingDiceThrowing
        ) ?: return //TODO: session.send(ErrorMsg)

        session.player = player
    }
}

object AttackPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CastSpellPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object SpellInfoPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceEndPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceUserEndPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceUserConfirmPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceChatPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BankEndPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceUserOkPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceUserRejectPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceBuyPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BankExtractItemPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceSellPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BankDepositPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BankMovePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceUserOfferPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestAccountStatePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CommerceStartPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BankStartPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BankExtractGoldPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BankDepositGoldPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestGuildLeaderPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildCreateNewPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildCodexUpdatePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildAcceptPeacePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildRejectAlliancePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildRejectPeacePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildAcceptAlliancePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildOfferPeacePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildOfferAlliancePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildAllianceDetailsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildPeaceDetailsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildRequestApplicantInfoPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildAlliancePropListPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildPeacePropListPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildDeclareWarePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildNewSitePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildAcceptNewMemberPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildRejectNewMemberPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildKickMemberPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildUpdateNewsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildMemberInfoPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildOpenElectionsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildRequestMembershipPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildRequestDetailsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildLeavePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildMessagePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildOnlinePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildVotePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildFundatePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildFoundationPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GuildShowNewsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object LeftClickPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object DoubleClickPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ForumPostPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object OnlinePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object HelpPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestMOTDPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object UptimePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object InquiryPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CouncilMessagePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RoleMasterRequestPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GMRequestPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object BugReportPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object PunishmentsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object InquiryVotePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object DenouncePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object PingPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object GMCommandsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object ConsultationPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyLeavePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyCreatePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyJoinPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyMessagePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyOnlinePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyKickPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartySetLeaderPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyAcceptMemberPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PartyFormRequestPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ToggleSafePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ToggleResurrectSafePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestAttributesPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestFamePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestSkillsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestMiniStatsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object WorkPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object UseSpellMacroPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object UseItemPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CraftBlacksmithPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CraftCarpenterPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object WorkLeftClickPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ItemEquipPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ChangeHeadingPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object SkillModifyPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object TrainPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object SpellMovePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object QuitPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object PetStandPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PetFollowPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ReleasePetPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object TrainListPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object RestPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object MeditatePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ResurrectPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object HealPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RequestStatsPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object EnlistPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object InformationPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object RewardPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object FactionLeavePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object ChangeDescriptionPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object ChangePasswordPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object GamblePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object ItemUpgradePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CraftInitPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object HomePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object NpcSharePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object NpcStopSharingPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}

object ItemMovePacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object TalkPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object YellPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object WhisperPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object WalkPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object UpdatePosPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object PickupPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object DropPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}
object CentinelReportPacket : IncomingPacket { override suspend fun handle(session: PlayerSession, data: ByteBuf){throw NotImplementedError()}}