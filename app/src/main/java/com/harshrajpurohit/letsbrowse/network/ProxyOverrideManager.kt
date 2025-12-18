package com.harshRajpurohit.letsBrowse.network

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.webkit.ProxyConfig
import androidx.webkit.ProxyController
import androidx.webkit.WebViewFeature

object ProxyOverrideManager {

    /**
     * Apply a SOCKS proxy override for WebView traffic only. This is a best-effort call and
     * depends on the device supporting [WebViewFeature.PROXY_OVERRIDE].
     */
    fun applyLocalSocksProxy(context: Context, port: Int = 10808): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            Toast.makeText(
                context,
                "Proxy override is not supported on this device",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        val proxyConfig = ProxyConfig.Builder()
            .addProxyRule("socks://127.0.0.1:$port")
            .build()

        ProxyController.getInstance().setProxyOverride(
            proxyConfig,
            ContextCompat.getMainExecutor(context)
        ) {
            Toast.makeText(
                context,
                "Using local proxy 127.0.0.1:$port",
                Toast.LENGTH_SHORT
            ).show()
        }

        return true
    }

    /**
     * Clear any active proxy overrides for WebView traffic.
     */
    fun clearProxy(context: Context): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            Toast.makeText(
                context,
                "Proxy override is not supported on this device",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        ProxyController.getInstance().clearProxyOverride(
            ContextCompat.getMainExecutor(context)
        ) {
            Toast.makeText(context, "Proxy disabled", Toast.LENGTH_SHORT).show()
        }

        return true
    }
}

