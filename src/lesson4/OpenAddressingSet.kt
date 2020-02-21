package lesson4

import java.lang.StringBuilder

class OpenAddressingSet<T : Any>(private val bits: Int) : AbstractMutableSet<T>() {

    init {
        require(bits in 2..31)
    }

    private val capacity = 1 shl bits

    private val storage = Array<Any?>(capacity) { null }

    private val removed = Array(capacity) { false }

    override var size: Int = 0

    private var firstIndex: Int = 0

    private fun T.startingIndex(): Int {
        return hashCode() and (0x7FFFFFFF shr (31 - bits))
    }

    //трудоемкость - О(1)
    //ресурсоемкость - О(capacity)
    override fun contains(element: T): Boolean {
        var index = element.startingIndex()
        var current = storage[index]
        while (current != null) {
            if (current == element && !removed[index]) {
                return true
            }
            index = (index + 1) % capacity
            current = storage[index]
        }
        return false
    }

    //трудоемкость - О(1)
    //ресурсоемкость - О(capacity)
    override fun add(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {
            if (current == element && !removed[index]) {
                return false
            }
            index = (index + 1) % capacity
            check(index != startingIndex) { "Table is full" }
            current = storage[index]
        }
        storage[index] = element
        size++
        if (size == 1) {
            firstIndex = index
        }
        return true
    }

    /**
     * Для этой задачи пока нет тестов, но вы можете попробовать привести решение и добавить к нему тесты
     */
    //трудоемкость - О(1)
    //ресурсоемкость - О(capacity)
    override fun remove(element: T): Boolean {
        var index = element.startingIndex()
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                removed[index] = true
                size--
                return true
            }
            index = (index + 1) % capacity
            current = storage[index]
        }
        return false
    }

    /**
     * Для этой задачи пока нет тестов, но вы можете попробовать привести решение и добавить к нему тесты
     */
    override fun iterator(): MutableIterator<T> {
        return OpenAddressingIterator()
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as OpenAddressingSet<*>

        if (bits != other.bits) return false
        if (capacity != other.capacity) return false
        if (!storage.contentEquals(other.storage)) return false
        if (!removed.contentEquals(other.removed)) return false
        if (size != other.size) return false
        if (firstIndex != other.firstIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + bits
        result = 31 * result + capacity
        result = 31 * result + storage.contentHashCode()
        result = 31 * result + removed.contentHashCode()
        result = 31 * result + size
        result = 31 * result + firstIndex
        return result
    }

    //трудоемкость - О(1)
    //ресурсоемкость - О(capacity)
    override fun toString(): String {
        if (size == 0) return emptyArray<String>().toString()
        val sb = StringBuilder()
        sb.append("[")
        val iter = iterator()
        while (iter.hasNext()) {
            sb.append(" ${iter.next()},")
        }
        sb.deleteCharAt(sb.lastIndex)
        sb.append(" ]")
        return sb.toString()
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    private inner class OpenAddressingIterator<T> internal constructor() : MutableIterator<T> {
        private var currentIndex: Int = 0
        private var currentElement: Any? = null

        init {
            if (size > 0) {
                currentIndex = firstIndex
                currentElement = storage[firstIndex]
            }
        }

        //трудоемкость - О(1)
        //ресурсоемкость - О(1)
        override fun hasNext(): Boolean {
            if (currentIndex < 0 || currentIndex >= capacity) {
                currentIndex = 0
                return false
            }
            return !removed[currentIndex] && currentElement != null
        }

        //трудоемкость - О(1)
        //ресурсоемкость - О(1)
        override fun next(): T {
            var index = currentIndex
            val result = currentElement
            index = (index + 1) % capacity
            currentElement = storage[index]
            currentIndex = index
            return result as T
        }


        //трудоемкость - О(1)
        //ресурсоемкость - О(1)
        override fun remove() {
            removed[currentIndex] = true
            currentIndex = (currentIndex - 1) % capacity
            if (size > 0) {
                size--
            }
        }

    }


}