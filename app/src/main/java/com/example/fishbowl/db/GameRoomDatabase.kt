package com.example.fishbowl.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fishbowl.dao.GameDao
import com.example.fishbowl.modals.Player
import com.example.fishbowl.modals.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val questionsEveryone: List<String> = listOf(
    "Als jouw naam meer dan 4 letters bevat, neem een shotje.",
    "Iedereen noemt het aantal volgers, zonder te kijken, die je hebt op instagram, als je het fout hebt neem een shotje. (Je mag afronden met 10)",
    "Voor de eer van de vissenkom moet iedereen een shotje doen.",
    "Drink als je welleens dronken achter het stuur hebt gezeten.",
    "Drink als je welleens een ongeluk hebt gehad, ongelukken op werk tellen ook!",
    "Drink als je ooit een keer uit een bar of discotheek bent gegooid.",
    "Ieddereen die ooit sex heeft gehad met een vriend of vriendin, moet drinken!",
    "De laatste persoon die zijn of haar telefoon aanraakt, moet drinken!",
    "Iedereen die een string draagt, moet drinken!",
    "Noem alle soorten alcholische drank op.",
    "Iedereen doneer 10 euro naar de maker van de app! Rekening nummer NL03 INGB 0005 4325 22",
    "Alle dames, geef de gene met de naam Tris een kusje. ",
    "Neem een shotje als je altijd DJ bent op een feestje.  ",
    "Alle mannen nemen een shotje",
    "Alle vrouwen nemen een shotje.",
    "Degene die als laatste drinkt, neemt een shotje.",
    "Doe allemaal een sexstandje na, degene die niks meer kan verzinnen, drinken!",
    "Wijs de persoon aan met het minst geld",
    "Wijs de persoon aan, wie het minst wild is in bed, de meeste stemmen moeten drinken",
    "Wijs de persoon aan wie als eerste kinderen zouden krijgen, de meeste stemmen moeten drinken",
    "Wijs de persoon aan wie als eerste zou scheiden, de meeste stemmen moeten drinken",
    "Wijs de moeder van de groep aan, moeders krijgen ook een shotje",
    "Wijs de vader van de groep aan, vaders krijgen ook een shotje",
    "Wijs aan wie het beste kan liegen, die persoon moet shotten",
    "Wijs aan wie altijd telaat komt, deze mag shotten",
    "Wijs de personen aan die op dit feestje/borrel telaat zijn gekomen, shotten!",
    "Wijs de persoon aan die altijd goedgelovig is, die mag shotten."
)

val votes: List<String> = listOf(
    "Liever kunnen vliegen \nof\nsuper sterk zijn?",
    "Liever een keer met een van je ouders doen \nof\neen keer met je broertje of zusje?",
    "Stem allemaal of deze app hard is of niet.",
    "Liever pizza \nof\nkapsalon?",
    "Stem allemaal of de maker van deze app sexy, intelligent en bescheiden is.",
    "Liever trump aan de macht in nederland \nof\nGeert Wilders?",
    "Liever de rest van je leven zonder sex \nof\nzonder muziek?",
    "Liever de rest van je leven zonder benen \nof\nzonder je ouders?",
)

val personalQuestions = listOf(
    "Noem het aantal mensen met wie je het zou doen in deze groep. De anderen moeten raden wie. Als de groep het raadt, moet je een shotje nemen, anders de groep.",
    "{PLAYER} kies een category en noem om en om de dingen die daarbij horen. De eerste die niks meer weet moet drinken.",
    "{PLAYER} heeft 30 seconden om 6 seksstandjes op te noemen. Als het niet lukt, drinken!",
    "{PLAYER} als je sex moest hebben met twee mensen waarvan de leeftijden gecombineerd 30 is, welke leeftijden zou je dan kiezen?",
    "{PLAYER} drink als je welleens de wet hebt overtreden en vertel hoe.",
    "{PLAYER} je bent nog niet dronken genoeg, neem maar een shotje!",
    "{PLAYER} neem een shotje als je welleens naar de stripclub bent geweest.",
    "{PLAYER} wat vind je het leukst aan jezelf? Geef een shotje weg!",
    "{PLAYER} jij hebt geluk, geef maar een shotje weg.",
    "{PLAYER} noem de lelijkste eigenschap van iedereen en geef een shotje weg.",
    "{PLAYER} drink als je welleens sexy berichtjes hebt gestuurd.",
    "{PLAYER} drink als je welleens plast onder de douche.",
    "{PLAYER} drink als je welleens iets hebt gedaan met een collega.",
    "{PLAYER} praat aankomende 15 seconden met een buitenlands accent.",
    "{PLAYER} je hebt 5 seconden om een sexstandje uit te beelden, gooo! Anders drinkenn!",
    "{PLAYER} vertel wat je het lekkerst vindt in bed.",
    "{PLAYER} imiteer iemand na uit een film/serie. Als niemand de personage kan raden, neem een shotje.",
    "{PLAYER} imiteer iemand na uit tuincentrum. Als niemand de personage raadt, neem een shotje.",
    "{PLAYER} drink als je deze week een wilde nacht hebt gehad.",
    "{PLAYER} zet nu een liedje op waar je voor schaamt, maar wel luistert, zo niet drinken!",
    "{PLAYER} noem iets op wat je hebt gedaan tijdens de sex, en nooit meer wilt doen.",
    "{PLAYER}, wat is jouw verborgen talent?",
    "{PLAYER}, waar ben je het beste in in bed?",
    "{PLAYER}, wat is de laatste keer dat je gehuild hebt?",
    "{PLAYER}, welk advies zou jij je jongere zelf geven?",
    "{PLAYER}, als je kon tijdreizen wat zou je veranderen aan de geschiedenis?",
    "{PLAYER}, vertel de laatste zonde die je bent begaan."
)

