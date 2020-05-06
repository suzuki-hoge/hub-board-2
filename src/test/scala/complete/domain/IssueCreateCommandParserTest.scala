package complete.domain

import org.scalatest.FunSuite
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table

class IssueCreateCommandParserTest extends FunSuite {

  private val ls = Seq(Label("開発 - 機能", "#ff0000"), Label("開発 - リファクタ", "#00ff00"), Label("デプロイ - 本番", "#0000ff"))
  private val as = Seq(Assignee("jack", "https://..."), Assignee("jane", "https://..."))
  private val ps = Seq(PipelineNames("doing"), PipelineNames("reviewing"))
  private val ms = Seq(MilestoneName("spring 1"), MilestoneName("sprint 2"))

  //@formatter:off
  private val table = Table(
    ("s",                                                               "exp"),

    ("issue create",                                                    Seq(NoCandidate)),
    ("issue create -t 'メッセージを送る'",                              Seq(NoCandidate)),
    ("issue create -t 'メッセージを送る' -",                            Seq(OptionKey.b, OptionKey.l, OptionKey.a, OptionKey.p, OptionKey.m, OptionKey.e)),
    ("issue create -t 'メッセージを送る' -l",                           Seq(NoCandidate)),
    ("issue create -t 'メッセージを送る' -l '",                         Seq(Label("開発 - 機能", "#ff0000"), Label("開発 - リファクタ", "#00ff00"), Label("デプロイ - 本番", "#0000ff"))),
    ("issue create -t 'メッセージを送る' -l '開発",                     Seq(Label("開発 - 機能", "#ff0000"), Label("開発 - リファクタ", "#00ff00"))),
    ("issue create -t 'メッセージを送る' -l '開発 - リ",                Seq(Label("開発 - リファクタ", "#00ff00"))),
    ("issue create -t 'メッセージを送る' -l '開発 - 機能'",             Seq(NoCandidate)),

    ("issue create -t 'メッセージを送る' -a 'jack' -",                  Seq(OptionKey.b, OptionKey.l, OptionKey.a, OptionKey.p, OptionKey.m, OptionKey.e)),
    ("issue create -t 'メッセージを送る' -a 'jack' -a 'j",              Seq(Assignee("jane", "https://..."))),

    ("issue create -t 't' -b 'b' -l 'l' -a 'j' -p 'd' -m 's' -e '3' -", Seq(OptionKey.l, OptionKey.a)),

    ("issue",                                                           Seq())
  )
  //@formatter:on

  forAll(table) { (s: String, exp: Seq[Candidate]) =>
    assert(
      IssueCreateCommandParser(s, ls, as, ps, ms) == exp
    )
  }
}
