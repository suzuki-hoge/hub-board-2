package command.api.issue

import command.domain.issue._
import command.domain.milestone.MilestoneName

import scala.util.matching.Regex
import scala.util.parsing.combinator.JavaTokenParsers

object Validator extends JavaTokenParsers {
  def formatV(s: String, prefix: String): Either[String, Unit] = {
    def p(prefix: String): Parser[Unit] = prefix ~ rep(("-t" | "-b" | "-l" | "-a" | "-p" | "-m" | "-e") ~ "'" ~ "[^']+".r ~ "'") ^^ (_ => ())

    parseAll(p(prefix), s) match {
      //@formatter:off
      case Success(_, _) => Right(())
      case _             => Left("validation error: invalid format")
      //@formatter:on
    }
  }

  def tV(s: String): Either[String, Title] = {
    new Regex("-t '([^']+)'", "val").findAllIn(s).matchData.map(_.group("val")).toSeq match {
      //@formatter:off
      case Seq(v)  => Right(Title(v))
      case Seq()   => Left("validation error: no title")
      case _       => Left("validation error: dup titles")
      //@formatter:on
    }
  }

  def bV(s: String): Either[String, Option[Body]] = {
    new Regex("-b '([^']+)'", "val").findAllIn(s).matchData.map(_.group("val")).toSeq match {
      //@formatter:off
      case Seq(v) => Right(Some(Body(v)))
      case Seq()  => Right(None)
      case _      => Left("dup bodies")
      //@formatter:on
    }
  }

  def lsV(s: String): Either[String, LabelNames] = {
    new Regex("-l '([^']+)'", "val").findAllIn(s).matchData.map(_.group("val")).toSeq.map(LabelName) match {
      //@formatter:off
      case ls if ls.length == ls.distinct.length => Right(LabelNames(ls))
      case _                                     => Left("validation error: dup labels")
      //@formatter:on
    }
  }

  def asV(s: String): Either[String, AssigneeNames] = {
    new Regex("-a '([^']+)'", "val").findAllIn(s).matchData.map(_.group("val")).toSeq.map(AssigneeName) match {
      //@formatter:off
      case as if as.length == as.distinct.length => Right(AssigneeNames(as))
      case _                                     => Left("dup assignees")
      //@formatter:on
    }
  }

  def pV(s: String): Either[String, Option[PipelineId]] = {
    new Regex("-p '([^']+)'", "val").findAllIn(s).matchData.map(_.group("val")).toSeq match {
      //@formatter:off
      case Seq(v) => Right(Some(PipelineId(v)))
      case Seq()  => Right(None)
      case _      => Left("dup pipelines")
      //@formatter:on
    }
  }

  def mV(s: String): Either[String, Option[MilestoneName]] = {
    new Regex("-m '([^']+)'", "val").findAllIn(s).matchData.map(_.group("val")).toSeq match {
      //@formatter:off
      case Seq(v) => Right(Some(MilestoneName(v)))
      case Seq()  => Right(None)
      case _      => Left("dup milestones")
      //@formatter:on
    }
  }

  def eV(s: String): Either[String, Option[Estimate]] = {
    new Regex("-e '([^']+)'", "val").findAllIn(s).matchData.map(_.group("val")).toSeq match {
      //@formatter:off
      case Seq(v) if v.toFloatOption.isEmpty => Left("validation error: not number format estimate")
      case Seq(v)                            => Right(Some(Estimate(v.toFloat)))
      case Seq()                             => Right(None)
      case _                                 => Left("validation error: dup estimates")
      //@formatter:on
    }
  }
}
