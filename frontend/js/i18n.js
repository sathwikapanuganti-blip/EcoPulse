/**
 * EcoPulse Multi-Language Translation System (i18n)
 * Supports: English (en), Hindi (hi), Telugu (te), Spanish (es), French (fr), German (de)
 * Specially designed for all age groups: from kids to grandparents!
 */

const translations = {
  en: {
    appName: "EcoPulse",
    tagline: "Monitor. Understand. Act. Together for a Greener Future.",
    purposeBadge: "🌍 Made for Everyone: Kids, Families, Elders & Communities",
    whyEcoPulseBtn: "ℹ️ Why EcoPulse? Read Our Purpose",
    navHome: "Home",
    navLiveEnv: "Live Air & Weather",
    navNearby: "Nearby Pollution",
    navTraffic: "Traffic & Pollution",
    navFood: "Food Waste",
    navWater: "Water Waste",
    navCommunity: "Community",
    navImpact: "My Eco Impact",
    navTips: "Eco Tips",
    navReports: "All Reports",
    navProfile: "Profile",
    navAdmin: "Admin Portal",
    modeStandard: "📊 Standard View (Detailed)",
    modeSimple: "🧒 Elder & Kid View (Simple & Easy)",
    listenBtn: "🔊 Listen to Today's Report",
    locationDetecting: "Detecting your location...",
    useMyLocation: "📍 Use My GPS Location",
    searchCityPlaceholder: "Search city (e.g., Delhi, Mumbai, London, New York...)",
    searchBtn: "Search",
    
    // Purpose explainer
    purposeTitle: "What is EcoPulse and Why Does It Exist?",
    purposeSubtitle: "A friendly community platform to protect the air we breathe, the water we drink, and the food we share.",
    purposePillar1Title: "1. Know Your Air & Weather",
    purposePillar1Desc: "Instantly check if it's safe to play outdoors, go for a morning walk, or if you should wear a mask.",
    purposePillar2Title: "2. Stop Waste in Your Neighborhood",
    purposePillar2Desc: "Spot a leaking tap, overflowing tank, or extra food after an event? Report it in 10 seconds so help arrives.",
    purposePillar3Title: "3. Build Greener Habits Together",
    purposePillar3Desc: "Track your personal eco points and help your apartment, school, or office become the greenest community.",
    closeBtn: "Close",

    // Dashboard Cards
    aqiCardTitle: "Air Quality Index (AQI)",
    standardUsed: "Standard: US EPA Standard (0-500)",
    temperature: "Temperature",
    humidity: "Humidity",
    lastUpdated: "Last updated",
    safeLabel: "Safe & Fresh",
    moderateLabel: "Moderate (Fair)",
    poorLabel: "Poor (Unhealthy)",
    veryPoorLabel: "Very Poor (Hazardous)",
    hazardousLabel: "Emergency Hazard",
    
    // EcoPulse Score
    ecoScoreTitle: "EcoPulse Score",
    ecoScoreDesc: "Overall Environmental Health of Your Neighborhood (0-100)",
    transparentFormulaTitle: "How is this score calculated? (Transparent Backend Formula)",
    
    // Traffic
    trafficTitle: "Traffic & Pollution Risk",
    congestionLevel: "Congestion Level",
    trafficIntensity: "Traffic Intensity",
    trafficPollutionRisk: "Pollution Impact",
    
    // Waste summaries
    foodWasteSummaryTitle: "Food Waste Sharing & Reduction",
    foodWasteSub: "Surplus meals saved & available for community donation",
    waterWasteSummaryTitle: "Water Waste & Leakage Tracking",
    waterWasteSub: "Active leaks reported by citizens to prevent clean water loss",
    
    // Buttons & CTAs
    reportProblemBtn: "🚨 Report an Environmental Problem",
    logFoodWasteBtn: "🍲 Post Surplus / Report Food Waste",
    logWaterWasteBtn: "💧 Report Water Leak / Wastage",
    logImpactBtn: "+ Log My Green Action",
    viewAllReportsBtn: "View All Reports on Map",
    
    // Simple Mode Labels
    simpleKidTipTitle: "Kid-Friendly Explorer Note",
    simpleElderTipTitle: "Grandparent Health Reminder",
    simpleKidExplanation: "PM2.5 are tiny dust specks smaller than a hair! When the smiley is green 😊, it is super safe to play football outside!",
    simpleElderExplanation: "Air is comfortable today. Morning walks before 8:00 AM are recommended. Stay well hydrated and carry light water."
  },

  hi: {
    appName: "इको-पल्स (EcoPulse)",
    tagline: "निगरानी करें। समझें। कदम उठाएं। एक हरित भविष्य के लिए मिलकर काम करें।",
    purposeBadge: "🌍 हर किसी के लिए: बच्चे, बुजुर्ग, परिवार और समाज",
    whyEcoPulseBtn: "ℹ️ इको-पल्स क्यों? हमारा उद्देश्य जानें",
    navHome: "होम",
    navLiveEnv: "वायु और मौसम",
    navNearby: "आसपास का प्रदूषण",
    navTraffic: "यातायात और प्रदूषण",
    navFood: "भोजन बर्बादी",
    navWater: "जल बर्बादी",
    navCommunity: "हमारा समाज",
    navImpact: "मेरा इको प्रभाव",
    navTips: "पर्यावरण सुझाव",
    navReports: "सभी रिपोर्टें",
    navProfile: "प्रोफ़ाइल",
    navAdmin: "व्यवस्थापक पोर्टल",
    modeStandard: "📊 विस्तृत दृश्य (Standard)",
    modeSimple: "🧒 बुजुर्ग व बच्चों के लिए सरल दृश्य (Simple)",
    listenBtn: "🔊 आज की रिपोर्ट सुनें",
    locationDetecting: "आपका स्थान खोजा जा रहा है...",
    useMyLocation: "📍 मेरा स्थान प्रयोग करें",
    searchCityPlaceholder: "शहर खोजें (जैसे: दिल्ली, मुंबई, लखनऊ, पटना...)",
    searchBtn: "खोजें",

    purposeTitle: "इको-पल्स क्या है और यह क्यों महत्वपूर्ण है?",
    purposeSubtitle: "सांस लेने वाली हवा, पीने का पानी और भोजन बचाने का एक सरल और सशक्त सामुदायिक मंच।",
    purposePillar1Title: "1. अपने पर्यावरण को जानें",
    purposePillar1Desc: "तुरंत जानें कि क्या बाहर खेलना या सुबह की सैर सुरक्षित है, या मास्क पहनना जरूरी है।",
    purposePillar2Title: "2. अपने इलाके में बर्बादी रोकें",
    purposePillar2Desc: "नल से टपकता पानी, बहता टैंक या बचा हुआ खाना देखें तो तुरंत रिपोर्ट करें।",
    purposePillar3Title: "3. मिलकर हरित भविष्य बनाएं",
    purposePillar3Desc: "अपने दैनिक पर्यावरण प्रयासों को ट्रैक करें और अपनी कॉलोनी या कॉलेज को सबसे स्वच्छ बनाएं।",
    closeBtn: "बंद करें",

    aqiCardTitle: "वायु गुणवत्ता सूचकांक (AQI)",
    standardUsed: "मानक: US EPA मानक (0-500)",
    temperature: "तापमान",
    humidity: "नमी (आर्द्रता)",
    lastUpdated: "अंतिम अपडेट",
    safeLabel: "सुरक्षित और स्वच्छ",
    moderateLabel: "मध्यम (संतोषजनक)",
    poorLabel: "खराब (अस्वास्थ्यकर)",
    veryPoorLabel: "अत्यधिक खराब",
    hazardousLabel: "आपातकालीन खतरनाक",

    ecoScoreTitle: "इको-पल्स स्कोर",
    ecoScoreDesc: "आपके इलाके का समग्र पर्यावरण स्वास्थ्य स्कोर (0-100)",
    transparentFormulaTitle: "यह स्कोर कैसे निकाला गया? (पारदर्शी फॉर्मूला)",

    trafficTitle: "यातायात और प्रदूषण स्तर",
    congestionLevel: "जाम का स्तर",
    trafficIntensity: "गाड़ियों का दबाव",
    trafficPollutionRisk: "प्रदूषण प्रभाव",

    foodWasteSummaryTitle: "भोजन बर्बादी निवारण",
    foodWasteSub: "बचा हुआ भोजन जो दान और वितरण के लिए उपलब्ध है",
    waterWasteSummaryTitle: "जल बर्बादी व रिसाव रिपोर्ट",
    waterWasteSub: "पीने के पानी को व्यर्थ बहने से बचाने के लिए नागरिकों द्वारा दर्ज समस्याएं",

    reportProblemBtn: "🚨 पर्यावरण समस्या दर्ज करें",
    logFoodWasteBtn: "🍲 भोजन बचाएं / बर्बादी दर्ज करें",
    logWaterWasteBtn: "💧 पानी का रिसाव रिपोर्ट करें",
    logImpactBtn: "+ मेरा पर्यावरण कार्य जोड़ें",
    viewAllReportsBtn: "नक्शे पर सभी रिपोर्ट देखें",

    simpleKidTipTitle: "बच्चों के लिए आसान जानकारी",
    simpleElderTipTitle: "बुजुर्गों के लिए स्वास्थ्य सुझाव",
    simpleKidExplanation: "PM2.5 बाल से भी छोटे धूल के कण होते हैं! जब स्माइली हरी 😊 हो, तो पार्क में खेलना बिल्कुल सुरक्षित है!",
    simpleElderExplanation: "आज हवा सामान्य है। सुबह 8 बजे से पहले टहलना सबसे अच्छा रहेगा। पानी भरपूर पिएं।"
  },

  te: {
    appName: "ఎకోపల్స్ (EcoPulse)",
    tagline: "గమనించండి. అర్థం చేసుకోండి. మార్పు తీసుకురండి. పచ్చని భవిష్యత్తు కోసం.",
    purposeBadge: "🌍 అందరికీ ఉపయోగపడే వేదిక: పిల్లలు, పెద్దలు మరియు కాలనీలు",
    whyEcoPulseBtn: "ℹ️ ఎకోపల్స్ ఎందుకు? మా లక్ష్యం తెలుసుకోండి",
    navHome: "హోమ్",
    navLiveEnv: "గాలి & వాతావరణం",
    navNearby: "చుట్టుపక్కల కాలుష్యం",
    navTraffic: "ట్రాఫిక్ & కాలుష్యం",
    navFood: "ఆహార వృథా",
    navWater: "నీటి వృథా",
    navCommunity: "కమ్యూనిటీ",
    navImpact: "నా ఎకో పాయింట్లు",
    navTips: "పర్యావరణ చిట్కాలు",
    navReports: "అన్ని రిపోర్టులు",
    navProfile: "ప్రొఫైల్",
    navAdmin: "అడ్మిన్ పోర్టల్",
    modeStandard: "📊 పూర్తి వివరాల వీక్షణ (Standard)",
    modeSimple: "🧒 పెద్దలు & పిల్లల సులభ వీక్షణ (Simple)",
    listenBtn: "🔊 నేటి రిపోర్టు వినండి",
    locationDetecting: "మీ ప్రాంతాన్ని గుర్తిస్తున్నాము...",
    useMyLocation: "📍 నా లొకేషన్ వాడండి",
    searchCityPlaceholder: "నగరం వెతకండి (ఉదా: హైదరాబాద్, విజయవాడ, విశాఖ...)",
    searchBtn: "శోధించండి",

    purposeTitle: "ఎకోపల్స్ అంటే ఏమిటి? ఎందుకు అవసరం?",
    purposeSubtitle: "మనం పీల్చే గాలి, తాగే నీరు, తినే ఆహారాన్ని కాపాడుకోవడానికి ఒక స్నేహపూర్వక వేదిక.",
    purposePillar1Title: "1. మీ గాలి నాణ్యత తెలుసుకోండి",
    purposePillar1Desc: "బయట వాకింగ్ వెళ్లడం క్షేమమేనా, పిల్లలు ఆడుకోవచ్చా లేదా మాస్క్ అవసరమా అని వెంటనే తెలుస్తుంది.",
    purposePillar2Title: "2. నీరు మరియు ఆహార వృథాను అరికట్టండి",
    purposePillar2Desc: "లీక్ అవుతున్న పైపులు లేదా మిగిలిన భోజనం కనిపిస్తే 10 సెకన్లలో రిపోర్ట్ చేసి సహాయం పొందండి.",
    purposePillar3Title: "3. అందరం కలిసి పచ్చని సమాజాన్ని నిర్మిద్దాం",
    purposePillar3Desc: "రోజువారీ మంచి పనులను నమోదు చేసుకుని మీ అపార్ట్‌మెంట్‌ను ఆదర్శవంతంగా తీర్చిదిద్దండి.",
    closeBtn: "మూసివేయి",

    aqiCardTitle: "గాలి నాణ్యత సూచిక (AQI)",
    standardUsed: "ప్రమాణం: US EPA ప్రమాణం (0-500)",
    temperature: "ఉష్ణోగ్రత",
    humidity: "తేమ శాతం",
    lastUpdated: "చివరి అప్‌డేట్",
    safeLabel: "స్వచ్ఛమైనది & సురక్షితం",
    moderateLabel: "మధ్యస్థం",
    poorLabel: "చెడు (అనారోగ్యకరం)",
    veryPoorLabel: "తీవ్రమైన చెడు",
    hazardousLabel: "ప్రమాదకరం",

    ecoScoreTitle: "ఎకోపల్స్ స్కోరు",
    ecoScoreDesc: "మీ పరిసరాల పర్యావరణ ఆరోగ్య స్కోరు (0-100)",
    transparentFormulaTitle: "ఈ స్కోరు ఎలా లెక్కించబడింది?",

    trafficTitle: "ట్రాఫిక్ & కాలుష్య తీవ్రత",
    congestionLevel: "ట్రాఫిక్ రద్దీ",
    trafficIntensity: "వాహనాల తీవ్రత",
    trafficPollutionRisk: "కాలుష్య ప్రమాదం",

    foodWasteSummaryTitle: "ఆహార వృథా నివారణ",
    foodWasteSub: "అవసరార్థులకు పంచడానికి సిద్ధంగా ఉన్న భోజనం",
    waterWasteSummaryTitle: "నీటి లీకేజీలు & వృథా నివారణ",
    waterWasteSub: "మంచి నీరు వృథా కాకుండా ప్రజలు నమోదు చేసిన రిపోర్టులు",

    reportProblemBtn: "🚨 పర్యావరణ సమస్యను రిపోర్ట్ చేయండి",
    logFoodWasteBtn: "🍲 ఆహార వృథా / దానం నమోదు చేయండి",
    logWaterWasteBtn: "💧 నీటి లీకేజ్ రిపోర్ట్ చేయండి",
    logImpactBtn: "+ నా మంచి పని నమోదు చేయండి",
    viewAllReportsBtn: "మ్యాప్‌లో అన్ని రిపోర్టులు చూడండి",

    simpleKidTipTitle: "పిల్లల కోసం సులభ వివరణ",
    simpleElderTipTitle: "పెద్దల కోసం ఆరోగ్య సలహా",
    simpleKidExplanation: "PM2.5 అంటే వెంట్రుక కంటే చిన్న దుమ్ము రేణువులు! బొమ్మ పచ్చగా ఉంటే 😊 మైదానంలో ఆడుకోవచ్చు!",
    simpleElderExplanation: "ఈ రోజు వాతావరణం అనుకూలంగా ఉంది. ఉదయం వాకింగ్ వెళ్లేవారు పుష్కలంగా నీరు తాగి వెళ్ళండి."
  },

  es: {
    appName: "EcoPulse",
    tagline: "Monitorea. Comprende. Actúa. Juntos por un futuro más verde.",
    purposeBadge: "🌍 Diseñado para todos: Niños, Familias, Mayores y Comunidades",
    whyEcoPulseBtn: "ℹ️ ¿Por qué EcoPulse? Conoce Nuestro Propósito",
    navHome: "Inicio",
    navLiveEnv: "Aire y Clima en Vivo",
    navNearby: "Contaminación Cercana",
    navTraffic: "Tráfico y Emisiones",
    navFood: "Desperdicio de Comida",
    navWater: "Desperdicio de Agua",
    navCommunity: "Comunidad",
    navImpact: "Mi Impacto Eco",
    navTips: "Consejos Eco",
    navReports: "Reportes",
    navProfile: "Perfil",
    navAdmin: "Portal Admin",
    modeStandard: "📊 Vista Estándar (Detallada)",
    modeSimple: "🧒 Vista Simple (Niños y Mayores)",
    listenBtn: "🔊 Escuchar Reporte de Hoy",
    locationDetecting: "Detectando su ubicación...",
    useMyLocation: "📍 Usar Mi Ubicación GPS",
    searchCityPlaceholder: "Buscar ciudad (ej. Madrid, México, Barcelona...)",
    searchBtn: "Buscar",

    purposeTitle: "¿Qué es EcoPulse y para qué sirve?",
    purposeSubtitle: "Una plataforma comunitaria para cuidar el aire que respiramos, el agua potable y los alimentos que compartimos.",
    purposePillar1Title: "1. Conoce la Calidad de tu Aire",
    purposePillar1Desc: "Descubre al instante si es seguro hacer ejercicio al aire libre o si es recomendable usar mascarilla.",
    purposePillar2Title: "2. Frena el Desperdicio Local",
    purposePillar2Desc: "¿Ves una tubería rota o comida sobrante de un evento? Reporta en 10 segundos para que se solucione.",
    purposePillar3Title: "3. Construye Hábitos Verdes",
    purposePillar3Desc: "Registra tus acciones sostenibles diarias y ayuda a tu vecindario a ser el más ecológico.",
    closeBtn: "Cerrar",

    aqiCardTitle: "Índice de Calidad del Aire (ICA)",
    standardUsed: "Estándar: US EPA (0-500)",
    temperature: "Temperatura",
    humidity: "Humedad",
    lastUpdated: "Última actualización",
    safeLabel: "Limpio y Seguro",
    moderateLabel: "Moderado",
    poorLabel: "Dañino / Pobre",
    veryPoorLabel: "Muy Dañino",
    hazardousLabel: "Peligro Crítico",

    ecoScoreTitle: "Puntuación EcoPulse",
    ecoScoreDesc: "Salud Ambiental Global de su Zona (0-100)",
    transparentFormulaTitle: "¿Cómo se calcula esta puntuación? (Fórmula Transparente)",

    trafficTitle: "Tráfico y Riesgo de Contaminación",
    congestionLevel: "Nivel de Congestión",
    trafficIntensity: "Intensidad Vehicular",
    trafficPollutionRisk: "Riesgo de Polución",

    foodWasteSummaryTitle: "Gestión de Desperdicio de Alimentos",
    foodWasteSub: "Comidas en buen estado disponibles para donación comunitaria",
    waterWasteSummaryTitle: "Fugas y Desperdicio de Agua",
    waterWasteSub: "Incidentes reportados por vecinos para evitar pérdidas de agua potable",

    reportProblemBtn: "🚨 Reportar Problema Ambiental",
    logFoodWasteBtn: "🍲 Reportar Excedente de Comida",
    logWaterWasteBtn: "💧 Reportar Fuga de Agua",
    logImpactBtn: "+ Registrar Mi Acción Verde",
    viewAllReportsBtn: "Ver Reportes en el Mapa",

    simpleKidTipTitle: "Nota para Niños Curiosos",
    simpleElderTipTitle: "Consejo para Adultos Mayores",
    simpleKidExplanation: "¡PM2.5 son pequeñas motas de polvo invisibles! Cuando la carita está verde 😊, ¡puedes salir a correr y jugar!",
    simpleElderExplanation: "El aire está favorable hoy. Se recomienda la caminata matinal temprano. Beba suficiente agua."
  },

  fr: {
    appName: "EcoPulse",
    tagline: "Surveiller. Comprendre. Agir. Ensemble pour un avenir plus vert.",
    purposeBadge: "🌍 Conçu pour tous : Enfants, Familles, Aînés et Voisins",
    whyEcoPulseBtn: "ℹ️ Pourquoi EcoPulse ? Découvrez Notre Mission",
    navHome: "Accueil",
    navLiveEnv: "Air & Météo en Direct",
    navNearby: "Pollution Environnante",
    navTraffic: "Trafic & Pollution",
    navFood: "Gaspillage Alimentaire",
    navWater: "Gaspillage d'Eau",
    navCommunity: "Communauté",
    navImpact: "Mon Impact Éco",
    navTips: "Éco-Conseils",
    navReports: "Signalements",
    navProfile: "Profil",
    navAdmin: "Portail Admin",
    modeStandard: "📊 Vue Standard (Détaillée)",
    modeSimple: "🧒 Vue Simplifiée (Enfants & Aînés)",
    listenBtn: "🔊 Écouter le Rapport du Jour",
    locationDetecting: "Détection de votre position...",
    useMyLocation: "📍 Utiliser Ma Position GPS",
    searchCityPlaceholder: "Rechercher une ville (ex. Paris, Lyon, Montréal...)",
    searchBtn: "Rechercher",

    purposeTitle: "Qu'est-ce qu'EcoPulse et à quoi sert-il ?",
    purposeSubtitle: "Une plateforme citoyenne pour préserver l'air que nous respirons, l'eau potable et la nourriture partagée.",
    purposePillar1Title: "1. Connaître la Qualité de l'Air",
    purposePillar1Desc: "Vérifiez immédiatement s'il est sain de jouer dehors, de faire du sport ou s'il faut porter un masque.",
    purposePillar2Title: "2. Stopper le Gaspillage Local",
    purposePillar2Desc: "Une fuite d'eau ou un surplus de repas ? Signalez-le en 10 secondes pour mobiliser votre communauté.",
    purposePillar3Title: "3. Agir Ensemble pour la Planète",
    purposePillar3Desc: "Suivez vos éco-points quotidiens et aidez votre quartier à devenir un modèle écologique.",
    closeBtn: "Fermer",

    aqiCardTitle: "Indice de Qualité de l'Air (IQA)",
    standardUsed: "Standard : Norme US EPA (0-500)",
    temperature: "Température",
    humidity: "Humidité",
    lastUpdated: "Dernière mise à jour",
    safeLabel: "Sain & Pur",
    moderateLabel: "Modéré",
    poorLabel: "Dégradé / Mauvais",
    veryPoorLabel: "Très Mauvais",
    hazardousLabel: "Danger Extrême",

    ecoScoreTitle: "Score EcoPulse",
    ecoScoreDesc: "Indice de Santé Environnementale Globale (0-100)",
    transparentFormulaTitle: "Comment ce score est-il calculé ? (Formule Transparente)",

    trafficTitle: "Trafic & Risque de Pollution",
    congestionLevel: "Niveau d'Embouteillage",
    trafficIntensity: "Intensité du Trafic",
    trafficPollutionRisk: "Impact Pollution",

    foodWasteSummaryTitle: "Lutte contre le Gaspillage Alimentaire",
    foodWasteSub: "Repas sains disponibles pour le partage et le don solidaire",
    waterWasteSummaryTitle: "Signalement des Fuites d'Eau",
    waterWasteSub: "Signalements citoyens pour éviter les pertes d'eau potable",

    reportProblemBtn: "🚨 Signaler un Problème Écologique",
    logFoodWasteBtn: "🍲 Déclarer un Surplus Alimentaire",
    logWaterWasteBtn: "💧 Signaler une Fuite d'Eau",
    logImpactBtn: "+ Noter Mon Geste Vert",
    viewAllReportsBtn: "Voir la Carte des Signalements",

    simpleKidTipTitle: "Pour les Petits Écolos",
    simpleElderTipTitle: "Conseil Santé pour les Aînés",
    simpleKidExplanation: "Les PM2.5 sont des grains de poussière minuscules. Si le smiley est vert 😊, allez jouer dehors !",
    simpleElderExplanation: "Qualité de l'air satisfaisante. Privilégiez les promenades avant 9h du matin et hydratez-vous bien."
  },

  de: {
    appName: "EcoPulse",
    tagline: "Beobachten. Verstehen. Handeln. Gemeinsam für eine grünere Zukunft.",
    purposeBadge: "🌍 Für alle Generationen: Kinder, Familien, Senioren & Nachbarschaften",
    whyEcoPulseBtn: "ℹ️ Warum EcoPulse? Unser Ziel verstehen",
    navHome: "Startseite",
    navLiveEnv: "Luft & Wetter Live",
    navNearby: "Umgebungsluft",
    navTraffic: "Verkehr & Emissionen",
    navFood: "Lebensmittelrettung",
    navWater: "Wasserverschwendung",
    navCommunity: "Gemeinschaft",
    navImpact: "Mein Öko-Beitrag",
    navTips: "Öko-Tipps",
    navReports: "Meldungen",
    navProfile: "Profil",
    navAdmin: "Admin-Bereich",
    modeStandard: "📊 Standardansicht (Detailliert)",
    modeSimple: "🧒 Senioren & Kinder (Einfach & Groß)",
    listenBtn: "🔊 Heutigen Bericht Vorlesen",
    locationDetecting: "Standort wird ermittelt...",
    useMyLocation: "📍 Meinen GPS-Standort nutzen",
    searchCityPlaceholder: "Stadt suchen (z. B. Berlin, München, Wien, Zürich...)",
    searchBtn: "Suchen",

    purposeTitle: "Was ist EcoPulse und warum brauchen wir es?",
    purposeSubtitle: "Eine bürgernahe Plattform zum Schutz unserer Atemluft, unseres Trinkwassers und wertvoller Lebensmittel.",
    purposePillar1Title: "1. Wissen, was man atmet",
    purposePillar1Desc: "Erfahren Sie sofort, ob das Lüften, Joggen oder Spielen im Freien heute unbedenklich ist.",
    purposePillar2Title: "2. Verschwendung in der Nachbarschaft stoppen",
    purposePillar2Desc: "Tropfender Hydrant oder übrig gebliebenes Konferenz-Buffet? In 10 Sekunden gemeldet und gerettet.",
    purposePillar3Title: "3. Zusammen eine grünere Stadt schaffen",
    purposePillar3Desc: "Verfolgen Sie Ihre täglichen grünen Taten und bringen Sie Ihr Wohnviertel an die Spitze.",
    closeBtn: "Schließen",

    aqiCardTitle: "Luftqualitätsindex (AQI)",
    standardUsed: "Standard: US EPA Standard (0-500)",
    temperature: "Temperatur",
    humidity: "Luftfeuchtigkeit",
    lastUpdated: "Zuletzt aktualisiert",
    safeLabel: "Sauber & Frisch",
    moderateLabel: "Mäßig (Akzeptabel)",
    poorLabel: "Ungesund",
    veryPoorLabel: "Sehr Ungesund",
    hazardousLabel: "Gefahr / Notfall",

    ecoScoreTitle: "EcoPulse Score",
    ecoScoreDesc: "Gesamt-Umweltgesundheit Ihres Bezirks (0-100)",
    transparentFormulaTitle: "Wie wird dieser Wert berechnet? (Transparente Formel)",

    trafficTitle: "Verkehr & Feinstaubrisiko",
    congestionLevel: "Verkehrsaufkommen",
    trafficIntensity: "Fahrzeugdichte",
    trafficPollutionRisk: "Emissionsrisiko",

    foodWasteSummaryTitle: "Lebensmittelrettung & Teilen",
    foodWasteSub: "Einwandfreie Mahlzeiten zur Abholung und Weitergabe",
    waterWasteSummaryTitle: "Wasserlecks & Rohrbrüche",
    waterWasteSub: "Bürgermeldungen zur Rettung unseres wertvollen Trinkwassers",

    reportProblemBtn: "🚨 Umweltproblem Melden",
    logFoodWasteBtn: "🍲 Lebensmittel Melden / Retten",
    logWaterWasteBtn: "💧 Wasserleck Melden",
    logImpactBtn: "+ Meine Öko-Tat Eintragen",
    viewAllReportsBtn: "Alle Meldungen auf der Karte ansehen",

    simpleKidTipTitle: "Tipp für junge Entdecker",
    simpleElderTipTitle: "Gesundheitshinweis für Senioren",
    simpleKidExplanation: "PM2.5 ist Staub, der kleiner ist als ein Haar! Wenn der Smiley grün lacht 😊, ab nach draußen auf den Spielplatz!",
    simpleElderExplanation: "Gute Luftqualität heute. Spaziergänge am Vormittag werden empfohlen. Denken Sie an ausreichend Wasser."
  }
};

let currentLanguage = localStorage.getItem("ecopulse_lang") || "en";

function setLanguage(lang) {
  if (!translations[lang]) lang = "en";
  currentLanguage = lang;
  localStorage.setItem("ecopulse_lang", lang);
  applyTranslations();
}

function t(key) {
  if (translations[currentLanguage] && translations[currentLanguage][key]) {
    return translations[currentLanguage][key];
  }
  return translations["en"][key] || key;
}

function applyTranslations() {
  document.querySelectorAll("[data-i18n]").forEach(elem => {
    const key = elem.getAttribute("data-i18n");
    const translated = t(key);
    if (translated) {
      if (elem.tagName === "INPUT" || elem.tagName === "TEXTAREA") {
        elem.placeholder = translated;
      } else {
        elem.textContent = translated;
      }
    }
  });

  // Update language selector dropdown if present
  const langSelect = document.getElementById("langSelector");
  if (langSelect && langSelect.value !== currentLanguage) {
    langSelect.value = currentLanguage;
  }
}
