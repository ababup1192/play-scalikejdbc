package models

import com.google.inject.ImplementedBy
import javax.inject.Singleton
import scalikejdbc._

case class User(id: Long, name: String)

object User extends SQLSyntaxSupport[User] {
  override val schemaName: Option[String] = None
  override val tableName: String = "users"
  override lazy val columns: Seq[String] = Seq("id", "name")

  def apply(rn: ResultName[User])(rs: WrappedResultSet): User = autoConstruct(rs, rn)
}

@ImplementedBy(classOf[UserDaoImpl])
trait UserDao {
  def create(name: String)(implicit s: DBSession = AutoSession): Long

  def findAll()(implicit s: DBSession = AutoSession): Seq[User]

  def findById(id: Long)(implicit s: DBSession = AutoSession): Option[User]
}

class UserDaoImpl extends UserDao {
  private[this] val u = User.syntax("u")

  def create(name: String)(implicit s: DBSession = AutoSession): Long =
    sql"insert into users (name) values ($name)".updateAndReturnGeneratedKey().apply()

  def findAll()(implicit s: DBSession = AutoSession): Seq[User] = withSQL {
    select.from(User as u)
  }.map(User(u.resultName)).list().apply()

  def findById(id: Long)(implicit s: DBSession = AutoSession): Option[User] = withSQL {
    select.from(User as u).where.eq(u.id, id)
  }.map(User(u.resultName)).single().apply()
}
