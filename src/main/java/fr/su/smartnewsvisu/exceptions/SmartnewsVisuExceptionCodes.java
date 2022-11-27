package fr.su.smartnewsvisu.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmartnewsVisuExceptionCodes {
    STORAGE_OBJET_NON_TROUVE("error.storage.objet.non.trouve");

    private final String code;

}
