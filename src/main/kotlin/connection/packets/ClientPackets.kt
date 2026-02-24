package com.godker.connection.packets

enum class ClientPackets(val packetId: Int){
    LOGINEXISTINGCHAR(0),       //OLOGIN
    THROWDICES(1),              //TIRDAD
    LOGINNEWCHAR(2),            //NLOGIN
    TALK(3),                    //;
    YELL(4),                    //-
    WHISPER(5),                 //\
    WALK(6),                    //M
    REQUESTPOSITIONUPDATE(7),   //RPU
    ATTACK(8),                  //AT
    PICKUP(9),                  //AG
    SAFETOGGLE(10),              ///SEG & SEG  (SEG//s behavior has to be coded in the client)
    RESURRECTSAFETOGGLE(11),
    REQUESTGUILDLEADERINFO(12),  //GLINFO
    REQUESTATRIBUTES(13),        //ATR
    REQUESTFAME(14),             //FAMA
    REQUESTSKILLS(15),           //ESKI
    REQUESTMINISTATS(16),        //FEST
    COMMERCEEND(17),             //FINCOM
    USERCOMMERCEEND(18),         //FINCOMUSU
    USERCOMMERCECONFIRM(19),
    COMMERCECHAT(20),
    BANKEND(21),                 //FINBAN
    USERCOMMERCEOK(22),          //COMUSUOK
    USERCOMMERCEREJECT(23),      //COMUSUNO
    DROP(24),                    //TI
    CASTSPELL(25),               //LH
    LEFTCLICK(26),               //LC
    DOUBLECLICK(27),             //RC
    WORK(28),                    //UK
    USESPELLMACRO(29),           //UMH
    USEITEM(30),                 //USA
    CRAFTBLACKSMITH(31),         //CNS
    CRAFTCARPENTER(32),          //CNC
    WORKLEFTCLICK(33),           //WLC
    CREATENEWGUILD(34),          //CIG
    SPELLINFO(35),               //INFS
    EQUIPITEM(36),               //EQUI
    CHANGEHEADING(37),           //CHEA
    MODIFYSKILLS(38),            //SKSE
    TRAIN(39),                   //ENTR
    COMMERCEBUY(40),             //COMP
    BANKEXTRACTITEM(41),         //RETI
    COMMERCESELL(42),            //VEND
    BANKDEPOSIT(43),             //DEPO
    FORUMPOST(44),               //DEMSG
    MOVESPELL(45),               //DESPHE
    MOVEBANK(46),
    CLANCODEXUPDATE(47),         //DESCOD
    USERCOMMERCEOFFER(48),       //OFRECER
    GUILDACCEPTPEACE(49),        //ACEPPEAT
    GUILDREJECTALLIANCE(50),     //RECPALIA
    GUILDREJECTPEACE(51),        //RECPPEAT
    GUILDACCEPTALLIANCE(52),     //ACEPALIA
    GUILDOFFERPEACE(53),         //PEACEOFF
    GUILDOFFERALLIANCE(54),      //ALLIEOFF
    GUILDALLIANCEDETAILS(55),    //ALLIEDET
    GUILDPEACEDETAILS(56),       //PEACEDET
    GUILDREQUESTJOINERINFO(57),  //ENVCOMEN
    GUILDALLIANCEPROPLIST(58),   //ENVALPRO
    GUILDPEACEPROPLIST(59),      //ENVPROPP
    GUILDDECLAREWAR(60),         //DECGUERR
    GUILDNEWWEBSITE(61),         //NEWWEBSI
    GUILDACCEPTNEWMEMBER(62),    //ACEPTARI
    GUILDREJECTNEWMEMBER(63),    //RECHAZAR
    GUILDKICKMEMBER(64),         //ECHARCLA
    GUILDUPDATENEWS(65),         //ACTGNEWS
    GUILDMEMBERINFO(66),         //1HRINFO<
    GUILDOPENELECTIONS(67),      //ABREELEC
    GUILDREQUESTMEMBERSHIP(68),  //SOLICITUD
    GUILDREQUESTDETAILS(69),     //CLANDETAILS
    ONLINE(70),                  ///ONLINE
    QUIT(71),                    ///SALIR
    GUILDLEAVE(72),              ///SALIRCLAN
    REQUESTACCOUNTSTATE(73),     ///BALANCE
    PETSTAND(74),                ///QUIETO
    PETFOLLOW(75),               ///ACOMPA�AR
    RELEASEPET(76),              ///LIBERAR
    TRAINLIST(77),               ///ENTRENAR
    REST(78),                    ///DESCANSAR
    MEDITATE(79),                ///MEDITAR
    RESUCITATE(80),              ///RESUCITAR
    HEAL(81),                    ///CURAR
    HELP(82),                    ///AYUDA
    REQUESTSTATS(83),            ///EST
    COMMERCESTART(84),           ///COMERCIAR
    BANKSTART(85),               ///BOVEDA
    ENLIST(86),                  ///ENLISTAR
    INFORMATION(87),             ///INFORMACION
    REWARD(88),                  ///RECOMPENSA
    REQUESTMOTD(89),             ///MOTD
    UPTIME(90),                  ///UPTIME
    PARTYLEAVE(91),              ///SALIRPARTY
    PARTYCREATE(92),             ///CREARPARTY
    PARTYJOIN(93),               ///PARTY
    INQUIRY(94),                 ///ENCUESTA ( with no params )
    GUILDMESSAGE(95),            ///CMSG
    PARTYMESSAGE(96),            ///PMSG
    CENTINELREPORT(97),          ///CENTINELA
    GUILDONLINE(98),             ///ONLINECLAN
    PARTYONLINE(99),             ///ONLINEPARTY
    COUNCILMESSAGE(100),          ///BMSG
    ROLEMASTERREQUEST(101),       ///ROL
    GMREQUEST(102),               ///GM
    BUGREPORT(103),               ///_BUG
    CHANGEDESCRIPTION(104),       ///DESC
    GUILDVOTE(105),               ///VOTO
    PUNISHMENTS(106),             ///PENAS
    CHANGEPASSWORD(107),          ///CONTRASE�A
    GAMBLE(108),                  ///APOSTAR
    INQUIRYVOTE(109),             ///ENCUESTA ( with parameters )
    LEAVEFACTION(110),            ///RETIRAR ( with no arguments )
    BANKEXTRACTGOLD(111),         ///RETIRAR ( with arguments )
    BANKDEPOSITGOLD(112),         ///DEPOSITAR
    DENOUNCE(113),                ///DENUNCIAR
    GUILDFUNDATE(114),            ///FUNDARCLAN
    GUILDFUNDATION(115),
    PARTYKICK(116),               ///ECHARPARTY
    PARTYSETLEADER(117),          ///PARTYLIDER
    PARTYACCEPTMEMBER(118),       ///ACCEPTPARTY
    PING(119),                    ///PING
    REQUESTPARTYFORM(120),
    ITEMUPGRADE(121),
    GMCOMMANDS(122),
    INITCRAFTING(123),
    HOME(124),
    SHOWGUILDNEWS(125),
    SHARENPC(126),                ///COMPARTIR
    STOPSHARINGNPC(127),
    CONSULTATION(128),
    MOVEITEM(129),
}