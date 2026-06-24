package com.godker.server

import com.godker.connection.PlayerSessionFactory
import com.godker.connection.SessionRegistry
import com.godker.database.DatabaseFactory
import com.godker.game.objects.Object
import com.godker.game.objects.ObjectLoader
import com.godker.game.objects.ObjectRegistry
import com.godker.game.player.PlayerService
import com.godker.game.player.loader.DatabasePlayerRepository
import com.godker.game.world.MapLoader
import com.godker.game.world.WorldActor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.slf4j.LoggerFactory
import java.nio.file.Paths
import java.util.TimeZone

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger("Main")

    //TODO: for Debug
    System.setProperty("io.netty.leakDetection.level", "paranoid")

    //JVM uses America/Buenos_Aires but that's invalid for postgres, so we set it manually here.
    TimeZone.setDefault(
        TimeZone.getTimeZone("America/Argentina/Buenos_Aires")
    )

    DatabaseFactory.init()

    logger.info("Connecting database...")
    var retries = 5

    while(!DatabaseFactory.isConnected() && retries > 0) {
        logger.warn("Database is not ready yet. Retrying in 3 seconds... ($retries left)")
        Thread.sleep(3000)
        retries--
    }

    if (!DatabaseFactory.isConnected()) {
        logger.error("Couldn't connect to the databse. Server shutting down.")
        //TODO: ??
    }

    logger.info("Database...Ok!")

    val serverScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val sessionRegistry = SessionRegistry()

    logger.info("Loading Objects...")
    val objectRegistry = ObjectRegistry(
        ObjectLoader.load(Paths.get("dats/")) as Map<Int, Object>)
    logger.info("Objects...Ok!")

    logger.info("Loading Maps...")
    val worldActor = WorldActor(
        MapLoader.loadAll(Paths.get("maps/")),
        serverScope)
    logger.info("Maps...Ok!")

    logger.info("Starting player service...")
    val playerService = PlayerService(
        DatabasePlayerRepository(),
        worldActor,
        objectRegistry,
        sessionRegistry)

    logger.info("Ok!")

    val sessionFactory = PlayerSessionFactory(playerService, serverScope)

    printLogo()

    val server = GameServer(7666, sessionRegistry, sessionFactory)

    server.start()

    //TODO: MAKE EVERYTHING GRACEFULLY ENDS
    //serverScope.cancel etc
}

fun printLogo() {
    // Define ANSI color codes
    val RED = "\u001b[31m"
    val BLUE = "\u001b[34m"
    val RESET = "\u001b[0m" // Resets color to default

    val logo = """
                  ${RED}#####                          
                 #######                         
                #########                        
               ###########                        
              #####  ######                      
             #####   ######                     
            #####     ######                     
           #################                    
          ###################${BLUE}%%%%%%              
         ${RED}####     ${BLUE}%%%%%%${RED}#####  ${BLUE}%%%%%%%          
        ${RED}######   ${BLUE}%%%%%%  ${RED}######   ${BLUE}%%%%%%%        
       ${RED}########  ${BLUE}%%%%% ${RED}########### ${BLUE}%%%%%%%       
                %%%%%                %%%%%       
                %%%%%                %%%%%       
                %%%%%                %%%%%       
                %%%%%                %%%%%       
                %%%%%%             %%%%%%%       
                 %%%%%             %%%%%%        
                  %%%%%%%       %%%%%%%          
                    %%%%%%%%%%%%%%%%%
                      %%%%%%%%%%%%%  
    ${RESET}""".trimIndent()

    println(logo)
}