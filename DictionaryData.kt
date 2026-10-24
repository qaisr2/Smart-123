package com.example.data.dictionary

import com.example.model.ContextSense
import com.example.model.DictionaryEntry

object DictionaryData {
    val entries: Map<String, DictionaryEntry> = mapOf(
        "abandon" to DictionaryEntry(
            word = "abandon",
            urduMeaning = "چھوڑ دینا / ترک کر دینا",
            romanUrdu = "Chhor dena / Tark kar dena",
            partOfSpeech = "verb",
            simpleEnglish = "To leave someone or something completely and not return.",
            simpleUrdu = "کسی شخص، جگہ یا چیز کو ہمیشہ کے لیے چھوڑ دینا یا لاوارث کر دینا۔",
            synonyms = listOf("leave", "desert", "forsake", "discard"),
            exampleSentence = "The sailors had to abandon the sinking ship."
        ),
        "ability" to DictionaryEntry(
            word = "ability",
            urduMeaning = "قابلیت / صلاحیت / مہارت",
            romanUrdu = "Qabiliyat / Salahiyat / Maharat",
            partOfSpeech = "noun",
            simpleEnglish = "The skill, power, or talent needed to do something well.",
            simpleUrdu = "کسی کام کو اچھے طریقے سے کرنے کی جسمانی یا ذہنی قوت اور ہنر۔",
            synonyms = listOf("capability", "skill", "talent", "competence"),
            exampleSentence = "She has an extraordinary ability to learn languages quickly."
        ),
        "acquire" to DictionaryEntry(
            word = "acquire",
            urduMeaning = "حاصل کرنا / سیکھنا / کمانا",
            romanUrdu = "Hasil karna / Seekhna / Kamana",
            partOfSpeech = "verb",
            simpleEnglish = "To get, buy, or develop knowledge or a skill through effort.",
            simpleUrdu = "کوشش، محنت یا خریداری کے ذریعے کوئی چیز یا علم حاصل کرنا۔",
            synonyms = listOf("obtain", "gain", "earn", "achieve"),
            exampleSentence = "Students acquire valuable skills through reading practice."
        ),
        "advantage" to DictionaryEntry(
            word = "advantage",
            urduMeaning = "فائدہ / برتری / نفع",
            romanUrdu = "Faida / Bartari / Nafeh",
            partOfSpeech = "noun",
            simpleEnglish = "A condition or circumstance that puts someone in a favorable or superior position.",
            simpleUrdu = "ایسی حالت یا خوبی جو آپ کو دوسروں سے آگے رکھے یا کامیابی دے۔",
            synonyms = listOf("benefit", "perk", "favor", "edge"),
            exampleSentence = "Speaking two languages gives you a great advantage in your career."
        ),
        "analyze" to DictionaryEntry(
            word = "analyze",
            urduMeaning = "تجزیہ کرنا / غور و خوض کرنا",
            romanUrdu = "Tajziya karna / Ghor-o-khooz karna",
            partOfSpeech = "verb",
            simpleEnglish = "To examine something methodically and in detail to understand its structure or nature.",
            simpleUrdu = "کسی بات یا ڈیٹا کو باریکی سے جانچنا تاکہ حقیقت سمجھی جا سکے۔",
            synonyms = listOf("examine", "inspect", "study", "evaluate"),
            exampleSentence = "Researchers analyze the data before drawing any final conclusions."
        ),
        "anxious" to DictionaryEntry(
            word = "anxious",
            urduMeaning = "پریشان / بے چین / فکر مند",
            romanUrdu = "Pareshan / Bechain / Fikarmand",
            partOfSpeech = "adjective",
            simpleEnglish = "Feeling worried, nervous, or uneasy about an uncertain event.",
            simpleUrdu = "کسی آنے والے معاملے یا نتیجے کی وجہ سے دل میں گھبراہٹ یا بے چینی محسوس کرنا۔",
            synonyms = listOf("worried", "nervous", "uneasy", "apprehensive"),
            exampleSentence = "He felt anxious before stepping onto the stage for his presentation."
        ),
        "appreciate" to DictionaryEntry(
            word = "appreciate",
            urduMeaning = "قدر کرنا / داد دینا / شکر گزار ہونا",
            romanUrdu = "Qadar karna / Daad dena / Shukar guzar hona",
            partOfSpeech = "verb",
            simpleEnglish = "To recognize the full worth or value of someone or something; to be grateful.",
            simpleUrdu = "کسی کی اچھائی، احسان یا خوبی کا احساس کرنا اور اس کی عزت و قدر کرنا۔",
            synonyms = listOf("value", "treasure", "cherish", "admire"),
            exampleSentence = "I really appreciate your kind help during my exams."
        ),
        "attempt" to DictionaryEntry(
            word = "attempt",
            urduMeaning = "کوشش کرنا / جدوجہد",
            romanUrdu = "Koshish karna / Jaddojahad",
            partOfSpeech = "verb / noun",
            simpleEnglish = "An effort or act of trying to achieve or complete something difficult.",
            simpleUrdu = "کسی مقصد کو پورا کرنے کے لیے پوری جان لگا کر کی جانے والی کوشش۔",
            synonyms = listOf("try", "effort", "endeavor", "trial"),
            exampleSentence = "She passed the difficult test on her first attempt."
        ),
        "bank" to DictionaryEntry(
            word = "bank",
            urduMeaning = "بینک (مالیاتی ادارہ) یا ندی کا کنارہ",
            romanUrdu = "Bank (Maliyati Idara) ya Nadi ka Kinara",
            partOfSpeech = "noun",
            simpleEnglish = "A financial institution where money is kept, or the sloping land beside a river.",
            simpleUrdu = "روپوں پیسوں کا ادارہ یا دریا اور ندی کے ساتھ والی مٹی کا کنارہ۔",
            synonyms = listOf("depository", "treasury", "shore", "riverbank"),
            exampleSentence = "He walked along the river bank after depositing money in the bank.",
            contextualSenses = listOf(
                ContextSense(
                    triggerKeywords = listOf("river", "water", "stream", "lake", "shore", "boat", "sand", "walk", "sit"),
                    contextName = "River / Water Shore",
                    urduMeaning = "ندی یا دریا کا کنارہ",
                    romanUrdu = "Nadi ya Darya ka Kinara",
                    explanation = "دریا، نہر یا جھیل کے ساتھ والا کنارہ"
                ),
                ContextSense(
                    triggerKeywords = listOf("money", "account", "cash", "deposit", "loan", "check", "teller", "finance", "credit"),
                    contextName = "Financial Institution",
                    urduMeaning = "بینک / مالیاتی ادارہ",
                    romanUrdu = "Bank / Maliyati Idara",
                    explanation = "روپے پیسے جمع کرنے اور ادھار لینے کی جگہ"
                )
            )
        ),
        "brave" to DictionaryEntry(
            word = "brave",
            urduMeaning = "بہادر / دلیر / نڈر",
            romanUrdu = "Bahadur / Diler / Nidar",
            partOfSpeech = "adjective",
            simpleEnglish = "Ready to face pain, danger, or difficulty without showing fear.",
            simpleUrdu = "جو خوف اور خطرے کے باوجود ہمت نہ ہارے اور ڈٹ جائے۔",
            synonyms = listOf("courageous", "valiant", "fearless", "bold"),
            exampleSentence = "The brave firefighter rushed into the house to rescue the child."
        ),
        "challenge" to DictionaryEntry(
            word = "challenge",
            urduMeaning = "چیلنج / مشکل کام / مقابلہ",
            romanUrdu = "Challenge / Mushkil kaam / Muqabla",
            partOfSpeech = "noun / verb",
            simpleEnglish = "A demanding situation that tests someone's ability or courage.",
            simpleUrdu = "ایک ایسا کٹھن مرحلہ جو انسان کی طاقت اور فہم کا امتحان لیتا ہے۔",
            synonyms = listOf("difficulty", "obstacle", "test", "contest"),
            exampleSentence = "Learning a foreign language is a fun and rewarding challenge."
        ),
        "comprehend" to DictionaryEntry(
            word = "comprehend",
            urduMeaning = "سمجھنا / ادراک کرنا / گرفت میں لانا",
            romanUrdu = "Samajhna / Idraak karna",
            partOfSpeech = "verb",
            simpleEnglish = "To mentally grasp the meaning or importance of something.",
            simpleUrdu = "کسی مسئلے، سبق یا بات کی گہرائی کو ذہن نشین کرنا۔",
            synonyms = listOf("understand", "grasp", "fathom", "perceive"),
            exampleSentence = "It takes focus to comprehend complex scientific articles."
        ),
        "confidence" to DictionaryEntry(
            word = "confidence",
            urduMeaning = "اعتماد / خود اعتمادی / بھروسہ",
            romanUrdu = "Aitmaad / Khud aitmaadi / Bharosa",
            partOfSpeech = "noun",
            simpleEnglish = "A feeling of trust in one's abilities, qualities, or judgment.",
            simpleUrdu = "اپنے آپ پر اور اپنی صلاحیتوں پر پختہ یقین اور بھروسہ۔",
            synonyms = listOf("trust", "self-assurance", "belief", "faith"),
            exampleSentence = "Practice gives you confidence to speak in public without fear."
        ),
        "consequence" to DictionaryEntry(
            word = "consequence",
            urduMeaning = "نتیجہ / انجام / ثمر",
            romanUrdu = "Nateeja / Anjaam / Samar",
            partOfSpeech = "noun",
            simpleEnglish = "A result or effect of an action or condition.",
            simpleUrdu = "کسی عمل، غلطی یا فیصلے کا پیدا ہونے والا انجام یا اثر۔",
            synonyms = listOf("result", "outcome", "aftermath", "effect"),
            exampleSentence = "Every choice we make carries a direct consequence."
        ),
        "courage" to DictionaryEntry(
            word = "courage",
            urduMeaning = "حوصلہ / ہمت / دلیری",
            romanUrdu = "Hosla / Himmat / Dileri",
            partOfSpeech = "noun",
            simpleEnglish = "Strength in the face of pain or grief; mental bravery.",
            simpleUrdu = "مشکل حالات اور خطرات کے سامنے ثابت قدم رہنے کا قلبی جذبہ۔",
            synonyms = listOf("bravery", "valor", "grit", "boldness"),
            exampleSentence = "It took tremendous courage to speak up for justice."
        ),
        "crucial" to DictionaryEntry(
            word = "crucial",
            urduMeaning = "انتہائی اہم / فیصلہ کن / لازمی",
            romanUrdu = "Intehayi ahem / Faisla kun / Lazmi",
            partOfSpeech = "adjective",
            simpleEnglish = "Decisive or critical, especially in the success or failure of something.",
            simpleUrdu = "ایسی چیز جس کے بغیر کام ادھورا رہ جائے یا نتیجہ الٹ جائے۔",
            synonyms = listOf("critical", "vital", "essential", "key"),
            exampleSentence = "Consistent daily practice is crucial for vocabulary mastery."
        ),
        "current" to DictionaryEntry(
            word = "current",
            urduMeaning = "موجودہ / حالیہ یا برقی رو / بہاؤ",
            romanUrdu = "Maujooda / Haliya ya Barqi Ro / Bahaao",
            partOfSpeech = "adjective / noun",
            simpleEnglish = "Happening right now, or a flow of water, air, or electricity.",
            simpleUrdu = "اس وقت کا جاری وقت، یا بجلی اور پانی کا تیز بہاؤ۔",
            synonyms = listOf("present", "contemporary", "flow", "stream"),
            exampleSentence = "She keeps up with current events and understands electrical current.",
            contextualSenses = listOf(
                ContextSense(
                    triggerKeywords = listOf("electric", "wire", "power", "circuit", "voltage", "amp", "charge"),
                    contextName = "Electricity",
                    urduMeaning = "برقی رو / کرنٹ",
                    romanUrdu = "Barqi Ro / Current",
                    explanation = "تاروں میں دوڑنے والی بجلی کا بہاؤ"
                ),
                ContextSense(
                    triggerKeywords = listOf("water", "river", "ocean", "swim", "tide", "stream", "flow"),
                    contextName = "Flow of Water",
                    urduMeaning = "پانی کا تیز بہاؤ",
                    romanUrdu = "Pani ka Bahaao",
                    explanation = "دریا یا سمندر کی لہروں کی رفتار"
                ),
                ContextSense(
                    triggerKeywords = listOf("affairs", "news", "events", "time", "present", "now", "today", "situation"),
                    contextName = "Present Time",
                    urduMeaning = "موجودہ / حالیہ دور کا",
                    romanUrdu = "Maujooda / Haliya",
                    explanation = "آج کے زمانے یا دور سے متعلق"
                )
            )
        ),
        "determine" to DictionaryEntry(
            word = "determine",
            urduMeaning = "تعین کرنا / فیصلہ کرنا / پختہ ارادہ کرنا",
            romanUrdu = "Taaiyun karna / Faisla karna / Pukhta irada",
            partOfSpeech = "verb",
            simpleEnglish = "To cause something to occur in a particular way; to firmly decide.",
            simpleUrdu = "کسی بات کا حتمی فیصلہ کرنا یا معلوم کرنا کہ اصل کیا ہے۔",
            synonyms = listOf("decide", "resolve", "settle", "establish"),
            exampleSentence = "Your daily habits will determine your future success."
        ),
        "diligent" to DictionaryEntry(
            word = "diligent",
            urduMeaning = "محنتی / مستعد / پرخلوص کام کرنے والا",
            romanUrdu = "Mehanti / Mustaid / Purkhaloos",
            partOfSpeech = "adjective",
            simpleEnglish = "Having or showing care and conscientiousness in one's work.",
            simpleUrdu = "جو اپنے کام میں کوتاہی نہ برتے اور لگن سے کام مکمل کرے۔",
            synonyms = listOf("hardworking", "industrious", "assiduous", "attentive"),
            exampleSentence = "Diligent students always achieve their educational goals."
        ),
        "effort" to DictionaryEntry(
            word = "effort",
            urduMeaning = "کوشش / محنت / تگ و دو",
            romanUrdu = "Koshish / Mehnat / Tag-o-doo",
            partOfSpeech = "noun",
            simpleEnglish = "Vigorous or determined attempt; physical or mental exertion.",
            simpleUrdu = "کسی مقصد کے لیے اپنی توانائی اور وقت صرف کرنا۔",
            synonyms = listOf("exertion", "struggle", "labor", "endeavor"),
            exampleSentence = "Her hard effort was rewarded with top honors."
        ),
        "encourage" to DictionaryEntry(
            word = "encourage",
            urduMeaning = "حوصلہ افزائی کرنا / ترغیب دینا",
            romanUrdu = "Hosla afzayi karna / Targheeb dena",
            partOfSpeech = "verb",
            simpleEnglish = "Give support, confidence, or hope to someone.",
            simpleUrdu = "کسی کا دل بڑھانا اور اسے آگے بڑھنے کی ترغیب دینا۔",
            synonyms = listOf("inspire", "motivate", "cheer", "heartened"),
            exampleSentence = "Good teachers encourage their students to ask questions freely."
        ),
        "essential" to DictionaryEntry(
            word = "essential",
            urduMeaning = "ضروری / بنیادی / لازمی",
            romanUrdu = "Zaroori / Bunyadi / Lazmi",
            partOfSpeech = "adjective",
            simpleEnglish = "Absolutely necessary; extremely important.",
            simpleUrdu = "ایسی چیز جس کے بغیر گزارا ممکن نہ ہو، اصل بنیاد۔",
            synonyms = listOf("necessary", "vital", "indispensable", "fundamental"),
            exampleSentence = "Clean water and sleep are essential for human health."
        ),
        "evaluate" to DictionaryEntry(
            word = "evaluate",
            urduMeaning = "جانچنا / تخمینہ لگانا / قیمت یا معیار پرکھنا",
            romanUrdu = "Jaanchna / Takhmeena lagana / Parkhna",
            partOfSpeech = "verb",
            simpleEnglish = "To judge or determine the significance, worth, or quality of something.",
            simpleUrdu = "کسی چیز کی قدر، معیار یا اچھائی برائی کا غور سے اندازہ لگانا۔",
            synonyms = listOf("assess", "appraise", "rate", "judge"),
            exampleSentence = "We must evaluate our progress at the end of each week."
        ),
        "flexible" to DictionaryEntry(
            word = "flexible",
            urduMeaning = "لچکدار / حالات کے مطابق ڈھلنے والا",
            romanUrdu = "Lachakdaar / Dhalne wala",
            partOfSpeech = "adjective",
            simpleEnglish = "Capable of bending easily without breaking; able to adapt to new conditions.",
            simpleUrdu = "جو آسانی سے مڑ سکے یا نئے حالات میں خود کو ڈھال لے۔",
            synonyms = listOf("adaptable", "pliable", "versatile", "adjustable"),
            exampleSentence = "A flexible mindset helps you navigate unexpected changes."
        ),
        "frequently" to DictionaryEntry(
            word = "frequently",
            urduMeaning = "اکثر / بار بار / آئے روز",
            romanUrdu = "Aksar / Baar baar / Aaye roz",
            partOfSpeech = "adverb",
            simpleEnglish = "Regularly or at short intervals; very often.",
            simpleUrdu = "جو بات بار بار یا مختصر وقفے سے پیش آئے۔",
            synonyms = listOf("often", "repeatedly", "regularly", "commonly"),
            exampleSentence = "He frequently reads English books to expand his vocabulary."
        ),
        "generous" to DictionaryEntry(
            word = "generous",
            urduMeaning = "سخی / فیاض / فراخ دل",
            romanUrdu = "Sakhi / Fayyaz / Farakh dil",
            partOfSpeech = "adjective",
            simpleEnglish = "Showing a readiness to give more of something than is strictly necessary.",
            simpleUrdu = "کھلے دل سے مدد کرنے والا، جو دوسروں پر خرچ کرنے سے نہ جھجھکے۔",
            synonyms = listOf("charitable", "magnanimous", "giving", "benevolent"),
            exampleSentence = "The generous donor helped build a modern library for the town."
        ),
        "genuine" to DictionaryEntry(
            word = "genuine",
            urduMeaning = "اصلی / کھرا / مخلص / سچا",
            romanUrdu = "Asli / Khara / Mukhlas / Sacha",
            partOfSpeech = "adjective",
            simpleEnglish = "Truly what something is said to be; authentic and sincere.",
            simpleUrdu = "جس میں بناوٹ یا دھوکہ نہ ہو، بالکل خالص اور سچا۔",
            synonyms = listOf("authentic", "real", "sincere", "honest"),
            exampleSentence = "He showed genuine kindness to everyone he met."
        ),
        "glance" to DictionaryEntry(
            word = "glance",
            urduMeaning = "سرسری نظر ڈالنا / جھلک دیکھنا",
            romanUrdu = "Sarsari nazar daalna / Jhalak dekhna",
            partOfSpeech = "verb / noun",
            simpleEnglish = "Take a brief or hurried look.",
            simpleUrdu = "ایک لمحے کے لیے جلدی سے نظر اٹھا کر دیکھنا۔",
            synonyms = listOf("peek", "glimpse", "look", "scan"),
            exampleSentence = "She stole a quick glance at the clock during the lecture."
        ),
        "habit" to DictionaryEntry(
            word = "habit",
            urduMeaning = "عادت / معمول / خصلت",
            romanUrdu = "Aadat / Maamool / Khaslat",
            partOfSpeech = "noun",
            simpleEnglish = "A settled or regular tendency or practice, especially one that is hard to give up.",
            simpleUrdu = "ایسا عمل جو انسان روزانہ خود بخود دہراتا ہو اور زندگی کا حصہ بن چکا ہو۔",
            synonyms = listOf("routine", "custom", "practice", "pattern"),
            exampleSentence = "Reading fifteen minutes before bed is a healthy habit."
        ),
        "hesitate" to DictionaryEntry(
            word = "hesitate",
            urduMeaning = "ہچکچانا / تذبذب کا شکار ہونا / جھجھکنا",
            romanUrdu = "Hichkichana / Tazabzub / Jhijhakna",
            partOfSpeech = "verb",
            simpleEnglish = "Pause before saying or doing something through uncertainty.",
            simpleUrdu = "شک یا خوف کی وجہ سے بولنے یا کوئی کام کرنے سے پہلے رک جانا۔",
            synonyms = listOf("pause", "waver", "delay", "falter"),
            exampleSentence = "Do not hesitate to ask for help whenever you feel stuck."
        ),
        "highlight" to DictionaryEntry(
            word = "highlight",
            urduMeaning = "نمایاں کرنا / اجاگر کرنا / خاص بات",
            romanUrdu = "Numayan karna / Ujaagar karna",
            partOfSpeech = "verb / noun",
            simpleEnglish = "To draw special attention to an important point or fact.",
            simpleUrdu = "کسی اہم بات یا لفظ کو نمایاں کرنا تاکہ فوری توجہ حاصل ہو۔",
            synonyms = listOf("emphasize", "underline", "accentuate", "feature"),
            exampleSentence = "Use a marker to highlight new English vocabulary in your notes."
        ),
        "ignorant" to DictionaryEntry(
            word = "ignorant",
            urduMeaning = "بے خبر / ناواقف / جاہل",
            romanUrdu = "Be-khabar / Nawaqif / Jahil",
            partOfSpeech = "adjective",
            simpleEnglish = "Lacking knowledge, education, or awareness in general.",
            simpleUrdu = "جسے کسی معاملے یا حقیقت کی بالکل جانکاری اور سمجھ نہ ہو۔",
            synonyms = listOf("unaware", "uninformed", "oblivious", "naive"),
            exampleSentence = "He remained ignorant of the danger until it was pointed out."
        ),
        "illustrate" to DictionaryEntry(
            word = "illustrate",
            urduMeaning = "وضاحت کرنا / تصویر یا مثال سے سمجھانا",
            romanUrdu = "Wazahat karna / Misaal se samjhana",
            partOfSpeech = "verb",
            simpleEnglish = "Explain or make something clear by using examples, charts, or pictures.",
            simpleUrdu = "مثالوں یا خاکوں کے ذریعے کسی مشکل بات کو آسان بنا کر پیش کرنا۔",
            synonyms = listOf("explain", "clarify", "demonstrate", "depict"),
            exampleSentence = "The author used real-life stories to illustrate his message."
        ),
        "immediate" to DictionaryEntry(
            word = "immediate",
            urduMeaning = "فوری / فی الفور / بروقت",
            romanUrdu = "Fawri / Fil-fawr / Barwaqt",
            partOfSpeech = "adjective",
            simpleEnglish = "Occurring or done at once; instant without delay.",
            simpleUrdu = "جو بغیر کسی تاخیر کے فوراً اسی وقت واقع ہو۔",
            synonyms = listOf("instant", "prompt", "direct", "urgent"),
            exampleSentence = "Tapping a word provides an immediate Urdu translation."
        ),
        "improve" to DictionaryEntry(
            word = "improve",
            urduMeaning = "بہتر بنانا / سدھارنا / ترقی دینا",
            romanUrdu = "Behtar banana / Sudharna / Taraqqi dena",
            partOfSpeech = "verb",
            simpleEnglish = "Make or become better in quality, value, or skill.",
            simpleUrdu = "کسی چیز کی خامیوں کو دور کر کے اسے مزید اچھا اور کارآمد بنانا۔",
            synonyms = listOf("enhance", "upgrade", "advance", "develop"),
            exampleSentence = "Consistent practice will rapidly improve your reading speed."
        ),
        "inspire" to DictionaryEntry(
            word = "inspire",
            urduMeaning = "متاثر کرنا / ولولہ پیدا کرنا / ترغیب دینا",
            romanUrdu = "Mutasir karna / Walwala paida karna",
            partOfSpeech = "verb",
            simpleEnglish = "Fill someone with the urge or ability to do or feel something creative.",
            simpleUrdu = "کسی کے دل میں نیا جذبہ، ہمت اور کام کرنے کی لگن پیدا کرنا۔",
            synonyms = listOf("motivate", "encourage", "invigorate", "stir"),
            exampleSentence = "Great leaders inspire people to believe in their own strength."
        ),
        "isolate" to DictionaryEntry(
            word = "isolate",
            urduMeaning = "الگ تھلگ کرنا / تنہا کرنا / جدا کرنا",
            romanUrdu = "Alag thalag karna / Tanha karna",
            partOfSpeech = "verb",
            simpleEnglish = "Cause a person or place to be or remain alone or apart from others.",
            simpleUrdu = "کسی کو دوسروں سے دور کر کے اکیلا یا الگ کر دینا۔",
            synonyms = listOf("separate", "detach", "segregate", "insulate"),
            exampleSentence = "Scientists isolate single cells to study their behavior under a microscope."
        ),
        "journey" to DictionaryEntry(
            word = "journey",
            urduMeaning = "سفر / مسافت / ارتقائی مرحلہ",
            romanUrdu = "Safar / Musafat / Marhala",
            partOfSpeech = "noun",
            simpleEnglish = "An act of traveling from one place to another; a long process of personal change.",
            simpleUrdu = "ایک جگہ سے دوسری جگہ جانا، یا زندگی میں علم اور تجربے کا طویل سفر۔",
            synonyms = listOf("trip", "voyage", "travel", "quest"),
            exampleSentence = "Learning English is a rewarding journey of personal growth."
        ),
        "knowledge" to DictionaryEntry(
            word = "knowledge",
            urduMeaning = "علم / معلومات / دانائی / واقفیت",
            romanUrdu = "Ilm / Maloomat / Danai / Waqfiyat",
            partOfSpeech = "noun",
            simpleEnglish = "Facts, information, and skills acquired through experience or education.",
            simpleUrdu = "وہ معلومات، فہم اور تجربہ جو انسان پڑھ کر یا دیکھ کر سیکھتا ہے۔",
            synonyms = listOf("wisdom", "learning", "information", "understanding"),
            exampleSentence = "Knowledge opens doors to limitless opportunities."
        ),
        "lead" to DictionaryEntry(
            word = "lead",
            urduMeaning = "رہنمائی کرنا یا سیسہ (دھات)",
            romanUrdu = "Rahnumai karna ya Seesa (Dhaat)",
            partOfSpeech = "verb / noun",
            simpleEnglish = "To guide or be ahead, or a heavy soft grey metallic element.",
            simpleUrdu = "راستہ دکھانا، آگے چلنا، یا ایک بھاری نیلی سرمئی دھات۔",
            synonyms = listOf("guide", "direct", "conduct", "metal"),
            exampleSentence = "The guide will lead the group, holding a lantern made of lead.",
            contextualSenses = listOf(
                ContextSense(
                    triggerKeywords = listOf("metal", "heavy", "pencil", "pipe", "poison", "element", "bullet"),
                    contextName = "Heavy Metal",
                    urduMeaning = "سیسہ (بھاری دھات)",
                    romanUrdu = "Seesa (Bhari Dhaat)",
                    explanation = "ایک کیمیائی سرمئی دھات"
                ),
                ContextSense(
                    triggerKeywords = listOf("guide", "leader", "way", "ahead", "follow", "captain", "direct", "team"),
                    contextName = "Guide / Direct",
                    urduMeaning = "رہنمائی کرنا / آگے چلنا",
                    romanUrdu = "Rahnumai karna / Aage chalna",
                    explanation = "کسی کو راستہ دکھانا یا ٹیم کی قیادت کرنا"
                )
            )
        ),
        "magnificent" to DictionaryEntry(
            word = "magnificent",
            urduMeaning = "شاندار / پرشکوہ / لاجواب",
            romanUrdu = "Shandar / Pur-shikoh / Lajawab",
            partOfSpeech = "adjective",
            simpleEnglish = "Impressively beautiful, elaborate, or extravagant; striking.",
            simpleUrdu = "بہت زیادہ خوبصورت، عظیم الشان اور دیکھنے میں دلکش۔",
            synonyms = listOf("splendid", "grand", "superb", "breathtaking"),
            exampleSentence = "The mountain view at sunrise was truly magnificent."
        ),
        "mature" to DictionaryEntry(
            word = "mature",
            urduMeaning = "بالغ / پختہ / سمجھدار / تیار",
            romanUrdu = "Baaligh / Pukhta / Samajhdar",
            partOfSpeech = "adjective / verb",
            simpleEnglish = "Fully developed physically; sensible and wise in thought.",
            simpleUrdu = "جو عقل اور عمر کے لحاظ سے پختہ اور سنجیدہ ہو چکا ہو۔",
            synonyms = listOf("grown", "sensible", "ripe", "seasoned"),
            exampleSentence = "He handled the disagreement with a very mature attitude."
        ),
        "necessary" to DictionaryEntry(
            word = "necessary",
            urduMeaning = "ضروری / لازمی / ناگزیر",
            romanUrdu = "Zaroori / Lazmi / Nagezeer",
            partOfSpeech = "adjective",
            simpleEnglish = "Required to be done, achieved, or present; needed.",
            simpleUrdu = "جس کا ہونا یا کیا جانا بے حد ضروری اور واجب ہو۔",
            synonyms = listOf("essential", "required", "vital", "needed"),
            exampleSentence = "Good sleep is necessary for maintaining sharp memory."
        ),
        "obstacle" to DictionaryEntry(
            word = "obstacle",
            urduMeaning = "رکاوٹ / اڑچن / مزاحمت",
            romanUrdu = "Rukawat / Archan / Muzahimat",
            partOfSpeech = "noun",
            simpleEnglish = "A thing that blocks one's way or prevents or hinders progress.",
            simpleUrdu = "کوئی ایسی چیز یا دشواری جو راستے میں حائل ہو جائے یا کام روک دے۔",
            synonyms = listOf("barrier", "hurdle", "impediment", "block"),
            exampleSentence = "Patience and hard work help overcome every obstacle."
        ),
        "opportunity" to DictionaryEntry(
            word = "opportunity",
            urduMeaning = "موقع / غنیمت گھڑی / چانس",
            romanUrdu = "Moqa / Ghaneemat ghari / Chance",
            partOfSpeech = "noun",
            simpleEnglish = "A set of circumstances that makes it possible to do something advantageous.",
            simpleUrdu = "ایسے اچھے حالات جو کچھ نیا کرنے یا کامیابی پانے کا سنہری موقع دیں۔",
            synonyms = listOf("chance", "opening", "occasion", "break"),
            exampleSentence = "Never miss an opportunity to learn something new every day."
        ),
        "persevere" to DictionaryEntry(
            word = "persevere",
            urduMeaning = "ثابت قدم رہنا / مسلسل محنت جاری رکھنا",
            romanUrdu = "Saabit qadam rehna / Musalsal mehnat",
            partOfSpeech = "verb",
            simpleEnglish = "Continue in a course of action even in the face of difficulty or with little prospect of success.",
            simpleUrdu = "مشکلات کے باوجود ہمت نہ ہارنا اور مسلسل ڈٹے رہنا۔",
            synonyms = listOf("persist", "endure", "continue", "carry on"),
            exampleSentence = "If you persevere through early hurdles, fluent English becomes natural."
        ),
        "potential" to DictionaryEntry(
            word = "potential",
            urduMeaning = "پوشیدہ صلاحیت / امکان / قوت",
            romanUrdu = "Posheeda salahiyat / Imkaan / Quwwat",
            partOfSpeech = "noun / adjective",
            simpleEnglish = "Latent qualities or abilities that may be developed and lead to future success.",
            simpleUrdu = "وہ اندرونی خوبیاں یا امکانات جو نکھرنے پر شاندار نتائج دے سکتے ہیں۔",
            synonyms = listOf("capacity", "possibility", "promise", "aptitude"),
            exampleSentence = "Every child has unlimited potential waiting to be unlocked."
        ),
        "practice" to DictionaryEntry(
            word = "practice",
            urduMeaning = "مشق کرنا / ریاضت / عملی مشق",
            romanUrdu = "Mashq karna / Riyazat / Practice",
            partOfSpeech = "noun / verb",
            simpleEnglish = "Repeated exercise in or performance of an activity so as to acquire or maintain proficiency in it.",
            simpleUrdu = "کسی کام میں مہارت پانے کے لیے اسے بار بار دہرانا۔",
            synonyms = listOf("rehearsal", "drill", "exercise", "training"),
            exampleSentence = "Daily practice turns difficult words into familiar friends."
        ),
        "precious" to DictionaryEntry(
            word = "precious",
            urduMeaning = "قیمتی / انمول / عزیز",
            romanUrdu = "Qeemti / Anmol / Azeez",
            partOfSpeech = "adjective",
            simpleEnglish = "Of great value; not to be wasted or treated carelessly.",
            simpleUrdu = "جس کی قدر بہت زیادہ ہو اور جو کھونے کے لائق نہ ہو۔",
            synonyms = listOf("valuable", "priceless", "treasured", "rare"),
            exampleSentence = "Time is our most precious asset; invest it wisely in education."
        ),
        "prevent" to DictionaryEntry(
            word = "prevent",
            urduMeaning = "روکنا / باز رکھنا / پیش بندی کرنا",
            romanUrdu = "Rokna / Baaz rakhna / Bachao",
            partOfSpeech = "verb",
            simpleEnglish = "Keep something undesirable from happening or arising.",
            simpleUrdu = "کسی نقصان دہ یا ناپسندیدہ بات کے ہونے سے پہلے ہی روک تھام کرنا۔",
            synonyms = listOf("stop", "avert", "hinder", "block"),
            exampleSentence = "Wearing a helmet helps prevent serious head injuries."
        ),
        "progress" to DictionaryEntry(
            word = "progress",
            urduMeaning = "ترقی / آگے بڑھنا / سدھار",
            romanUrdu = "Taraqqi / Aage barhna / Sudhaar",
            partOfSpeech = "noun / verb",
            simpleEnglish = "Forward or onward movement toward a destination or better state.",
            simpleUrdu = "منزل کی طرف پیش قدمی اور بہتری کی جانب سفر۔",
            synonyms = listOf("advancement", "improvement", "development", "growth"),
            exampleSentence = "She celebrated her continuous progress in English vocabulary."
        ),
        "recommend" to DictionaryEntry(
            word = "recommend",
            urduMeaning = "تجویز کرنا / سفارش کرنا / مشورہ دینا",
            romanUrdu = "Tajweez karna / Sifarish karna",
            partOfSpeech = "verb",
            simpleEnglish = "Put forward someone or something with approval as being suitable for a purpose.",
            simpleUrdu = "کسی کو کوئی اچھی چیز یا طریقہ اپنانے کا نیک مشورہ دینا۔",
            synonyms = listOf("suggest", "advise", "endorse", "propose"),
            exampleSentence = "I recommend reading a short English story every morning."
        ),
        "remember" to DictionaryEntry(
            word = "remember",
            urduMeaning = "یاد رکھنا / ذہن میں محفوظ رکھنا",
            romanUrdu = "Yaad rakhna / Zehan mein mehfooz",
            partOfSpeech = "verb",
            simpleEnglish = "Have in or be able to bring to one's mind an awareness of someone or something.",
            simpleUrdu = "کسی بات، سبق یا چہرے کو بھولنے نہ دینا اور یاد رکھنا۔",
            synonyms = listOf("recall", "recollect", "retain", "memorize"),
            exampleSentence = "Flashcards help students remember difficult Urdu definitions effortlessly."
        ),
        "require" to DictionaryEntry(
            word = "require",
            urduMeaning = "ضرورت ہونا / مانگنا / تقاضا کرنا",
            romanUrdu = "Zaroorat hona / Maangna / Taqaza",
            partOfSpeech = "verb",
            simpleEnglish = "Need for a particular purpose; depend on for success or fulfillment.",
            simpleUrdu = "کسی کام کی تکمیل کے لیے کسی چیز کا محتاج یا طلبگار ہونا۔",
            synonyms = listOf("need", "demand", "call for", "mandate"),
            exampleSentence = "Mastering any skill requires steady patience and devotion."
        ),
        "respect" to DictionaryEntry(
            word = "respect",
            urduMeaning = "عزت کرنا / احترام / تعظیم",
            romanUrdu = "Izzat karna / Ehtiram / Taazeem",
            partOfSpeech = "noun / verb",
            simpleEnglish = "A feeling of deep admiration for someone or something elicited by their abilities, qualities, or achievements.",
            simpleUrdu = "کسی کی اچھائی اور مرتبے کی وجہ سے دل سے اس کا ادب و احترام کرنا۔",
            synonyms = listOf("esteem", "honor", "regard", "admiration"),
            exampleSentence = "Treating others with mutual respect makes every community peaceful."
        ),
        "responsible" to DictionaryEntry(
            word = "responsible",
            urduMeaning = "ذمہ دار / قابل اعتماد / جوابدہ",
            romanUrdu = "Zimmedar / Qabil-e-aitmaad",
            partOfSpeech = "adjective",
            simpleEnglish = "Having an obligation to do something, or having control over or care for someone.",
            simpleUrdu = "جو اپنے فرائض کو پہچانے اور اپنے اعمال کا جوابدہ ہو۔",
            synonyms = listOf("accountable", "trustworthy", "reliable", "dutiful"),
            exampleSentence = "A responsible student plans their study hours ahead of time."
        ),
        "reveal" to DictionaryEntry(
            word = "reveal",
            urduMeaning = "ظاہر کرنا / فاش کرنا / کھولنا",
            romanUrdu = "Zahir karna / Faash karna / Kholna",
            partOfSpeech = "verb",
            simpleEnglish = "Make previously unknown or secret information known to others.",
            simpleUrdu = "کسی چھپی ہوئی حقیقت، راز یا بات کو سامنے لے آنا۔",
            synonyms = listOf("disclose", "unveil", "uncover", "expose"),
            exampleSentence = "The story's ending will reveal the true identity of the hero."
        ),
        "scarce" to DictionaryEntry(
            word = "scarce",
            urduMeaning = "نایاب / قلیل / کم یاب",
            romanUrdu = "Nayaab / Qaleel / Kamyaab",
            partOfSpeech = "adjective",
            simpleEnglish = "Insufficient for the demand; hard to find or rare.",
            simpleUrdu = "جو چیز بہت کم مقدار میں دستیاب ہو اور مشکل سے ملے۔",
            synonyms = listOf("rare", "limited", "sparse", "scant"),
            exampleSentence = "Fresh water is scarce in desert regions."
        ),
        "significant" to DictionaryEntry(
            word = "significant",
            urduMeaning = "نمایاں / معنی خیز / قابل ذکر",
            romanUrdu = "Numayan / Maani khez / Qabil-e-zikr",
            partOfSpeech = "adjective",
            simpleEnglish = "Sufficiently great or important to be worthy of attention; noteworthy.",
            simpleUrdu = "اتنا اہم یا بڑا کہ اسے نظر انداز نہ کیا جا سکے۔",
            synonyms = listOf("important", "notable", "meaningful", "substantial"),
            exampleSentence = "Reading regularly produces a significant increase in vocabulary."
        ),
        "sincere" to DictionaryEntry(
            word = "sincere",
            urduMeaning = "مخلص / سچا / بے ریا",
            romanUrdu = "Mukhlas / Sacha / Be-riya",
            partOfSpeech = "adjective",
            simpleEnglish = "Free from pretense or deceit; proceeding from genuine feelings.",
            simpleUrdu = "جس کے دل اور زبان میں کوئی فرق نہ ہو، خالص نیت والا۔",
            synonyms = listOf("honest", "genuine", "truthful", "heartfelt"),
            exampleSentence = "A sincere friend stands by you during difficult moments."
        ),
        "struggle" to DictionaryEntry(
            word = "struggle",
            urduMeaning = "جدوجہد کرنا / کشمکش / مشقت",
            romanUrdu = "Jaddojahad karna / Kashmakash",
            partOfSpeech = "verb / noun",
            simpleEnglish = "Make forceful or violent efforts to get free of restraint or resist attack; strive to achieve something.",
            simpleUrdu = "کسی رکاوٹ کے سامنے پوری طاقت اور ہمت سے جدوجہد کرنا۔",
            synonyms = listOf("fight", "strive", "battle", "labor"),
            exampleSentence = "Every great success story begins with an honest struggle."
        ),
        "temporary" to DictionaryEntry(
            word = "temporary",
            urduMeaning = "عارضی / وقتی / چند روزہ",
            romanUrdu = "Aarzi / Waqti / Chand roza",
            partOfSpeech = "adjective",
            simpleEnglish = "Lasting for only a limited period of time; not permanent.",
            simpleUrdu = "جو ہمیشہ نہ رہے بلکہ کچھ وقت کے لیے ہو۔",
            synonyms = listOf("brief", "short-term", "fleeting", "transient"),
            exampleSentence = "Difficult days are only temporary; stay optimistic."
        ),
        "thorough" to DictionaryEntry(
            word = "thorough",
            urduMeaning = "مکمل / تفصیلی / باریک بین",
            romanUrdu = "Mukammal / Tafseeli / Bareek been",
            partOfSpeech = "adjective",
            simpleEnglish = "Complete with regard to every detail; not superficial or partial.",
            simpleUrdu = "ہر پہلو سے مکمل اور باریکی کے ساتھ انجام دیا گیا۔",
            synonyms = listOf("complete", "comprehensive", "detailed", "exhaustive"),
            exampleSentence = "She conducted a thorough review of her vocabulary notes."
        ),
        "transform" to DictionaryEntry(
            word = "transform",
            urduMeaning = "تبدیل کرنا / کایا پلٹنا / نئی شکل دینا",
            romanUrdu = "Tabdeel karna / Kaya palatna",
            partOfSpeech = "verb",
            simpleEnglish = "Make a thorough or dramatic change in the form, appearance, or character.",
            simpleUrdu = "کسی چیز کی حالت، شکل یا معیار کو یکسر بدل کر بہتر بنا دینا۔",
            synonyms = listOf("change", "convert", "metamorphose", "revolutionize"),
            exampleSentence = "Consistent self-education can transform your entire life."
        ),
        "urgent" to DictionaryEntry(
            word = "urgent",
            urduMeaning = "فوری / ہنگامی / لازمی",
            romanUrdu = "Fawri / Hangami / Lazmi",
            partOfSpeech = "adjective",
            simpleEnglish = "Requiring immediate action or attention.",
            simpleUrdu = "جس پر فوری توجہ اور کارروائی کی ضرورت ہو۔",
            synonyms = listOf("pressing", "critical", "immediate", "emergency"),
            exampleSentence = "The doctor attended to the urgent medical call right away."
        ),
        "valuable" to DictionaryEntry(
            word = "valuable",
            urduMeaning = "قیمتی / بیش بہا / مفید",
            romanUrdu = "Qeemti / Besh baha / Mufeed",
            partOfSpeech = "adjective",
            simpleEnglish = "Worth a great deal of money or of great use or service.",
            simpleUrdu = "جس کی قدر زیادہ ہو اور جس سے بڑا فائدہ حاصل ہو۔",
            synonyms = listOf("precious", "useful", "worthwhile", "invaluable"),
            exampleSentence = "Good reading habits are a valuable treasure for students."
        ),
        "wisdom" to DictionaryEntry(
            word = "wisdom",
            urduMeaning = "حکمت / دانائی / عقل و فہم",
            romanUrdu = "Hikmat / Danai / Aqal-o-feham",
            partOfSpeech = "noun",
            simpleEnglish = "The quality of having experience, knowledge, and good judgment.",
            simpleUrdu = "تجربے، علم اور اچھے فیصلے کرنے کی صلاحیت اور سوجھ بوجھ۔",
            synonyms = listOf("knowledge", "insight", "sagacity", "understanding"),
            exampleSentence = "Age and diverse reading bring deep wisdom to people."
        )
    )
}
