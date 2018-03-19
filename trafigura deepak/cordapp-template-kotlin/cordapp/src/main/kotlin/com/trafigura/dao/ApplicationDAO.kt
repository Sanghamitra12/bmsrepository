package com.trafigura.dao

import com.trafigura.DTO.UserDetails
import com.trafigura.stateandcontracts.LCState
import net.corda.core.identity.Party
import java.net.InetAddress
import java.sql.*

const val ADMIN_DB_PORT = ""
class ApplicationDao {

    fun createOrUpdateLCOnTargetPeers(lcState: LCState, me : Party, listOfParticipants: MutableList<Party>, createNewLC : Boolean) {
        for (participant in listOfParticipants) {
            val dbPort = getDBPort(participant.toString())
            val connection = getConnection(dbPort)
            var statement: PreparedStatement
            var sql: String?
            try {
                if(createNewLC){
                    sql = "INSERT INTO LCDetails(lc_id_seq.next, name, address, contactno, city, state, country, zipcode) VALUES (?,?,?,?,?,?,?,?)"
                } else {
                    sql = "UPDATE LCDetails set name = ? where lc_id = ?"
                }

                statement = connection.prepareStatement(sql)
                statement.setString(2, lcState.lcDetails.name)

                statement.executeUpdate()

            } catch (se: SQLException) {
                System.out.println(se.message)
            } finally {
            }
        }
        updateLC(lcState,me)
    }

    fun updateLC(lcState: LCState, me: Party) {

        val dbPort = getDBPort(me.toString())
        val  connection = getConnection(dbPort)
        val statement: PreparedStatement

        try {
            val sql: String? = "UPDATE LCDetails set name = ? where lc_id = ?"

            statement = connection.prepareStatement(sql)
            statement.setString(1, lcState.lcDetails.name)
            statement.setString(2, lcState.lcDetails.lc_id)

            statement.executeUpdate()

        } catch (se: SQLException) {
            System.out.println(se.message)
        } finally {
        }
    }

    fun getConnection(port: String) : Connection {
        Class.forName("org.h2.Driver")
        println("h2 driver")
        val dbConnectionString = "jdbc:h2:tcp://"+getSystemIpAddress()+":$port/node"
        val DBConnection = DriverManager.getConnection(
                dbConnectionString, "sa", "")
        return DBConnection
    }

    fun getSystemIpAddress() : String {
        try {
            val ipAddr = InetAddress.getLocalHost()
            return ipAddr.hostAddress
        } catch (ex: Exception) {
            System.out.println(ex.message)
            return "Exeption in getting host address"
        }
    }

    fun getDBPort(nodeName : String) : String {
        var port = ""
        if(("C=GB,L=London,O=PartyA").equals(nodeName)){
            port = "10007"
        } else if(("C=US,L=New York,O=PartyB").equals(nodeName)){
            port = "10007"
        } else if(("C=FR,L=Paris,O=PartyC").equals(nodeName)){
            port = "59005"
        } else if(("C=FR,L=Rome,O=PartyD").equals(nodeName)){
            port = "59006"
        }
        return port
    }

    fun createLCAsDraft(lcState: LCState, me: Party) {

        val dbPort = getDBPort(me.toString())
        val connection = getConnection(dbPort)
        var statement: PreparedStatement
        var sql: String?
        try {
            sql = "INSERT INTO LCDetails(lc_id_seq.next, name, address, contactno, city, state, country, zipcode) VALUES (?,?,?,?,?,?,?,?)"
            statement = connection.prepareStatement(sql)
            statement.setString(2, lcState.lcDetails.name)
            statement.executeUpdate()
        } catch (se: SQLException) {
            System.out.println(se.message)
        } finally {
        }

    }

    fun checkUserExists(userDetails: UserDetails): UserDetails {
        val dbPort = ADMIN_DB_PORT
        val connection = getConnection(dbPort)
        var statement: PreparedStatement
        var sql: String?
        try {
            sql = ""
            statement = connection.prepareStatement(sql)
            statement.setString(1, userDetails.user_id)
            statement.executeUpdate()
        } catch (se: SQLException) {
            System.out.println(se.message)
        } finally {
        }
        return  userDetails
    }
}