package boot

import command.domain.issue.{AssigneeName, LabelName}
import play.api.libs.json.{Json, Reads}

import scala.io.Source

case class Config(owner: String, repo: String, rId: String, gToken: String, zToken: String, as: Seq[AssigneeName], ignoreLs: Seq[LabelName])

object Config {
  private var x: Option[Config] = None

  private var dir: Option[String] = None

  def initialize(dir: String): Unit = this.dir = Some(dir)

  def apply(): Config = x.getOrElse(read)

  private def read: Config = {
    val dir = if (this.dir.isEmpty) throw new RuntimeException("config dir missing.") else this.dir.get

    val s1 = Source.fromFile(s"$dir/board.json")
    val s2 = Source.fromFile(s"$dir/personal.json")

    val board = Json.parse(s1.getLines.mkString).validate[Board].get
    val personal = Json.parse(s2.getLines.mkString).validate[Personal].get

    s1.close()
    s2.close()

    val x = Config(
      board.owner,
      board.repository,
      board.repositoryId,
      personal.githubToken,
      personal.zenhubToken,
      board.presetAssigneeNames.map(AssigneeName),
      board.ignoreLabelNamePrefix.map(LabelName)
    )

    this.x = Some(x)

    x
  }
}

case class Board(owner: String, repository: String, repositoryId: String, presetAssigneeNames: Seq[String], ignoreLabelNamePrefix: Seq[String])

object Board {
  implicit val jsonReads: Reads[Board] = Json.reads[Board]
}

case class Personal(githubToken: String, zenhubToken: String)

object Personal {
  implicit val jsonReads: Reads[Personal] = Json.reads[Personal]
}
