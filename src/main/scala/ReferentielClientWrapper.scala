import scala.concurrent.Future

class ReferentielClientWrapper {

  def getPersonne(id: Int): Future[Personne] = {
    Future.successful(Personne("David", "Mencarelli", "12-12-1984", 1, 2))
  }

}
