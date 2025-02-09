package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglercraftVersion;
import org.json.JSONObject;
import org.teavm.jso.JSObject;

import net.lax1dude.eaglercraft.internal.IClientConfigAdapter;
import net.lax1dude.eaglercraft.internal.IClientConfigAdapterHooks;
import net.lax1dude.eaglercraft.internal.teavm.opts.JSEaglercraftXOptsHooks;
import net.lax1dude.eaglercraft.internal.teavm.opts.JSEaglercraftXOptsRoot;

/**
 * Copyright (c) 2022-2024 lax1dude. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class TeaVMClientConfigAdapter implements IClientConfigAdapter {

	public static final IClientConfigAdapter instance = new TeaVMClientConfigAdapter();

	private String defaultLocale = "en_US";
	private String serverToJoin = null;   
	private String worldsDB = "worlds";
	private String resourcePacksDB = "resourcePacks";
	private JSONObject integratedServerOpts;
	private boolean checkGLErrors = false;
	private boolean checkShaderGLErrors = false;
	private boolean demoMode = EaglercraftVersion.forceDemoMode;
	private boolean isEnableDownloadOfflineButton = true;
	private String downloadOfflineButtonLink = null;
	private boolean useSpecialCursors = false;
	private boolean allowVoiceClient = true;
	private boolean allowFNAWSkins = true;
	private String localStorageNamespace = "_eaglercraftX";
	private final TeaVMClientConfigAdapterHooks hooks = new TeaVMClientConfigAdapterHooks();
	private boolean enableMinceraft = true;
	private boolean enableServerCookies = true;
	private boolean allowServerRedirects = true;
	private boolean crashOnUncaughtExceptions = false;
	private boolean openDebugConsoleOnLaunch = false;
	private boolean fixDebugConsoleUnloadListener = false;
	private boolean forceWebViewSupport = false;
	private boolean enableWebViewCSP = true;
	private boolean autoFixLegacyStyleAttr = false;
	private boolean showBootMenuOnLaunch = false;
	private boolean bootMenuBlocksUnsignedClients = false;
	private boolean allowBootMenu = true;
	private boolean forceProfanityFilter = false;
	private boolean forceWebGL1 = false;
	private boolean forceWebGL2 = false;
	private boolean allowExperimentalWebGL1 = true;
	private boolean useWebGLExt = true;
	private boolean useDelayOnSwap = false;
	private boolean useJOrbisAudioDecoder = false;
	private boolean useXHRFetch = false;
	private boolean useVisualViewport = true;
	private boolean deobfStackTraces = true;
	private boolean disableBlobURLs = false;
	private boolean eaglerNoDelay = false;
	private boolean ramdiskMode = false;
	private boolean singleThreadMode = false;
	private boolean enableEPKVersionCheck = true;

	public void loadNative(JSObject jsObject) {
		integratedServerOpts = new JSONObject();
		JSEaglercraftXOptsRoot eaglercraftXOpts = (JSEaglercraftXOptsRoot)jsObject;
		
		defaultLocale = eaglercraftXOpts.getLang("en_US");
		serverToJoin = eaglercraftXOpts.getJoinServer(null);
		worldsDB = eaglercraftXOpts.getWorldsDB("worlds");
		resourcePacksDB = eaglercraftXOpts.getResourcePacksDB("resourcePacks");
		checkGLErrors = eaglercraftXOpts.getCheckGLErrors(false);
		checkShaderGLErrors = eaglercraftXOpts.getCheckShaderGLErrors(false);
		demoMode = EaglercraftVersion.forceDemoMode || eaglercraftXOpts.getDemoMode(false);
		isEnableDownloadOfflineButton = eaglercraftXOpts.getEnableDownloadOfflineButton(true);
		downloadOfflineButtonLink = eaglercraftXOpts.getDownloadOfflineButtonLink(null);
		useSpecialCursors = eaglercraftXOpts.getHtml5CursorSupport(false);
		allowVoiceClient = eaglercraftXOpts.getAllowVoiceClient(true);
		allowFNAWSkins = !demoMode && eaglercraftXOpts.getAllowFNAWSkins(true);
		localStorageNamespace = eaglercraftXOpts.getLocalStorageNamespace(EaglercraftVersion.localStorageNamespace);
		enableMinceraft = eaglercraftXOpts.getEnableMinceraft(true);
		enableServerCookies = !demoMode && eaglercraftXOpts.getEnableServerCookies(true);
		allowServerRedirects = eaglercraftXOpts.getAllowServerRedirects(true);
		crashOnUncaughtExceptions = eaglercraftXOpts.getCrashOnUncaughtExceptions(false);
		openDebugConsoleOnLaunch = eaglercraftXOpts.getOpenDebugConsoleOnLaunch(false);
		fixDebugConsoleUnloadListener = eaglercraftXOpts.getFixDebugConsoleUnloadListener(false);
		forceWebViewSupport = eaglercraftXOpts.getForceWebViewSupport(false);
		enableWebViewCSP = eaglercraftXOpts.getEnableWebViewCSP(true);
		autoFixLegacyStyleAttr = eaglercraftXOpts.getAutoFixLegacyStyleAttr(true);
		showBootMenuOnLaunch = eaglercraftXOpts.getShowBootMenuOnLaunch(false);
		bootMenuBlocksUnsignedClients = eaglercraftXOpts.getBootMenuBlocksUnsignedClients(false);
		allowBootMenu = eaglercraftXOpts.getAllowBootMenu(!demoMode);
		forceProfanityFilter = eaglercraftXOpts.getForceProfanityFilter(false);
		forceWebGL1 = eaglercraftXOpts.getForceWebGL1(false);
		forceWebGL2 = eaglercraftXOpts.getForceWebGL2(false);
		allowExperimentalWebGL1 = eaglercraftXOpts.getAllowExperimentalWebGL1(true);
		useWebGLExt = eaglercraftXOpts.getUseWebGLExt(true);
		useDelayOnSwap = eaglercraftXOpts.getUseDelayOnSwap(false);
		useJOrbisAudioDecoder = eaglercraftXOpts.getUseJOrbisAudioDecoder(false);
		useXHRFetch = eaglercraftXOpts.getUseXHRFetch(false);
		useVisualViewport = eaglercraftXOpts.getUseVisualViewport(true);
		deobfStackTraces = eaglercraftXOpts.getDeobfStackTraces(true);
		disableBlobURLs = eaglercraftXOpts.getDisableBlobURLs(false);
		eaglerNoDelay = eaglercraftXOpts.getEaglerNoDelay(false);
		ramdiskMode = eaglercraftXOpts.getRamdiskMode(false);
		singleThreadMode = eaglercraftXOpts.getSingleThreadMode(false);
		enableEPKVersionCheck = eaglercraftXOpts.getEnableEPKVersionCheck(true);
		JSEaglercraftXOptsHooks hooksObj = eaglercraftXOpts.getHooks();
		if(hooksObj != null) {
			hooks.loadHooks(hooksObj);
		}
		
		integratedServerOpts.put("worldsDB", worldsDB);
		integratedServerOpts.put("demoMode", demoMode);
		integratedServerOpts.put("lang", defaultLocale);
		integratedServerOpts.put("allowVoiceClient", allowVoiceClient);
		integratedServerOpts.put("allowFNAWSkins", allowFNAWSkins);
		integratedServerOpts.put("crashOnUncaughtExceptions", crashOnUncaughtExceptions);
		integratedServerOpts.put("deobfStackTraces", deobfStackTraces);
		integratedServerOpts.put("disableBlobURLs", disableBlobURLs);
		integratedServerOpts.put("eaglerNoDelay", eaglerNoDelay);
		integratedServerOpts.put("ramdiskMode", ramdiskMode);
		integratedServerOpts.put("singleThreadMode", singleThreadMode);
	}

	public void loadJSON(JSONObject eaglercraftOpts) {
		integratedServerOpts = eaglercraftOpts;
		defaultLocale = eaglercraftOpts.optString("lang", "en_US");
		serverToJoin = eaglercraftOpts.optString("joinServer", null);
		worldsDB = eaglercraftOpts.optString("worldsDB", "worlds");
		resourcePacksDB = eaglercraftOpts.optString("resourcePacksDB", "resourcePacks");
		checkGLErrors = eaglercraftOpts.optBoolean("checkGLErrors", false);
		checkShaderGLErrors = eaglercraftOpts.optBoolean("checkShaderGLErrors", false);
		if(EaglercraftVersion.forceDemoMode) {
			eaglercraftOpts.put("demoMode", true);
		}
		demoMode = EaglercraftVersion.forceDemoMode || eaglercraftOpts.optBoolean("demoMode", false);
		isEnableDownloadOfflineButton = eaglercraftOpts.optBoolean("enableDownloadOfflineButton", true);
		downloadOfflineButtonLink = eaglercraftOpts.optString("downloadOfflineButtonLink", null);
		useSpecialCursors = eaglercraftOpts.optBoolean("html5CursorSupport", false);
		allowVoiceClient = eaglercraftOpts.optBoolean("allowVoiceClient", true);
		allowFNAWSkins = eaglercraftOpts.optBoolean("allowFNAWSkins", true);
		localStorageNamespace = eaglercraftOpts.optString("localStorageNamespace", EaglercraftVersion.localStorageNamespace);
		enableMinceraft = eaglercraftOpts.optBoolean("enableMinceraft", true);
		enableServerCookies = !demoMode && eaglercraftOpts.optBoolean("enableServerCookies", true);
		allowServerRedirects = eaglercraftOpts.optBoolean("allowServerRedirects", true);
		crashOnUncaughtExceptions = eaglercraftOpts.optBoolean("crashOnUncaughtExceptions", false);
		openDebugConsoleOnLaunch = eaglercraftOpts.optBoolean("openDebugConsoleOnLaunch", false);
		fixDebugConsoleUnloadListener = eaglercraftOpts.optBoolean("fixDebugConsoleUnloadListener", false);
		forceWebViewSupport = eaglercraftOpts.optBoolean("forceWebViewSupport", false);
		enableWebViewCSP = eaglercraftOpts.optBoolean("enableWebViewCSP", true);
		autoFixLegacyStyleAttr = eaglercraftOpts.optBoolean("autoFixLegacyStyleAttr", true);
		showBootMenuOnLaunch = eaglercraftOpts.optBoolean("showBootMenuOnLaunch", false);
		bootMenuBlocksUnsignedClients = eaglercraftOpts.optBoolean("bootMenuBlocksUnsignedClients", false);
		allowBootMenu = eaglercraftOpts.optBoolean("allowBootMenu", !demoMode);
		forceProfanityFilter = eaglercraftOpts.optBoolean("forceProfanityFilter", false);
		forceWebGL1 = eaglercraftOpts.optBoolean("forceWebGL1", false);
		forceWebGL2 = eaglercraftOpts.optBoolean("forceWebGL2", false);
		allowExperimentalWebGL1 = eaglercraftOpts.optBoolean("allowExperimentalWebGL1", true);
		useWebGLExt = eaglercraftOpts.optBoolean("useWebGLExt", true);
		useDelayOnSwap = eaglercraftOpts.optBoolean("useDelayOnSwap", false);
		useJOrbisAudioDecoder = eaglercraftOpts.optBoolean("useJOrbisAudioDecoder", false);
		useXHRFetch = eaglercraftOpts.optBoolean("useXHRFetch", false);
		useVisualViewport = eaglercraftOpts.optBoolean("useVisualViewport", true);
		deobfStackTraces = eaglercraftOpts.optBoolean("deobfStackTraces", true);
		disableBlobURLs = eaglercraftOpts.optBoolean("disableBlobURLs", false);
		eaglerNoDelay = eaglercraftOpts.optBoolean("eaglerNoDelay", false);
		ramdiskMode = eaglercraftOpts.optBoolean("ramdiskMode", false);
		singleThreadMode = eaglercraftOpts.optBoolean("singleThreadMode", false);
		enableEPKVersionCheck = eaglercraftOpts.optBoolean("enableEPKVersionCheck", true);
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	@Override
	public String getServerToJoin() {
		return serverToJoin;
	}

	@Override
	public String getWorldsDB() {
		return worldsDB;
	}

	@Override
	public boolean isCheckGLErrors() {
		return checkGLErrors;
	}

	@Override
	public String getLocalStorageNamespace() {
		return localStorageNamespace;
	}

	public boolean isFixDebugConsoleUnloadListenerTeaVM() {
		return fixDebugConsoleUnloadListener;
	}

	public boolean isAutoFixLegacyStyleAttrTeaVM() {
		return autoFixLegacyStyleAttr;
	}

	public boolean isForceWebGL1TeaVM() {
		return forceWebGL1;
	}

	public boolean isForceWebGL2TeaVM() {
		return forceWebGL2;
	}

	public boolean isAllowExperimentalWebGL1TeaVM() {
		return allowExperimentalWebGL1;
	}

	public boolean isUseWebGLExtTeaVM() {
		return useWebGLExt;
	}

	public boolean isUseDelayOnSwapTeaVM() {
		return useDelayOnSwap;
	}

	public boolean isUseJOrbisAudioDecoderTeaVM() {
		return useJOrbisAudioDecoder;
	}

	public boolean isUseXHRFetchTeaVM() {
		return useXHRFetch;
	}

	public boolean isDeobfStackTracesTeaVM() {
		return deobfStackTraces;
	}

	public boolean isUseVisualViewportTeaVM() {
		return useVisualViewport;
	}

	public boolean isDisableBlobURLsTeaVM() {
		return disableBlobURLs;
	}

	public boolean isSingleThreadModeTeaVM() {
		return singleThreadMode;
	}

	public boolean isEnableEPKVersionCheckTeaVM() {
		return enableEPKVersionCheck;
	}

	@Override
	public boolean isEaglerNoDelay() {
		return eaglerNoDelay;
	}

	@Override
	public boolean isRamdiskMode() {
		return ramdiskMode;
	}

	@Override
	public boolean isEnforceVSync() {
		return false;
	}

	@Override
	public IClientConfigAdapterHooks getHooks() {
		return hooks;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lang", defaultLocale);
		jsonObject.put("joinServer", serverToJoin);
		jsonObject.put("worldsDB", worldsDB);
		jsonObject.put("resourcePacksDB", resourcePacksDB);
		jsonObject.put("checkGLErrors", checkGLErrors);
		jsonObject.put("checkShaderGLErrors", checkShaderGLErrors);
		jsonObject.put("demoMode", demoMode);
		jsonObject.put("enableDownloadOfflineButton", isEnableDownloadOfflineButton);
		jsonObject.put("downloadOfflineButtonLink", downloadOfflineButtonLink);
		jsonObject.put("html5CursorSupport", useSpecialCursors);
		jsonObject.put("allowVoiceClient", allowVoiceClient);
		jsonObject.put("allowFNAWSkins", allowFNAWSkins);
		jsonObject.put("localStorageNamespace", localStorageNamespace);
		jsonObject.put("enableMinceraft", enableMinceraft);
		jsonObject.put("enableServerCookies", enableServerCookies);
		jsonObject.put("allowServerRedirects", allowServerRedirects);
		jsonObject.put("crashOnUncaughtExceptions", crashOnUncaughtExceptions);
		jsonObject.put("openDebugConsoleOnLaunch", openDebugConsoleOnLaunch);
		jsonObject.put("fixDebugConsoleUnloadListener", fixDebugConsoleUnloadListener);
		jsonObject.put("forceWebViewSupport", forceWebViewSupport);
		jsonObject.put("enableWebViewCSP", enableWebViewCSP);
		jsonObject.put("autoFixLegacyStyleAttr", autoFixLegacyStyleAttr);
		jsonObject.put("showBootMenuOnLaunch", showBootMenuOnLaunch);
		jsonObject.put("bootMenuBlocksUnsignedClients", bootMenuBlocksUnsignedClients);
		jsonObject.put("allowBootMenu", allowBootMenu);
		jsonObject.put("forceProfanityFilter", forceProfanityFilter);
		jsonObject.put("forceWebGL1", forceWebGL1);
		jsonObject.put("forceWebGL2", forceWebGL2);
		jsonObject.put("allowExperimentalWebGL1", allowExperimentalWebGL1);
		jsonObject.put("useWebGLExt", useWebGLExt);
		jsonObject.put("useDelayOnSwap", useDelayOnSwap);
		jsonObject.put("useJOrbisAudioDecoder", useJOrbisAudioDecoder);
		jsonObject.put("useXHRFetch", useXHRFetch);
		jsonObject.put("useVisualViewport", useVisualViewport);
		jsonObject.put("deobfStackTraces", deobfStackTraces);
		jsonObject.put("disableBlobURLs", disableBlobURLs);
		jsonObject.put("eaglerNoDelay", eaglerNoDelay);
		jsonObject.put("ramdiskMode", ramdiskMode);
		jsonObject.put("singleThreadMode", singleThreadMode);
		jsonObject.put("enableEPKVersionCheck", enableEPKVersionCheck);
		return jsonObject;
	}

	@Override
	public String toString() {
		return toJSONObject().toString();
	}

	public String toStringFormatted() {
		return toJSONObject().toString(4);
	}

}