package command.domain.issue

import command.domain.issue.Helper._
import org.scalatest.FunSuite

class IssueTest extends FunSuite {

  val sut = Issue(1, "title_1", "body_2", "label_1", "assignee_1", "pipeline_1", "milestone_1", 3)

  test("copy - all specified") {
    val act = sut.copy("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 3)

    assert(
      act == CopyRequest(
        Creation("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 3)
      )
    )
  }

  test("copy - no specified") {
    val act = sut.copy("title_2", None, "", "", None, None, None)

    assert(
      act == CopyRequest(
        Creation("title_2", None, "label_1", "assignee_1", "pipeline_1", "milestone_1", 3)
      )
    )
  }

  test("cut - all specified") {
    val act = sut.cut("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 3)

    assert(
      act.right.get == CutRequest(
        Creation("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 3), 1, (1, 3), 1
      )
    )
  }

  test("cut - no specified") {
    val act = sut.cut("title_2", None, "", "", None, None, None)

    assert(
      act.right.get == CutRequest(
        Creation("title_2", None, "label_1", "assignee_1", "pipeline_1", "milestone_1", 0), 1, (1, 0), None
      )
    )
  }

  test("cut - failure") {
    val act = sut.cut("title_2", None, "", "", None, None, 4)

    assert(
      act.left.get == OriginEstimateIsLessThanNewEstimate
    )
  }

  test("crunch - all specified - total issue's estimate equals origin issue's estimate") {
    val act = sut.crunch(
      "base_title",
      Seq(
        ("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 1),
        ("title_3", "body_3", "label_2", "assignee_2", "pipeline_2", "milestone_2", 2)
      )
    )

    assert(
      act == CrunchRequests(
        Seq(
          CrunchRequest(Creation("base_title - title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 1), 1),
          CrunchRequest(Creation("base_title - title_3", "body_3", "label_2", "assignee_2", "pipeline_2", "milestone_2", 2), 1)
        ),
        (1, 3),
        1
      )
    )
  }

  test("crunch - no specified - total issue's estimate less than origin issue's estimate") {
    val act = sut.crunch(
      None,
      Seq(
        ("title_2", None, "", "", None, None, None),
        ("title_3", None, "", "", None, None, None)
      )
    )

    assert(
      act == CrunchRequests(
        Seq(
          CrunchRequest(Creation("title_2", None, "label_1", "assignee_1", "pipeline_1", "milestone_1", 0), 1),
          CrunchRequest(Creation("title_3", None, "label_1", "assignee_1", "pipeline_1", "milestone_1", 0), 1)
        ),
        (1, 0),
        None
      )
    )
  }

  test("crunch - all specified - total issue's estimate greater than origin issue's estimate") {
    val act = sut.crunch(
      "base_title",
      Seq(
        ("title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 2),
        ("title_3", "body_3", "label_2", "assignee_2", "pipeline_2", "milestone_2", 2)
      )
    )

    assert(
      act == CrunchRequests(
        Seq(
          CrunchRequest(Creation("base_title - title_2", "body_2", "label_2", "assignee_2", "pipeline_2", "milestone_2", 2), 1),
          CrunchRequest(Creation("base_title - title_3", "body_3", "label_2", "assignee_2", "pipeline_2", "milestone_2", 2), 1)
        ),
        (1, 4),
        1
      )
    )
  }
}

