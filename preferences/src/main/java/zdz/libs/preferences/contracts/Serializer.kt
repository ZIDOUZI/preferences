package zdz.libs.preferences.contracts

interface Serializer<S, T> {
    fun serialize(s: S): T
    fun deserialize(t: T): S
}

fun <S, T> Serializer(convert: (S) -> T, reverse: (T) -> S) = object : Serializer<S, T> {
    override fun serialize(s: S): T = convert(s)
    override fun deserialize(t: T): S = reverse(t)
}