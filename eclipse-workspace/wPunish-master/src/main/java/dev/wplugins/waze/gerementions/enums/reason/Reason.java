package dev.wplugins.waze.gerementions.enums.reason;

import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.enums.punish.PunishType;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum Reason {
    AMEACA("Ameaça", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoAmeaca")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("AmeacaTempoType")).toMillis(Main.getInstance().getConfig().getInt("AmeacaTempoBan"))),
    ANTIJOGOGAME("Anti jogo (Jogo)", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoAntiJogo")),  TimeUnit.valueOf(Main.getInstance().getConfig().getString("AntiJogoTempoType")).toMillis(Main.getInstance().getConfig().getInt("AntiJogo-Game-TempoBan"))),
    DISCRIMINACAO("Atitude de discriminação", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDiscrininacao")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("DiscriminacaoTempoType")).toMillis(Main.getInstance().getConfig().getInt("DiscriminacaoTempoMute"))),
    COMERCIO("Comércio", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoComercio")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("ComercioTempoType")).toMillis(Main.getInstance().getConfig().getInt("ComercioTempoBan"))),
    CONSTRUCAO_INADEQUADA("Construção inadequada", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoBuildInadequada")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("BuildInadequadaTempoType")).toMillis(Main.getInstance().getConfig().getInt("BuildInadequadaTempoBan"))),
    DESINFORMACAO("Desinformação", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDesinformacao")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("DesinformacaoTempoType")).toMillis(Main.getInstance().getConfig().getInt("DesinformacaoTempoBan"))),
    DIVULGACAO_SIMPLES("Divulgação Simples", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDivulgacaoSimples")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("DivulgacaoSimplesTempoType")).toMillis(Main.getInstance().getConfig().getInt("DivulgacaoSimplesTempoMute"))),
    DIVULGACAO_GRAVE("Divulgação Grave", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoDivulgacaoGrave")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("DivulgacaoGraveTempoType")).toMillis(Main.getInstance().getConfig().getInt("DivulgacaoGraveTempoBan"))),
    FLOOD("Spam ou Flood", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoSpam")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("SpamTempoType")).toMillis(Main.getInstance().getConfig().getInt("SpamTempoMute"))),
    HACK("Hack", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoHack")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("HackTempoType")).toMillis(Main.getInstance().getConfig().getInt("HackTempoBan"))),
    CONTA_FAKE("Conta Fake", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoContaFake")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("ContaFakeTempoType")).toMillis(Main.getInstance().getConfig().getInt("ContaFakeTempoBan"))),
    DIRETI("Violação das Diretrizes", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoViolacaoDiretrizes")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("ViolacaoDasDiretrizesTempoType")).toMillis(Main.getInstance().getConfig().getInt("ViolacaoDasDiretrizesTempoBan"))),

    ESTORNO_DE_PAGAMENTO("Estorno de pagamento", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoEstorno")),  TimeUnit.valueOf(Main.getInstance().getConfig().getString("EstornoTempoType")).toMillis(Main.getInstance().getConfig().getInt("EstornoTempoBan"))),
    CONTA_ALTERNATIVA("Conta alternativa", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoContaAlternativa")),TimeUnit.valueOf(Main.getInstance().getConfig().getString("ContaAltTempoType")).toMillis(Main.getInstance().getConfig().getInt("ContaAltTempoBan"))),
    NICKNAMEINADEQUADO("Nickname inapropriado", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoNickOfensivo")),TimeUnit.valueOf(Main.getInstance().getConfig().getString("NickOfensivoTempoType")).toMillis( Main.getInstance().getConfig().getInt("NickOfensivoTempoBan"))),
    OFENSA_JOGADOR("Ofensa a jogador", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoOfensaAPlayer")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("OfensaAPlayerTempoType")).toMillis(Main.getInstance().getConfig().getInt("OfensaaPlayerTempoMute"))),
    OFENSA_STAFF("Ofensa a staff/servidor", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoOfensaAStaff")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("OfensaAStaffTempoType")).toMillis(Main.getInstance().getConfig().getInt("OfensaaStaffTempoMute"))),
    CROSS_TEAMING("Time ou aliança", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoTime")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("TimeTempoType")).toMillis(Main.getInstance().getConfig().getInt("TimeTempoBan"))),
    ABUSODEBUGS("Abuso de bugs", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoAbusodeBug")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("Abuso-de-bugs-TempoType")).toMillis(Main.getInstance().getConfig().getInt("Abuso-de-bug-TempoBan"))),
    SKININAPROPRIADA("Skin inapropriada", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoSkinInadequada")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("SkinImpropriaTempoType")).toMillis(Main.getInstance().getConfig().getInt("SkinImpropria-TempoBan"))),
    INCENTIVARFLOOD("Incentivar flood", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoIncentivoFlood")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("IncentivoFloodTempoType")).toMillis(Main.getInstance().getConfig().getInt("IncentivoFlood-TempoMute"))),
    CONVERSAEXPLICITA("Conversa explícita", PunishType.valueOf(Main.getInstance().getConfig().getString("PunicaoConversaExplicita")), TimeUnit.valueOf(Main.getInstance().getConfig().getString("ConversaExplicitaTempoType")).toMillis(Main.getInstance().getConfig().getInt("ConversaExplicita-TempoMute")));

    private final String text;
    private final PunishType punishType;
    private final long time;

    Reason(String text, PunishType punishType, long time) {
        this.text = text;
        this.punishType = punishType;
        this.time = time;
    }
}
