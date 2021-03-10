package com.example.miniweibo.common.span

class SpanClickableSpan : ClickableSpanNoUnderline {
    var urlString: String? = null

    constructor(color: Int, onClickListener: OnClickListener?) : super(color, onClickListener) {}

    constructor(onClickListener: OnClickListener?) : super(onClickListener!!) {}
}
