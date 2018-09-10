package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.battle.Generation;
import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.external.SmogonParser;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.types.Type;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.*;

/**
 * Created by DrSmugleaf on 11/08/2017.
 */
public enum Species {

    ABOMASNOW("Abomasnow"),
    ABOMASNOW_MEGA("Abomasnow-Mega"),
    ABRA("Abra"),
    ABSOL("Absol"),
    ABSOL_MEGA("Absol-Mega"),
    ACCELGOR("Accelgor"),
    AEGISLASH("Aegislash"),
    AEGISLASH_BLADE("Aegislash-Blade"),
    AERODACTYL("Aerodactyl"),
    AERODACTYL_MEGA("Aerodactyl-Mega"),
    AGGRON("Aggron"),
    AGGRON_MEGA("Aggron-Mega"),
    AIPOM("Aipom"),
    ALAKAZAM("Alakazam"),
    ALAKAZAM_MEGA("Alakazam-Mega"),
    ALOMOMOLA("Alomomola"),
    ALTARIA("Altaria"),
    ALTARIA_MEGA("Altaria-Mega"),
    AMAURA("Amaura"),
    AMBIPOM("Ambipom"),
    AMOONGUSS("Amoonguss"),
    AMPHAROS("Ampharos"),
    AMPHAROS_MEGA("Ampharos-Mega"),
    ANORITH("Anorith"),
    ARAQUANID("Araquanid"),
    ARBOK("Arbok"),
    ARCANINE("Arcanine"),
    ARCEUS("Arceus", Gender.GENDERLESS),
    ARCEUS_BUG("Arceus-Bug", Gender.GENDERLESS),
    ARCEUS_DARK("Arceus-Dark", Gender.GENDERLESS),
    ARCEUS_DRAGON("Arceus-Dragon", Gender.GENDERLESS),
    ARCEUS_ELECTRIC("Arceus-Electric", Gender.GENDERLESS),
    ARCEUS_FAIRY("Arceus-Fairy", Gender.GENDERLESS),
    ARCEUS_FIGHTING("Arceus-Fighting", Gender.GENDERLESS),
    ARCEUS_FIRE("Arceus-Fire", Gender.GENDERLESS),
    ARCEUS_FLYING("Arceus-Flying", Gender.GENDERLESS),
    ARCEUS_GHOST("Arceus-Ghost", Gender.GENDERLESS),
    ARCEUS_GRASS("Arceus-Grass", Gender.GENDERLESS),
    ARCEUS_GROUND("Arceus-Ground", Gender.GENDERLESS),
    ARCEUS_ICE("Arceus-Ice", Gender.GENDERLESS),
    ARCEUS_POISON("Arceus-Poison", Gender.GENDERLESS),
    ARCEUS_PSYCHIC("Arceus-Psychic", Gender.GENDERLESS),
    ARCEUS_ROCK("Arceus-Rock", Gender.GENDERLESS),
    ARCEUS_STEEL("Arceus-Steel", Gender.GENDERLESS),
    ARCEUS_WATER("Arceus-Water", Gender.GENDERLESS),
    ARCHEN("Archen"),
    ARCHEOPS("Archeops"),
    ARIADOS("Ariados"),
    ARMALDO("Armaldo"),
    AROMATISSE("Aromatisse"),
    ARON("Aron"),
    ARTICUNO("Articuno", Gender.GENDERLESS),
    AUDINO("Audino"),
    AUDINO_MEGA("Audino-Mega"),
    AURORUS("Aurorus"),
    AVALUGG("Avalugg"),
    AXEW("Axew"),
    AZELF("Azelf", Gender.GENDERLESS),
    AZUMARILL("Azumarill"),
    AZURILL("Azurill"),
    BAGON("Bagon"),
    BALTOY("Baltoy", Gender.GENDERLESS),
    BANETTE("Banette"),
    BANETTE_MEGA("Banette-Mega"),
    BARBARACLE("Barbaracle"),
    BARBOACH("Barboach"),
    BASCULIN("Basculin"),
    BASTIODON("Bastiodon"),
    BAYLEEF("Bayleef"),
    BEARTIC("Beartic"),
    BEAUTIFLY("Beautifly"),
    BEEDRILL("Beedrill"),
    BEEDRILL_MEGA("Beedrill-Mega"),
    BEHEEYEM("Beheeyem"),
    BELDUM("Beldum", Gender.GENDERLESS),
    BELLOSSOM("Bellossom"),
    BELLSPROUT("Bellsprout"),
    BERGMITE("Bergmite"),
    BEWEAR("Bewear"),
    BIBAREL("Bibarel"),
    BIDOOF("Bidoof"),
    BINACLE("Binacle"),
    BISHARP("Bisharp"),
    BLACEPHALON("Blacephalon", Gender.GENDERLESS),
    BLASTOISE("Blastoise"),
    BLASTOISE_MEGA("Blastoise-Mega"),
    BLAZIKEN("Blaziken"),
    BLAZIKEN_MEGA("Blaziken-Mega"),
    BLISSEY("Blissey", Gender.FEMALE),
    BLITZLE("Blitzle"),
    BOLDORE("Boldore"),
    BONSLY("Bonsly"),
    BOUFFALANT("Bouffalant"),
    BOUNSWEET("Bounsweet", Gender.FEMALE),
    BRAIXEN("Braixen"),
    BRAVIARY("Braviary", Gender.MALE),
    BRELOOM("Breloom"),
    BRIONNE("Brionne"),
    BRONZONG("Bronzong", Gender.GENDERLESS),
    BRONZOR("Bronzor", Gender.GENDERLESS),
    BRUXISH("Bruxish"),
    BUDEW("Budew"),
    BUIZEL("Buizel"),
    BULBASAUR("Bulbasaur"),
    BUNEARY("Buneary"),
    BUNNELBY("Bunnelby"),
    BURMY("Burmy"),
    BUTTERFREE("Butterfree"),
    BUZZWOLE("Buzzwole", Gender.GENDERLESS),
    CACNEA("Cacnea"),
    CACTURNE("Cacturne"),
    CAMERUPT("Camerupt"),
    CAMERUPT_MEGA("Camerupt-Mega"),
    CARBINK("Carbink", Gender.GENDERLESS),
    CARNIVINE("Carnivine"),
    CARRACOSTA("Carracosta"),
    CARVANHA("Carvanha"),
    CASCOON("Cascoon"),
    CASTFORM("Castform"),
    CATERPIE("Caterpie"),
    CELEBI("Celebi", Gender.GENDERLESS),
    CELESTEELA("Celesteela", Gender.GENDERLESS),
    CHANDELURE("Chandelure"),
    CHANSEY("Chansey", Gender.FEMALE),
    CHARIZARD("Charizard"),
    CHARIZARD_MEGA_X("Charizard-Mega-X"),
    CHARIZARD_MEGA_Y("Charizard-Mega-Y"),
    CHARJABUG("Charjabug"),
    CHARMANDER("Charmander"),
    CHARMELEON("Charmeleon"),
    CHATOT("Chatot"),
    CHERRIM("Cherrim"),
    CHERUBI("Cherubi"),
    CHESNAUGHT("Chesnaught"),
    CHESPIN("Chespin"),
    CHIKORITA("Chikorita"),
    CHIMCHAR("Chimchar"),
    CHIMECHO("Chimecho"),
    CHINCHOU("Chinchou"),
    CHINGLING("Chingling"),
    CINCCINO("Cinccino"),
    CLAMPERL("Clamperl"),
    CLAUNCHER("Clauncher"),
    CLAWITZER("Clawitzer"),
    CLAYDOL("Claydol", Gender.GENDERLESS),
    CLEFABLE("Clefable"),
    CLEFAIRY("Clefairy"),
    CLEFFA("Cleffa"),
    CLOYSTER("Cloyster"),
    COBALION("Cobalion", Gender.GENDERLESS),
    COFAGRIGUS("Cofagrigus"),
    COMBEE("Combee"),
    COMBUSKEN("Combusken"),
    COMFEY("Comfey"),
    CONKELDURR("Conkeldurr"),
    CORPHISH("Corphish"),
    CORSOLA("Corsola"),
    COSMOEM("Cosmoem", Gender.GENDERLESS),
    COSMOG("Cosmog", Gender.GENDERLESS),
    COTTONEE("Cottonee"),
    CRABOMINABLE("Crabominable"),
    CRABRAWLER("Crabrawler"),
    CRADILY("Cradily"),
    CRANIDOS("Cranidos"),
    CRAWDAUNT("Crawdaunt"),
    CRESSELIA("Cresselia", Gender.FEMALE),
    CROAGUNK("Croagunk"),
    CROBAT("Crobat"),
    CROCONAW("Croconaw"),
    CRUSTLE("Crustle"),
    CRYOGONAL("Cryogonal", Gender.GENDERLESS),
    CUBCHOO("Cubchoo"),
    CUBONE("Cubone"),
    CUTIEFLY("Cutiefly"),
    CYNDAQUIL("Cyndaquil"),
    DARKRAI("Darkrai", Gender.GENDERLESS),
    DARMANITAN("Darmanitan"),
    DARMANITAN_ZEN("Darmanitan-Zen"),
    DARTRIX("Dartrix"),
    DARUMAKA("Darumaka"),
    DECIDUEYE("Decidueye"),
    DEDENNE("Dedenne"),
    DEERLING("Deerling"),
    DEINO("Deino"),
    DELCATTY("Delcatty"),
    DELIBIRD("Delibird"),
    DELPHOX("Delphox"),
    DEOXYS("Deoxys", Gender.GENDERLESS),
    DEOXYS_ATTACK("Deoxys-Attack", Gender.GENDERLESS),
    DEOXYS_DEFENSE("Deoxys-Defense", Gender.GENDERLESS),
    DEOXYS_SPEED("Deoxys-Speed", Gender.GENDERLESS),
    DEWGONG("Dewgong"),
    DEWOTT("Dewott"),
    DEWPIDER("Dewpider"),
    DHELMISE("Dhelmise", Gender.GENDERLESS),
    DIALGA("Dialga", Gender.GENDERLESS),
    DIANCIE("Diancie", Gender.GENDERLESS),
    DIANCIE_MEGA("Diancie-Mega", Gender.GENDERLESS),
    DIGGERSBY("Diggersby"),
    DIGLETT("Diglett"),
    DIGLETT_ALOLA("Diglett-Alola"),
    DITTO("Ditto", Gender.GENDERLESS),
    DODRIO("Dodrio"),
    DODUO("Doduo"),
    DONPHAN("Donphan"),
    DOUBLADE("Doublade"),
    DRAGALGE("Dragalge"),
    DRAGONAIR("Dragonair"),
    DRAGONITE("Dragonite"),
    DRAMPA("Drampa"),
    DRAPION("Drapion"),
    DRATINI("Dratini"),
    DRIFBLIM("Drifblim"),
    DRIFLOON("Drifloon"),
    DRILBUR("Drilbur"),
    DROWZEE("Drowzee"),
    DRUDDIGON("Druddigon"),
    DUCKLETT("Ducklett"),
    DUGTRIO("Dugtrio"),
    DUGTRIO_ALOLA("Dugtrio-Alola"),
    DUNSPARCE("Dunsparce"),
    DUOSION("Duosion"),
    DURANT("Durant"),
    DUSCLOPS("Dusclops"),
    DUSKNOIR("Dusknoir"),
    DUSKULL("Duskull"),
    DUSTOX("Dustox"),
    DWEBBLE("Dwebble"),
    EELEKTRIK("Eelektrik"),
    EELEKTROSS("Eelektross"),
    EEVEE("Eevee"),
    EKANS("Ekans"),
    ELECTABUZZ("Electabuzz"),
    ELECTIVIRE("Electivire"),
    ELECTRIKE("Electrike"),
    ELECTRODE("Electrode", Gender.GENDERLESS),
    ELEKID("Elekid"),
    ELGYEM("Elgyem"),
    EMBOAR("Emboar"),
    EMOLGA("Emolga"),
    EMPOLEON("Empoleon"),
    ENTEI("Entei", Gender.GENDERLESS),
    ESCAVALIER("Escavalier"),
    ESPEON("Espeon"),
    ESPURR("Espurr"),
    EXCADRILL("Excadrill"),
    EXEGGCUTE("Exeggcute"),
    EXEGGUTOR("Exeggutor"),
    EXEGGUTOR_ALOLA("Exeggutor-Alola"),
    EXPLOUD("Exploud"),
    FARFETCHD("Farfetch'd"),
    FEAROW("Fearow"),
    FEEBAS("Feebas"),
    FENNEKIN("Fennekin"),
    FERALIGATR("Feraligatr"),
    FERROSEED("Ferroseed"),
    FERROTHORN("Ferrothorn"),
    FINNEON("Finneon"),
    FLAAFFY("Flaaffy"),
    FLABEBE("Flabebe", Gender.FEMALE),
    FLAREON("Flareon"),
    FLETCHINDER("Fletchinder"),
    FLETCHLING("Fletchling"),
    FLOATZEL("Floatzel"),
    FLOETTE("Floette", Gender.FEMALE),
    FLOETTE_ETERNAL("Floette-Eternal", Gender.FEMALE),
    FLORGES("Florges", Gender.FEMALE),
    FLYGON("Flygon"),
    FOMANTIS("Fomantis"),
    FOONGUS("Foongus"),
    FORRETRESS("Forretress"),
    FRAXURE("Fraxure"),
    FRILLISH("Frillish"),
    FROAKIE("Froakie"),
    FROGADIER("Frogadier"),
    FROSLASS("Froslass", Gender.FEMALE),
    FURFROU("Furfrou"),
    FURRET("Furret"),
    GABITE("Gabite"),
    GALLADE("Gallade", Gender.MALE),
    GALLADE_MEGA("Gallade-Mega", Gender.MALE),
    GALVANTULA("Galvantula"),
    GARBODOR("Garbodor"),
    GARCHOMP("Garchomp"),
    GARCHOMP_MEGA("Garchomp-Mega"),
    GARDEVOIR("Gardevoir"),
    GARDEVOIR_MEGA("Gardevoir-Mega"),
    GASTLY("Gastly"),
    GASTRODON("Gastrodon"),
    GENESECT("Genesect", Gender.GENDERLESS),
    GENGAR("Gengar"),
    GENGAR_MEGA("Gengar-Mega"),
    GEODUDE("Geodude"),
    GEODUDE_ALOLA("Geodude-Alola"),
    GIBLE("Gible"),
    GIGALITH("Gigalith"),
    GIRAFARIG("Girafarig"),
    GIRATINA("Giratina", Gender.GENDERLESS),
    GIRATINA_ORIGIN("Giratina-Origin", Gender.GENDERLESS),
    GLACEON("Glaceon"),
    GLALIE("Glalie"),
    GLALIE_MEGA("Glalie-Mega"),
    GLAMEOW("Glameow"),
    GLIGAR("Gligar"),
    GLISCOR("Gliscor"),
    GLOOM("Gloom"),
    GOGOAT("Gogoat"),
    GOLBAT("Golbat"),
    GOLDEEN("Goldeen"),
    GOLDUCK("Golduck"),
    GOLEM("Golem"),
    GOLEM_ALOLA("Golem-Alola"),
    GOLETT("Golett", Gender.GENDERLESS),
    GOLISOPOD("Golisopod"),
    GOLURK("Golurk", Gender.GENDERLESS),
    GOODRA("Goodra"),
    GOOMY("Goomy"),
    GOREBYSS("Gorebyss"),
    GOTHITA("Gothita"),
    GOTHITELLE("Gothitelle"),
    GOTHORITA("Gothorita"),
    GOURGEIST("Gourgeist"),
    GOURGEIST_LARGE("Gourgeist-Large"),
    GOURGEIST_SMALL("Gourgeist-Small"),
    GOURGEIST_SUPER("Gourgeist-Super"),
    GRANBULL("Granbull"),
    GRAVELER("Graveler"),
    GRAVELER_ALOLA("Graveler-Alola"),
    GRENINJA("Greninja"),
    GRENINJA_ASH("Greninja-Ash"),
    GRIMER("Grimer"),
    GRIMER_ALOLA("Grimer-Alola"),
    GROTLE("Grotle"),
    GROUDON("Groudon", Gender.GENDERLESS),
    GROUDON_PRIMAL("Groudon-Primal", Gender.GENDERLESS),
    GROVYLE("Grovyle"),
    GROWLITHE("Growlithe"),
    GRUBBIN("Grubbin"),
    GRUMPIG("Grumpig"),
    GULPIN("Gulpin"),
    GUMSHOOS("Gumshoos"),
    GURDURR("Gurdurr"),
    GUZZLORD("Guzzlord", Gender.GENDERLESS),
    GYARADOS("Gyarados"),
    GYARADOS_MEGA("Gyarados-Mega"),
    HAKAMO_O("Hakamo-o"),
    HAPPINY("Happiny", Gender.FEMALE),
    HARIYAMA("Hariyama"),
    HAUNTER("Haunter"),
    HAWLUCHA("Hawlucha"),
    HAXORUS("Haxorus"),
    HEATMOR("Heatmor"),
    HEATRAN("Heatran"),
    HELIOLISK("Heliolisk"),
    HELIOPTILE("Helioptile"),
    HERACROSS("Heracross"),
    HERACROSS_MEGA("Heracross-Mega"),
    HERDIER("Herdier"),
    HIPPOPOTAS("Hippopotas"),
    HIPPOWDON("Hippowdon"),
    HITMONCHAN("Hitmonchan", Gender.MALE),
    HITMONLEE("Hitmonlee", Gender.MALE),
    HITMONTOP("Hitmontop", Gender.MALE),
    HO_OH("Ho-Oh", Gender.GENDERLESS),
    HONCHKROW("Honchkrow"),
    HONEDGE("Honedge"),
    HOOPA("Hoopa", Gender.GENDERLESS),
    HOOPA_UNBOUND("Hoopa-Unbound", Gender.GENDERLESS),
    HOOTHOOT("Hoothoot"),
    HOPPIP("Hoppip"),
    HORSEA("Horsea"),
    HOUNDOOM("Houndoom"),
    HOUNDOOM_MEGA("Houndoom-Mega"),
    HOUNDOUR("Houndour"),
    HUNTAIL("Huntail"),
    HYDREIGON("Hydreigon"),
    HYPNO("Hypno"),
    IGGLYBUFF("Igglybuff"),
    ILLUMISE("Illumise", Gender.FEMALE),
    INCINEROAR("Incineroar"),
    INFERNAPE("Infernape"),
    INKAY("Inkay"),
    IVYSAUR("Ivysaur"),
    JANGMO_O("Jangmo-o"),
    JELLICENT("Jellicent"),
    JIGGLYPUFF("Jigglypuff"),
    JIRACHI("Jirachi", Gender.GENDERLESS),
    JOLTEON("Jolteon"),
    JOLTIK("Joltik"),
    JUMPLUFF("Jumpluff"),
    JYNX("Jynx", Gender.FEMALE),
    KABUTO("Kabuto"),
    KABUTOPS("Kabutops"),
    KADABRA("Kadabra"),
    KAKUNA("Kakuna"),
    KANGASKHAN("Kangaskhan", Gender.FEMALE),
    KANGASKHAN_MEGA("Kangaskhan-Mega", Gender.FEMALE),
    KARRABLAST("Karrablast"),
    KARTANA("Kartana", Gender.GENDERLESS),
    KECLEON("Kecleon"),
    KELDEO("Keldeo", Gender.GENDERLESS),
    KINGDRA("Kingdra"),
    KINGLER("Kingler"),
    KIRLIA("Kirlia"),
    KLANG("Klang", Gender.GENDERLESS),
    KLEFKI("Klefki"),
    KLINK("Klink", Gender.GENDERLESS),
    KLINKLANG("Klinklang", Gender.GENDERLESS),
    KOFFING("Koffing"),
    KOMALA("Komala"),
    KOMMO_O("Kommo-o"),
    KRABBY("Krabby"),
    KRICKETOT("Kricketot"),
    KRICKETUNE("Kricketune"),
    KROKOROK("Krokorok"),
    KROOKODILE("Krookodile"),
    KYOGRE("Kyogre", Gender.GENDERLESS),
    KYOGRE_PRIMAL("Kyogre-Primal", Gender.GENDERLESS),
    KYUREM("Kyurem", Gender.GENDERLESS),
    KYUREM_BLACK("Kyurem-Black", Gender.GENDERLESS),
    KYUREM_WHITE("Kyurem-White", Gender.GENDERLESS),
    LAIRON("Lairon"),
    LAMPENT("Lampent"),
    LANDORUS("Landorus", Gender.MALE),
    LANDORUS_THERIAN("Landorus-Therian", Gender.MALE),
    LANTURN("Lanturn"),
    LAPRAS("Lapras"),
    LARVESTA("Larvesta"),
    LARVITAR("Larvitar"),
    LATIAS("Latias", Gender.FEMALE),
    LATIAS_MEGA("Latias-Mega", Gender.FEMALE),
    LATIOS("Latios", Gender.MALE),
    LATIOS_MEGA("Latios-Mega", Gender.MALE),
    LEAFEON("Leafeon"),
    LEAVANNY("Leavanny"),
    LEDIAN("Ledian"),
    LEDYBA("Ledyba"),
    LICKILICKY("Lickilicky"),
    LICKITUNG("Lickitung"),
    LIEPARD("Liepard"),
    LILEEP("Lileep"),
    LILLIGANT("Lilligant", Gender.FEMALE),
    LILLIPUP("Lillipup"),
    LINOONE("Linoone"),
    LITLEO("Litleo"),
    LITTEN("Litten"),
    LITWICK("Litwick"),
    LOMBRE("Lombre"),
    LOPUNNY("Lopunny"),
    LOPUNNY_MEGA("Lopunny-Mega"),
    LOTAD("Lotad"),
    LOUDRED("Loudred"),
    LUCARIO("Lucario"),
    LUCARIO_MEGA("Lucario-Mega"),
    LUDICOLO("Ludicolo"),
    LUGIA("Lugia", Gender.GENDERLESS),
    LUMINEON("Lumineon"),
    LUNALA("Lunala", Gender.GENDERLESS),
    LUNATONE("Lunatone", Gender.GENDERLESS),
    LURANTIS("Lurantis"),
    LUVDISC("Luvdisc"),
    LUXIO("Luxio"),
    LUXRAY("Luxray"),
    LYCANROC("Lycanroc"),
    LYCANROC_DUSK("Lycanroc-Dusk"),
    LYCANROC_MIDNIGHT("Lycanroc-Midnight"),
    MACHAMP("Machamp"),
    MACHOKE("Machoke"),
    MACHOP("Machop"),
    MAGBY("Magby"),
    MAGCARGO("Magcargo"),
    MAGEARNA("Magearna", Gender.GENDERLESS),
    MAGIKARP("Magikarp"),
    MAGMAR("Magmar"),
    MAGMORTAR("Magmortar"),
    MAGNEMITE("Magnemite", Gender.GENDERLESS),
    MAGNETON("Magneton", Gender.GENDERLESS),
    MAGNEZONE("Magnezone", Gender.GENDERLESS),
    MAKUHITA("Makuhita"),
    MALAMAR("Malamar"),
    MAMOSWINE("Mamoswine"),
    MANAPHY("Manaphy", Gender.GENDERLESS),
    MANDIBUZZ("Mandibuzz", Gender.FEMALE),
    MANECTRIC("Manectric"),
    MANECTRIC_MEGA("Manectric-Mega"),
    MANKEY("Mankey"),
    MANTINE("Mantine"),
    MANTYKE("Mantyke"),
    MARACTUS("Maractus"),
    MAREANIE("Mareanie"),
    MAREEP("Mareep"),
    MARILL("Marill"),
    MAROWAK("Marowak"),
    MAROWAK_ALOLA("Marowak-Alola"),
    MARSHADOW("Marshadow", Gender.GENDERLESS),
    MARSHTOMP("Marshtomp"),
    MASQUERAIN("Masquerain"),
    MAWILE("Mawile"),
    MAWILE_MEGA("Mawile-Mega"),
    MEDICHAM("Medicham"),
    MEDICHAM_MEGA("Medicham-Mega"),
    MEDITITE("Meditite"),
    MEGANIUM("Meganium"),
    MELOETTA("Meloetta", Gender.GENDERLESS),
    MELOETTA_PIROUETTE("Meloetta-Pirouette"),
    MEOWSTIC_F("Meowstic-F", Gender.FEMALE),
    MEOWSTIC_M("Meowstic-M", Gender.MALE),
    MEOWTH("Meowth"),
    MEOWTH_ALOLA("Meowth-Alola"),
    MESPRIT("Mesprit", Gender.GENDERLESS),
    METAGROSS("Metagross", Gender.GENDERLESS),
    METAGROSS_MEGA("Metagross-Mega", Gender.GENDERLESS),
    METANG("Metang", Gender.GENDERLESS),
    METAPOD("Metapod"),
    MEW("Mew", Gender.GENDERLESS),
    MEWTWO("Mewtwo", Gender.GENDERLESS),
    MEWTWO_MEGA_X("Mewtwo-Mega-X", Gender.GENDERLESS),
    MEWTWO_MEGA_Y("Mewtwo-Mega-Y", Gender.GENDERLESS),
    MIENFOO("Mienfoo"),
    MIENSHAO("Mienshao"),
    MIGHTYENA("Mightyena"),
    MILOTIC("Milotic"),
    MILTANK("Miltank", Gender.FEMALE),
    MIME_JR("Mime Jr."),
    MIMIKYU("Mimikyu"),
    MINCCINO("Minccino"),
    MINIOR("Minior", Gender.GENDERLESS),
    MINIOR_METEOR("Minior-Meteor", Gender.GENDERLESS),
    MINUN("Minun"),
    MISDREAVUS("Misdreavus"),
    MISMAGIUS("Mismagius"),
    MOLTRES("Moltres", Gender.GENDERLESS),
    MONFERNO("Monferno"),
    MORELULL("Morelull"),
    MOTHIM("Mothim", Gender.MALE),
    MR_MIME("Mr. Mime"),
    MUDBRAY("Mudbray"),
    MUDKIP("Mudkip"),
    MUDSDALE("Mudsdale"),
    MUK("Muk"),
    MUK_ALOLA("Muk-Alola"),
    MUNCHLAX("Munchlax"),
    MUNNA("Munna"),
    MURKROW("Murkrow"),
    MUSHARNA("Musharna"),
    NAGANADEL("Naganadel", Gender.GENDERLESS),
    NATU("Natu"),
    NECROZMA("Necrozma", Gender.GENDERLESS),
    NECROZMA_DAWN_WINGS("Necrozma-Dawn Wings", Gender.GENDERLESS),
    NECROZMA_DAWN_WINGS_ULTRA("Necrozma-Dawn Wings-Ultra", Gender.GENDERLESS),
    NECROZMA_DUSK_MANE("Necrozma-Dusk Mane", Gender.GENDERLESS),
    NECROZMA_DUSK_MANE_ULTRA("Necrozma-Dusk Mane-Ultra", Gender.GENDERLESS),
    NIDOKING("Nidoking", Gender.MALE),
    NIDOQUEEN("Nidoqueen", Gender.FEMALE),
    NIDORAN_F("Nidoran-F", Gender.FEMALE),
    NIDORAN_M("Nidoran-M", Gender.MALE),
    NIDORINA("Nidorina", Gender.FEMALE),
    NIDORINO("Nidorino", Gender.MALE),
    NIHILEGO("Nihilego", Gender.GENDERLESS),
    NINCADA("Nincada"),
    NINETALES("Ninetales"),
    NINETALES_ALOLA("Ninetales-Alola"),
    NINJASK("Ninjask"),
    NOCTOWL("Noctowl"),
    NOIBAT("Noibat"),
    NOIVERN("Noivern"),
    NOSEPASS("Nosepass"),
    NUMEL("Numel"),
    NUZLEAF("Nuzleaf"),
    OCTILLERY("Octillery"),
    ODDISH("Oddish"),
    OMANYTE("Omanyte"),
    OMASTAR("Omastar"),
    ONIX("Onix"),
    ORANGURU("Oranguru"),
    ORICORIO("Oricorio"),
    ORICORIO_PAU("Oricorio-Pa'u"),
    ORICORIO_POM_POM("Oricorio-Pom-Pom"),
    ORICORIO_SENSU("Oricorio-Sensu"),
    OSHAWOTT("Oshawott"),
    PACHIRISU("Pachirisu"),
    PALKIA("Palkia", Gender.GENDERLESS),
    PALOSSAND("Palossand"),
    PALPITOAD("Palpitoad"),
    PANCHAM("Pancham"),
    PANGORO("Pangoro"),
    PANPOUR("Panpour"),
    PANSAGE("Pansage"),
    PANSEAR("Pansear"),
    PARAS("Paras"),
    PARASECT("Parasect"),
    PASSIMIAN("Passimian"),
    PATRAT("Patrat"),
    PAWNIARD("Pawniard"),
    PELIPPER("Pelipper"),
    PERSIAN("Persian"),
    PERSIAN_ALOLA("Persian-Alola"),
    PETILIL("Petilil", Gender.FEMALE),
    PHANPY("Phanpy"),
    PHANTUMP("Phantump"),
    PHEROMOSA("Pheromosa", Gender.GENDERLESS),
    PHIONE("Phione", Gender.GENDERLESS),
    PICHU("Pichu"),
    PIDGEOT("Pidgeot"),
    PIDGEOT_MEGA("Pidgeot-Mega"),
    PIDGEOTTO("Pidgeotto"),
    PIDGEY("Pidgey"),
    PIDOVE("Pidove"),
    PIGNITE("Pignite"),
    PIKACHU("Pikachu"),
    PIKIPEK("Pikipek"),
    PILOSWINE("Piloswine"),
    PINECO("Pineco"),
    PINSIR("Pinsir"),
    PINSIR_MEGA("Pinsir-Mega"),
    PIPLUP("Piplup"),
    PLUSLE("Plusle"),
    POIPOLE("Poipole", Gender.GENDERLESS),
    POLITOED("Politoed"),
    POLIWAG("Poliwag"),
    POLIWHIRL("Poliwhirl"),
    POLIWRATH("Poliwrath"),
    PONYTA("Ponyta"),
    POOCHYENA("Poochyena"),
    POPPLIO("Popplio"),
    PORYGON2("Porygon2", Gender.GENDERLESS),
    PORYGON("Porygon", Gender.GENDERLESS),
    PORYGON_Z("Porygon-Z", Gender.GENDERLESS),
    PRIMARINA("Primarina"),
    PRIMEAPE("Primeape"),
    PRINPLUP("Prinplup"),
    PROBOPASS("Probopass"),
    PSYDUCK("Psyduck"),
    PUMPKABOO("Pumpkaboo"),
    PUMPKABOO_LARGE("Pumpkaboo-Large"),
    PUMPKABOO_SMALL("Pumpkaboo-Small"),
    PUMPKABOO_SUPER("Pumpkaboo-Super"),
    PUPITAR("Pupitar"),
    PURRLOIN("Purrloin"),
    PURUGLY("Purugly"),
    PYROAR("Pyroar"),
    PYUKUMUKU("Pyukumuku"),
    QUAGSIRE("Quagsire"),
    QUILAVA("Quilava"),
    QUILLADIN("Quilladin"),
    QWILFISH("Qwilfish"),
    RAICHU("Raichu"),
    RAICHU_ALOLA("Raichu-Alola"),
    RAIKOU("Raikou", Gender.GENDERLESS),
    RALTS("Ralts"),
    RAMPARDOS("Rampardos"),
    RAPIDASH("Rapidash"),
    RATICATE("Raticate"),
    RATICATE_ALOLA("Raticate-Alola"),
    RATTATA("Rattata"),
    RATTATA_ALOLA("Rattata-Alola"),
    RAYQUAZA("Rayquaza", Gender.GENDERLESS),
    RAYQUAZA_MEGA("Rayquaza-Mega", Gender.GENDERLESS),
    REGICE("Regice", Gender.GENDERLESS),
    REGIGIGAS("Regigigas", Gender.GENDERLESS),
    REGIROCK("Regirock", Gender.GENDERLESS),
    REGISTEEL("Registeel", Gender.GENDERLESS),
    RELICANTH("Relicanth"),
    REMORAID("Remoraid"),
    RESHIRAM("Reshiram", Gender.GENDERLESS),
    REUNICLUS("Reuniclus"),
    RHYDON("Rhydon"),
    RHYHORN("Rhyhorn"),
    RHYPERIOR("Rhyperior"),
    RIBOMBEE("Ribombee"),
    RIOLU("Riolu"),
    ROCKRUFF("Rockruff"),
    ROGGENROLA("Roggenrola"),
    ROSELIA("Roselia"),
    ROSERADE("Roserade"),
    ROTOM("Rotom", Gender.GENDERLESS),
    ROTOM_FAN("Rotom-Fan", Gender.GENDERLESS),
    ROTOM_FROST("Rotom-Frost", Gender.GENDERLESS),
    ROTOM_HEAT("Rotom-Heat", Gender.GENDERLESS),
    ROTOM_MOW("Rotom-Mow", Gender.GENDERLESS),
    ROTOM_WASH("Rotom-Wash", Gender.GENDERLESS),
    ROWLET("Rowlet"),
    RUFFLET("Rufflet", Gender.MALE),
    SABLEYE("Sableye"),
    SABLEYE_MEGA("Sableye-Mega"),
    SALAMENCE("Salamence"),
    SALAMENCE_MEGA("Salamence-Mega"),
    SALANDIT("Salandit"),
    SALAZZLE("Salazzle", Gender.FEMALE),
    SAMUROTT("Samurott"),
    SANDILE("Sandile"),
    SANDSHREW("Sandshrew"),
    SANDSHREW_ALOLA("Sandshrew-Alola"),
    SANDSLASH("Sandslash"),
    SANDSLASH_ALOLA("Sandslash-Alola"),
    SANDYGAST("Sandygast"),
    SAWK("Sawk", Gender.MALE),
    SAWSBUCK("Sawsbuck"),
    SCATTERBUG("Scatterbug"),
    SCEPTILE("Sceptile"),
    SCEPTILE_MEGA("Sceptile-Mega"),
    SCIZOR("Scizor"),
    SCIZOR_MEGA("Scizor-Mega"),
    SCOLIPEDE("Scolipede"),
    SCRAFTY("Scrafty"),
    SCRAGGY("Scraggy"),
    SCYTHER("Scyther"),
    SEADRA("Seadra"),
    SEAKING("Seaking"),
    SEALEO("Sealeo"),
    SEEDOT("Seedot"),
    SEEL("Seel"),
    SEISMITOAD("Seismitoad"),
    SENTRET("Sentret"),
    SERPERIOR("Serperior"),
    SERVINE("Servine"),
    SEVIPER("Seviper"),
    SEWADDLE("Sewaddle"),
    SHARPEDO("Sharpedo"),
    SHARPEDO_MEGA("Sharpedo-Mega"),
    SHAYMIN("Shaymin", Gender.GENDERLESS),
    SHAYMIN_SKY("Shaymin-Sky", Gender.GENDERLESS),
    SHEDINJA("Shedinja", Gender.GENDERLESS),
    SHELGON("Shelgon"),
    SHELLDER("Shellder"),
    SHELLOS("Shellos"),
    SHELMET("Shelmet"),
    SHIELDON("Shieldon"),
    SHIFTRY("Shiftry"),
    SHIINOTIC("Shiinotic"),
    SHINX("Shinx"),
    SHROOMISH("Shroomish"),
    SHUCKLE("Shuckle"),
    SHUPPET("Shuppet"),
    SIGILYPH("Sigilyph"),
    SILCOON("Silcoon"),
    SILVALLY("Silvally", Gender.GENDERLESS),
    SILVALLY_BUG("Silvally-Bug", Gender.GENDERLESS),
    SILVALLY_DARK("Silvally-Dark", Gender.GENDERLESS),
    SILVALLY_DRAGON("Silvally-Dragon", Gender.GENDERLESS),
    SILVALLY_ELECTRIC("Silvally-Electric", Gender.GENDERLESS),
    SILVALLY_FAIRY("Silvally-Fairy", Gender.GENDERLESS),
    SILVALLY_FIGHTING("Silvally-Fighting", Gender.GENDERLESS),
    SILVALLY_FIRE("Silvally-Fire", Gender.GENDERLESS),
    SILVALLY_FLYING("Silvally-Flying", Gender.GENDERLESS),
    SILVALLY_GHOST("Silvally-Ghost", Gender.GENDERLESS),
    SILVALLY_GRASS("Silvally-Grass", Gender.GENDERLESS),
    SILVALLY_GROUND("Silvally-Ground", Gender.GENDERLESS),
    SILVALLY_ICE("Silvally-Ice", Gender.GENDERLESS),
    SILVALLY_POISON("Silvally-Poison", Gender.GENDERLESS),
    SILVALLY_PSYCHIC("Silvally-Psychic", Gender.GENDERLESS),
    SILVALLY_ROCK("Silvally-Rock", Gender.GENDERLESS),
    SILVALLY_STEEL("Silvally-Steel", Gender.GENDERLESS),
    SILVALLY_WATER("Silvally-Water", Gender.GENDERLESS),
    SIMIPOUR("Simipour"),
    SIMISAGE("Simisage"),
    SIMISEAR("Simisear"),
    SKARMORY("Skarmory"),
    SKIDDO("Skiddo"),
    SKIPLOOM("Skiploom"),
    SKITTY("Skitty"),
    SKORUPI("Skorupi"),
    SKRELP("Skrelp"),
    SKUNTANK("Skuntank"),
    SLAKING("Slaking"),
    SLAKOTH("Slakoth"),
    SLIGGOO("Sliggoo"),
    SLOWBRO("Slowbro"),
    SLOWBRO_MEGA("Slowbro-Mega"),
    SLOWKING("Slowking"),
    SLOWPOKE("Slowpoke"),
    SLUGMA("Slugma"),
    SLURPUFF("Slurpuff"),
    SMEARGLE("Smeargle"),
    SMOOCHUM("Smoochum", Gender.FEMALE),
    SNEASEL("Sneasel"),
    SNIVY("Snivy"),
    SNORLAX("Snorlax"),
    SNORUNT("Snorunt"),
    SNOVER("Snover"),
    SNUBBULL("Snubbull"),
    SOLGALEO("Solgaleo", Gender.GENDERLESS),
    SOLOSIS("Solosis"),
    SOLROCK("Solrock", Gender.GENDERLESS),
    SPEAROW("Spearow"),
    SPEWPA("Spewpa"),
    SPHEAL("Spheal"),
    SPINARAK("Spinarak"),
    SPINDA("Spinda"),
    SPIRITOMB("Spiritomb"),
    SPOINK("Spoink"),
    SPRITZEE("Spritzee"),
    SQUIRTLE("Squirtle"),
    STAKATAKA("Stakataka", Gender.GENDERLESS),
    STANTLER("Stantler"),
    STARAPTOR("Staraptor"),
    STARAVIA("Staravia"),
    STARLY("Starly"),
    STARMIE("Starmie", Gender.GENDERLESS),
    STARYU("Staryu", Gender.GENDERLESS),
    STEELIX("Steelix"),
    STEELIX_MEGA("Steelix-Mega"),
    STEENEE("Steenee", Gender.FEMALE),
    STOUTLAND("Stoutland"),
    STUFFUL("Stufful"),
    STUNFISK("Stunfisk"),
    STUNKY("Stunky"),
    SUDOWOODO("Sudowoodo"),
    SUICUNE("Suicune", Gender.GENDERLESS),
    SUNFLORA("Sunflora"),
    SUNKERN("Sunkern"),
    SURSKIT("Surskit"),
    SWABLU("Swablu"),
    SWADLOON("Swadloon"),
    SWALOT("Swalot"),
    SWAMPERT("Swampert"),
    SWAMPERT_MEGA("Swampert-Mega"),
    SWANNA("Swanna"),
    SWELLOW("Swellow"),
    SWINUB("Swinub"),
    SWIRLIX("Swirlix"),
    SWOOBAT("Swoobat"),
    SYLVEON("Sylveon"),
    TAILLOW("Taillow"),
    TALONFLAME("Talonflame"),
    TANGELA("Tangela"),
    TANGROWTH("Tangrowth"),
    TAPU_BULU("Tapu Bulu", Gender.GENDERLESS),
    TAPU_FINI("Tapu Fini", Gender.GENDERLESS),
    TAPU_KOKO("Tapu Koko", Gender.GENDERLESS),
    TAPU_LELE("Tapu Lele", Gender.GENDERLESS),
    TAUROS("Tauros", Gender.MALE),
    TEDDIURSA("Teddiursa"),
    TENTACOOL("Tentacool"),
    TENTACRUEL("Tentacruel"),
    TEPIG("Tepig"),
    TERRAKION("Terrakion", Gender.GENDERLESS),
    THROH("Throh", Gender.MALE),
    THUNDURUS("Thundurus", Gender.MALE),
    THUNDURUS_THERIAN("Thundurus-Therian", Gender.MALE),
    TIMBURR("Timburr"),
    TIRTOUGA("Tirtouga"),
    TOGEDEMARU("Togedemaru"),
    TOGEKISS("Togekiss"),
    TOGEPI("Togepi"),
    TOGETIC("Togetic"),
    TORCHIC("Torchic"),
    TORKOAL("Torkoal"),
    TORNADUS("Tornadus", Gender.MALE),
    TORNADUS_THERIAN("Tornadus-Therian", Gender.MALE),
    TORRACAT("Torracat"),
    TORTERRA("Torterra"),
    TOTODILE("Totodile"),
    TOUCANNON("Toucannon"),
    TOXAPEX("Toxapex"),
    TOXICROAK("Toxicroak"),
    TRANQUILL("Tranquill"),
    TRAPINCH("Trapinch"),
    TREECKO("Treecko"),
    TREVENANT("Trevenant"),
    TROPIUS("Tropius"),
    TRUBBISH("Trubbish"),
    TRUMBEAK("Trumbeak"),
    TSAREENA("Tsareena", Gender.FEMALE),
    TURTONATOR("Turtonator"),
    TURTWIG("Turtwig"),
    TYMPOLE("Tympole"),
    TYNAMO("Tynamo"),
    TYPE_NULL("Type: Null", Gender.GENDERLESS),
    TYPHLOSION("Typhlosion"),
    TYRANITAR("Tyranitar"),
    TYRANITAR_MEGA("Tyranitar-Mega"),
    TYRANTRUM("Tyrantrum"),
    TYROGUE("Tyrogue", Gender.MALE),
    TYRUNT("Tyrunt"),
    UMBREON("Umbreon"),
    UNFEZANT("Unfezant"),
    UNOWN("Unown", Gender.GENDERLESS),
    URSARING("Ursaring"),
    UXIE("Uxie", Gender.GENDERLESS),
    VANILLISH("Vanillish"),
    VANILLITE("Vanillite"),
    VANILLUXE("Vanilluxe"),
    VAPOREON("Vaporeon"),
    VENIPEDE("Venipede"),
    VENOMOTH("Venomoth"),
    VENONAT("Venonat"),
    VENUSAUR("Venusaur"),
    VENUSAUR_MEGA("Venusaur-Mega"),
    VESPIQUEN("Vespiquen", Gender.FEMALE),
    VIBRAVA("Vibrava"),
    VICTINI("Victini", Gender.GENDERLESS),
    VICTREEBEL("Victreebel"),
    VIGOROTH("Vigoroth"),
    VIKAVOLT("Vikavolt"),
    VILEPLUME("Vileplume"),
    VIRIZION("Virizion", Gender.GENDERLESS),
    VIVILLON("Vivillon"),
    VOLBEAT("Volbeat", Gender.MALE),
    VOLCANION("Volcanion", Gender.GENDERLESS),
    VOLCARONA("Volcarona"),
    VOLTORB("Voltorb", Gender.GENDERLESS),
    VULLABY("Vullaby", Gender.FEMALE),
    VULPIX("Vulpix"),
    VULPIX_ALOLA("Vulpix-Alola"),
    WAILMER("Wailmer"),
    WAILORD("Wailord"),
    WALREIN("Walrein"),
    WARTORTLE("Wartortle"),
    WATCHOG("Watchog"),
    WEAVILE("Weavile"),
    WEEDLE("Weedle"),
    WEEPINBELL("Weepinbell"),
    WEEZING("Weezing"),
    WHIMSICOTT("Whimsicott"),
    WHIRLIPEDE("Whirlipede"),
    WHISCASH("Whiscash"),
    WHISMUR("Whismur"),
    WIGGLYTUFF("Wigglytuff"),
    WIMPOD("Wimpod"),
    WINGULL("Wingull"),
    WISHIWASHI("Wishiwashi"),
    WISHIWASHI_SCHOOL("Wishiwashi-School"),
    WOBBUFFET("Wobbuffet"),
    WOOBAT("Woobat"),
    WOOPER("Wooper"),
    WORMADAM("Wormadam", Gender.FEMALE),
    WORMADAM_SANDY("Wormadam-Sandy", Gender.FEMALE),
    WORMADAM_TRASH("Wormadam-Trash", Gender.FEMALE),
    WURMPLE("Wurmple"),
    WYNAUT("Wynaut"),
    XATU("Xatu"),
    XERNEAS("Xerneas", Gender.GENDERLESS),
    XURKITREE("Xurkitree", Gender.GENDERLESS),
    YAMASK("Yamask"),
    YANMA("Yanma"),
    YANMEGA("Yanmega"),
    YUNGOOS("Yungoos"),
    YVELTAL("Yveltal", Gender.GENDERLESS),
    ZANGOOSE("Zangoose"),
    ZAPDOS("Zapdos", Gender.GENDERLESS),
    ZEBSTRIKA("Zebstrika"),
    ZEKROM("Zekrom", Gender.GENDERLESS),
    ZERAORA("Zeraora", Gender.GENDERLESS),
    ZIGZAGOON("Zigzagoon"),
    ZOROARK("Zoroark"),
    ZORUA("Zorua"),
    ZUBAT("Zubat"),
    ZWEILOUS("Zweilous"),
    ZYGARDE("Zygarde", Gender.GENDERLESS),
    ZYGARDE_10("Zygarde-10%", Gender.GENDERLESS),
    ZYGARDE_COMPLETE("Zygarde-Complete", Gender.GENDERLESS);

