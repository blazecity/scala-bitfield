class BitField(override val size: Int, initialValue: Boolean = false) extends Iterable[Boolean]:
  private var _bitField = new Array[Byte](if this.size % 8 == 0 then size / 8 else size / 8 + 1)

  def apply(index: Int): Option[Boolean] = 
    if index >= this.size then return None
    val num = this._bitField(index / 8).toInt & (1 << index % 8)
    if num == 0 then Some(false) else Some(true) 
  end apply

  def update(index: Int, value: Boolean): Boolean = 
    if index >= this.size then return false

    val arrayIndex = index / 8
    val subIndex = index % 8
    var num = this._bitField(arrayIndex).toInt

    if value then num |= (1 << subIndex) else num &= ~(1 << subIndex)         
    this._bitField(arrayIndex) = num.toByte

    true
  end update

  override def iterator: Iterator[Boolean] = new Iterator[Boolean] {
    var currentIndex = 0

    override def hasNext: Boolean = this.currentIndex < BitField.this.size

    override def next(): Boolean = 
        val nextValue = apply(currentIndex) match
            case Some(bool) => bool
            case _ => false

        this.currentIndex += 1
        nextValue
    end next

  }


  