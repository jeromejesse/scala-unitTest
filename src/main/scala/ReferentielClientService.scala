import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

case class Personne(nom: String, prenom: String, dateNaissance: String, idGroup1: Int, idGroup2: Int)


class ReferentielClientService @Inject()(referentielClientWrapper: ReferentielClientWrapper, ldapWrapper: LdapWrapper) {

    def getPersonneWithGroupInformation(id: Int): Future[Seq[LdapGroup]] = {
      referentielClientWrapper.getPersonne(id).flatMap(personne => {
        Future.sequence(Seq(
          ldapWrapper.getGroups(personne.idGroup1),
          ldapWrapper.getGroups(personne.idGroup2)
        ))
      })
    }
}
