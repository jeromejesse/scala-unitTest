import scala.concurrent.Future

case class LdapGroup(id: Int, nom: String, membres: Seq[String])


class LdapWrapper {
  def getGroups(id: Int): Future[LdapGroup] =
    Future.successful(LdapGroup(1, "Communaute API", Seq("JEJ", "LLI", "A lot of other people") ))
}
