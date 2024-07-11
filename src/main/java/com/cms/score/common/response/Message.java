package com.cms.score.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {

    //Proccess
    SUCESSFULLY_DEFAULT(200, "Proses Berhasil"),
    NOT_FOUND_DEFAULT(200, "Data tidak ditemukan"),
    FAILED_DEFAULT(400, "Proses Gagal"),

    //Auth
    SUCCESSFULLY_LOGIN(200, "Login Berhasil"),
    SUCCESSFULLY_LOGOUT(200, "Logout Berhasil"),
    FAILED_LOGOUT(404, "Logout Gagal"),
    FAILED_LOGIN(404, "Login Gagal"),

    EXCEPTION_NOT_FOUND(404, "Data tidak ditemukan"),
    EXCEPTION_ALREADY_EXIST(302, "Data sudah ada"),
    EXCEPTION_BAD_REQUEST(400, "Request tidak sesuai"),
    EXCEPTION_INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int statusCode;
    private final String message;
    
}
