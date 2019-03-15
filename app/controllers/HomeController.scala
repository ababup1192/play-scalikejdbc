package controllers

import javax.inject._
import play.api.mvc._
import scalikejdbc._

case class User(id: Long, name: String)

object User extends SQLSyntaxSupport[User] {
  private[this] val u = User.syntax("u")

  override val schemaName: Option[String] = None
  override val tableName: String = "users"
  override lazy val columns: Seq[String] = Seq("id", "name")

  def apply(rn: ResultName[User])(rs: WrappedResultSet): User = autoConstruct(rs, rn)

  def create(name: String)(implicit s: DBSession = AutoSession): Long =
    withSQL {
      insertInto(User).values(null, name)
    }.updateAndReturnGeneratedKey().apply()

  def findByName(name: String)(implicit s: DBSession = AutoSession): Option[User] = withSQL {
    select.from(User as u).where.eq(u.name, name)
  }.map(User(u.resultName)).single().apply()
}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index() = Action { implicit request: Request[AnyContent] => {

    DB.autoCommit { implicit session =>
      sql"""
        drop table if exists users;

        create table users(
          id serial not null primary key,
          name varchar(64)
        )
      """.execute.apply()
    }

    val user = DB.localTx { implicit session =>
      User.create("John")
      User.findByName("John")
    }

    Ok(user.toString)
  }
  }
}
