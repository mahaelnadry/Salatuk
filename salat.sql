DROP TABLE IF EXISTS "pray";
CREATE TABLE "pray" ("prayer" VARCHAR NOT NULL , "desc" TEXT, "s_before" VARCHAR, "fard" VARCHAR, "s_after" VARCHAR, "plural" TEXT);
INSERT INTO "pray" VALUES('Fajr','Subh or Fajr (Dawn) prayer
The true dawn begins when we see light spread at the horizon in the East. When the true dawn appears the Fajr prayer time has begun and this prayer time remains until the first glimpse of the disk of the sun appears on the Eastern horizon.','2','2','-
-','The Imam should recit Quran  loudly');
INSERT INTO "pray" VALUES('Shurooq','Narrated Aisha (May Allah be pleased with her): Allah''s Messenger (p.b.u.h.) used to pray four Rakats (units) in Duha prayer and added whatever Allah wished for him. (Muslim)
 
 Let it be known that Ishraaq, Salaat ul-Awwabin and Duha are different names that stand for identically the same forenoon prayer. The time of this prayer starts with the sunrise and goes on until one-fourth of the day. The least number of Rakats of Duha prayer is two and the maximum is twelve. The preferable among them are four which are supported and approved by the Ahadith of the Prophet (p.b.u.h.). It may also be remembered that this prayer is a Sunnah of former messengers as they used to offer it during their times. Hence, this prayer is a Sunnah and mustahabb (desirable). As to the matter of this prayer being called a Bidah (innovation) by Umar (May Allah be pleased with him) it only alludes to the fact that if someone develops a habit of continually offering it, it may be regarded as an innovation, as the Prophet (p.b.u.h.) did not always offer it.
 
 Narrated Zaid bin Arqam (May Allah be pleased with him) Allah''s Messenger (p.b.u.h.), "The prayer of those who are penitent is offered when the young weaned camels feel the heat of the sun." (at-Tirmidhi)
 
 This Hadith indicates that the best time of Duha prayer is just before noon.
 
 Narrated Anas (May Allah be pleased with him) Allah''s Messenger (p.b.u.h.) said, "Whoever prays twelve Rakats (units) of Duha, Allah will build a castle for him in Paradise." (at-Tirmidhi who graded it Gharib "unfamiliar, doubtful").
 
 Narrated Aisha (May Allah be pleased with her): Allah''s Messenger (p.b.u.h.) entered my house and prayed eight Rakats (units) of Duha prayer. (Ibn Hibban in his Sahih)
 ',NULL,NULL,NULL,NULL);
INSERT INTO "pray" VALUES('Dhuhr','Its time begins when the sun has declined westward from the middle of the sky (zenith). The time remains until the length of the shadow of an object becomes equal to that of the object per Se, in addition to the length of the shadow of that object when the sun was at its zenith. For example, if the length of your shadow when the sun is at its zenith is 5 feet and you are 6 feet high, then once your shadow becomes 11 feet long the Dhuhr prayer time ends.','2+2','4','2','The Imam should recite Qur''aan in a quiet whisper ');
INSERT INTO "pray" VALUES('Asr','As soon as the Dhuhr prayer time ends the ^Asr prayer time starts. Its time remains until sunset.','-','4','-','The Imam should recite Qur''aan in a quiet whisper ');
INSERT INTO "pray" VALUES('Maghrib','Maghrib(Sunset) prayer
After the entire disk of the sun has set, then the Maghrib prayer time begins. This prayer time lasts until the redness has disappeared in the western horizon.','-','3','2','The Imam should recit Quran  loudly');
INSERT INTO "pray" VALUES('Isha','Isha''(Nightfall) prayer
As soon as the Maghrib prayer time is finished the ^isha'' prayer time begins. You can be certain that this prayer time is in when you can see many small stars in the sky on a clear night. This prayer time lasts until the true dawn appears.','-','4','2','The Imam should recite Qur''aan out loud');
