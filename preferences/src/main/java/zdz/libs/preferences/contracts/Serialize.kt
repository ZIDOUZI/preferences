package zdz.libs.preferences.contracts

interface Serializer<S, T> {
    fun serialize(s: S): T
    fun deserialize(t: T): S
    operator fun <R> times(other: Serializer<T, R>): Serializer<S, R> = Serializer(
        { other.serialize(serialize(it)) },
        { deserialize(other.deserialize(it)) }
    )

    companion object : Serializer<Any?, Any?> {
        override fun serialize(s: Any?): Any? = s
        override fun deserialize(t: Any?): Any? = t
    }
}

class Identity<T> : Serializer<T, T> {
    override fun serialize(s: T): T = s
    override fun deserialize(t: T): T = t
}

internal class SerializerImpl<S, T>(val convert: (S) -> T, val reverse: (T) -> S) :
    Serializer<S, T> {
    override fun serialize(s: S): T = convert(s)
    override fun deserialize(t: T): S = reverse(t)
    override fun <R> times(other: Serializer<T, R>): Serializer<S, R> =
        (other as? SerializerImpl)?.let {
            SerializerImpl({ other.convert(convert(it)) }, { reverse(other.reverse(it)) })
        } ?: SerializerImpl({ other.serialize(convert(it)) }, { reverse(other.deserialize(it)) })
}

fun <S, T> Serializer(convert: (S) -> T, reverse: (T) -> S): Serializer<S, T> =
    SerializerImpl(convert, reverse)