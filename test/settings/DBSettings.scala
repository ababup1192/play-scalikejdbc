package settings

import scalikejdbc._, config._

trait DBSettings {
  DBSettings.initialize()
}

object DBSettings {
  private var isInitialized = false

  def initialize(): Unit = this.synchronized {
    if (isInitialized) return
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:mem:play", "user", "pass")
    GlobalSettings.loggingSQLErrors = false
    GlobalSettings.sqlFormatter = SQLFormatterSettings("utils.HibernateSQLFormatter")
    DBInitializer.run()
    isInitialized = true
  }
}

object DBInitializer {

  def run() {
    DB.autoCommit { implicit session =>
      sql"""
        drop table if exists users;

        create table users(
          id serial not null primary key,
          name varchar(64)
        )
      """.execute.apply()
    }
  }

}

