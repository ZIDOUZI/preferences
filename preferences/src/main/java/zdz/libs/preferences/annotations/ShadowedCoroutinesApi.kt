package zdz.libs.preferences.annotations

@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This API shadowed a coroutines function. " +
            "You should evaluate the risk of using this API."
)
@Retention(AnnotationRetention.BINARY)
annotation class ShadowedCoroutinesApi
