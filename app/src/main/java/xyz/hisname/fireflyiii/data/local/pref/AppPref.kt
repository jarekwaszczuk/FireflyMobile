package xyz.hisname.fireflyiii.data.local.pref

import android.content.SharedPreferences
import androidx.core.content.edit

class AppPref(private val sharedPref: SharedPreferences): PreferenceHelper {

    override var baseUrl
        get() = sharedPref.getString("fireflyUrl", "") ?: ""
        set(value) = sharedPref.edit { putString("fireflyUrl", value) }

    override var isTransactionPersistent
        get() = sharedPref.getBoolean("persistent_notification", false)
        set(value) = sharedPref.edit { putBoolean("persistent_notification", value) }

    override var userRole
        get() = sharedPref.getString("userRole", "") ?: ""
        set(value) = sharedPref.edit { putString("userRole", value) }

    override var remoteApiVersion
        get() = sharedPref.getString("api_version", "") ?: ""
        set(value) = sharedPref.edit { putString("api_version", value) }

    override var serverVersion
        get() = sharedPref.getString("server_version", "") ?: ""
        set(value) = sharedPref.edit { putString("server_version", value) }

    override var userOs
        get() = sharedPref.getString("user_os", "") ?: ""
        set(value) = sharedPref.edit { putString("user_os", value) }

    override var certValue
        get() = sharedPref.getString("cert_value", "") ?: ""
        set(value) = sharedPref.edit { putString("cert_value", value) }

    override var enableCertPinning
        get() = sharedPref.getBoolean("enable_cert_pinning", false)
        set(value) = sharedPref.edit{ putBoolean("enable_cert_pinning", value)}

    override var languagePref: String
        get() = sharedPref.getString("language_pref", "") ?: ""
        set(value) = sharedPref.edit{ putString("language_pref", value)}

    override var nightModeEnabled: Boolean
        get() = sharedPref.getBoolean("night_mode", false)
        set(value) = sharedPref.edit { putBoolean("night_mode", value) }

    override var isKeyguardEnabled: Boolean
        get() = sharedPref.getBoolean("keyguard", false)
        set(value) = sharedPref.edit{ putBoolean("keyguard", value)}

    override var transactionListType: Boolean
        get() = sharedPref.getBoolean("transactionListType", false)
        set(value) = sharedPref.edit{ putBoolean("transactionListType", value)}

    override fun clearPref() = sharedPref.edit().clear().apply()
}