package seminars.seminar11.elections

import cats.effect.IO
import cats.effect.concurrent.Ref
import io.finch._
import seminars.seminar11.elections.ElectionsServiceImpl._

trait ElectionsService {
  def putVoter(candidate: String, voter: String): IO[Output[Unit]]

  def getVoters(candidate: String): IO[Output[List[String]]]

  def getCandidates: IO[Output[List[String]]]

  def getElections: IO[Output[List[(String, Int)]]]
}

final class ElectionsServiceImpl(cfg: ElectionsConfig, ref: Ref[IO, BallotBox]) extends ElectionsService {

  private def validateCandidate(candidate: String): IO[Unit] =
    IO.raiseUnless(cfg.candidates.contains(candidate))(new IllegalArgumentException(s"$candidate is not a valid candidate"))

  def putVoter(candidate: String, voter: String): IO[Output[Unit]] =
    validateCandidate(candidate) *>
      ref.updateMaybe(ballotBox => putBallot(ballotBox, voter, candidate)).flatMap {
        case true =>
          IO.pure(NoContent[Unit])
        case false =>
          IO.raiseError(new IllegalArgumentException(s"$voter already voted for another candidate"))
      }

  def getVoters(candidate: String): IO[Output[List[String]]] =
    validateCandidate(candidate) *>
      ref.get.map(ballotBox => Ok(ballotBox.getOrElse(candidate, Nil)))

  def getCandidates: IO[Output[List[String]]] =
    IO.pure(Ok(cfg.candidates))

  def getElections: IO[Output[List[(String, Int)]]] =
    ref.get.map(ballotBox => Ok(ballotBox.transform((_, voters) => voters.size).toList))
}

object ElectionsServiceImpl {
  def apply(cfg: ElectionsConfig): IO[ElectionsServiceImpl] =
    Ref.of[IO, BallotBox](Map.empty).map(ref =>
      new ElectionsServiceImpl(cfg, ref)
    )

  type BallotBox = Map[String, List[String]]

  def putBallot(ballotBox: BallotBox, voter: String, candidate: String): Option[BallotBox] =
    ballotBox.find { case (_, voters) => voters.contains(voter) } match {
      case None =>
        Some(
          ballotBox.updatedWith(candidate) {
            case Some(voters) => Some(voter :: voters)
            case None => Some(voter :: Nil)
          }
        )
      case Some((boxCandidate, _)) if boxCandidate == candidate => Some(ballotBox)
      case _ => None
    }

}
