package com.godker.connection.packets

enum class ServerPackets(val packetId: Int) {
    LOGGED(0),                  // LOGGED
    REMOVEDIALOGS(1),           // QTDL
    REMOVECHARDIALOG(2),        // QDL
    NAVIGATETOGGLE(3),          // NAVEG
    DISCONNECT(4),              // FINOK
    COMMERCEEND(5),             // FINCOMOK
    BANKEND(6),                 // FINBANOK
    COMMERCEINIT(7),            // INITCOM
    BANKINIT(8),                // INITBANCO
    USERCOMMERCEINIT(9),        // INITCOMUSU
    USERCOMMERCEEND(10),         // FINCOMUSUOK
    USEROFFERCONFIRM(11),
    COMMERCECHAT(12),
    SHOWBLACKSMITHFORM(13),      // SFH
    SHOWCARPENTERFORM(14),       // SFC
    UPDATESTA(15),               // ASS
    UPDATEMANA(16),              // ASM
    UPDATEHP(17),                // ASH
    UPDATEGOLD(18),              // ASG
    UPDATEBANKGOLD(19),
    UPDATEEXP(20),               // ASE
    CHANGEMAP(21),               // CM
    POSUPDATE(22),               // PU
    CHATOVERHEAD(23),            // ||
    CONSOLEMSG(24),              // || - Beware!! its the same as above, but it was properly split
    GUILDCHAT(25),               // |+
    SHOWMESSAGEBOX(26),          // !!
    USERINDEXINSERVER(27),       // IU
    USERCHARINDEXINSERVER(28),   // IP
    CHARACTERCREATE(29),         // CC
    CHARACTERREMOVE(30),         // BP
    CHARACTERCHANGENICK(31),
    CHARACTERMOVE(32),           // MP, +, * and _ //
    FORCECHARMOVE(33),
    CHARACTERCHANGE(34),         // CP
    OBJECTCREATE(35),            // HO
    OBJECTDELETE(36),            // BO
    BLOCKPOSITION(37),           // BQ
    PLAYMIDI(38),                // TM
    PLAYWAVE(39),                // TW
    GUILDLIST(40),               // GL
    AREACHANGED(41),             // CA
    PAUSETOGGLE(42),             // BKW
    RAINTOGGLE(43),              // LLU
    CREATEFX(44),                // CFX
    UPDATEUSERSTATS(45),         // EST
    WORKREQUESTTARGET(46),       // T01
    CHANGEINVENTORYSLOT(47),     // CSI
    CHANGEBANKSLOT(48),          // SBO
    CHANGESPELLSLOT(49),         // SHS
    ATRIBUTES(50),               // ATR
    BLACKSMITHWEAPONS(51),       // LAH
    BLACKSMITHARMORS(52),        // LAR
    CARPENTEROBJECTS(53),        // OBR
    RESTOK(54),                  // DOK
    ERRORMSG(55),                // ERR
    BLIND(56),                   // CEGU
    DUMB(57),                    // DUMB
    SHOWSIGNAL(58),              // MCAR
    CHANGENPCINVENTORYSLOT(59),  // NPCI
    UPDATEHUNGERANDTHIRST(60),   // EHYS
    FAME(61),                    // FAMA
    MINISTATS(62),               // MEST
    LEVELUP(63),                 // SUNI
    ADDFORUMMSG(64),             // FMSG
    SHOWFORUMFORM(65),           // MFOR
    SETINVISIBLE(66),            // NOVER
    DICEROLL(67),                // DADOS
    MEDITATETOGGLE(68),          // MEDOK
    BLINDNOMORE(69),             // NSEGUE
    DUMBNOMORE(70),              // NESTUP
    SENDSKILLS(71),              // SKILLS
    TRAINERCREATURELIST(72),     // LSTCRI
    GUILDNEWS(73),               // GUILDNE
    OFFERDETAILS(74),            // PEACEDE & ALLIEDE
    ALIANCEPROPOSALSLIST(75),    // ALLIEPR
    PEACEPROPOSALSLIST(76),      // PEACEPR
    CHARACTERINFO(77),           // CHRINFO
    GUILDLEADERINFO(78),         // LEADERI
    GUILDMEMBERINFO(79),
    GUILDDETAILS(80),            // CLANDET
    SHOWGUILDFUNDATIONFORM(81),  // SHOWFUN
    PARALYZEOK(82),              // PARADOK
    SHOWUSERREQUEST(83),         // PETICIO
    TRADEOK(84),                 // TRANSOK
    BANKOK(85),                  // BANCOOK
    CHANGEUSERTRADESLOT(86),     // COMUSUINV
    SENDNIGHT(87),               // NOC
    PONG(88),
    UPDATETAGANDSTATUS(89),
    //GM messages
    SPAWNLIST(90),               // SPL
    SHOWSOSFORM(91),             // MSOS
    SHOWMOTDEDITIONFORM(92),     // ZMOTD
    SHOWGMPANELFORM(93),         // ABPANEL
    USERNAMELIST(94),            // LISTUSU
    SHOWDENOUNCES(95),
    RECORDLIST(96),
    RECORDDETAILS(97),
    SHOWGUILDALIGN(98),
    SHOWPARTYFORM(99),
    UPDATESTRENGHTANDDEXTERITY(100),
    UPDATESTRENGHT(101),
    UPDATEDEXTERITY(102),
    ADDSLOTS(103),
    MULTIMESSAGE(104),
    STOPWORKING(105),
    CANCELOFFERITEM(106)
}