val personalQuestionWithOtherPlayer = listOf(
    "{PLAYER} en {OTHER_PLAYER} nemen de volle straf!",
    "{PLAYER} noem de namen van {OTHER_PLAYER}'s  ouders.",
    "Iedereen die ouder is dan {PLAYER} moet drinken.",
    "{PLAYER} vs {OTHER_PLAYER} opstaan! Laatste die staat moet drinken!",
    "{PLAYER} speel steen, papier, schaar met {OTHER_PLAYER}. Verliezer drinkt!",
    "{PLAYER} vs {OTHER_PLAYER} strip battle! Degene die het meest sexy danst, mag een shotje uitdelen.",
    "{PLAYER} vs {OTHER_PLAYER} wie als eerst alle provincies van nederland opnoemt, mag een shotje uitdelen",
    "{PLAYER} vs {OTHER_PLAYER} wie het minste pandapunten heeft, mag een shotje uitdelen.",
    "{PLAYER} vs {OTHER_PLAYER} kies allebei de knapste persoon en de lelijkste persoon uit de groep, deze personen moeten drinken.",
    "{PLAYER} vs {OTHER_PLAYER} wie als eerst in zijn neus peutert en in zijn mond stopt, mag een shotje uitdelen aan de ander.",
    "{PLAYER} vs {OTHER_PLAYER}, speel 2 waarheden en 1 leugen. Als {OTHER_PLAYER} de leugen raadt, mag {PLAYER} shotten en anders de groep.",
    "{PLAYER} & {OTHER_PLAYER}, bepaal hoe de familie stamboom eruit zou zien, als deze groep een grote familie is. Wie is de moeder, vader, kinderen, huisdieren, etc.?",
    "{PLAYER} vs {OTHER_PLAYER} at wedstrijdje!",
    "{PLAYER} geeft {OTHER_PLAYER} zijn/haar telefoon en {OTHER_PLAYER} mag een woord opzoeken in whatsapp.",
    "{PLAYER} geeft {OTHER_PLAYER} zijn/haar telefoon en {OTHER_PLAYER} mag een bericht sturen naar iemand, maar {PLAYER} mag het niet zien.",
    "{PLAYER} geeft {OTHER_PLAYER} zijn/haar telefoon en {OTHER_PLAYER} mag iets plaatsen zijn/haar verhaal. ",
    "{PLAYER} belt {OTHER_PLAYERS} baas/chef op om te vertellen dat {PLAYER} niet komt werken morgen. Verzin een goed excuus! "
)

