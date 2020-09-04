package controller
import java.util.*

class Config {
    var server = ""
    var user = ""
    var password = ""
    var env = ""
    var connString = ""
    var debug = false
    var autoCommit = false
    var numberOfRows = ""
    init {
        val prop = Properties()
        val fileInputStream = this::class.java.getClassLoader().getResourceAsStream("config.properties")
        prop.load(fileInputStream)
        server = prop.getProperty("server")
        user = prop.getProperty("user")
        password = prop.getProperty("password")
        env = prop.getProperty("env")
        debug = prop.getProperty("debug") == "yes"
        autoCommit = prop.getProperty("autoCommit") == "true"
        numberOfRows = prop.getProperty("numberOfRows")
        connString = "jdbc:as400://$server;naming=system;errors=full;translate=1"
    }
}
