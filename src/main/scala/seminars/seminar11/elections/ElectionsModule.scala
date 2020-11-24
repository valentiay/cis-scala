package seminars.seminar11.elections

import cats.effect.IO
import io.finch._

class ElectionsModule(implicit service: ElectionsService) extends Endpoint.Module[IO] {

  /*_*/
  val putVoter: Endpoint[IO, Unit] =
    put(
      "candidate" ::
        path[String] ::
        "voter" ::
        path[String]
    )(service.putVoter _)

  val getVoters: Endpoint[IO, List[String]] =
    get(
      "candidate" ::
        path[String] ::
        "voters"
    )(service.getVoters _)

  val getCandidates: Endpoint[IO, List[String]] =
    get("candidates")(service.getCandidates)

  val getElections: Endpoint[IO, List[(String, Int)]] =
    get(root)(service.getElections)

  val api = path("elections") :: (putVoter :+: getVoters :+: getCandidates :+: getElections)

}
