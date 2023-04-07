@main def main: Unit =
  val bitField = BitField(17)
  bitField(3) = true
  for (value <- bitField) {
    println(value)
  }
