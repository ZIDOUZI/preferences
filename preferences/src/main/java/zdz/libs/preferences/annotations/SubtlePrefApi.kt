package zdz.libs.preferences.annotations

@RequiresOptIn(
    "This API is subtle, you should know what you are doing clearly."
)
@Retention(AnnotationRetention.BINARY)
annotation class SubtlePrefApi