val quizQuestions = listOf(
    "Hoe heet de planeet die het dichtst bij de zon is?#Mercurius",
    "Wat is ananas in het engels?#Pineapple, als je enenas hebt gezegd, shotten!",
    "In welke stad staat de katherdraal La Sagrada Familia?#Barcelona",
    "Wat voor werk doet Lisa Ann?#Porno actrice",
    "Wat is het meest unieke en opvallende eigenschap van Stewie Wonder?#Hij is blind",
    "Waar is de karakter Indiana Jones het meest bang voor?#Slangen",
    "Harry Potter heeft een peet oom, hoe heet hij?#Sirius Black",
    "Wanneer is Tris jarig?#22 Juli",
    "In welk land is Mahatma Ghandi geboren?#India",
    "Wat is de hoofdstad van Australië?#Canberra",
    "Wat is de hoofdstad van Nederland?#Amsterdam",
    "Hoeveel tijdszones zijn er in Rusland?#11",
    "Wat is de nationale bloem in Japan, die we soms ook in het Amsterdamse bos hebben?#Kersenbloem",
    "Hoelang duurt het als de aarde een rondje om de zon maakt?#365 dagen",
    "Noem de langste rivier van de wereld?#De Nijl (6650km)",
    "Welke beroemde graffitikunstenaar komt uit Bristol?#Banksy",
    "Hoe heette de eerste internet?#ARPANET",
    "In Amsterdam is er een coffeeshop geopend door een bekende bokser, van wie?#Mike Tyson the goat",
    "Hoe noem je in de griekse mythologie een half mens en half paard?#Centaur",
    "Wat is de naam van de griekse held die de 12 beproevingen moest doen?#Herakles/Hercules",
    "Wat is de naam van de griekste god van de Zee?#Poseidon",
    "Wat is de naam van de god van Chaos in de Noorse mythologie?#Loki",
    "Met welk dier word de god Fenrir geassocieerd?#Een wolf",
    "In welk jaar hebben Dwayne Johnson en de Rock elkaar ontmoet?#Niet, is de dezelfde persoon.",
    "Wie de kunstenaar van het werk 'Wanderer above the Sea of Fog'? #Caspar David Friedrich",
    "Wie is de kunstenaar van het werk 'De laatste avondmaal'?#Leonardo da Vinci",
    "Wie is de kunstenaar van het werk 'De nachtwacht'?#Rembrandt van Rijn",
    "Wie is de kunstenaar van het surrealisme werk 'Persistence of memory'?#Salvador Dalí",
    "Wie is de band die het liedje counting stars zingt?#OneRepublic",
    "Wie zijn de artiesten van het liedje Pornstar Martini?#Boef, Lil Kleine, \$hirak & Cristian D",
    "Wat is de naam van het vogeltje van het oude logo van Twitter?#Larry verwijzing naar Larry Bird (NBA speler)",
    "Waar kwam Alexander de Grote vandaan?#Marcedonië",
    "In welk land is Adolf Hitler geboren?#Oostenrijk",
    "Welke zee scheidde Mozes in het bijbelse verhaal?#De Rode Zee",
    "Wie maakte een snoekduik tijdens de WK in 2014?#Robin van Persie",
    "Wie is de kunstenaar van het werk 'De Aardapeleters'?#Vincent van Gogh",
    "Bij welke stroming horen de werken van Salvador Dalí?#Surrealisme",
    "Waar staat e.g. voor? (Wordt gebruikt om een voorbeeld te geven)#exempli gratia",
    "Welk van de volgende groepen bevatten geen celkern?\nPlantaardige cellen, Bacteriën of Schimmels.#Bacteriën, Zij hebben geen celkern en is dus een prokaryoot",
    "Wat is de maximale snelheid op de snelweg tussen 6 en 19 uur?#Zo hard als je wilt natuurlijk, zolang je maar uitkijkt voor flitsers!",
    "Een dokter uit Pumerend heeft broer die monteur is in Amsterdam, maar die monteur uit Amsterdam heeft geen broer die dokter is, hoe ken dat?#Is de zus",
    "Stel je voor je hebt 3 broertjes, ze heten 2, 3 en 4. Hoe heet de oudste in dit gezin?#Jouw naam..."
)

@Database(entities = [Player::class, Question::class], version = 7, exportSchema = false)
abstract class GameRoomDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "FISHBOWL_DB"

        @Volatile
        private var INSTANCE: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(GameRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GameRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onOpen(db: SupportSQLiteDatabase) {
                                    super.onOpen(db)
                                    Log.d("TEST", "Database opned")
                                    INSTANCE?.let { gameRoomDatabase ->

                                        CoroutineScope(Dispatchers.IO).launch {

                                            // First reset all.
                                            gameRoomDatabase.gameDao().deleteAllQuestions()

                                            // Put all questions for everyone in.
                                            for (question in questionsEveryone) {
                                                gameRoomDatabase.gameDao().insertQuestion(
                                                    Question(question, 0, false)
                                                )
                                            }

                                            // Put all personal questions in.
                                            for (question in personalQuestions) {
                                                gameRoomDatabase.gameDao().insertQuestion(
                                                    Question(question, 1, false)
                                                )
                                            }

                                            // Put all personal questions in which require another player
                                            for (question in personalQuestionWithOtherPlayer) {
                                                gameRoomDatabase.gameDao().insertQuestion(
                                                    Question(question, 2, true)
                                                )
                                            }

                                            // Put all votes in.
                                            for (vote in votes) {
                                                gameRoomDatabase.gameDao().insertQuestion(
                                                    Question(vote, 3, false)
                                                )
                                            }

                                            // Put all quiz questions in.
                                            for (question in quizQuestions) {
                                                gameRoomDatabase.gameDao().insertQuestion(
                                                    Question(question, 4, false)
                                                )
                                            }
                                        }
                                    }
                                }
                            }).build()
                    }
                }
            }
            return INSTANCE;
        }

    }
}