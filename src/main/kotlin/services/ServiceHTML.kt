package services

open class Tag(val name: String) {
    private val children = mutableListOf<Tag>()
    protected fun <T : Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child)
    }

    override fun toString() =
        "<$name>${children.joinToString("")}</$name>"
}

class H1 : Tag("h1"){

}

class HR : Tag("hr"){

}
class P : Tag("p"){

}
class DIV : Tag("div"){

}
class UL : Tag("ul"){

}
class LI : Tag("li"){

}
