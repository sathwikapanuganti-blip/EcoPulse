/**
 * EcoPulse Speech Synthesis Engine
 * Provides audio read-aloud for kids, elders, and visually impaired users.
 * Uses browser's native window.speechSynthesis (Zero external dependencies).
 */

const VoiceAssistant = {
  isSpeaking: false,

  speak(text, lang = "en") {
    if (!("speechSynthesis" in window)) {
      alert("Voice narration is not supported on this browser.");
      return;
    }

    window.speechSynthesis.cancel(); // Stop any previous playback

    const utterance = new SpeechSynthesisUtterance(text);
    utterance.rate = 0.95; // Slightly slower for clarity for elders & kids
    utterance.pitch = 1.0;

    // Map language code to BCP-47 tags
    const langMap = {
      en: "en-US",
      hi: "hi-IN",
      te: "te-IN",
      es: "es-ES",
      fr: "fr-FR",
      de: "de-DE"
    };

    utterance.lang = langMap[lang] || "en-US";

    utterance.onstart = () => {
      this.isSpeaking = true;
      this.updateButtonUI(true);
    };

    utterance.onend = () => {
      this.isSpeaking = false;
      this.updateButtonUI(false);
    };

    utterance.onerror = () => {
      this.isSpeaking = false;
      this.updateButtonUI(false);
    };

    window.speechSynthesis.speak(utterance);
  },

  stop() {
    if ("speechSynthesis" in window) {
      window.speechSynthesis.cancel();
      this.isSpeaking = false;
      this.updateButtonUI(false);
    }
  },

  updateButtonUI(speaking) {
    const btn = document.getElementById("readAloudBtn");
    if (!btn) return;
    if (speaking) {
      btn.innerHTML = "⏹️ " + (t("stopVoice") || "Stop Reading");
      btn.classList.add("pulse-voice");
    } else {
      btn.innerHTML = "🔊 " + (t("listenBtn") || "Listen to Today's Report");
      btn.classList.remove("pulse-voice");
    }
  },

  readDashboardSummary(airData, scoreData, lang) {
    if (this.isSpeaking) {
      this.stop();
      return;
    }

    const loc = airData.locationName || "your area";
    const aqi = airData.aqi || 65;
    const status = airData.statusLabel || "Moderate";
    const temp = airData.temperature ? Math.round(airData.temperature) + " degrees Celsius" : "25 degrees";
    const score = scoreData ? scoreData.score : 75;

    let message = "";
    if (lang === "hi") {
      message = `नमस्ते। इको-पल्स में आपका स्वागत है। ${loc} में वर्तमान वायु गुणवत्ता सूचकांक ${aqi} है, जो कि ${status} श्रेणी में है। तापमान लगभग ${temp} है। आपके क्षेत्र का पर्यावरण स्वास्थ्य स्कोर 100 में से ${score} है। ${airData.healthRecommendation || ""}`;
    } else if (lang === "te") {
      message = `నమస్కారం! ఎకోపల్స్‌కు స్వాగతం. ${loc} లో గాలి నాణ్యత సూచిక ${aqi}, అంటే ${status}. ఉష్ణోగ్రత ${temp}. మీ కాలనీ పర్యావరణ స్కోరు ${score}. ఆరోగ్య సలహా: ${airData.healthRecommendation || ""}`;
    } else if (lang === "es") {
      message = `Hola. Bienvenido a EcoPulse. En ${loc}, el índice de calidad del aire es ${aqi}, considerado ${status}. La temperatura es de ${temp}. La puntuación ecológica de su vecindario es de ${score} sobre 100. ${airData.healthRecommendation || ""}`;
    } else if (lang === "fr") {
      message = `Bonjour. Bienvenue sur EcoPulse. À ${loc}, l'indice de qualité de l'air est de ${aqi}, niveau ${status}. La température est de ${temp}. Le score écologique de votre quartier est de ${score} sur 100. ${airData.healthRecommendation || ""}`;
    } else if (lang === "de") {
      message = `Hallo. Willkommen bei EcoPulse. In ${loc} beträgt der Luftqualitätsindex ${aqi}, Status ${status}. Die Temperatur liegt bei ${temp}. Der Öko-Score Ihres Bezirks liegt bei ${score} von 100.`;
    } else {
      message = `Hello and welcome to EcoPulse. In ${loc}, the current Air Quality Index is ${aqi}, which is rated as ${status}. Temperature is ${temp}. Your community EcoPulse score is ${score} out of 100. Health advice: ${airData.healthRecommendation || "Have a wonderful, healthy day!"}`;
    }

    this.speak(message, lang);
  }
};
