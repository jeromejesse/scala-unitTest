import org.scalatest.FlatSpec
import org.scalamock.scalatest.MockFactory
import scala.concurrent.ExecutionContext.Implicits._

import scala.concurrent.Future

class ReferentielClientServiceSpec extends FlatSpec with MockFactory {

  "A ReferentielClient Service" should "get group with members in ldap" in {
    // Créer un mock de la classe ReferentielClientWrapper
    val mockReferentielClientWrapper = mock[ReferentielClientWrapper]
    // Créer un mock de la classe LdapWrapper
    val mockLdapWrapper = mock[LdapWrapper]
    // Créer un mock du service que l'on veut tester ici
    val mockService = new ReferentielClientService(mockReferentielClientWrapper, mockLdapWrapper)


    // Créer deux variables LdapGroup
    val ldapGroup1 = LdapGroup(1, "Communaute API", Seq("JEJ", "LLI", "A lot of other people"))
    val ldapGroup2 = LdapGroup(2, "Communaute Front", Seq("No body"))


    // Mocker l'appel getPersonne
    (mockReferentielClientWrapper.getPersonne(_: Int))
      .expects(*)
      .once()
      .returns(Future.successful(Personne("David", "Mencarelli", "12-12-1984", 1, 2)))

    // Mocker l'appel getGroups avec la variable id à 1
    (mockLdapWrapper.getGroups(_: Int))
      .expects(where {idGroup: Int => idGroup == 1 })
      .once()
      .returns(Future.successful(ldapGroup1))

    // Mocker l'appel getGroups avec la variable id à 2
    (mockLdapWrapper.getGroups(_: Int))
      .expects(where {idGroup: Int => idGroup == 2 })
      .once()
      .returns(Future.successful(ldapGroup2))


    // Tester le retour attendu par le service
    mockService.getPersonneWithGroupInformation(1).map(resultToTest => assert( resultToTest == Seq(
      ldapGroup1,
      ldapGroup2
    )) )
  }

}
