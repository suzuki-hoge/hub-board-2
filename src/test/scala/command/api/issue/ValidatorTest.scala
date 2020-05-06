package command.api.issue

import command.domain.issue._
import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table

class ValidatorTest extends FunSuite {
  //@formatter:off
  private val format = Table(
    ("s",                        "exp"),
    ("issue create -t 'title'",  Right(())),
    ("issue create - t 'title'", Left("validation error: invalid format")),
    ("issue create - t 'title",  Left("validation error: invalid format")),
    ("issue create -t",          Left("validation error: invalid format")),
    ("issue create -",           Left("validation error: invalid format")),
    ("issue cut -t 'title'",     Left("validation error: invalid format"))
  )
  //@formatter:on

  forAll(format) { (s: String, exp: Either[String, Unit]) =>
    assert(
      Validator.formatV(s, "issue create") == exp
    )
  }

  //@formatter:off
  private val t = Table(
    ("s",                                                "exp"),
    ("issue create -t 'send message'",                   Right(Title("send message"))),
    ("issue create -a 'jack'",                           Left("validation error: no title")),
    ("issue create -t 'send message' -t 'send message'", Left("validation error: dup titles")),
  )
  //@formatter:on

  forAll(t) { (s: String, exp: Either[String, Title]) =>
    assert(
      Validator.tV(s) == exp
    )
  }

  //@formatter:off
  private val ls = Table(
    ("s",                                      "exp"),
    ("issue create -l 'feature'",              Right(LabelNames(Seq(LabelName("feature"))))),
    ("issue create -a 'jack'",                 Right(LabelNames(Seq()))),
    ("issue create -l 'feature' -l 'review'",  Right(LabelNames(Seq(LabelName("feature"), LabelName("review"))))),
    ("issue create -l 'feature' -l 'feature'", Left("validation error: dup labels"))
  )
  //@formatter:on

  forAll(ls) { (s: String, exp: Either[String, LabelNames]) =>
    assert(
      Validator.lsV(s) == exp
    )
  }

  //@formatter:off
  private val e = Table(
    ("s",                          "exp"),
    ("issue create -e '1'",        Right(Some(Estimate(1.0f)))),
    ("issue create -e '0.5'",      Right(Some(Estimate(0.5f)))),
    ("issue create -e 'feature'",  Left("validation error: not number format estimate")),
    ("issue create -e '1' -e '2'", Left("validation error: dup estimates")),
  )
  //@formatter:on

  forAll(e) { (s: String, exp: Either[String, Option[Estimate]]) =>
    assert(
      Validator.eV(s) == exp
    )
  }
}
