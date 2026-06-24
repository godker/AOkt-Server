package com.godker.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.SQLException
import kotlin.math.log

object DatabaseFactory {
    private val logger = LoggerFactory.getLogger(DatabaseFactory::class.java)
    private lateinit var dataSource: HikariDataSource

    fun init() {
        val dbHost = System.getenv("DB_HOST") ?: "localhost"
        val dbPort = System.getenv("DB_PORT") ?: "5432"
        val dbName = System.getenv("DB_NAME") ?: "ao_server"
        val dbUser = System.getenv("DB_USER") ?: "root"
        val dbPass = System.getenv("DB_PASSWORD") ?: "argentum"

        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"

            jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

            username = dbUser
            password = dbPass

            maximumPoolSize = 16
            minimumIdle = 2
            isAutoCommit = false

            addDataSourceProperty("tcpKeepAlive", "true")
            addDataSourceProperty("reWriteBatchedInserts", "true")
        }

        dataSource = HikariDataSource(config)
    }

    fun getConnection() = dataSource.connection

    suspend fun <T> query(block: (Connection) -> T): T = withContext(Dispatchers.IO) {
        dataSource.connection.use(block)
    }

    suspend fun <T> transaction(block: (Connection) -> T): T = withContext(Dispatchers.IO) {
        dataSource.connection.use{ connection ->
            try{
                val result = block(connection)
                connection.commit()
                result
            }catch(e: Exception){
                connection.rollback()
                e.printStackTrace()
                throw e
            }
        }
    }

    fun isConnected(): Boolean {
        return try{
            dataSource.connection.use { connection ->
                connection.isValid(2)
            }
        } catch (e: SQLException){
            false
        }
    }

    fun shutdown() {
        logger.info("Shutting down database...")
        if (!dataSource.isClosed) {
            dataSource.close()
            logger.info("Database pool closed.")
        }
    }
}