    static {
        JSONArray pokemons = null;
        try {
            pokemons = SmogonParser.getPokemons();
        } catch (IOException e) {
            BanterBot4J.LOGGER.error("Error parsing pokemons", e);
            System.exit(1);
        }

        for (int i = 0; i < pokemons.length(); i++) {
            JSONObject jsonPokemon = pokemons.getJSONObject(i);
            String name = jsonPokemon.getString("name");

            JSONArray jsonGenerations = jsonPokemon.getJSONArray("genfamily");
            List<Generation> genFamilies = new ArrayList<>();
            for (int k = 0; k < jsonGenerations.length(); k++) {
                Generation generation = Generation.getGeneration(jsonGenerations.getString(k));
                genFamilies.add(generation);
            }

            JSONArray alts = jsonPokemon.getJSONArray("alts");
            for (int j = 0; j < alts.length(); j++) {
                JSONObject alt = alts.getJSONObject(j);
                String pokemonName = name;
                String suffix = alt.getString("suffix");
                if (!suffix.isEmpty()) {
                    pokemonName = pokemonName.concat("-" + suffix);
                }
                Species pokemon = getPokemon(pokemonName);

                int hp = alt.getInt("hp");
                int attack = alt.getInt("atk");
                int defense = alt.getInt("def");
                int specialAttack = alt.getInt("spa");
                int specialDefense = alt.getInt("spd");
                int speed = alt.getInt("spe");
                double weight = alt.getDouble("weight");
                double height = alt.getDouble("height");

                JSONArray jsonAbilities = alt.getJSONArray("abilities");
                for (int k = 0; k < jsonAbilities.length(); k++) {
                    Abilities ability = Abilities.getAbility(jsonAbilities.getString(k));
                    pokemon.addAbilities(ability);
                }

                JSONArray jsonTypes = alt.getJSONArray("types");
                for (int k = 0; k < jsonTypes.length(); k++) {
                    Type type = Type.getType(jsonTypes.getString(k));
                    pokemon.addTypes(type);
                }

                JSONArray jsonTiers = alt.getJSONArray("formats");
                for (int k = 0; k < jsonTiers.length(); k++) {
                    Tier tier = Tier.getTier(jsonTiers.getString(k));
                    pokemon.addTiers(tier);
                }

                pokemon
                        .addGenerations(genFamilies)
                        .addStats(PermanentStat.HP, hp)
                        .addStats(PermanentStat.ATTACK, attack)
                        .addStats(PermanentStat.DEFENSE, defense)
                        .addStats(PermanentStat.SPECIAL_ATTACK, specialAttack)
                        .addStats(PermanentStat.SPECIAL_DEFENSE, specialDefense)
                        .addStats(PermanentStat.SPEED, speed)
                        .setWeight(weight)
                        .setHeight(height);
            }
        }
    }

