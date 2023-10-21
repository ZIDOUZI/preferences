package zdz.libs.preferences.annotations

@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This API is experimental and may be changed in the future."
)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalPrefApi
