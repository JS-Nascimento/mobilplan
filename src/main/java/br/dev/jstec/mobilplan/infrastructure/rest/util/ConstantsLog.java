package br.dev.jstec.mobilplan.infrastructure.rest.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ConstantsLog {

    public static final String MSG_REQUESTEXCEPTION = "ServiceName: {0}. HttpStatus: {1}. ResponseBody: {2}";
}
