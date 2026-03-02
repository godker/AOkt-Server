package com.godker.server

import com.godker.connection.PlayerSessionFactory
import com.godker.connection.SessionRegistry
import com.godker.game.objects.Object
import com.godker.game.objects.ObjectLoader
import com.godker.game.objects.ObjectRegistry
import com.godker.game.player.PlayerService
import com.godker.game.player.loader.CharFilePlayerRepository
import com.godker.game.world.MapLoader
import com.godker.game.world.WorldActor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.nio.file.Paths

fun main(args: Array<String>) {

    printLogo()
    //TODO: for Debug
    System.setProperty("io.netty.leakDetection.level", "paranoid")

    val serverScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val sessionRegistry = SessionRegistry()

    println("Loading Objects...")
    val objectRegistry = ObjectRegistry(
        ObjectLoader.load(Paths.get("dats/")) as Map<Int, Object>)
    println("Ok!")

    println("Loading Maps...")
    val worldActor = WorldActor(
        MapLoader.loadAll(Paths.get("maps/")),
        serverScope)
    println("Ok!")

    println("Starting player service...")
        //TODO: could be DatabasePlayerRepository
    val playerService = PlayerService(
        CharFilePlayerRepository(Paths.get("chars/")),
        worldActor,
        objectRegistry,
        sessionRegistry)

    println("Ok!")

    val sessionFactory = PlayerSessionFactory(playerService, serverScope)

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