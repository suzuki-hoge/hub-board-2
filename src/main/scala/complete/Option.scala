package complete

case class FixedOption(k: OptionKey, v: OptionVal)

case class OptionVal(v: String)

sealed trait UnfixedOption

case class Hole(x:Any) extends UnfixedOption

case class KeyUnfixed(x: Any) extends UnfixedOption

case class KeyFixed(k: OptionKey) extends UnfixedOption

case class ValWriting(k: OptionKey, v: OptionVal) extends UnfixedOption

