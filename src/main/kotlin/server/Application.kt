package com.godker.server

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
    val mapData = MapLoader.loadAll(Paths.get("maps/"))
    val worldActor = WorldActor(mapData, sessionRegistry, serverScope)
    println("Ok!")

    println("Starting player service...")
        //TODO: could be DatabasePlayerRepository
    val playerRepository = CharFilePlayerRepository(Paths.get("chars/"))

    val playerService = PlayerService(playerRepository, worldActor)
    println("Ok!")

    val context = ServerContext(playerService, worldActor,  objectRegistry, serverScope)

    val server = GameServer(7666, context, sessionRegistry)

    server.start()
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