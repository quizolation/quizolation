package com.gavinfenton.quizolation.helper;

public class EndpointHelper {

    public static String insertId(String path, Long id) {
        return path.replaceFirst("\\{\\w+}", id.toString());
    }

}
