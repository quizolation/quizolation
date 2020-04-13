package com.gavinfenton.quizolation.helper;

import java.util.Arrays;

public class EndpointHelper {

    public static String insertIds(String path, Long... ids) {
        return Arrays.stream(ids)
                .map(Object::toString)
                .reduce(path, (filledPath, id) -> filledPath.replaceFirst("\\{\\w+}", id));
    }

}
