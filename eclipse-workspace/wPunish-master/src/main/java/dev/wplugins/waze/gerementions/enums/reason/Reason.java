package dev.wplugins.waze.gerementions.enums.reason;

import dev.wplugins.waze.gerementions.enums.punish.PunishType;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum Reason {

    AMEACA("Ameaça", PunishType.TEMPBAN, TimeUnit.DAYS.toMillis(7)),
    ANTIJOGOGAME("Anti jogo (Jogo)", PunishType.TEMPBAN,  TimeUnit.HOURS.toMillis(7)),
    DISCRIMINACAO("Atitude de discriminação", PunishType.TEMPMUTE, TimeUnit.DAYS.toMillis(7)),
    COMERCIO("Comércio", PunishType.TEMPMUTE, TimeUnit.DAYS.toMillis(3)),
    CONSTRUCAO_INADEQUADA("Construção inadequada", PunishType.TEMPBAN, TimeUnit.DAYS.toMillis(3)),
    DESINFORMACAO("Desinformação", PunishType.TEMPBAN, TimeUnit.DAYS.toMillis(3)),
    DIVULGACAO_SIMPLES("Divulgação Simples", PunishType.TEMPMUTE, TimeUnit.DAYS.toMillis(1)),
    DIVULGACAO_GRAVE("Divulgação Grave", PunishType.BAN, 0),
    FLOOD("Spam ou Flood", PunishType.TEMPMUTE, TimeUnit.HOURS.toMillis(1)),
    HACK("Hack", PunishType.BAN, 0),
    CONTA_FAKE("Conta Fake", PunishType.BAN, 0),
    DIRETI("Violação das Diretrizes", PunishType.BAN, 0),

    ESTORNO_DE_PAGAMENTO("Estorno de pagamento", PunishType.BAN, 0),
    CONTA_ALTERNATIVA("Conta alternativa", PunishType.BAN, 0),
    NICKNAMEINADEQUADO("Nickname inapropriado", PunishType.BAN, 0),
    OFENSA_JOGADOR("Ofensa a jogador", PunishType.TEMPMUTE, TimeUnit.HOURS.toMillis(3)),
    OFENSA_STAFF("Ofensa a staff/servidor", PunishType.TEMPMUTE, TimeUnit.DAYS.toMillis(5)),
    CROSS_TEAMING("Time ou aliança", PunishType.TEMPBAN, TimeUnit.DAYS.toMillis(7)),
    ABUSODEBUGS("Abuso de bugs", PunishType.TEMPBAN, TimeUnit.DAYS.toMillis(15)),
    SKININAPROPRIADA("Skin inapropriada", PunishType.TEMPBAN, TimeUnit.DAYS.toMillis(1)),
    INCENTIVARFLOOD("Incentivar flood", PunishType.TEMPMUTE, TimeUnit.HOURS.toMillis(3)),
    CONVERSAEXPLICITA("Conversa explícita", PunishType.TEMPMUTE, TimeUnit.DAYS.toMillis(3));

    private final String text;
    private final PunishType punishType;
    private final long time;

    Reason(String text, PunishType punishType, long time) {
        this.text = text;
        this.punishType = punishType;
        this.time = time;
    }
}
