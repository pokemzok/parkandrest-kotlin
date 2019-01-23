package parkandrest.parkingmanagement.core.extensions

internal class NullableExtensions

fun <T : Any> T?.orElseThrow(runtimeException: RuntimeException): T {
    if (this != null) {
        return this
    } else {
        throw runtimeException
    }
}