package controllers

import javax.inject._
import play.api.mvc._
import scalikejdbc._
import scalikejdbc.config.DBs

case class User(name: String)

object User extends SQLSyntaxSupport[User] {

  override val schemaName: Option[String] = None
  override val tableName: String = "users"

  override def columnNames: Seq[String] = Seq("name")

  def apply(rs: WrappedResultSet) = new User(
    rs.string("name")
  )
}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index() = Action { implicit request: Request[AnyContent] => {
    val u = User.syntax("u")
    val name = "John"

    DB.autoCommit { implicit session =>
      sql"""
        drop table if exists users;

        create table users(
          name varchar(64)
        )
      """.execute.apply()
    }

    DB.localTx { implicit session =>
      withSQL {
        insertInto(User).values(name)
      }.execute().apply()
    }

    val user = DB.readOnly { implicit session =>
      withSQL {
        select.from(User as u)
      }.map(rs => User(rs)).single().apply()
    }

    Ok(user.toString)
  }
  }
}
