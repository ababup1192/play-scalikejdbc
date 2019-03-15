package controllers

import javax.inject._
import play.api.mvc._
import scalikejdbc._

case class User(id: Long, name: String)

object User extends SQLSyntaxSupport[User] {

  override val schemaName: Option[String] = None
  override val tableName: String = "users"

  def apply(u: ResultName[User])(rs: WrappedResultSet): User = {
    User(rs.long(u.id), rs.string(u.name))
  }
}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val session = AutoSession

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] => {
    val name = "John"

    val user =
      try sql"select * from users".toMap.single.apply()
      catch {
        case e: Exception =>
          sql"create table users(name varchar(100) not null)".execute.apply()
          sql"insert into users values ($name)".update.apply()
          sql"select * from users".toMap.single().apply()
      }

    Ok(user.toString)
  }}
}
