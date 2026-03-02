package com.godker.connection.packets

import com.godker.connection.PlayerSession
import io.netty.buffer.ByteBuf

sealed interface IncomingPacket

data class LoginPacket(val name: String, val password: String): IncomingPacket
object ThrowDicePacket: IncomingPacket
data class LoginCreatePacket(val name: String, val password: String, val race: Byte, val gender: Byte, val archetype: Byte, val head: Int, val mail: String, val home: Byte): IncomingPacket
/*data class AttackPacket(): IncomingPacket
data class CastSpellPacket(): IncomingPacket
data class SpellInfoPacket(): IncomingPacket
data class CommerceEndPacket(): IncomingPacket
data class CommerceUserEndPacket(): IncomingPacket
data class CommerceUserConfirmPacket(): IncomingPacket
data class CommerceChatPacket(): IncomingPacket
data class BankEndPacket(): IncomingPacket
data class CommerceUserOkPacket(): IncomingPacket
data class CommerceUserRejectPacket(): IncomingPacket
data class CommerceBuyPacket(): IncomingPacket
data class BankExtractItemPacket(): IncomingPacket
data class CommerceSellPacket(): IncomingPacket
data class BankDepositPacket(): IncomingPacket
data class BankMovePacket(): IncomingPacket
data class CommerceUserOfferPacket(): IncomingPacket
data class RequestAccountStatePacket(): IncomingPacket
data class CommerceStartPacket(): IncomingPacket
data class BankStartPacket(): IncomingPacket
data class BankExtractGoldPacket(): IncomingPacket
data class BankDepositGoldPacket(): IncomingPacket
data class RequestGuildLeaderPacket(): IncomingPacket
data class GuildCreateNewPacket(): IncomingPacket
data class GuildCodexUpdatePacket(): IncomingPacket
data class GuildAcceptPeacePacket(): IncomingPacket
data class GuildRejectAlliancePacket(): IncomingPacket
data class GuildRejectPeacePacket(): IncomingPacket
data class GuildAcceptAlliancePacket(): IncomingPacket
data class GuildOfferPeacePacket(): IncomingPacket
data class GuildOfferAlliancePacket(): IncomingPacket
data class GuildAllianceDetailsPacket(): IncomingPacket
data class GuildPeaceDetailsPacket(): IncomingPacket
data class GuildRequestApplicantInfoPacket(): IncomingPacket
data class GuildAlliancePropListPacket(): IncomingPacket
data class GuildPeacePropListPacket(): IncomingPacket
data class GuildDeclareWarePacket(): IncomingPacket
data class GuildNewSitePacket(): IncomingPacket
data class GuildAcceptNewMemberPacket(): IncomingPacket
data class GuildRejectNewMemberPacket(): IncomingPacket
data class GuildKickMemberPacket(): IncomingPacket
data class GuildUpdateNewsPacket(): IncomingPacket
data class GuildMemberInfoPacket(): IncomingPacket
data class GuildOpenElectionsPacket(): IncomingPacket
data class GuildRequestMembershipPacket(): IncomingPacket
data class GuildRequestDetailsPacket(): IncomingPacket
data class GuildLeavePacket(): IncomingPacket
data class GuildMessagePacket(): IncomingPacket
data class GuildOnlinePacket(): IncomingPacket
data class GuildVotePacket(): IncomingPacket
data class GuildFundatePacket(): IncomingPacket
data class GuildFoundationPacket(): IncomingPacket
data class GuildShowNewsPacket(): IncomingPacket
data class LeftClickPacket(): IncomingPacket
data class DoubleClickPacket(): IncomingPacket
data class ForumPostPacket(): IncomingPacket
data class OnlinePacket(): IncomingPacket
data class HelpPacket(): IncomingPacket
data class RequestMOTDPacket(): IncomingPacket
data class UptimePacket(): IncomingPacket
data class InquiryPacket(): IncomingPacket
data class CouncilMessagePacket(): IncomingPacket
data class RoleMasterRequestPacket(): IncomingPacket
data class GMRequestPacket(): IncomingPacket
data class BugReportPacket(): IncomingPacket

data class PunishmentsPacket(): IncomingPacket
data class InquiryVotePacket(): IncomingPacket

data class DenouncePacket(): IncomingPacket

data class PingPacket(): IncomingPacket

data class GMCommandsPacket(): IncomingPacket

data class ConsultationPacket(): IncomingPacket
data class PartyLeavePacket(): IncomingPacket
data class PartyCreatePacket(): IncomingPacket
data class PartyJoinPacket(): IncomingPacket
data class PartyMessagePacket(): IncomingPacket
data class PartyOnlinePacket(): IncomingPacket
data class PartyKickPacket(): IncomingPacket
data class PartySetLeaderPacket(): IncomingPacket
data class PartyAcceptMemberPacket(): IncomingPacket
data class PartyFormRequestPacket(): IncomingPacket
data class ToggleSafePacket(): IncomingPacket
data class ToggleResurrectSafePacket(): IncomingPacket
data class RequestAttributesPacket(): IncomingPacket
data class RequestFamePacket(): IncomingPacket
data class RequestSkillsPacket(): IncomingPacket
data class RequestMiniStatsPacket(): IncomingPacket
data class WorkPacket(): IncomingPacket
data class UseSpellMacroPacket(): IncomingPacket
data class UseItemPacket(): IncomingPacket
data class CraftBlacksmithPacket(): IncomingPacket
data class CraftCarpenterPacket(): IncomingPacket
data class WorkLeftClickPacket(): IncomingPacket
data class ItemEquipPacket(): IncomingPacket
data class ChangeHeadingPacket(): IncomingPacket
data class SkillModifyPacket(): IncomingPacket
data class TrainPacket(): IncomingPacket
data class SpellMovePacket(): IncomingPacket*/
object QuitPacket: IncomingPacket
/*
data class PetStandPacket(): IncomingPacket
data class PetFollowPacket(): IncomingPacket
data class ReleasePetPacket(): IncomingPacket
data class TrainListPacket(): IncomingPacket

data class RestPacket(): IncomingPacket
data class MeditatePacket(): IncomingPacket
data class ResurrectPacket(): IncomingPacket
data class HealPacket(): IncomingPacket
data class RequestStatsPacket(): IncomingPacket

data class EnlistPacket(): IncomingPacket
data class InformationPacket(): IncomingPacket
data class RewardPacket(): IncomingPacket
data class FactionLeavePacket(): IncomingPacket

data class ChangeDescriptionPacket(): IncomingPacket
data class ChangePasswordPacket(): IncomingPacket
data class GamblePacket(): IncomingPacket

data class ItemUpgradePacket(): IncomingPacket
data class CraftInitPacket(): IncomingPacket
data class HomePacket(): IncomingPacket

data class NpcSharePacket(): IncomingPacket
data class NpcStopSharingPacket(): IncomingPacket

data class ItemMovePacket(): IncomingPacket
data class TalkPacket(): IncomingPacket
data class YellPacket(): IncomingPacket
data class WhisperPacket(): IncomingPacket
data class WalkPacket(): IncomingPacket
data class UpdatePosPacket(): IncomingPacket
data class PickupPacket(): IncomingPacket
data class DropPacket(): IncomingPacket
data class CentinelReportPacket(): IncomingPacket*/