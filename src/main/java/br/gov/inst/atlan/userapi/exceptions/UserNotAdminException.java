package br.gov.inst.atlan.userapi.exceptions;

public class UserNotAdminException extends  RuntimeException{

    public UserNotAdminException(String message) {
        super(message);
    }
}