    @Nonnull
    public final String NAME;

    @Nonnull
    private final List<Generation> GENERATIONS = new ArrayList<>();

    @Nonnull
    private final List<Abilities> ABILITIES = new ArrayList<>();

    @Nonnull
    private final List<Type> TYPES = new ArrayList<>();

    @Nonnull
    private final List<Tier> TIERS = new ArrayList<>();

    @Nonnull
    private final Map<PermanentStat, Integer> STATS = new HashMap<>();

    @Nonnull
    private final List<Species> EVOLUTIONS = new ArrayList<>();

    private Double WEIGHT = null;
    private Double HEIGHT = null;
    private String SUFFIX = null;

    @Nonnull
    private final List<Gender> VALID_GENDERS = new ArrayList<>();

    Species(@Nonnull String name, @Nonnull Gender... genders) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;

        if (genders.length == 0) {
            throw new IllegalArgumentException("No genders set for Pokemon " + name);
        }

        Collections.addAll(VALID_GENDERS, genders);
    }

    Species(@Nonnull String name) {
        this(name, Gender.MALE, Gender.FEMALE);
    }

    @Nonnull
    public static Species getPokemon(@Nonnull String pokemon) {
        pokemon = pokemon.toLowerCase();

        if (!Holder.MAP.containsKey(pokemon)) {
            throw new NullPointerException("Pokemon " + pokemon + " doesn't exist");
        }

        return Holder.MAP.get(pokemon);
    }

    public static boolean isArceus(@Nonnull Pokemon pokemon) {
        return pokemon.getName().contains("Arceus");
    }

    public static boolean isSilvally(@Nonnull Pokemon pokemon) {
        return pokemon.getName().contains("Silvally");
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    @Nonnull
    public List<Generation> getGenerations() {
        return new ArrayList<>(GENERATIONS);
    }

    @Nonnull
    private Species addGenerations(@Nonnull Generation... generations) {
        Collections.addAll(GENERATIONS, generations);
        return this;
    }

    @Nonnull
    private Species addGenerations(@Nonnull Collection<Generation> generations) {
        GENERATIONS.addAll(generations);
        return this;
    }

    @Nonnull
    private Species setGenerations(@Nonnull Generation... generations) {
        GENERATIONS.clear();
        return addGenerations(generations);
    }

    @Nonnull
    public List<Abilities> getAbilities() {
        return new ArrayList<>(ABILITIES);
    }

    @Nonnull
    private Species addAbilities(@Nonnull Abilities... abilities) {
        Collections.addAll(ABILITIES, abilities);
        return this;
    }

    @Nonnull
    private Species setAbilities(@Nonnull Abilities... abilities) {
        ABILITIES.clear();
        return addAbilities(abilities);
    }

    @Nonnull
    public List<Type> getTypes() {
        return new ArrayList<>(TYPES);
    }

    @Nonnull
    private Species addTypes(@Nonnull Type... types) {
        Collections.addAll(TYPES, types);
        return this;
    }

    @Nonnull
    private Species setTypes(@Nonnull Type... types) {
        TYPES.clear();
        return this;
    }

    @Nonnull
    public List<Tier> getTiers() {
        return TIERS;
    }

    @Nonnull
    private Species addTiers(@Nonnull Tier... tiers) {
        Collections.addAll(TIERS, tiers);
        return this;
    }

    @Nonnull
    private Species setTiers(@Nonnull Tier... tiers) {
        TIERS.clear();
        return this;
    }

    @Nonnull
    public Map<PermanentStat, Integer> getStats() {
        return STATS;
    }

    @Nonnull
    private Species addStats(@Nonnull PermanentStat stat, int amount) {
        STATS.put(stat, amount);
        return this;
    }

    @Nonnull
    public List<Species> getEvolutions() {
        return EVOLUTIONS;
    }

    @Nonnull
    private Species addEvolutions(@Nonnull Species... evolutions) {
        Collections.addAll(EVOLUTIONS, evolutions);
        return this;
    }

    @Nonnull
    private Species setEvolutions(@Nonnull Species... evolutions) {
        EVOLUTIONS.clear();
        return addEvolutions(evolutions);
    }

    @Nonnull
    public Double getWeight() {
        return WEIGHT;
    }

    @Nonnull
    private Species setWeight(double weight) {
        WEIGHT = weight;
        return this;
    }

    @Nonnull
    public Double getHeight() {
        return HEIGHT;
    }

    @Nonnull
    private Species setHeight(double height) {
        HEIGHT = height;
        return this;
    }

    @Nonnull
    public String getSuffix() {
        return SUFFIX;
    }

    private void setSuffix(@Nonnull String suffix) {
        SUFFIX = suffix;
    }

    @Nonnull
    public List<Gender> getValidGenders() {
        return new ArrayList<>(VALID_GENDERS);
    }

    private static class Holder {
        @Nonnull
        static Map<String, Species> MAP = new HashMap<>();
    }

}
