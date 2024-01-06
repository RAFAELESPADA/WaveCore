package dev.wplugins.waze.gerementions.enums.reason;

import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.enums.punish.PunishType;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum Reason {

    AMEACA("Ameaça", PunishType.valueOf("PunicaoAmeaca"), TimeUnit.valueOf("AmeacaTempoType").toMillis(Main.getInstance().getConfig().getInt("AmeacaTempoBan"))),
    ANTIJOGOGAME("Anti jogo (Jogo)", PunishType.valueOf("PunicaoAntiJogo"),  TimeUnit.valueOf("AntiJogoTempoType").toMillis(Main.getInstance().getConfig().getInt("AntiJogo-Game-TempoBan"))),
    DISCRIMINACAO("Atitude de discriminação", PunishType.valueOf("PunicaoDiscrininacao"), TimeUnit.valueOf("DiscriminacaoTempoType").toMillis(Main.getInstance().getConfig().getInt("DiscriminacaoTempoMute"))),
    COMERCIO("Comércio", PunishType.valueOf("PunicaoComercio"), TimeUnit.valueOf("ComercioTempoType").toMillis(Main.getInstance().getConfig().getInt("ComercioTempoBan"))),
    CONSTRUCAO_INADEQUADA("Construção inadequada", PunishType.valueOf("PunicaoBuildInadequada"), TimeUnit.valueOf("BuildInadequadaTempoType").toMillis(Main.getInstance().getConfig().getInt("BuildInadequadaTempoBan"))),
    DESINFORMACAO("Desinformação", PunishType.valueOf("PunicaoDesinformacao"), TimeUnit.valueOf("DesinformacaoTempoType").toMillis(Main.getInstance().getConfig().getInt("DesinformacaoTempoBan"))),
    DIVULGACAO_SIMPLES("Divulgação Simples", PunishType.valueOf("PunicaoDivulgacaoSimples"), TimeUnit.valueOf("DivulgacaoSimplesTempoType").toMillis(Main.getInstance().getConfig().getInt("DivulgacaoSimplesTempoMute"))),
    DIVULGACAO_GRAVE("Divulgação Grave", PunishType.valueOf("PunicaoDivulgacaoGrave"), Main.getInstance().getConfig().getInt("DivulgacaoGraveTempoBan")),
    FLOOD("Spam ou Flood", PunishType.valueOf("PunicaoSpam"), TimeUnit.valueOf("SpamTempoType").toMillis(Main.getInstance().getConfig().getInt("SpamTempoMute"))),
    HACK("Hack", PunishType.valueOf("PunicaoHack"), Main.getInstance().getConfig().getInt("HackTempoBan")),
    CONTA_FAKE("Conta Fake", PunishType.valueOf("PunicaoContaFake"), Main.getInstance().getConfig().getInt("ContaFakeTempoBan")),
    DIRETI("Violação das Diretrizes", PunishType.valueOf("PunicaoViolacaoDiretrizes"), Main.getInstance().getConfig().getInt("ViolacaoDasDiretrizesTempoBan")),

    ESTORNO_DE_PAGAMENTO("Estorno de pagamento", PunishType.valueOf("PunicaoEstorno"), Main.getInstance().getConfig().getInt("EstornoTempoBan")),
    CONTA_ALTERNATIVA("Conta alternativa", PunishType.valueOf("PunicaoContaAlternativa"), Main.getInstance().getConfig().getInt("ContaAltTempoBan")),
    NICKNAMEINADEQUADO("Nickname inapropriado", PunishType.valueOf("PunicaoNickOfensivo"), Main.getInstance().getConfig().getInt("NickOfensivoTempoBan")),
    OFENSA_JOGADOR("Ofensa a jogador", PunishType.valueOf("PunicaoOfensaAPlayer"), TimeUnit.valueOf("OfensaAPlayerTempoType").toMillis(Main.getInstance().getConfig().getInt("OfensaaPlayerTempoMute"))),
    OFENSA_STAFF("Ofensa a staff/servidor", PunishType.valueOf("PunicaoOfensaAStaff"), TimeUnit.valueOf("OfensaAStaffTempoType").toMillis(Main.getInstance().getConfig().getInt("OfensaaStaffTempoMute"))),
    CROSS_TEAMING("Time ou aliança", PunishType.valueOf("PunicaoTime"), TimeUnit.valueOf("TimeTempoType").toMillis(Main.getInstance().getConfig().getInt("TimeTempoBan"))),
    ABUSODEBUGS("Abuso de bugs", PunishType.valueOf("PunicaoAbusodeBug"), TimeUnit.valueOf("Abuso-de-bugs-TempoType").toMillis(Main.getInstance().getConfig().getInt("Abuso-de-bug-TempoBan"))),
    SKININAPROPRIADA("Skin inapropriada", PunishType.valueOf("PunicaoSkinInadequada"), TimeUnit.valueOf("SkinImpropriaTempoType").toMillis(Main.getInstance().getConfig().getInt("SkinImpropria-TempoBan"))),
    INCENTIVARFLOOD("Incentivar flood", PunishType.valueOf("PunicaoIncentivoFlood"), TimeUnit.valueOf("IncentivoFloodTempoType").toMillis(Main.getInstance().getConfig().getInt("IncentivoFlood-TempoMute"))),
    CONVERSAEXPLICITA("Conversa explícita", PunishType.valueOf("PunicaoConversaExplicita"), TimeUnit.valueOf("ConversaExplicitaTempoType").toMillis(Main.getInstance().getConfig().getInt("ConversaExplicita-TempoMute")));

    private final String text;
    private final PunishType punishType;
    private final long time;

    Reason(String text, PunishType punishType, long time) {
        this.text = text;
        this.punishType = punishType;
        this.time = time;
    }
}
