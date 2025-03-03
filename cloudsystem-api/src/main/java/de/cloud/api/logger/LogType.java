package de.cloud.api.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogType {

    SUCCESS("§aSUCCESS§7"),
    INFO("§bINFO§7"),
    ERROR("§cERROR§7"),
    WARNING("§6WARNING§7"),
    SCREEN("§1SCREEN§7"),
    EMPTY("");

    private final String textField;

}
