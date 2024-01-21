package dev.wplugins.waze.gerementions.enums.reason;

import dev.wplugins.waze.gerementions.BukkitMain;
import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.database.MySQLDatabase;
import dev.wplugins.waze.gerementions.database.PluginInstance;
import dev.wplugins.waze.gerementions.enums.punish.PunishType;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum Reason {
    AMEACA("Ameaça", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoAmeaca")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoAmeaca")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("AmeacaTempoType")).toMillis(Main.getInstance().getConfig().getInt("AmeacaTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("AmeacaTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("AmeacaTempoBan"))),
    ANTIJOGO("Anti jogo (Jogo)", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoAntiJogo")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoAntiJogo")) , MySQLDatabase.instancia == PluginInstance.BUNGEECORD ?  TimeUnit.valueOf(Main.getInstance().getConfig().getString("AntiJogoTempoType")).toMillis(Main.getInstance().getConfig().getInt("AntiJogo-Game-TempoBan")): TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("AntiJogoTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("AntiJogo-Game-TempoBan"))),
    DISCRIMINACAO("Atitude de discriminação", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDiscrininacao")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoDiscrininacao")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ?  TimeUnit.valueOf(Main.getInstance().getConfig().getString("DiscriminacaoTempoType")).toMillis(Main.getInstance().getConfig().getInt("DiscriminacaoTempoMute")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("DiscriminacaoTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("DiscriminacaoTempoMute"))),
    COMERCIO("Comércio", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoComercio")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoComercio")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("ComercioTempoType")).toMillis(Main.getInstance().getConfig().getInt("ComercioTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("ComercioTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("ComercioTempoBan"))),
    CONSTRUCAO_INADEQUADA("Construção inadequada", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoBuildInadequada")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoBuildInadequada")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("BuildInadequadaTempoType")).toMillis(Main.getInstance().getConfig().getInt("BuildInadequadaTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("BuildInadequadaTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("BuildInadequadaTempoBan"))),
    DESINFORMACAO("Desinformação", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDesinformacao")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoDesinformacao")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("DesinformacaoTempoType")).toMillis(Main.getInstance().getConfig().getInt("DesinformacaoTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("DesinformacaoTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("DesinformacaoTempoBan"))),
    DIVULGACAO_SIMPLES("Divulgação Simples", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDivulgacaoSimples")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoDivulgacaoSimples")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("DivulgacaoSimplesTempoType")).toMillis(Main.getInstance().getConfig().getInt("DivulgacaoSimplesTempoMute")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("DivulgacaoSimplesTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("DivulgacaoSimplesTempoMute"))),
    DIVULGACAO_GRAVE("Divulgação Grave", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDivulgacaoGrave")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoDivulgacaoGrave")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("DivulgacaoGraveTempoType")).toMillis(Main.getInstance().getConfig().getInt("DivulgacaoGraveTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("DivulgacaoGraveTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("DivulgacaoGraveTempoBan"))),
    FLOOD("Spam ou Flood", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoSpam")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoSpam")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("SpamTempoType")).toMillis(Main.getInstance().getConfig().getInt("SpamTempoMute")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("SpamTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("SpamTempoMute"))),
    HACK("Hack", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoHack")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoHack")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("HackTempoType")).toMillis(Main.getInstance().getConfig().getInt("HackTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("HackTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("HackTempoBan"))),
    CONTA_FAKE("Conta Fake", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoContaFake")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoContaFake")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("ContaFakeTempoType")).toMillis(Main.getInstance().getConfig().getInt("ContaFakeTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("ContaFakeTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("ContaFakeTempoBan"))),
    VIOLACAO_DAS_DIRETRIZES("Violação das Diretrizes", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoViolacaoDiretrizes")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoViolacaoDiretrizes")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ?  TimeUnit.valueOf(Main.getInstance().getConfig().getString("ViolacaoDasDiretrizesTempoType")).toMillis(Main.getInstance().getConfig().getInt("ViolacaoDasDiretrizesTempoBan")) :  TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("ViolacaoDasDiretrizesTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("ViolacaoDasDiretrizesTempoBan"))),

    ESTORNO_DE_PAGAMENTO("Estorno de pagamento", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoEstorno")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoEstorno")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ?  TimeUnit.valueOf(Main.getInstance().getConfig().getString("EstornoTempoType")).toMillis(Main.getInstance().getConfig().getInt("EstornoTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("EstornoTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("EstornoTempoBan"))),
    CONTA_ALTERNATIVA("Conta alternativa", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoContaAlternativa")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoContaAlternativa")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("ContaAltTempoType")).toMillis(Main.getInstance().getConfig().getInt("ContaAltTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("ContaAltTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("ContaAltTempoBan"))),
    NICKNAMEINADEQUADO("Nickname inapropriado", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoNickOfensivo")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoNickOfensivo")),MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("NickOfensivoTempoType")).toMillis( Main.getInstance().getConfig().getInt("NickOfensivoTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("NickOfensivoTempoType")).toMillis( BukkitMain.getInstance().getConfig().getInt("NickOfensivoTempoBan"))),
    OFENSA_A_JOGADOR("Ofensa a jogador", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoOfensaAPlayer")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoOfensaAPlayer")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("OfensaAPlayerTempoType")).toMillis(Main.getInstance().getConfig().getInt("OfensaaPlayerTempoMute")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("OfensaAPlayerTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("OfensaaPlayerTempoMute"))),
    OFENSA_A_STAFF("Ofensa a staff/servidor", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoOfensaAStaff")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoOfensaAStaff")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("OfensaAStaffTempoType")).toMillis(Main.getInstance().getConfig().getInt("OfensaaStaffTempoMute")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("OfensaAStaffTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("OfensaaStaffTempoMute"))),
    CROSS_TEAMING("Time ou aliança", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoTime")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoTime")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ?  TimeUnit.valueOf(Main.getInstance().getConfig().getString("TimeTempoType")).toMillis(Main.getInstance().getConfig().getInt("TimeTempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("TimeTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("TimeTempoBan"))),
    ABUSODEBUGS("Abuso de bugs", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoAbusodeBug")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoAbusodeBug")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("Abuso-de-bugs-TempoType")).toMillis(Main.getInstance().getConfig().getInt("Abuso-de-bug-TempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("Abuso-de-bugs-TempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("Abuso-de-bug-TempoBan"))),
    FREEKILL("FreeKill", PunishType.TEMPBAN, TimeUnit.DAYS.toMillis(7)),
    DOXXING("Doxxing", PunishType.BAN, 0),
    SKININAPROPRIADA("Skin inapropriada", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoSkinInadequada")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoSkinInadequada")),  MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("SkinImpropriaTempoType")).toMillis(Main.getInstance().getConfig().getInt("SkinImpropria-TempoBan")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("SkinImpropriaTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("SkinImpropria-TempoBan"))),
    INCENTIVARFLOOD("Incentivar flood", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoIncentivoFlood")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoIncentivoFlood")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("IncentivoFloodTempoType")).toMillis(Main.getInstance().getConfig().getInt("IncentivoFlood-TempoMute")) :  TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("IncentivoFloodTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("IncentivoFlood-TempoMute"))),
    CONVERSAEXPLICITA("Conversa explícita", MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoConversaExplicita")) : PunishType.valueOf(BukkitMain.getInstance().getConfig().getString("PunicaoConversaExplicita")), MySQLDatabase.instancia == PluginInstance.BUNGEECORD ? TimeUnit.valueOf(Main.getInstance().getConfig().getString("ConversaExplicitaTempoType")).toMillis(Main.getInstance().getConfig().getInt("ConversaExplicita-TempoMute")) : TimeUnit.valueOf(BukkitMain.getInstance().getConfig().getString("ConversaExplicitaTempoType")).toMillis(BukkitMain.getInstance().getConfig().getInt("ConversaExplicita-TempoMute")));

    private final String text;
    private final PunishType punishType;
    private final long time;

    Reason(String text, PunishType punishType, long time) {
        this.text = text;
        this.punishType = punishType;
        this.time = time;
    }
}
