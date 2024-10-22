package top.alazeprt.lprank.util

enum class StorageMode {
    PREFIX,
    SUFFIX,
    CUSTOM;

    companion object {
        fun getMode(mode: String): StorageMode {
            return when (mode.lowercase()) {
                "prefix" -> PREFIX
                "suffix" -> SUFFIX
                else -> CUSTOM
            }
        }
    }
}