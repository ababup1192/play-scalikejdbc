package repository

import scalikejdbc._

case class User(id: Long, name: String)

object User extends SQLSyntaxSupport[User] {
  override val schemaName: Option[String] = None
  override val tableName: String = "users"
  override lazy val columns: Seq[String] = Seq("id", "name")

  def apply(rn: ResultName[User])(rs: WrappedResultSet): User = autoConstruct(rs, rn)
}

object UserDao {
  private[this] val u = User.syntax("u")

  def create(name: String)(implicit s: DBSession = AutoSession): Long =
    withSQL {
      insertInto(User).values(null, name)
    }.updateAndReturnGeneratedKey().apply()

  def findById(id: Long)(implicit s: DBSession = AutoSession): Option[User] = withSQL {
    select.from(User as u).where.eq(u.id, id)
  }.map(User(u.resultName)).single().apply()
}
