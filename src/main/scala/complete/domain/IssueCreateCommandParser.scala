package complete.domain

import scala.util.parsing.combinator.JavaTokenParsers

object IssueCreateCommandParser extends JavaTokenParsers {
  def apply(s: String, ls: Seq[Label], as: Seq[Assignee], ps: Seq[PipelineNames], ms: Seq[MilestoneName]): Seq[Candidate] = {
    //@formatter:off
    parseAll(command, s) match {
      case Success((_,   Hole(_)),                   _)   => Seq(NoCandidate)
      case Success((fos, KeyUnfixed(_)),             _)   => OptionKey.all.filter(k => !fos.map(_.k).contains(k) || k == OptionKey.l || k == OptionKey.a)
      case Success((_,   KeyFixed(_)),               _)   => Seq(NoCandidate)
      case Success((fos, ValWriting(OptionKey.l, v)),_) => ls.filter(l => l.value.startsWith(v.v) && !fos.filter(_.k == OptionKey.l).map(_.v.v).contains(l.value))
      case Success((fos, ValWriting(OptionKey.a, v)),_) => as.filter(a => a.value.startsWith(v.v) && !fos.filter(_.k == OptionKey.a).map(_.v.v).contains(a.value))
      case Success((_,   ValWriting(OptionKey.p, v)),_) => ps.filter(_.value.startsWith(v.v))
      case Success((_,   ValWriting(OptionKey.m, v)),_) => ms.filter(_.value.startsWith(v.v))
      case Success((_,   ValWriting(_, _)),          _) => Seq(NoCandidate)
      case _                                            => Seq()
    }
    //@formatter:on
  }

  private def command: Parser[(Seq[FixedOption], UnfixedOption)] = {
    "issue" ~ "create" ~> rep(fixedOption) ~ unfixedOption ^^ { case fos ~ uo => (fos, uo) }
  }

  private def fixedOption: Parser[FixedOption] = {
    //@formatter:off
    "-t"  ~ "'" ~> words <~ "'" ^^ (x => FixedOption(OptionKey.t,  OptionVal(x))) |
    "-b"  ~ "'" ~> words <~ "'" ^^ (x => FixedOption(OptionKey.b,  OptionVal(x))) |
    "-l"  ~ "'" ~> words <~ "'" ^^ (x => FixedOption(OptionKey.l,  OptionVal(x))) |
    "-a"  ~ "'" ~> words <~ "'" ^^ (x => FixedOption(OptionKey.a,  OptionVal(x))) |
    "-p"  ~ "'" ~> words <~ "'" ^^ (x => FixedOption(OptionKey.p,  OptionVal(x))) |
    "-m"  ~ "'" ~> words <~ "'" ^^ (x => FixedOption(OptionKey.m,  OptionVal(x))) |
    "-e"  ~ "'" ~> num   <~ "'" ^^ (x => FixedOption(OptionKey.e,  OptionVal(x)))
    //@formatter:on
  }

  private def words: Parser[String] = "[^']+".r

  private def num: Parser[String] = "0" | "[1-9][0-9]*".r | "[0-9]\\.[0-9]+".r | "[0-9]\\.".r

  private def unfixedOption: Parser[UnfixedOption] = {
    val kp = "t" ^^ (_ => OptionKey.t) |
      "b" ^^ (_ => OptionKey.b) | "l" ^^ (_ => OptionKey.l) |
      "a" ^^ (_ => OptionKey.a) | "p" ^^ (_ => OptionKey.p) |
      "m" ^^ (_ => OptionKey.m) | "e" ^^ (_ => OptionKey.e)

    //@formatter:off
    "-" ~> kp ~ "'" ~ (words | num) ^^ { case k ~ _ ~ v => ValWriting(k, OptionVal(v))  } | // -l 'labe
    "-" ~> kp <~ "'"                ^^ {      k         => ValWriting(k, OptionVal("")) } | // -l '
    "-" ~> kp                       ^^ KeyFixed |                                           // -l
    "-"                             ^^ KeyUnfixed |                                         // -
    ""                              ^^ Hole                                                 //
    //@formatter:on
  }
}
