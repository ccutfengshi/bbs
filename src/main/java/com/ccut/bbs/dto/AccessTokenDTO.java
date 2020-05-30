package com.ccut.bbs.dto;

import lombok.Data;

/**
 * 授权accesstoken的对象
 */

@Data
public class AccessTokenDTO {
    //需要五个参数，详见 https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
