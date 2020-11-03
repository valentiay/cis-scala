package seminars.seminar9

import cats.effect.concurrent.Ref
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

import scala.util.Random

object elections {
  type BallotBox = Map[String, List[String]]

  def putBallot(ballotBox: BallotBox, voter: String, candidate: String): Option[BallotBox] =
    if (ballotBox.values.exists(_.contains(voter))) {
      None
    } else {
      Some(
        ballotBox.updatedWith(candidate) {
          case Some(voters) => Some(voter :: voters)
          case None => Some(voter :: Nil)
        }
      )
    }

  def showResults(ballotBox: BallotBox): String = {
    val results = ballotBox.transform((_, voters) => voters.size).toList.sortBy(-_._2)
    s"""Winner: ${results.head._1}
       |
       |${results.map { case (candidate, count) => s"$candidate | $count" }.mkString("\n")}
       |
       |Total votes: ${results.map(_._2).sum}
    """.stripMargin
  }

  val candidates = List("Elmo", "Bert", "Ernie", "Count von Count")

  val genVoters: IO[List[(String, String)]] =
    IO(
      List
        .fill(100)(Random.alphanumeric.take(10).mkString)
        .map(voter => voter -> candidates(Random.nextInt(candidates.size)))
    )
}

import elections._

// Ref - примитив, представляющий собой атомарную ссылку. Ref обязательно хранит в себе какое-то значение
// Значение по этой ссылке можно модифицировать с помощью чистых функций.
// Основные методы
//   .set(a: A): F[Unit] - задать новое значение
//   .update(f: A => A): F[Unit] - для обычного обновления
//   .modify[B](f: A => (A, B)): F[B] - для обновления и возвращения какого-то значения из функции
//   .get: F[A] - прочитать значение
object ref extends IOApp {
  def vote(ref: Ref[IO, BallotBox])(voter: String, candidate: String): IO[Unit] =
    ref
      .updateMaybe(ballotBox => putBallot(ballotBox, voter, candidate))
      .flatMap(success => IO.raiseUnless(success)(new IllegalStateException(s"$voter already voted!")))

  def run(args: List[String]): IO[ExitCode] =
    for {
      ref <- Ref.of[IO, BallotBox](Map.empty)
      voters <- genVoters
      _ <- voters.parTraverse { case (voter, candidate) => vote(ref)(voter, candidate) }
      ballotBox <- ref.get
      _ <- IO(println(showResults(ballotBox)))
    } yield ExitCode.Success
}

@SuppressWarnings(Array("org.wartremover.warts.Var"))
object variable extends IOApp {
  type BallotBox = Map[String, List[String]]

  var ballotBox: BallotBox = Map.empty

  def vote(candidate: String, voter: String): IO[Unit] =
    putBallot(ballotBox, voter, candidate)
      .fold(IO.raiseError[Unit](new IllegalStateException(s"$voter already voted!"))) { newBallotBox =>
        IO.delay {
          ballotBox = newBallotBox
        }
      }

  def run(args: List[String]): IO[ExitCode] =
    for {
      voters <- genVoters
      _ <- voters.parTraverse { case (voter, candidate) => vote(candidate, voter) }
      _ <- IO(println(showResults(ballotBox)))
    } yield ExitCode.Success
}