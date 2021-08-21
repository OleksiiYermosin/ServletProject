package ua.training.model.utils;

public interface RegularExpressions {

    String NAME_REGEX = "[A-Z][a-z]+|[А-ЯЁІЇ][а-яёії']+$";

    String SURNAME_REGEX = "[A-Z][a-z]+|[А-ЯЁІЇ][а-яёії']+$";

    String NICK_REGEX = "^[A-Za-z][A-Za-z0-9_-]{3,25}$";

    String PHONE_REGEX = "\\+?[0-9]{12}|[0-9]{10}";

}
