package controller
import com.ibm.as400.access.AS400JDBCConnectionImpl
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

private val Int.DB: Array<DB>
    get() = Array(this) { DB() }

class DB(conf: Config? = null) {

    private val config = conf ?: Config()

    val dummy = Class.forName("com.ibm.as400.access.AS400JDBCDriver")
    lateinit var conn : Connection


    fun se(env: String? = null) = runCommand("PKTOOL/SE ${env ?: config.env.toUpperCase()}")

    fun runCommand(command: String) = runQuery("CALL QSYS2.QCMDEXC('$command')")

    fun runQuery(query: String) = conn.createStatement().execute(query)

    fun fetchRows(query: String): ResultSet = conn.createStatement().executeQuery(query)

    fun arrayDB(num: Int): Array<DB> = num.DB

    fun close() = conn.close()

    fun writeData(inputValue: String) = println(inputValue)

    private fun debug() = runCommand("strdbg")

    fun getJobInfo(): String {
        val jobString = (conn as AS400JDBCConnectionImpl).serverJobIdentifier
        val name = jobString[0..10].trim()
        val user = jobString[10..20].trim()
        val number = jobString[20..26].trim()
        return "$number/$user/$name"
        println("asdsdas"[1..2])

    }

    init {
        conn =  DriverManager.getConnection(config.connString, config.user, config.password)
        se()
        conn.autoCommit = config.autoCommit
        if (config.debug) debug()
    }
}

operator fun String.get(intRange: IntRange) = substring(intRange)
