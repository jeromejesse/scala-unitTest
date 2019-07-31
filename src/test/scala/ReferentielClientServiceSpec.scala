import org.scalatest.FlatSpec
import org.scalamock.scalatest.MockFactory
import scala.concurrent.ExecutionContext.Implicits._

import scala.concurrent.Future

class ReferentielClientServiceSpec extends FlatSpec with MockFactory {

  "A ReferentielClient Service" should "get group with members in ldqp" in {
    val mockReferentielClientWrapper = mock[ReferentielClientWrapper]
    val mockLdapWrapper = mock[LdapWrapper]
    val mockService = new ReferentielClientService(mockReferentielClientWrapper, mockLdapWrapper)

    val ldapGroup1 = LdapGroup(1, "Communaute API", Seq("JEJ", "LLI", "A lot of other people"))
    val ldapGroup2 = LdapGroup(2, "Communaute Front", Seq("No body"))

    (mockReferentielClientWrapper.getPersonne(_: Int))
      .expects(*)
      .once()
      .returns(Future.successful(Personne("David", "Mencarelli", "12-12-1984", 1, 2)))

    (mockLdapWrapper.getGroups(_: Int))
      .expects(where {idGroup: Int => idGroup == 1 })
      .once()
      .returns(Future.successful(
        LdapGroup(1, "Communaute API", Seq("JEJ", "LLI", "A lot of other people"))
      ))

    (mockLdapWrapper.getGroups(_: Int))
      .expects(where {idGroup: Int => idGroup == 2 })
      .once()
      .returns(Future.successful(
        LdapGroup(2, "Communaute Front", Seq("No body"))
      ))

    mockService.getPersonneWithGroupInformation(1).map(resultToTest => assert( resultToTest == Seq(
      ldapGroup1,
      ldapGroup2
    )) )
  }

}
